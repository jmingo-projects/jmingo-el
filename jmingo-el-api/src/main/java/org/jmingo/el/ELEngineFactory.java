/**
 * Copyright 2013-2014 The JMingo Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmingo.el;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import org.jmingo.el.api.ELEngine;
import org.jmingo.el.exception.ELException;

import java.io.IOException;
import java.util.Set;

/**
 * Creates instance of an {@link ELEngine} implementation that exists in an application classpath.
 */
public final class ELEngineFactory {
    // base package to search for ELEngine implementations
    public static final String BASE_EL_PACKAGE = "org.jmingo.el";
    //set of classes that should be skipped
    public static final Set<String> CLASS_EXCLUDE = Sets.newHashSet(ELEngine.class.getName());

    // private constructor prevents instantiation
    private ELEngineFactory() {
    }

    /**
     * Creates an instance of an {@link ELEngine} implementation that exists in an application classpath.
     *
     * @param packageName the package name to search {@link ELEngine} implementations
     * @return new instance of an {@link ELEngine} implementation or null if in there are no {@link ELEngine}
     * implementations in an application classpath
     * @throws ELException in a case of any errors
     */
    public static ELEngine getElEngine(String packageName) throws ELException {
        return getElEngineFromPackage(packageName);
    }

    /**
     * Creates an instance of an {@link ELEngine} implementation that exists in an application classpath.
     *
     * @return new instance of an {@link ELEngine} implementation or null if in there are no {@link ELEngine}
     * implementations in an application classpath
     * @throws ELException in a case of any errors
     */
    public static ELEngine getElEngine() throws ELException {
        return getElEngineFromPackage(BASE_EL_PACKAGE);
    }

    private static ELEngine getElEngineFromPackage(String packageName) throws ELException {
        ELEngine elEngine;
        Class<?> elClass = null;
        Set<Class<?>> candidates = Sets.newHashSet();
        try {
            Set<ClassPath.ClassInfo> classes = ClassPath.from(ELEngine.class.getClassLoader())
                    .getTopLevelClassesRecursive(packageName);
            classes.forEach(classInfo -> {
                try {
                    if (!CLASS_EXCLUDE.contains(classInfo.getName())) {
                        Class<?> clazz = Class.forName(classInfo.getName());
                        if (ELEngine.class.isAssignableFrom(clazz)) {
                            candidates.add(clazz);
                        }
                    }
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    throw new ELException(e);
                }

            });
            if (candidates.size() == 0) {
                return null;
            }
            if (candidates.size() > 1) {
                throw new ELException("ELEngine implementations conflict: there are multiple ELEngine implementations in the application classpath, but must be only one. " +
                        Iterables.transform(candidates, Class::getName));
            }

            elClass = candidates.iterator().next();
            return (ELEngine) elClass.newInstance();

        } catch (IOException e) {
            throw new ELException("failed to read classes from the package: " + packageName, e);
        } catch (InstantiationException e) {
            throw new ELException("failed to create instance of " + elClass, e);
        } catch (IllegalAccessException e) {
            throw new ELException("Security error", e);
        }
    }
}

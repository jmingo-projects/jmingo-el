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
package com.jmingo.el.api;

import java.util.Map;

/**
 * Base interface declares methods to evaluate a expression language.
 */
public interface ELEngine {

    /**
     * Evaluates the expression with the specified parameters.
     *
     * @param expression expression
     * @param parameters parameters
     * @return the result of this evaluation
     */
    boolean evaluate(String expression, Map<String, Object> parameters);

}

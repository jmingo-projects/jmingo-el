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
package com.jmingo.el.springel;

import com.jmingo.el.api.ELEngine;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * This implementation is based on Spring EL engine.
 */
public class SpringELEngine implements ELEngine {

    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean evaluate(String expression, Map<String, Object> parameters) {
        Validate.notBlank(expression, "expression cannot be empty");
        Expression exp = parser.parseExpression(expression);
        EvaluationContext context = new StandardEvaluationContext();
        if (MapUtils.isNotEmpty(parameters)) {
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                context.setVariable(parameter.getKey(), parameter.getValue());
            }
        }
        return exp.getValue(context, Boolean.class);
    }

}
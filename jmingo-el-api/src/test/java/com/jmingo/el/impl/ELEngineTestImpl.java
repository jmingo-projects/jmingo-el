package com.jmingo.el.impl;

import com.jmingo.el.api.ELEngine;

import java.util.Map;

/**
 * Test implementation of ELEngine for tests.
 */
public class ELEngineTestImpl implements ELEngine {
    @Override
    public boolean evaluate(String expression, Map<String, Object> parameters) {
        return false;
    }
}

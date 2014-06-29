package com.jmingo.el;

import com.jmingo.el.api.ELEngine;
import com.jmingo.el.impl.ELEngineTestImpl;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ELEngineFactoryTest {

    @Test
    public void testGetElEngine() {
        ELEngine elEngine = ELEngineFactory.getElEngine();
        assertNotNull(elEngine);
        assertTrue(elEngine instanceof ELEngineTestImpl);
    }
}

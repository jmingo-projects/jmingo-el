package org.jmingo.el;

import org.jmingo.el.api.ELEngine;
import org.jmingo.el.impl.ELEngineTestImpl;
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

package org.robotgame.gui.GameControll.Entities;

import org.junit.jupiter.api.Test;
import org.robotgame.controller.entities.Base;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BaseTest {
    private Base base;

    @BeforeEach
    void setUp() {
        base = new Base();
    }

    @Test
    void testBuildBase() {
        double positionX = 10.5;
        double positionY = 20.5;
        base.buildBase(positionX, positionY);

        assertEquals((int) positionX, base.getPositionX());
        assertEquals((int) positionY, base.getPositionY());
        assertEquals(100, base.getHealthPoint());
        assertTrue(base.getBaseBuilt());
    }

    @Test
    void testBurnResources_OutOfRange() {
        base.buildBase(0, 0);
        base.burnResources();

        assertEquals(99, base.getHealthPoint());
    }

    @Test
    void testTakeResources_InRange() {
        base.buildBase(0, 0);
        base.takeResources(10);

        assertEquals(100, base.getHealthPoint());
    }
}
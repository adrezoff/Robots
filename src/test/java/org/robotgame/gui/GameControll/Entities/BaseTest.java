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
    void testTakePositionRobot_OutOfRange() {
        base.buildBase(0, 0);
        double positionRobotX = 50.0;
        double positionRobotY = 50.0;
        base.takePositionRobot(positionRobotX, positionRobotY);

        assertEquals(95, base.getHealthPoint());
    }

    @Test
    void testTakePositionRobot_InRange() {
        base.buildBase(0, 0);
        double positionRobotX = 5.0;
        double positionRobotY = 5.0;
        base.takePositionRobot(positionRobotX, positionRobotY);

        assertEquals(100, base.getHealthPoint());
    }
}
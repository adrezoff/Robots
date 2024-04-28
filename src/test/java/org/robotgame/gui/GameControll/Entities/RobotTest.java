package org.robotgame.gui.GameControll.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.robotgame.controller.entities.Robot;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {
    private Robot robot;

    @BeforeEach
    void setUp() {
        robot = new Robot(0, 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals(0, robot.getPositionX());
        assertEquals(0, robot.getPositionY());
        assertEquals(0, robot.getDirection());
        assertEquals(0.07, robot.getMaxVelocity());
        assertEquals(0.01, robot.getMaxAngularVelocity());
    }

    @Test
    void testSetVelocity_PositiveValue() {
        robot.setVelocity(0.05);
        assertEquals(0.05, robot.getVelocity());
    }

    @Test
    void testSetVelocity_NegativeValue() {
        robot.setVelocity(-0.05);
        assertEquals(0, robot.getVelocity());
    }

    @Test
    void testSetPositionX() {
        robot.setPositionX(10);
        assertEquals(10, robot.getPositionX());
    }

    @Test
    void testSetPositionY() {
        robot.setPositionY(20);
        assertEquals(20, robot.getPositionY());
    }

    @Test
    void testSetDirection() {
        robot.setDirection(Math.PI / 2);
        assertEquals(Math.PI / 2, robot.getDirection());
    }
}

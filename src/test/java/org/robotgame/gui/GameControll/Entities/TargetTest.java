package org.robotgame.gui.GameControll.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.robotgame.controller.entities.Target;

import static org.junit.jupiter.api.Assertions.*;

public class TargetTest {
    private Target target;

    @BeforeEach
    void setUp() {
        target = new Target(0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals(0, target.getPositionX());
        assertEquals(0, target.getPositionY());
    }

    @Test
    void testSetPositionX() {
        target.setPositionX(10);
        assertEquals(10, target.getPositionX());
    }

    @Test
    void testSetPositionY() {
        target.setPositionY(20);
        assertEquals(20, target.getPositionY());
    }
}

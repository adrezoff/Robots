package org.robotgame.gui.GameControll.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.robotgame.controller.entities.Base;
import org.robotgame.controller.entities.Enemies;

import static org.junit.jupiter.api.Assertions.*;

class EnemiesTest {

    private Enemies enemy;
    private Base base = new Base();

    @BeforeEach
    void setUp() {
        enemy = new Enemies(50.0, 50.0, 0.0, 100.0);
        base.buildBase(60, 60);
    }

    @Test
    void testInitialization() {
        assertEquals(50.0, enemy.getPositionX());
        assertEquals(50.0, enemy.getPositionY());
        assertEquals(0.0, enemy.getDirection());
    }

    @Test
    void testSetPosition() {
        enemy.setPositionX(40.0);
        enemy.setPositionY(40.0);
        assertEquals(40.0, enemy.getPositionX());
        assertEquals(40.0, enemy.getPositionY());
    }

    @Test
    void testSetDirection() {
        enemy.setDirection(90.0);
        assertEquals(90.0, enemy.getDirection());
    }

    @Test
    void testSetVelocity() {
        enemy.setVelocity(0.02);
        assertEquals(0.02, enemy.getVelocity());
        enemy.setVelocity(-0.01);
        assertEquals(0.0, enemy.getVelocity());
        enemy.setVelocity(0.05);
        assertEquals(0.05, enemy.getVelocity());
    }

    @Test
    void testNextId() {
        int initialId = enemy.getIdImage();
        enemy.nextId();
        assertEquals((initialId + 1) % 10, enemy.getIdImage());
    }

    @Test
    void testSetAction() {
        enemy.setAction(base);
        assertEquals("extracts", enemy.getAction());
        enemy.setVelocity(1);
        enemy.setPositionX(1000.0);
        enemy.setAction(base);
        assertEquals("movesLeft", enemy.getAction());
        enemy.setPositionX(-1000.0);
        enemy.setAction(base);
        assertEquals("movesRight", enemy.getAction());
    }

    @Test
    void testAttackBase() {
        double initialHealth = 100;
        base.setHealthPoint(initialHealth);
        enemy.attackBase(base);
        assertEquals(initialHealth - enemy.getDamage(), base.getHealthPoint());
    }

    @Test
    void testDistanceCalculation() {
        enemy.setPositionX(10.0);
        enemy.setPositionY(10.0);
        base.setPositionX(13);
        base.setPositionY(14);
        double distance = Math.sqrt(Math.pow(10.0 - 13.0, 2) + Math.pow(10.0 - 14.0, 2));
        double calculatedDistance = enemy.distance(10.0, 10.0, 13.0, 14.0);
        assertEquals(distance, calculatedDistance);
    }
}


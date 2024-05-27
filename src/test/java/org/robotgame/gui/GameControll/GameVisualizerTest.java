package org.robotgame.gui.GameControll;

import org.junit.jupiter.api.Test;
import org.robotgame.controller.GameVisualizer;
import org.robotgame.controller.entities.Robot;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.robotgame.controller.entities.*;

import java.util.ArrayList;

public class GameVisualizerTest {

    private GameVisualizer gameVisualizer;
    private int width = 800;
    private int height = 600;

    @BeforeEach
    public void setUp() {
        gameVisualizer = new GameVisualizer(width, height);
    }

    @Test
    public void testSetTargetPosition() {
        int x = 400;
        int y = 300;
        gameVisualizer.setTargetPosition(x, y);
        Point targetPoint = gameVisualizer.getTargetPoint();
        assertEquals(x, targetPoint.x);
        assertEquals(y, targetPoint.y);
    }

    @Test
    public void testRobotMovementTowardsTarget() {
        gameVisualizer.setTargetPosition(400, 300);
        gameVisualizer.onModelUpdateEvent();
        Robot robot = gameVisualizer.getRobot();
        assertNotEquals(100, robot.getPositionX());
        assertNotEquals(100, robot.getPositionY());
    }

    @Test
    public void testRobotFillsTank() {
        Robot robot = gameVisualizer.getRobot();
        robot.setTank(10);
        gameVisualizer.onModelUpdateEvent();
        assertTrue(robot.getTank() > 0);
    }

    @Test
    public void testBaseBuilding() {
        Robot robot = gameVisualizer.getRobot();
        robot.fillTank(200);
        gameVisualizer.setTargetPosition(200, 200);
        robot.setPositionX(200);
        robot.setPositionY(200);
        robot.giveResource(100);
        Base base = gameVisualizer.getBase();
        base.buildBase(200, 200);
        assertTrue(base.getBaseBuilt());
    }

    @Test
    public void testResourceGeneration() {
        Resources resources = gameVisualizer.getResources();
        int initialResourceCount = resources.getResources().size();
        gameVisualizer.onModelUpdateEvent();
        assertTrue(resources.getResources().size() >= initialResourceCount);
    }

    @Test
    public void testEnemiesMovement() {
        gameVisualizer.spawnEnemies();
        ArrayList<Enemies> enemies = gameVisualizer.getEnemies();
        Enemies enemy = enemies.get(0);
        double initialX = enemy.getPositionX();
        double initialY = enemy.getPositionY();
        gameVisualizer.onModelUpdateEvent();
        assertNotEquals(initialX, enemy.getPositionX());
        assertNotEquals(initialY, enemy.getPositionY());
    }

    @Test
    public void testBaseHealth() {
        Base base = gameVisualizer.getBase();
        Enemies enemy = new Enemies(100, 100, 0, 100);
        gameVisualizer.getEnemies().add(enemy);
        enemy.setPositionX(base.getPositionX());
        enemy.setPositionY(base.getPositionY());
        gameVisualizer.onModelUpdateEvent();
        assertTrue(base.getHealthPoint() < 100);
    }
}

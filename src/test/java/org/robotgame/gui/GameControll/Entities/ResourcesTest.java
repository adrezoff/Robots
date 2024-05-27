package org.robotgame.gui.GameControll.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.robotgame.controller.entities.Resources;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesTest {
    private Resources resources;

    @BeforeEach
    void setUp() {
        resources = new Resources();
    }

    @Test
    void testInitialization() {
        ArrayList<ArrayList<Integer>> initialResources = resources.getResources();
        assertNotNull(initialResources);
        assertEquals(10, initialResources.size());

        for (ArrayList<Integer> resource : initialResources) {
            assertEquals(3, resource.size());
            assertTrue(resource.get(0) >= 0 && resource.get(0) < 1500);
            assertTrue(resource.get(1) >= 0 && resource.get(1) < 1500);
            assertEquals(100, resource.get(2));
        }
    }

    @Test
    void testAddNewResource() {
        int initialSize = resources.getResources().size();
        resources.addNewResource();
        assertEquals(initialSize + 1, resources.getResources().size());
    }

    @Test
    void testGiveResourceSuccessful() {
        ArrayList<Integer> firstResource = resources.getResources().get(0);
        double robotX = firstResource.get(0) + 50;
        double robotY = firstResource.get(1) + 25;

        int result = resources.giveResource(robotX, robotY);
        assertEquals(1, result);
        assertEquals(99, firstResource.get(2));
    }

    @Test
    void testGiveResourceFail() {
        double robotX = 2000;
        double robotY = 2000;

        int result = resources.giveResource(robotX, robotY);
        assertEquals(0, result);

        for (ArrayList<Integer> resource : resources.getResources()) {
            assertEquals(100, resource.get(2));
        }
    }

    @Test
    void testGiveResourceEmpty() {
        ArrayList<Integer> firstResource = resources.getResources().get(0);
        double robotX = firstResource.get(0) + 50;
        double robotY = firstResource.get(1) + 25;

        for (int i = 0; i < 100; i++) {
            resources.giveResource(robotX, robotY);
        }

        assertEquals(0, firstResource.get(2));

        int result = resources.giveResource(robotX, robotY);
        assertEquals(0, result);
    }
}

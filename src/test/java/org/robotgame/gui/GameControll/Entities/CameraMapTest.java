package org.robotgame.gui.GameControll.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.robotgame.controller.entities.CameraMap;

import static org.junit.jupiter.api.Assertions.*;

public class CameraMapTest {
    private CameraMap cameraMap;

    @BeforeEach
    void setUp() {
        cameraMap = new CameraMap(0, 0, 800, 600, 2000, 1500);
    }

    @Test
    void testConstructor() {
        assertEquals(0, cameraMap.getX());
        assertEquals(0, cameraMap.getY());
        assertEquals(800, cameraMap.getScreenWidth());
        assertEquals(600, cameraMap.getScreenHeight());
        assertEquals(2000, cameraMap.getMapSizeX());
        assertEquals(1500, cameraMap.getMapSizeY());
    }

    @Test
    void testSetX() {
        cameraMap.setX(100);
        assertEquals(100, cameraMap.getX());
    }

    @Test
    void testSetY() {
        cameraMap.setY(200);
        assertEquals(200, cameraMap.getY());
    }

    @Test
    void testSetMapSizeX() {
        cameraMap.setMapSizeX(3000);
        assertEquals(3000, cameraMap.getMapSizeX());
    }

    @Test
    void testSetMapSizeY() {
        cameraMap.setMapSizeY(2500);
        assertEquals(2500, cameraMap.getMapSizeY());
    }

    @Test
    void testSetScreenSize() {
        cameraMap.setScreenSize(1024, 768);
        assertEquals(1024, cameraMap.getScreenWidth());
        assertEquals(768, cameraMap.getScreenHeight());
    }

    @Test
    void testUpdateCameraMapPosition() {
        cameraMap.updateCameraMapPosition(50, 50);
        assertEquals(0, cameraMap.getX());
        assertEquals(0, cameraMap.getY());

        cameraMap.updateCameraMapPosition(1500, 900);
        assertEquals(1100, cameraMap.getX());
        assertEquals(600, cameraMap.getY());

        cameraMap.updateCameraMapPosition(2000, 1500);
        assertEquals(1200, cameraMap.getX());
        assertEquals(900, cameraMap.getY());
    }
}


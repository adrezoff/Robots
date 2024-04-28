package org.robotgame.controller.entities;

public class CameraMap {
    private double x;
    private double y;
    private double screenWidth;
    private double screenHeight;
    private double mapSizeX;
    private double mapSizeY;

    public CameraMap(double startX, double startY, double screenWidth, double screenHeight,
                     double mapSizeX, double mapSizeY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        updateCameraMapPosition(startX, startY);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setMapSizeX(double mapSizeX) {
        this.mapSizeX = mapSizeX;
    }

    public double getMapSizeX() {
        return mapSizeX;
    }

    public void setMapSizeY(double mapSizeY) {
        this.mapSizeY = mapSizeY;
    }

    public double getMapSizeY() {
        return mapSizeY;
    }

    public void setScreenSize(double screenWidth, double screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void updateCameraMapPosition(double robotX, double robotY) {
       x = applyLimits(robotX - this.screenWidth/2, 0, mapSizeX - this.screenWidth);
       y = applyLimits(robotY - this.screenHeight/2, 0, mapSizeY - this.screenHeight);
    }
    private double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }
}

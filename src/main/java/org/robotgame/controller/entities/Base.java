package org.robotgame.controller.entities;

public class Base {
    private int positionX;
    private int positionY;
    private double healthPoint;
    private boolean baseBuilt = false;
    public void buildBase(double positionX, double positionY) {
        healthPoint = 0;
        this.positionX = (int) positionX;
        this.positionY = (int) positionY;
        baseBuilt = true;
    }
    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }

    public double getHealthPoint() {
        return healthPoint;
    }
    public boolean getBaseBuilt(){return baseBuilt;}

    public void takeResources(int resources){
        healthPoint = applyLimits(healthPoint + (double) resources / 5, 0, 100);
    }
    public void takeDamage(double point){healthPoint = applyLimits(healthPoint - point, 0, 100);}
    private double applyLimits(double value, int min, int max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
}

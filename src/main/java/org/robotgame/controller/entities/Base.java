package org.robotgame.controller.entities;

public class Base {
    private int positionX;
    private int positionY;
    private int healthPoint;
    private boolean baseBuilt = false;
    public void buildBase(double positionX, double positionY) {
        healthPoint = 100;
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

    public int getHealthPoint() {
        return healthPoint;
    }
    public boolean getBaseBuilt(){return baseBuilt;}

    public void takePositionRobot(double positionRobotX, double positionRobotY){
        if (Math.abs(positionX - positionRobotX) > 25 | Math.abs(positionY - positionRobotY) > 25){
            healthPoint = applyLimits(healthPoint - 5,0,100);
        }
        else{
            healthPoint = applyLimits(healthPoint + 25, 0, 100);
        }
    }

    private int applyLimits(int value, int min, int max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
}

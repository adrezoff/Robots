package org.robotgame.controller.entities;

public class Robot {
    private double positionX;
    private double positionY;
    private double direction;
    private double velocity = 0;

    private double maxVelocity = 0.07;
    private double maxAngularVelocity = 0.01;
    private int tank = 0;

    public Robot(double positionX, double positionY, double direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
    }
    public Robot(double positionX, double positionY, double direction, double maxVelocity, double maxAngularVelocity) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.maxVelocity = maxVelocity;
        this.maxAngularVelocity = maxAngularVelocity;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getMaxAngularVelocity() {
        return maxAngularVelocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setVelocity(double newVelocity) {

        if (newVelocity > 0){
            this.velocity = newVelocity;
        } else {
            this.velocity = 0;
        }

    }

    public int giveResource(){
        int res = Math.min(tank,10);
        tank = Math.max(tank-10,0);
        return res;
    }

    public void fillTank(int resources){
        tank = tank+resources;
    }

    public double getVelocity() {
        return velocity;
    }
    public int getTank(){
        return tank;
    }
}

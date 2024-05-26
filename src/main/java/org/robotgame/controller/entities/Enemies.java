package org.robotgame.controller.entities;

import java.util.Objects;


public class Enemies {
    private int idImage = 0;
    private String action = "standing";
    private double positionX;
    private double positionY;
    private double direction;
    private double velocity = 0;

    private double maxVelocity = 0.07;
    private double maxAngularVelocity = 0.01;

    private double damage = 0.01;

    public Enemies(double positionX, double positionY, double direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
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
        if (newVelocity > 0) {
            this.velocity = newVelocity;
        } else {
            this.velocity = 0;
        }
    }

    public double getVelocity() {
        return velocity;
    }

    public int getIdImage() {
        return idImage;
    }

    public void nextId() {
        if (!(Objects.equals(action, "standing") && idImage == 9)) {
            idImage = (idImage + 1) % 10;
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(Base base) {
        double distanceToBase = distance(positionX, positionY, base.getPositionX(), base.getPositionY());

        if (distanceToBase < 25) {
            if (!Objects.equals(action, "extracts")) {
                action = "extracts";
                idImage = 0;
            }
        } else if (velocity == 1) {
            if ((base.getPositionX() < positionX) && (!Objects.equals(action, "movesLeft"))) {
                action = "movesLeft";
                idImage = 0;
            } else if ((base.getPositionX() >= positionX) && !Objects.equals(action, "movesRight")) {
                action = "movesRight";
                idImage = 0;
            }
        } else {
            if (!Objects.equals(action, "standing")) {
                action = "standing";
                idImage = 0;
            }
        }
    }

    private double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public void attackBase(Base base) {
        base.takeDamage(damage);
    }
}

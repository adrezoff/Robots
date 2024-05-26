package org.robotgame.controller.entities;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;

public class Robot {
    private int idImage = 0;
    private String action = "standing";
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

    public int giveResource(int quantity){
        int res = Math.min(tank,quantity);
        tank = Math.max(tank-quantity,0);
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
    public int getIdImage(){
        return idImage;
    }
    public void nextId(){
        if (!(Objects.equals(action, "standing") && idImage ==9)) {
            idImage = (idImage + 1) % 10;
        }
    }
    public String getAction(){
        return action;
    }
    public void setAction(Resources resources, double targetX){
        if (velocity == 1 ){
            if ((targetX < positionX) && (!Objects.equals(action, "movesLeft"))){
                action = "movesLeft";
                idImage = 0;
            }
            else if((targetX >= positionX) &&!Objects.equals(action, "movesRight")){
                action = "movesRight";
                idImage = 0;
            }
        }
        else {
            boolean flagRes = false;
            for (ArrayList<Integer> arrayResource : resources.getResources()) {
                if (abs(positionX - arrayResource.get(0) - 50) < 50 &&
                        abs(positionY - arrayResource.get(1) - 25) < 25) {
                    if (arrayResource.get(2) > 0) {
                        flagRes = true;
                        break;
                    }
                }
            }
            if (flagRes && !Objects.equals(action, "extracts") && tank < 200) {
                action = "extracts";
                idImage = 0;
            }
            if ((!flagRes | tank >= 200) && !Objects.equals(action, "standing")) {
                action = "standing";
                idImage = 0;
            }
        }
    }
}

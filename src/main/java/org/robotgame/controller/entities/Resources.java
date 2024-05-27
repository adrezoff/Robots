package org.robotgame.controller.entities;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class Resources {
    private final int quantityResources = 10;
    private final ArrayList<ArrayList<Integer>> arrayResources = new ArrayList<>();

    public Resources() {
        for (int i = 0; i < quantityResources; i++) {
            addNewResource();
        }
    }

    public void addNewResource() {
        ThreadLocalRandom randomGenerator = ThreadLocalRandom.current();
        ArrayList<Integer> newResource = new ArrayList<>();
        newResource.add(randomGenerator.nextInt(1500));
        newResource.add(randomGenerator.nextInt(1500));
        newResource.add(100);
        arrayResources.add(newResource);
    }

    public int giveResource(double positionRobotX, double positionRobotY) {
        for (ArrayList<Integer> arrayResource : arrayResources) {
            if (abs(positionRobotX - arrayResource.get(0) - 50) < 50 &&
                    abs(positionRobotY - arrayResource.get(1) - 25) < 25) {
                if (arrayResource.get(2) > 0) {
                    arrayResource.set(2, arrayResource.get(2) - 1);
                    return 1;
                }
            }
        }
        return 0;
    }


    public ArrayList<ArrayList<Integer>> getResources() {
        return arrayResources;
    }
}

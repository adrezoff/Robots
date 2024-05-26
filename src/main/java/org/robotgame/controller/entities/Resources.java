package org.robotgame.controller.entities;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class Resources{
    private final int quantityResources = 10;

    private final ArrayList<ArrayList<Integer>> arrayResources = new ArrayList<>();

    public Resources() {
        ThreadLocalRandom randomGenerator = ThreadLocalRandom.current();
        for(int i = 0; i < quantityResources; i++){
            ArrayList<Integer> currResource = new ArrayList<Integer>();
            currResource.add(randomGenerator.nextInt(1500));
            currResource.add(randomGenerator.nextInt(1500));
            currResource.add(100);
            arrayResources.add(currResource);
        }
    }

    public int giveResource(double positionRobotX, double positionRobotY){
        for (ArrayList<Integer> arrayResource : arrayResources) {
            if (abs(positionRobotX-arrayResource.get(0)-50)<50 &&
                    abs(positionRobotY-arrayResource.get(1)-25)<25){
                if (arrayResource.get(2)>0){
                    arrayResource.set(2,arrayResource.get(2)-1);
                    return 1;
                }
            }
        }
        return 0;
    }

    public ArrayList<ArrayList<Integer>> getResources(){
        return arrayResources;
    }

}

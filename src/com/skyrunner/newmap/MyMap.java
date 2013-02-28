package com.skyrunner.newmap;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.02.13
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class MyMap {
    private final int minTarget = 1;
    private final int maxTarget = 5;
    private final int fieldSize;
    private final Bitmap ground ;
    private final Bitmap tree ;
    private final Bitmap lake ;
    private final Bitmap target ;

    private BaseMapItem[][] map;
    private List<Target> targets;
    private List<Lake> lakes;
    private List<Tree> trees;


    public MyMap(int field_size, Bitmap ground, Bitmap tree, Bitmap lake, Bitmap target) {
        this.fieldSize = field_size;
        this.ground = ground;
        this.tree = tree;
        this.lake = lake;
        this.target = target;
        this.map = new BaseMapItem[fieldSize][fieldSize];

    }

    public void setParameters(int targetItems, int treeItems, int lakeItems){
        setTarget(targetItems);
        setTrees(treeItems);
        setLake(lakeItems);
        setGround();
    }

    private void setLake(int lakeItems) {
        //To change body of created methods use File | Settings | File Templates.
    }



    private void setTrees(int treeItems) {
        //To change body of created methods use File | Settings | File Templates.
    }

    //if it 2 then target field 2X2
    private void setTarget(int tCellsCount){
        if (tCellsCount < minTarget) tCellsCount = minTarget;
        if (tCellsCount > maxTarget) tCellsCount = maxTarget;
        Random random = new Random();
        int placeX = random.nextInt(fieldSize - 2*tCellsCount) + tCellsCount;
        int placeY = random.nextInt(fieldSize - 2*tCellsCount) + tCellsCount;
    }

    private void setGround(){
        for (int i = 0; i < fieldSize; i++)
            for (int j = 0; j < fieldSize; j++){
                if (map[i][j] == null)
                map[i][j] = new Ground(ground, new Point(i,j));
            }
    }
}

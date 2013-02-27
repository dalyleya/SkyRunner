package com.skyrunner.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import com.example.R;
import com.skyrunner.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: trid
 * Date: 27.02.13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class Game {
    PlayerCharacter character;

    private List <StaticObstacle> obstacles = new ArrayList<StaticObstacle>();
    private List <CyclicGameTimer> timers = new ArrayList<CyclicGameTimer>();

    private float screenWidth;
    private float screenHeight;
    private View view;

    private Bitmap obstacleBitmap;

    private int hits;

    public Game(float screenWidth, float screenHeight, View view) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.view = view;

        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.character);
        character = new com.skyrunner.PlayerCharacter(bitmap, (screenWidth - bitmap.getWidth())/2, (int) ((screenHeight - bitmap.getHeight())/5));
        obstacleBitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.obstacle);

        timers.add(new CyclicGameTimer(50, new ObstacleGenerator()));
        timers.add(new CyclicGameTimer(1000, new ObstacleCleanup()));
    }

    public PlayerCharacter getCharacter(){
        return character;
    }

    public void process(long delta){

        for (StaticObstacle obstacle: obstacles){
            obstacle.process(delta);
            detectCollision(obstacle, character);
        }

        for (CyclicGameTimer timer: timers){
            timer.process(delta);
        }
    }

    private void cleanOldObstacles() {
        Iterator iterator =  obstacles.iterator();
        while (iterator.hasNext()) {
            StaticObstacle obstacle = (StaticObstacle)iterator.next();
            if ( obstacle.getY() <= -obstacle.getScaledBitmap().getHeight() ){
                iterator.remove();
            }
        }
    }

    public List<StaticObstacle> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }

    private void detectCollision(StaticObstacle obstacle, PlayerCharacter character) {
        if (obstacle.isCollided()) return;

        float obstacleLeft = obstacle.getX();
        float obstacleRight = obstacleLeft + obstacle.getScaledBitmap().getWidth();
        float obstacleTop = obstacle.getY();
        float obstacleBottom = obstacleTop + obstacle.getScaledBitmap().getHeight();

        float characterLeft = character.getX();
        float characterRight = characterLeft + character.getBitmap().getWidth();
        float characterTop = character.getY();
        float characterBottom = characterTop + character.getBitmap().getHeight();

        if (obstacleLeft > characterRight) return;
        if (obstacleRight < characterLeft) return;
        if (obstacleTop > characterBottom) return;
        if (obstacleBottom < characterTop) return;

        obstacle.setCollided(true);

        hits++;
    }

    public int getHits() {
        return hits;
    }

    private class ObstacleGenerator implements Runnable {
        private Random random = new Random();

        @Override
        public void run() {
            if (random.nextInt(50) == 0){
                obstacles.add(new StaticObstacle(obstacleBitmap, random.nextInt((int)screenWidth + 1000) - 500, screenHeight));
            }
        }
    }

    private class ObstacleCleanup implements Runnable {
        @Override
        public void run() {
            cleanOldObstacles();
        }
    }
}

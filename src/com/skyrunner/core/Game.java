package com.skyrunner.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import com.example.R;
import com.skyrunner.*;

import java.util.ArrayList;
import java.util.List;

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

    private float screenWidth;
    private float screenHeight;
    private View view;

    public Game(float screenWidth, float screenHeight, View view) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.view = view;

        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.character);
        character = new com.skyrunner.PlayerCharacter(bitmap, (screenWidth - bitmap.getWidth())/2, (int) ((screenHeight - bitmap.getHeight())/5));
    }

    public PlayerCharacter getCharacter(){
        return character;
    }
}

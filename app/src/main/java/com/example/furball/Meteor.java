package com.example.furball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Meteor {
    Bitmap meteor[] = new Bitmap[4];
    int meteorFrame=0;
    int meteorX, meteorY, meteorVelocity;
    Random random;

    public Meteor(Context context) {
        meteor[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.m1);
        meteor[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.m2);
        meteor[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.m3);
        meteor[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.m2);

        random = new Random();
        resetPosition();
    }
    public Bitmap getMeteor(int meteorFrame){
        return meteor[meteorFrame];
    }

    public int getMeteorWidth(){
        return meteor[0].getWidth();
    }

    public int getMeteorHeight(){
        return meteor[0].getHeight();
    }

    public void resetPosition(){
        meteorX = random.nextInt(gameView.dWidth - getMeteorWidth());
        meteorY = -200 + random.nextInt(600) * -1;
        meteorVelocity =16 + random.nextInt(30);

    }

}
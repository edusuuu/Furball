package com.example.furball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Smoke {
    Bitmap smoke []= new Bitmap[3];
    int smokeFrame = 0;
    int smokeX, smokeY;

    public Smoke(Context context){
        smoke[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.smoke1);
        smoke[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.smoke2);
        smoke[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.smoke3);

    }

    public Bitmap getSmoke(int smokeFrame){
        return smoke[smokeFrame];

    }
}
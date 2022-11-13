package com.example.furball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.EditText;

import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Random;

public class gameView extends View {

    Bitmap background,ground,furball;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    String playername;
    int life = 3;
    static int dWidth,dHeight;
    Random random;
    float furX,furY;
    float oldX;
    float oldfurX;
    ArrayList<Meteor> meteors;
    ArrayList<Smoke> smokes;
    EditText name;

    public gameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(),R.drawable.bg1);
        ground = BitmapFactory.decodeResource(getResources(),R.drawable.ground);
        furball = BitmapFactory.decodeResource(getResources(),R.drawable.furball);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0,0,dWidth,dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(),dWidth,dHeight);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        // Health Points Color values, Character Positioning

        textPaint.setColor(Color.rgb(255,165,0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.kenney_blocks));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        furX = dWidth / 2 - furball.getWidth() / 2;
        furY = dHeight - ground.getHeight() - furball.getHeight();
        meteors = new ArrayList<>();
        smokes = new ArrayList<>();

        // formula kung ilang meteor ang mag aappear sa screem

        for (int i=0; i<5; i++){
        Meteor meteor = new Meteor(context);
        meteors.add(meteor);

        }
    }

    //placement of bitmaps, Mga picture nung mga icacall na resources dito at yung positioning ng character and meteorsV

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,null,rectBackground,null);
        canvas.drawBitmap(ground,null,rectGround,null);
        canvas.drawBitmap(furball,furX,furY,null);
        for (int i=0; i<meteors.size(); i++) {
            canvas.drawBitmap(meteors.get(i).getMeteor(meteors.get(i).meteorFrame),meteors.get(i).meteorX,meteors.get(i).meteorY,null);
            meteors.get(i).meteorFrame++;
            if (meteors.get(i).meteorFrame > 2) {
            meteors.get(i).meteorFrame = 0;

            }
            meteors.get(i).meteorY += meteors.get(i).meteorVelocity;

            //Pointing system(when meteors hit the ground)
            if (meteors.get(i).meteorY + meteors.get(i).getMeteorHeight() >= dHeight - ground.getHeight()){
                points += 1;

                //Smoke animation and trigger to spawn conditions
                Smoke smoke = new Smoke(context);
                smoke.smokeX = meteors.get(i).meteorX;
                smoke.smokeY = meteors.get(i).meteorY;
                smokes.add(smoke);
                meteors.get(i).resetPosition();


            }
        }
        // Conditions when furball was hit by meteors in different sides
        for (int i = 0; i < meteors.size(); i++) {
            if (meteors.get(i).meteorX + meteors.get(i).getMeteorWidth() >= furX
                    && meteors.get(i).meteorX <= furX + furball.getWidth()
                    && meteors.get(i).meteorY + meteors.get(i).getMeteorWidth() >= furY
                    && meteors.get(i).meteorY + meteors.get(i).getMeteorWidth() <= furY + furball.getHeight()) {
                        life --;
                        meteors.get(i).resetPosition();

                        // Conditions for game over, to open game over activity
                        if (life == 0){
                            Intent intent = new Intent(context, GameOver.class);
                            intent.putExtra("points",points);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }

            }

        }
        //smoke animation
        for (int i = 0; i<smokes.size(); i++) {
            canvas.drawBitmap(smokes.get(i).getSmoke(smokes.get(i).smokeFrame),smokes.get(i).smokeX,
                    smokes
                            .get(i).smokeY, null);
                    smokes.get(i).smokeFrame++;
                    //removal of smoke object from the interface
                    if (smokes.get(i).smokeFrame > 2) {
                        smokes.remove(smokes.get(i));

                    }

        }
        //colorization of health bar, from green to red
        if (life == 2) { healthPaint.setColor(Color.YELLOW);
        }
            else if (life == 1) {
                healthPaint.setColor(Color.RED);

        }
            //placement of health bar
        canvas.drawRect(dWidth-200,30,dWidth-200+60*life,80,healthPaint);
            //placement of points
        canvas.drawText("" + points, 20 , TEXT_SIZE, textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }
        //on touch movement of furball
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= furY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldfurX = furX;
            }
            //touch action preventing character to go out of bounds from the screen
            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newfurX = oldfurX - shift;
                if (newfurX <=0 )
                    furX = 0;
                else if (newfurX >= dWidth - furball.getWidth())
                    furX = dWidth - furball.getWidth();
                else
                    furX = newfurX;
            }
        }
        return true;
    }
}

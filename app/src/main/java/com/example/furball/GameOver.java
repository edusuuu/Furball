package com.example.furball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class GameOver extends AppCompatActivity {

    TextView high_points;
    TextView NewHighest;
    SharedPreferences sharedPreferences;
    ImageView trophy_highest;
    TextView tvName;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvName = findViewById(R.id.tvName);
        String playerName = Player.getName();

        //Set text view to player name
        tvName.setText(playerName);
        high_points = findViewById(R.id.high_points);
        NewHighest = findViewById(R.id.NewHighest);
        trophy_highest = findViewById(R.id.trophy_highest);
        int points = getIntent().getExtras().getInt("points");
        high_points.setText(""+ points);
        sharedPreferences = getSharedPreferences("my_pref", 0);
        int highest = sharedPreferences.getInt("highest", 0);

        //Update Highest Point
        if (points > highest) {
            trophy_highest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putInt("highest", highest);
            editor.commit();

        }

        NewHighest.setText("" + highest);
    }
    public void restart(View view) {
        gameView GameView = new gameView(this);
        setContentView(GameView);
    }

    public void exit(View view) {
        finish();
    }
}

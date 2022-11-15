package com.example.furball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public void StartGame(View view) {
        EditText inputBox = findViewById(R.id.name);

        boolean playerNameNotSet = isInputBoxEmpty(inputBox);

        //The Game View will not render if the player name is not set.
        if (playerNameNotSet) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            String playerName = inputBox.getText().toString();
            Player.setName(playerName);
            gameView GameView = new gameView(this);
            setContentView(GameView);
        }

    }

    private boolean isInputBoxEmpty(EditText inputBox) {
        return inputBox.getText().toString().trim().length() == 0;
    }
}

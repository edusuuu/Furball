package com.example.furball;

public class Player {
    private String _playerName;

    private static Player player_instance = null;

    private Player(String name) {
        this._playerName = name;
    }

    public static void setName(String name) {
        if (player_instance == null)
            player_instance = new Player(name);
    }

    public static String getName()
    {
        return player_instance._playerName;
    }
}

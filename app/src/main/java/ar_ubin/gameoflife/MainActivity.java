package ar_ubin.gameoflife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        GameWorld gameWorld = new GameWorld( this, 40, 60 );

        setContentView( gameWorld );
    }
}

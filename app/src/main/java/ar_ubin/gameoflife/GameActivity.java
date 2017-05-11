package ar_ubin.gameoflife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity
{
    private GameOfLife mGame;
    private GameWorld mGameWorld;
    private ImageView vPlayBtn;
    private GameView vGame;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game );

        mGameWorld = new GameWorld();
        mGame = new GameOfLife( mGameWorld );
        vGame = new GameView( this, 40, 60, mGame );

        final FrameLayout worldViewHolder = (FrameLayout) findViewById( R.id.contentFrame );
        worldViewHolder.addView( vGame );

        vPlayBtn = (ImageView) findViewById( R.id.btn_play );

        vPlayBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v ) {
                startOrPauseGame();
            }
        } );
    }

    private void startOrPauseGame() {
        if( mGame.isRunning() ) {
            pauseGame();
        } else {
            startGame();
        }
    }

    private void startGame() {
        mGame.run( vGame );
        vPlayBtn.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_pause ) );
    }

    private void pauseGame() {
        mGame.pause();
        vPlayBtn.setImageDrawable( getResources().getDrawable( R.drawable.ic_action_play ) );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        pauseGame();
        super.onPause();
    }
}

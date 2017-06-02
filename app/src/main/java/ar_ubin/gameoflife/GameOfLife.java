package ar_ubin.gameoflife;


import android.os.Handler;

class GameOfLife
{
    private final Handler myOffMainThreadHandler;
    private GameWorld mWorld;
    private boolean mIsRunning = true;

    public GameOfLife( GameWorld world ) {
        mWorld = world;
        myOffMainThreadHandler = new Handler();
    }

    public GameWorld getWorld() {
        return mWorld;
    }

    public GameWorld nextGeneration() {
        GameWorld nextWorld = mWorld.nextIteration();
        mWorld = nextWorld;

        return mWorld;
    }

    public void runOnce() {
        nextGeneration();
    }

    public void pause() {
        mIsRunning = false;
    }

    public void run( final GameView gameView ) {
        mIsRunning = true;

        Runnable runnableOffMain = new Runnable()
        {
            @Override
            public void run() {  // this thread is not on the main

                while( mIsRunning ) {

                    generationLifeSpan();

                    myOffMainThreadHandler.post( new Runnable()
                    {  // this is on the main thread

                        public void run() {
                            mWorld = nextGeneration();
                            gameView.nextGeneration( mWorld );
                        }
                    } );
                }
            }
        };

        new Thread( runnableOffMain ).start();
    }

    private void generationLifeSpan() {
        try {
            Thread.sleep( 100 );
        } catch( InterruptedException e ) {

            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return mIsRunning;
    }
}

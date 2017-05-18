package ar_ubin.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.Random;

public class GameView extends View
{
    private GameWorld mWorld;
    private final float WORLD_WIDTH, WORLD_HEIGHT;
    private float mColumns, mRows;
    private float mCellWidth, mCellHeight;

    private ScaleGestureDetector mScaleDetector;
    private final float MAX_SCALE_FACTOR = 3.0f;
    private final float DEFAULT_SCALE_FACTOR = 1.0f;
    private final float MIN_SCALE_FACTOR = 0.4f;

    private float mScaleFactor = DEFAULT_SCALE_FACTOR;

    private Paint mGridPaint = new Paint();
    private Paint mCellPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();

    public GameView( Context context, int width, int height, GameOfLife game ) {
        super( context );
        mBackgroundPaint.setColor( Color.WHITE );
        mGridPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        mCellPaint.setColor( getRandomColor() );

        WORLD_WIDTH = width;
        WORLD_HEIGHT = height;
        mColumns = WORLD_WIDTH;
        mRows = WORLD_HEIGHT;

        mWorld = game.getWorld();
        mScaleDetector = new ScaleGestureDetector( context, new ScaleListener() );
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
        super.onSizeChanged( w, h, oldw, oldh );
        calculateDimensions();
        invalidate();
    }

    private void calculateDimensions() {
        if( mColumns < 1 || mRows < 1 ) {
            return;
        }
        mColumns = WORLD_WIDTH / mScaleFactor;
        mRows = WORLD_HEIGHT / mScaleFactor;

        mCellWidth = getWidth() / mColumns;
        mCellHeight = getHeight() / mRows;
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        scale( canvas );
        canvas.drawPaint( mBackgroundPaint );
        canvas.restore();

        if( mColumns == 0 || mRows == 0 ) {
            return;
        }

        drawCells( canvas );
        drawGrid( canvas );
    }

    private void scale( Canvas canvas ) {
        canvas.save();
        canvas.scale( mScaleFactor, mScaleFactor );
    }

    private void drawCells( Canvas canvas ) {
        for( int i = 0; i < mColumns; i++ ) {
            for( int j = 0; j < mRows; j++ ) {
                if( mWorld.isAlive( new Cell( i, j ) ) ) {

                    canvas.drawRect( i * mCellWidth, j * mCellHeight, ( i + 1 ) * mCellWidth, ( j + 1 ) * mCellHeight,
                            mCellPaint );
                }
            }
        }
    }

    private void drawGrid( Canvas canvas ) {
        int width = getWidth();
        int height = getHeight();

        for( int i = 1; i < mColumns; i++ ) {
            canvas.drawLine( i * mCellWidth, 0, i * mCellWidth, height, mGridPaint );
        }

        for( int i = 1; i < mRows; i++ ) {
            canvas.drawLine( 0, i * mCellHeight, width, i * mCellHeight, mGridPaint );
        }
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        if( event.getAction() == MotionEvent.ACTION_MOVE ) {
            int column = (int) ( event.getX() / mCellWidth );
            int row = (int) ( event.getY() / mCellHeight );

            mWorld.addCell( new Cell( column, row ) );

            invalidate();
        }

        if( event.getAction() == MotionEvent.ACTION_DOWN ) {
            mCellPaint.setColor( getRandomColor() );
        }

        mScaleDetector.onTouchEvent( event );

        return true;
    }

    public void nextGeneration( GameWorld world ) {
        mWorld = world;
        invalidate();
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb( 255, rnd.nextInt( 256 ), rnd.nextInt( 256 ), rnd.nextInt( 256 ) );
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale( ScaleGestureDetector detector ) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max( MIN_SCALE_FACTOR, Math.min( mScaleFactor, MAX_SCALE_FACTOR ) );

            calculateDimensions();
            invalidate();
            return true;
        }
    }
}

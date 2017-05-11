package ar_ubin.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View
{
    private GameWorld mWorld;
    private int mColumns, mRows;
    private int mCellWidth, mCellHeight;

    private Paint mGridPaint = new Paint();
    private Paint mCellPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();

    public GameView( Context context, int width, int height, GameOfLife game ) {
        this( context, null );
        mColumns = width;
        mRows = height;
        mWorld = game.getWorld();
    }

    private GameView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        mBackgroundPaint.setColor( Color.WHITE );
        mGridPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        mCellPaint.setColor( getRandomColor() );
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
        super.onSizeChanged( w, h, oldw, oldh );
        calculateDimensions();
    }

    private void calculateDimensions() {
        if( mColumns < 1 || mRows < 1 ) {
            return;
        }

        mCellWidth = getWidth() / mColumns;
        mCellHeight = getHeight() / mRows;

        invalidate();
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        canvas.drawPaint( mBackgroundPaint );

        if( mColumns == 0 || mRows == 0 ) {
            return;
        }

        drawCells( canvas );
        drawGrid( canvas );
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

        return true;
    }

    public void setNextGeneration( GameWorld world ) {
        mWorld = world;
        invalidate();
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb( 255, rnd.nextInt( 256 ), rnd.nextInt( 256 ), rnd.nextInt( 256 ) );
    }
}

package ar_ubin.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameWorld extends View
{
    private int mColumns, mRows;
    private int mCellWidth, mCellHeight;
    private boolean[][] mVisitedCells;

    private Paint mGridPaint = new Paint();
    private Paint mCellPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();

    public GameWorld( Context context, int width, int height ) {
        this( context, null );
        mColumns = width;
        mRows = height;
    }

    private GameWorld( Context context, AttributeSet attrs ) {
        super( context, attrs );
        mBackgroundPaint.setColor( Color.BLACK );
        mGridPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        mCellPaint.setColor( Color.BLUE );
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

        mVisitedCells = new boolean[mColumns][mRows];

        invalidate();
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        canvas.drawPaint( mBackgroundPaint );

        if( mColumns == 0 || mRows == 0 ) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for( int i = 0; i < mColumns; i++ ) {
            for( int j = 0; j < mRows; j++ ) {
                if( mVisitedCells[i][j] ) {

                    canvas.drawRect( i * mCellWidth, j * mCellHeight, ( i + 1 ) * mCellWidth, ( j + 1 ) * mCellHeight,
                            mCellPaint );
                }
            }
        }

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

            mVisitedCells[column][row] = !mVisitedCells[column][row];
            invalidate();
        }

        return true;
    }
}

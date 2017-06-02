package ar_ubin.gameoflife

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import java.util.*


class GameViewKotlin(context: Context?, width: Float, height: Float, game: GameOfLifeKotlin): View(
        context), ScaleGestureDetector.OnScaleGestureListener {

    var gameWorld = game.world

    val WORLD_WIDTH = width
    val WORLD_HEIGHT = height
    var colums = WORLD_WIDTH
    var rows = WORLD_HEIGHT
    var cellWidth = getWidth() / colums
    var cellHeight = getHeight() / rows

    val scaleDetector = ScaleGestureDetector(context, this)
    var scaleFactor = 1.0f
    val gridPaint = Paint()
    val cellPaint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateDimensions()
        invalidate()
    }

    private fun calculateDimensions() {
        if (colums < 1 || rows < 1) return
        colums = WORLD_WIDTH / scaleFactor
        rows = WORLD_HEIGHT / scaleFactor

        cellWidth = width / colums
        cellHeight = height / rows
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        scale(canvas)

        val backgroundPaint = Paint()
        backgroundPaint.color = Color.WHITE
        canvas.drawPaint(backgroundPaint)
        canvas.restore()

        if (colums == 0F || rows == 0F) return

        drawCells(canvas)
        drawGrid(canvas)
    }

    private fun scale(canvas: Canvas) {
        canvas.save()
        canvas.scale(scaleFactor, scaleFactor)
    }

    private fun drawCells(canvas: Canvas) {
        for (i in 0..(colums - 1).toInt()) {
            for (j in 0..(rows - 1).toInt()) {
                if (gameWorld.isAlive(CellKotlin(i, j))) {
                    val cellRect = Rect((i * cellWidth).toInt(), (j * cellHeight).toInt(),
                            ((i + 1) * cellWidth).toInt(), ((j + 1) * cellHeight).toInt())
                    canvas.drawRect(cellRect, cellPaint)
                }
            }
        }
    }

    private fun drawGrid(canvas: Canvas) {
        gridPaint.style = Paint.Style.FILL_AND_STROKE
        for (i in 1..(colums - 1).toInt()) {
            canvas.drawLine(i * cellWidth, 0F, i * cellWidth, height.toFloat(), gridPaint)
        }
        for (i in 1..(rows - 1).toInt()) {
            canvas.drawLine(0F, i * cellHeight, width.toFloat(), i * cellHeight, gridPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false

        if (event.action == MotionEvent.ACTION_MOVE) {
            val column = event.x / cellWidth
            val row = event.y / cellHeight

            gameWorld.addCell(CellKotlin(column.toInt(), row.toInt()))
            invalidate()
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            cellPaint.color = getRandomColor()
        }

        scaleDetector.onTouchEvent(event)

        return true
    }

    fun nextGeneration(world: GameWorldKotlin) {
        gameWorld = world
        invalidate()
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (detector == null) return false

        scaleFactor *= detector.scaleFactor
        scaleFactor = Math.max(0.4f, Math.min(scaleFactor, 3.0f))

        calculateDimensions()
        invalidate()
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {}
}

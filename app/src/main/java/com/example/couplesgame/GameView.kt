package com.example.couplesgame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var thread: GameThread

    //features
    private val anniversaryTracker = AnniversaryTracker(context)


    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.running = false
        thread.join()
    }

    fun update() {
        // Game logic updates here
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE) // Background color

        val paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f

        val screenWidth = width.toFloat()
        val screenHeight = height.toFloat()

        //Draw house
        val houseLeft = screenWidth / 4
        val houseRight = screenWidth * 3 / 4
        val houseTop = screenHeight / 4
        val houseBottom = screenHeight * 3 / 4
        canvas.drawRect(houseLeft, houseTop, houseRight, houseBottom, paint)

        val roofPaint = Paint()
        roofPaint.color = Color.BLACK
        roofPaint.style = Paint.Style.STROKE
        roofPaint.strokeWidth = 8f

        val roofPath = Path()
        roofPath.moveTo(screenWidth / 2, houseTop - 300) // Roof peak
        roofPath.lineTo(houseLeft, houseTop) // Left roof side
        roofPath.lineTo(houseRight, houseTop) // Right roof side
        roofPath.close()
        canvas.drawPath(roofPath, roofPaint)

        //old
        val textPaint = Paint()

        textPaint.color = Color.RED
        textPaint.textSize = 40f
        textPaint.textAlign = Paint.Align.CENTER

        //canvas.drawCircle(100f, 100f, 50f, paint) //test drawing a circle
        canvas.drawText(anniversaryTracker.getTimeSinceAnniversary(), screenWidth / 2, houseTop - 350, textPaint)
    }
}
package ar_ubin.gameoflife

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.ImageView

class GameActivityKotlin: AppCompatActivity() {

    val gameWorld = GameWorldKotlin()
    val game = GameOfLifeKotlin(gameWorld)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameView = GameViewKotlin(this, 40F, 60F, game)

        val worldViewHolder = findViewById(R.id.contentFrame) as FrameLayout
        worldViewHolder.addView(gameView)

        val playBtn = findViewById(R.id.btn_play) as ImageView
        playBtn.setOnClickListener { startOrPauseGame(gameView, playBtn) }
    }

    private fun startOrPauseGame(gameView: GameViewKotlin, playBtn: ImageView) {
        if (game.isRunning) pauseGame(playBtn) else startGame(gameView, playBtn)
    }

    private fun startGame(gameView: GameViewKotlin, playBtn: ImageView) {
        game.run(gameView)
        playBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_action_pause))
    }

    private fun pauseGame(playBtn: ImageView) {
        game.pause()
        playBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_action_play))
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}

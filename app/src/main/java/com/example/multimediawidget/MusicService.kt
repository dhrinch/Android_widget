package com.example.multimediawidget

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

lateinit var player1: MediaPlayer
lateinit var player2: MediaPlayer
lateinit var player3: MediaPlayer

class MusicService : Service() {
    private var player: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        /*player1 = MediaPlayer.create(this, R.raw.adamczyk_the_rebel_path)
        player2 = MediaPlayer.create(this, R.raw.przybylowicz_v)*/
    }

    /*@SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player?.start()
        return 1
    }*/

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent == null || intent.getAction().equals("")){
            return super.onStartCommand(intent, flags, startId)
        }

        if(intent.getAction().equals("PLAY")){
            player = MediaPlayer.create(this, R.raw.adamczyk_the_rebel_path)
            player?.start()
        }
        if(intent.getAction().equals("STOP")){
            player?.stop()
        }
        if(intent.getAction().equals("NEXT")){
                player?.stop()
                player = MediaPlayer.create(this, R.raw.przybylowicz_v)
                player?.start()

        /*player1 = MediaPlayer.create(this, R.raw.adamczyk_the_rebel_path)
            player2 = MediaPlayer.create(this, R.raw.przybylowicz_v)
            player3 = MediaPlayer.create(this, R.raw.przybylowicz_cloudy_day)

            player?.stop()
            player2.start()

            player2.start()
            if(player1.isPlaying) {
                player?.stop()
                //player = MediaPlayer.create(this, R.raw.przybylowicz_v)
                player2.start()
            } else if (player2.isPlaying){
                player?.stop()
                //player = MediaPlayer.create(this, R.raw.przybylowicz_cloudy_day)
                player3.start()
            } else if (player3.isPlaying){
                player?.stop()
                //player = MediaPlayer.create(this, R.raw.adamczyk_the_rebel_path)
                player1.start()
            }*/

        }
        if(intent.getAction().equals("PREV")){
            player?.stop()
            player = MediaPlayer.create(this, R.raw.adamczyk_the_rebel_path)
            player?.start()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    fun onStop() {
        player?.stop()
    }

    override fun onDestroy() {
        player!!.stop()
        player!!.release()
    }
}

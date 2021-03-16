package com.jjh.games.service.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import com.jjh.games.R

class MusicPlayerService : Service() {

    // Binder pattern - commonly used
    inner class MusicServiceBinder : Binder() {
        val service: MusicPlayerService
            get() = this@MusicPlayerService
    }

    private val binder: IBinder = MusicServiceBinder()

    private lateinit var mediaPlayer: MediaPlayer

    // Ensures music is played in a single thread that can be reused
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.song)
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun start() {
        executor.execute { mediaPlayer.start() }
    }

    fun stop() {
        executor.execute {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
    }

}

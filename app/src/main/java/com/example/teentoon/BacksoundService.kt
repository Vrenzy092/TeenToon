package com.example.teentoon

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class BacksoundService : Service() {

    companion object {
        const val CHANNEL_ID = "BackSoundServiceChannel"
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val BROADCAST_STATUS = "BROADCAST_STATUS"
        const val EXTRA_STATUS = "EXTRA_STATUS"

        const val STATUS_PLAYING = "STATUS_PLAYING"
        const val STATUS_PAUSED = "STATUS_PAUSED"
        const val STATUS_STOPPED = "STATUS_STOPPED"
    }

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val resId = intent.getIntExtra("SONG_RES_ID", R.raw.daynight)

                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(this, resId)
                mediaPlayer?.isLooping = true
                mediaPlayer?.start()
                sendStatusBroadcast(STATUS_PLAYING)
                startForeground(1, buildNotification("Playing backsound"))
            }
            ACTION_PAUSE -> {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                    sendStatusBroadcast(STATUS_PAUSED)
                    stopForeground(false)
                }
            }
            ACTION_STOP -> {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                sendStatusBroadcast(STATUS_STOPPED)
                stopForeground(true)
                stopSelf()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "BackSound Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(contentText: String): Notification {
        val playIntent = Intent(this, BacksoundService::class.java).apply { action = ACTION_PLAY }
        val pauseIntent = Intent(this, BacksoundService::class.java).apply { action = ACTION_PAUSE }
        val stopIntent = Intent(this, BacksoundService::class.java).apply { action = ACTION_STOP }

        val playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val pausePendingIntent = PendingIntent.getService(this, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val stopPendingIntent = PendingIntent.getService(this, 2, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TeenToon Backsound")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .addAction(R.drawable.ic_play, "Play", playPendingIntent)
            .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)
            .addAction(R.drawable.ic_stop, "Stop", stopPendingIntent)
            .setOngoing(contentText == "Playing backsound")
            .build()
    }

    private fun sendStatusBroadcast(status: String) {
        val intent = Intent(BROADCAST_STATUS)
        intent.putExtra(EXTRA_STATUS, status)
        sendBroadcast(intent)
    }
}

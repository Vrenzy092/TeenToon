package com.example.teentoon.nav.prof

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.teentoon.BacksoundService
import com.example.teentoon.MainActivity
import com.example.teentoon.R

class BacksoundActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var spinner: Spinner
    private lateinit var btnBack: Button

    private val statusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val status = intent?.getStringExtra(BacksoundService.EXTRA_STATUS)
            tvStatus.text = "Status: $status"
        }
    }

    // Daftar lagu dan mapping ke res/raw
    private val songMap = mapOf(
        "Day Night" to R.raw.daynight,
        "I Miss You" to R.raw.missyou,
        "Blue" to R.raw.blue
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backsound)

        // Inisialisasi view
        tvStatus = findViewById(R.id.tvStatus)
        spinner = findViewById(R.id.spinnerBGM)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Siapkan data untuk spinner
        val songList = songMap.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, songList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Tombol Play
        findViewById<Button>(R.id.btnPlay).setOnClickListener {
            val selectedSong = spinner.selectedItem.toString()
            val songResId = songMap[selectedSong] ?: R.raw.daynight

            val intent = Intent(this, BacksoundService::class.java).apply {
                action = BacksoundService.ACTION_PLAY
                putExtra("SONG_RES_ID", songResId)
            }
            startService(intent)
        }

        // Tombol Pause
        findViewById<Button>(R.id.btnPause).setOnClickListener {
            val intent = Intent(this, BacksoundService::class.java).apply {
                action = BacksoundService.ACTION_PAUSE
            }
            startService(intent)
        }

        // Tombol Stop
        findViewById<Button>(R.id.btnStop).setOnClickListener {
            val intent = Intent(this, BacksoundService::class.java).apply {
                action = BacksoundService.ACTION_STOP
            }
            startService(intent)
        }

        // Terima status dari Service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                statusReceiver,
                IntentFilter(BacksoundService.BROADCAST_STATUS),
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(
                statusReceiver,
                IntentFilter(BacksoundService.BROADCAST_STATUS)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(statusReceiver)
    }
}
package com.example.scanner

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var recordButton: Button
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording = false

    private val REQUEST_PERMISSION_CODE = 1001
    private val TAG = "MainActivity"
    private var outputFile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
       // recordButton = findViewById(R.id.record_button)



    }


    fun scanButton(view: View) {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setOrientationLocked(false) // Allow both portrait and landscape orientations
        intentIntegrator.setPrompt("Scan a barcode")
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES) // Optional, defaults to all types
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents == null) {
                textView.text = "Cancelled"
            } else {
                textView.text = intentResult.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    fun copyToClipboard(view: View) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Decoded message", textView.text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}

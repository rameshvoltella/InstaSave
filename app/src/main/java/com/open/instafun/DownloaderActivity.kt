package com.open.instafun

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import com.open.instafun.ui.components.LinkOpenerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloaderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the activity was started with an intent that includes shared data
        val sharedUrl = handleIntent(intent)

        setContent {
            LinkOpenerView(sharedUrl)
        }
        // Handle back button press
        onBackPressedDispatcher.addCallback(this) {
            // Start AnotherActivity when the back button is pressed
            val intent = Intent(this@DownloaderActivity, MainActivity::class.java)
            startActivity(intent)
            // Optionally, you can call finish() if you want to close this activity
            finish()
        }
    }

    // Handle the intent to get the shared URL
    private fun handleIntent(intent: Intent?): String? {
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            return intent.getStringExtra(Intent.EXTRA_TEXT)
        }
        return null
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val sharedUrl = handleIntent(intent)
        setContent {
            LinkOpenerView(sharedUrl)
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        // Start AnotherActivity when the back button is pressed
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//    }
}


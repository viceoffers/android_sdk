package com.viceoffers.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viceoffers.tracking.ViceTracking
import android.net.Uri
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize the ViceTracking SDK
        ViceTracking.initialize(this, "YOUR_API_KEY")

        // Handle deep link if present
        intent?.data?.let { uri ->
            if (uri.host == "install") {
                // Only process our specific deep links
                ViceTracking.handleDeepLink(uri.toString())
            }
        }

        // Track install
        ViceTracking.trackInstall()

        // Example purchase tracking
        btnPurchase.setOnClickListener {
            ViceTracking.trackEvent("purchase", mapOf(
                "amount" to 9.99,
                "currency" to "USD"
            ))
        }
    }

    // Handle deep links that open the app
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let { uri ->
            if (uri.host == "install") {
                ViceTracking.handleDeepLink(uri.toString())
            }
        }
    }
}
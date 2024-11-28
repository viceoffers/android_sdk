
package com.viceoffers.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viceoffers.tracking.ViceTracking
import kotlinx.android.synthetic.main.activity_main.*  // Import synthetic properties if using them

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set your layout
        setContentView(R.layout.activity_main)

        // Initialize the ViceTracking SDK
        ViceTracking.initialize(this, "YOUR_API_KEY")

        // Track install
        ViceTracking.trackInstall()

        // Set up button click to track a custom event
        btnPurchase.setOnClickListener {
            ViceTracking.trackEvent("purchase", mapOf("item_id" to "SKU12345", "amount" to 9.99))
        }
    }
}

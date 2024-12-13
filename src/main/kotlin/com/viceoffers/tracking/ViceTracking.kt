
package com.viceoffers.tracking

import android.content.Context
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object ViceTracking {
    private var apiKey: String? = null
    private var installToken: String? = null
    private const val BASE_URL = "https://viceoffers.com/sdk/event/"
    private lateinit var context: Context
    

    /**
     * Initialize the SDK with the provided API key.
     */
    fun initialize(context: Context, apiKey: String) {
        this.context = context.applicationContext
        this.apiKey = apiKey
    }

    /**
     * Handle deep links
     */
    fun handleDeepLink(url: String) {
        try {
            val uri = Uri.parse(url)
            // Extract install token
            uri.getQueryParameter("token")?.let { token ->
                installToken = token
                context.getSharedPreferences("ViceTracking", Context.MODE_PRIVATE)
                    .edit()
                    .putString("install_token", token)
                    .apply()
            }
        } catch (e: Exception) {
            Log.e("ViceTracking", "Error processing deep link: ${e.message}")
        }
    }

    /**
     * Track an install event.
     */
    fun trackInstall() {
        val eventData = JSONObject()
        eventData.put("event", "install")
        eventData.put("timestamp", System.currentTimeMillis())
        sendEvent(eventData)
    }

    /**
     * Track a custom event with optional parameters.
     */
    fun trackEvent(eventName: String, parameters: Map<String, Any>? = null) {
        val eventData = JSONObject()
        eventData.put("event", eventName)
        eventData.put("timestamp", System.currentTimeMillis())
        if (parameters != null) {
            val paramsJson = JSONObject(parameters)
            eventData.put("parameters", paramsJson)
        }
        sendEvent(eventData)
    }

    /**
     * Send the event data to the backend.
     */
    private fun sendEvent(eventData: JSONObject) {
        if (apiKey == null) {
            throw IllegalStateException("ViceTracking is not initialized. Call initialize() with a valid API key.")
        }

        val token = installToken ?: context.getSharedPreferences("ViceTracking", Context.MODE_PRIVATE)
            .getString("install_token", null)
        token?.let {
            eventData.put("install_token", it)
        }

        eventData.put("platform", "android")
        eventData.put("device_model", Build.MODEL)
        eventData.put("os_version", Build.VERSION.RELEASE)
        eventData.put("advertising_id", getAdvertisingId())

        val url = BASE_URL + apiKey

        val client = OkHttpClient()

        val requestBody = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            eventData.toString()
        )

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ViceTracking", "Failed to send event: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.close()
            }
        })
    }

    // Helper method to get advertising ID
    private fun getAdvertisingId(): String? {
        return try {
            AdvertisingIdClient.getAdvertisingIdInfo(context).id
        } catch (e: Exception) {
            null
        }
    }
}
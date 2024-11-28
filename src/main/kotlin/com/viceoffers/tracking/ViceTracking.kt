
package com.viceoffers.tracking

import android.content.Context
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object ViceTracking {

    private var apiKey: String? = null
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
                // Handle failure (optional: implement retry logic)
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle success
                response.close()
            }
        })
    }
}

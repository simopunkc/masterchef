package com.pasuh.masterchef.model.worker

import android.util.Log
import com.pasuh.masterchef.model.util.DELAY_TIME_MILLIS

private const val TAG = "Delay"
class Delay {
    fun sleepTemp() {
        try {
            Thread.sleep(DELAY_TIME_MILLIS, 0)
        } catch (e: InterruptedException) {
            Log.e(TAG, e.message.toString())
        }
    }
}
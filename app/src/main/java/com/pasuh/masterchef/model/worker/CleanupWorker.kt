package com.pasuh.masterchef.model.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class CleanupWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
//        Notification().makeStatusNotification("Cleaning up old temporary result", applicationContext)
        Delay().sleepTemp()

        return try {
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }
}
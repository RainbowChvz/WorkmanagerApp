package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

private const val TAG = "BlurWorker"

class BlurWorker(ctx: Context, params: WorkerParameters)
    : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        makeStatusNotification(
            "Blurring the image...",
            appContext
        )

        try {
            val picture = BitmapFactory.decodeResource(
                appContext.resources,
                R.drawable.android_cupcake
            )

            val blurred = blurBitmap(picture, appContext)
            val writeFileUri = writeBitmapToFile(appContext, blurred)

            makeStatusNotification(
                writeFileUri.toString(),
                appContext
            )
        } catch (e: Throwable) {
            Log.e(TAG, "Error applying blur")

            return Result.failure()
        }

        return Result.success()
    }
}
package com.example.multimediawidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class MmediaWidget : AppWidgetProvider() {
    private val PIC1 = "pic1"
    private val PIC2 = "pic2"
    private val INTENT_FLAGS = 0
    private val mPlayerRequestCode = 0
    private var requestCode = 0

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        val remoteView = RemoteViews(context.packageName, R.layout.mmedia_widget)
        val serviceIntent = Intent(context, MusicService::class.java)
        serviceIntent.setAction("")
        val servicePendingIntent = PendingIntent.getService(context, 0, serviceIntent, 0)

        remoteView.setOnClickPendingIntent(R.id.btn_Play, servicePendingIntent)
        remoteView.setOnClickPendingIntent(R.id.btn_Stop, servicePendingIntent)
        remoteView.setOnClickPendingIntent(R.id.btn_PlayNxt, servicePendingIntent)
        remoteView.setImageViewResource(R.id.imageView, R.drawable.pic1)
        //remoteView.setOnClickPendingIntent(R.id.btn_PlayPrev, servicePendingIntent)

        remoteView.setOnClickPendingIntent(R.id.btn_prevImg, imgPendingIntent(context, PIC1))
        remoteView.setOnClickPendingIntent(R.id.btn_nextImg, imgPendingIntent(context, PIC2))
        for (appWidgetId in appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, remoteView)
        }
    }

    private fun onUpdate(context: Context, remoteViews: RemoteViews) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)

        for (appWidgetId in appWidgetIds) {
            appWidgetManager.partiallyUpdateAppWidget(appWidgetId, remoteViews)
        }
    }

    private fun imgPendingIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    //button clicks
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val remoteView = RemoteViews(context.packageName, R.layout.mmedia_widget)
        if (intent.action == PIC1) {
            remoteView.setImageViewResource(R.id.imageView, R.drawable.pic1)
        } else if (intent.action == PIC2) {
            remoteView.setImageViewResource(R.id.imageView, R.drawable.pic2)
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://edition.cnn.com/")
        val browserIntent = PendingIntent.getActivity(
            context,
            requestCode++,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        remoteView.setOnClickPendingIntent(R.id.btn_Browser, browserIntent)

        onUpdate(context, remoteView)

        val controlButtons = RemoteViews(
            context.packageName,
            R.layout.mmedia_widget
        )
        val playIntent = Intent(context, MusicService::class.java).setAction("PLAY")
        val stopIntent = Intent(context, MusicService::class.java).setAction("STOP")
        val playNextIntent = Intent(context, MusicService::class.java).setAction("NEXT")
        val playPrevIntent = Intent(context, MusicService::class.java).setAction("PREV")

        val playPendingIntent = PendingIntent.getService(
            context, mPlayerRequestCode, playIntent, INTENT_FLAGS
        )
        val playNextPendingIntent = PendingIntent.getService(
            context, mPlayerRequestCode, playNextIntent, INTENT_FLAGS
        )
        val playPrevPendingIntent = PendingIntent.getService(
            context, mPlayerRequestCode, playPrevIntent, INTENT_FLAGS
        )
        val stopPendingIntent = PendingIntent.getService(
            context, mPlayerRequestCode, stopIntent, INTENT_FLAGS
        )
        controlButtons.setOnClickPendingIntent(
            R.id.btn_Play, playPendingIntent
        )

        controlButtons.setOnClickPendingIntent(
            R.id.btn_Stop, stopPendingIntent
        )

        controlButtons.setOnClickPendingIntent(
            R.id.btn_PlayNxt, playNextPendingIntent
        )

        onUpdate(context,controlButtons)
    }

    internal fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
    ) {
        val widgetText = context.getString(R.string.appwidget_text)
    }
}

package com.example.widgetmove

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.RemoteViews
import java.sql.Time
import java.util.*

class WidgetProvider : AppWidgetProvider() {
    private val MY_ACTION = "android.action.MY_ACTION"

    private fun setMyAction(context: Context?): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent,PendingIntent.FLAG_IMMUTABLE)
    }

    private fun buildURIIntent(context: Context?): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://stickode.tistory.com/150"))
        return PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE)
    }

    private fun addViews(context: Context?): RemoteViews {
        val views = RemoteViews(context?.packageName, R.layout.widget)
        views.setOnClickPendingIntent(R.id.btn, setMyAction(context))
        views.setOnClickPendingIntent(R.id.btn2,buildURIIntent(context))
        return views
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds?.forEach { appWidgetId ->
            val views: RemoteViews = addViews(context)
            appWidgetManager?.updateAppWidget(appWidgetId,views)
        }
    }

}



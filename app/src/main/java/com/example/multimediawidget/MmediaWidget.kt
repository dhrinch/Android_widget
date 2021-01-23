package com.example.multimediawidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */
class MmediaWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        Log.i("widget-app", "First widget enabled")// Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        Log.i("widget-app", "First widget disabled")// Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if(intent?.action == context?.getString(R.string.action))
            Toast.makeText(context,"Action1", Toast.LENGTH_SHORT).show()
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
) {
    var requestCode = 0
    val widgetText = context.getString(R.string.appwidget_text)
    val views = RemoteViews(context.packageName, R.layout.mmedia_widget)
    //views.setTextViewText(R.id.appwidget_text, widgetText)

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://www.pja.edu.pl")
    val pendingIntent = PendingIntent.getActivity(
        context,
        requestCode++,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btn_Browser, pendingIntent)

    val intent2 = Intent(context.getString(R.string.action))
    intent2.action = "com.example.widgetapplication.Action1"
    intent2.component = ComponentName(context, MmediaWidget::class.java)
    val pendingIntent2 = PendingIntent.getBroadcast(
        context,
        requestCode++,
        intent2,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.button2, pendingIntent2)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

/*
private static final int GALLERY_REQUEST = 9;

//Widgets
private ImageView imageView;
private Context context;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    context = this;

    //Initialize ImageView widget
    imageView = findViewById(R.id.imageView);

    //Set onclick listener on ImageView
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Open dialog
            showImageOptionDialog();

        }
    });
}

    AlertDialog dialog = builder.create();
    dialog.show();
}

//Open phone gallery
private void getImageFromGallery(){
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    startActivityForResult(intent, GALLERY_REQUEST);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    //Check if the intent was to pick image, was successful and an image was picked
    if(requestCode == GALLERY_REQUEST &amp;&amp; resultCode == RESULT_OK &amp;&amp; data != null){

        //Get selected image uri from phone gallery
        Uri selectedImage = data.getData();

        //Display selected photo in image view
        imageView.setImageURI(selectedImage);
    }
}*/

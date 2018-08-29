package com.perezjquim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.perezjquim.uihelper.R;

import java.util.HashMap;

public abstract class UIHelper
{
    public static void show(View view)
    {
        runOnUiThread(()-> view.setVisibility(RelativeLayout.VISIBLE));
    }
    public static void hide(View view)
    {
        runOnUiThread(()-> view.setVisibility(RelativeLayout.GONE));
    }
    public static boolean isVisible(View view)
    {
        return view.getVisibility() == RelativeLayout.VISIBLE;
    }

    public static void toast(Context c, String s)
    {
        runOnUiThread(()-> Toast.makeText(c, s, Toast.LENGTH_SHORT).show());
    }

    public static final String[] colors =
    {
            "#FF0000",
            "#FF8000",
            "#FFFF00",
            "#00FF00",
            "#00FFFF",
            "#0080FF",
            "#0000FF",
            "#8000FF",
            "#FF00FF",
            "#705050",
            "#FFFFFF"
    };

    private static HashMap<Activity,Dialog> dialogs;
    public static void openProgressDialog(Activity a, String message)
    {
        if(dialogs == null) dialogs = new HashMap<>();

        if(!dialogs.containsKey(a))
        {
            runOnUiThread(() ->
            {
                Dialog dialog = new Dialog(a, R.style.TransparentProgressDialog);
                dialog.setCancelable(false);
                dialog.addContentView(
                        new ProgressBar(a),
                        new WindowManager.LayoutParams(
                                WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.WRAP_CONTENT));
                dialog.setTitle(message);
                dialog.show();

                dialogs.put(a,dialog);
            });
        }
        else
        {
            Dialog dialog = dialogs.get(a);
            runOnUiThread(() ->
            {
                dialog.hide();
                dialog.setTitle(message);
                dialog.show();
            });
        }
    }
    public static void closeProgressDialog(Activity a)
    {
        if(dialogs != null && dialogs.containsKey(a))
        {
            Dialog dialog = dialogs.get(a);
            dialog.hide();
        }
    }

    public static void fitToScreen(Activity a, View view)
    {
        fitToScreen(a,view,0);
    }
    public static void fitToScreen(Activity a, View view, int padding)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = metrics.widthPixels - padding;
        params.height = metrics.heightPixels - padding;
        runOnUiThread(()-> view.setLayoutParams(params));
    }

    public static void toggleVisibility(View v)
    {
        if(isVisible(v)) hide(v);
        else show(v);
    }

    public static void updateTime(TextView text, int hour, int minutes)
    {
        String strHour = "";
        String strMinute = "";

        if(hour < 10) strHour += "0";
        strHour += hour;

        if(minutes < 10) strMinute += "0";
        strMinute += minutes;

        String finalStrHour = strHour;
        String finalStrMinute = strMinute;
        runOnUiThread(()-> text.setText(finalStrHour + ":" + finalStrMinute));
    }

    private static final int LED_ON_MS = 500;
    private static final int LED_OFF_MS = 2000;
    public static void notify(Context c,Class destination, int iconResID, String title, String text)
    {
        Intent intent = new Intent(c, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        buildNotification(c,title,text,iconResID,Color.WHITE,pending);
    }
    public static void notify(Context c, int iconResID, String title, String text)
    {
        Intent intent = new Intent();
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        buildNotification(c,title,text,iconResID,Color.WHITE,pending);
    }
    public static void notify(Context c,Class destination, int iconResID, String title, String text, int argb_color)
    {
        Intent intent = new Intent(c, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        buildNotification(c,title,text,iconResID,argb_color,pending);
    }
    public static void notify(Context c, int iconResID, String title, String text, int argb_color)
    {
        Intent intent = new Intent();
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        buildNotification(c,title,text,iconResID,argb_color,pending);
    }
    public static void notify(Context c,Class destination, String title, String text)
    {
        Intent intent = new Intent(c, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        buildNotification(c,title,text,android.R.drawable.ic_dialog_info,Color.WHITE,pending);
    }
    public static void notify(Context c, String title, String text)
    {
        Intent intent = new Intent();
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        buildNotification(c,title,text,android.R.drawable.ic_dialog_info,Color.WHITE,pending);
    }
    public static void notify(Context c,Class destination, String title, String text, int argb_color)
    {
        Intent intent = new Intent(c, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        buildNotification(c,title,text,android.R.drawable.ic_dialog_info,argb_color,pending);
    }
    public static void notify(Context c, String title, String text, int argb_color)
    {
        Intent intent = new Intent();
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        buildNotification(c,title,text,android.R.drawable.ic_dialog_info,argb_color,pending);
    }
    public static void buildNotification(Context c, String title, String text, int iconResID, Uri sound, int argb_color, int led_on_ms, int led_off_ms, PendingIntent pending)
    {
        runOnUiThread(()->
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setSmallIcon(iconResID)
                    .setSound(sound)
                    .setLights(argb_color,led_on_ms,led_off_ms)
                    .setContentIntent(pending);

            NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(title.hashCode(), mBuilder.build());
        });
    }
    public static void buildNotification(Context c, String title, String text, int iconResID, int argb_color, PendingIntent pending)
    {
        buildNotification(c,title,text,iconResID,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),argb_color,LED_ON_MS,LED_OFF_MS,pending);
    }

    public static void ask(Context c, String title, String message, String positiveLabel, String negativeLabel, View form, InputListener action)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setView(form);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(positiveLabel,
                (dialog, which) ->
                        action.run(form));
        alertDialog.setNegativeButton(negativeLabel,
                (dialog, which) ->
                        runOnUiThread(()-> dialog.cancel()));
        runOnUiThread(()->alertDialog.show());
    }

    public static void askBinary(Context c,String title,String message,Runnable action)
    {
        ask(c,title,message,"Yes","No",null,(form) ->
        {
           action.run();
        });
    }

    public static void askString(Context c,String title,String message,InputListener action)
    {
        final EditText input = new EditText(c);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ask(c,title,message,"Confirm","Cancel",input,(form) ->
        {
            action.run(input.getText()+"");
        });
    }

    public static void askURL(Context c,String title,String message,InputListener action)
    {
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ask(c,title,message,"Confirm","Cancel",input,(form) ->
        {
            action.run(input.getText()+"");
        });
    }

    public static void askDouble(Context c,String title,String message,InputListener action)
    {
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ask(c,title,message,"Confirm","Cancel",input,(form) ->
        {
            action.run(input.getText()+"");
        });
    }

    public interface InputListener
    {
        void run(Object o);
    }

    public static void runOnUiThread(Runnable action)
    {
        if(Thread.currentThread() == Looper.getMainLooper().getThread())
        {
            action.run();
        }
        else
        {
            new Thread(()->
            {
                Looper.prepare();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(action);
                Looper.loop();
            }).start();
        }
    }
}

package com.perezjquim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

public abstract class UIHelper
{
    private static Dialog dialog;

    public static void show(View view)
    {
        runOnUiThread(()->
            view.setVisibility(RelativeLayout.VISIBLE));
    }

    public static void hide(View view)
    {
        runOnUiThread(()->
            view.setVisibility(RelativeLayout.GONE));
    }

    public static boolean isVisible(View view)
    {
        return view.getVisibility() == RelativeLayout.VISIBLE;
    }

    public static void toast(Context c, String s)
    {
        runOnUiThread(()->
            Toast.makeText(c, s, Toast.LENGTH_SHORT).show());
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

    public static void openProgressDialog(Context c, String message)
    {
        // Fecha dialogs prévios
        closeProgressDialog();

        // O dialog é criado e configurado
        dialog = new Dialog(c, R.style.TransparentProgressDialog);
        dialog.setTitle(message);
        dialog.setCancelable(false);
        dialog.addContentView(
                new ProgressBar(c),
                new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT));

        // É mostrado o progress dialog
        runOnUiThread(()->
                dialog.show());
    }

    public static void closeProgressDialog()
    {
        if(dialog != null && dialog.isShowing())
        {
            runOnUiThread(()->
                    dialog.dismiss());
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
        runOnUiThread(()->
                view.setLayoutParams(params));
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
        runOnUiThread(()->
                text.setText(finalStrHour + ":" + finalStrMinute));
    }

    public static void notify(Context c,Class destination,String title, String text)
    {
        Intent intent = new Intent(c, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(pending);

        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(title.hashCode(), mBuilder.build());
    }

    public static void notify(Context c,Class destination, int iconResID, String title, String text)
    {
        Intent intent = new Intent(c, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(c,0,intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSmallIcon(iconResID)
                .setContentIntent(pending);

        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(title.hashCode(), mBuilder.build());
    }

    public static void askBinary(Context c,String title,String message,Runnable action)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes",
                (dialog, which) ->
                        action.run());
        alertDialog.setNegativeButton("No",
                (dialog, which) ->
                        runOnUiThread(()->
                                dialog.cancel()));
        runOnUiThread(()->alertDialog.show());
    }

    public static void askString(Context c,String title,String message,InputListener action)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        final EditText input = new EditText(c);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Confirm",
                (dialog, which) ->
                {
                    String response = ""+input.getText();
                    action.run(response);
                });
        alertDialog.setNegativeButton("Cancel",
                (dialog, which) ->
                        runOnUiThread(()->
                                dialog.cancel()));
        runOnUiThread(()->alertDialog.show());
    }

    public static void askDouble(Context c,String title,String message,InputListener action)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Confirm",
                (dialog, which) ->
                {
                    double response = Double.parseDouble(""+input.getText());
                    action.run(response);
                });
        alertDialog.setNegativeButton("Cancel",
                (dialog, which) ->
                        runOnUiThread(()->
                                dialog.cancel()));
        runOnUiThread(()->alertDialog.show());
    }

    public interface InputListener
    {
        void run(Object o);
    }

    public static void runOnUiThread(Runnable action)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(action);
    }

}

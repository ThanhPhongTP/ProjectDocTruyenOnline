package com.example.truyen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class Commons {
    public static final String Categories = "KeyCategories" ;
    public static String CategoriesDetail = "CategoriesDetail";
    public static String Story= "Story";
    public static String Chapter = "Chapter";
    public static final String KEY_STORY = "Story";
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String url_SettingWifi = Settings.ACTION_WIFI_SETTINGS;

    public static final float size_default = 22;
    public static final float size_12 = 12;
    public static final float size_14 = 14;
    public static final float size_16 = 16;
    public static final float size_18 = 18;
    public static final float size_20 = 20;
    public static final float size_22 = 22;
    public static final int DEFAULT_SECOUND  = 1800000;
    public static final int THIRTY_SECOUND  = 3000;
    public static final int ONE_MINUTE = 60000;
    public static final int TEN_MINUTE = 600000;
    public static final int THIRTY_MINUTE  = 1800000;

    public static final float line_Height_1 = 10;
    public static final float line_Height_2 = 15;
    public static final float line_Height_3 = 20;
    public static final float line_Height_4 = 25;
    public static final float line_Height_5 = 30;
    public static final float line_Height_6 = 35;
    public static  boolean isConnectedtoInternet(Context context) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo[] info= connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static void showDialogError(Context context){
        AlertDialog.Builder builder =   new AlertDialog.Builder(context);
        builder.setMessage("Vui lòng kiểm tra mạng của bạn");
        builder.setTitle("Thông Báo");
        builder.setPositiveButton("Kiểm Tra", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                setActionIntent(url_SettingWifi,context);
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
    private static void setActionIntent(String actionIntent, Context context) {
        Intent intent = new Intent();
        intent.setAction(actionIntent);
        context.startActivity(intent);
    }
}

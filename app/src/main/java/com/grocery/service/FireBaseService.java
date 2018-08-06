package com.grocery.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.activity.MainMenu;
import com.grocery.activity.MainViewOrders;
import com.grocery.common.Config;
import com.grocery.common.ServiceUtils;
import com.grocery.model.ItemOrders;


public class FireBaseService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        /*String sss = remoteMessage.getNotification().getBody();
        String ss = "";*/
        try {
            String body = remoteMessage.getData().get("body");
            int orderId = Integer.parseInt(remoteMessage.getData().get("order_no"));
            int orderType = Integer.parseInt(remoteMessage.getData().get("order_type"));
            String flat_no = "";
            String delivery_time = "";
            if (orderType == -1) {
                flat_no = remoteMessage.getData().get("flat_no");
                delivery_time = remoteMessage.getData().get("delivery_time");
            }
            sendNotification(body, orderId, orderType, flat_no, delivery_time);
        } catch (Exception err) {
            sendNotification("", -1, 0, "", "");
        }
    }

//    @Override
//    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);
//        try {
//            Bundle bundle = intent.getExtras();
//            String body = bundle.get("body").toString();
//            int orderId = Integer.parseInt(bundle.get("order_no").toString());
//            int orderType = Integer.parseInt(bundle.get("order_type").toString());
//            String flat_no = "";
//            String delivery_time = "";
//            if (orderType == -1) {
//                flat_no = bundle.get("flat_no").toString();
//                delivery_time = bundle.get("delivery_time").toString();
//            }
//            sendNotification(body, orderId, orderType, flat_no, delivery_time);
//        } catch (Exception err) {
//            sendNotification("", -1, 0, "", "");
//        }
//    }

    private void sendNotification(final String messageBody, final int orderId,
                                  final int orderType, final String flat_no, final String delivery_time) {
        Config.ItemOrdersView = new ItemOrders(orderId, 3);
        Config.isCallFromNotifi = true;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Config.KEY_VIEW_ORDER, Config.ItemOrdersView);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap thumb = Bitmap.createBitmap(120, 140, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(thumb);
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect(0, 0, thumb.getWidth(), thumb.getHeight()), null);
        Drawable drawable = new BitmapDrawable(getResources(), thumb);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder;
        if (orderType != -1) {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(((BitmapDrawable) drawable).getBitmap())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(((BitmapDrawable) drawable).getBitmap())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri);
//                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        try {
            MainActivity.instanceMain.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        MainActivity.dialogNotifi.cancel();
                        MainActivity.dialogPingShop.cancel();
                    } catch (Exception er) {

                    }
                    if (orderType != -1) {
                        if(orderType==4) {
                            MainActivity.initDialogNotifi(orderId, orderType);
                        }
                        ServiceUtils.sendLocalBroadcast(getApplicationContext(), Config.ACTION_RELOAD_DATA);
                    } else if (orderType == -1) {
                        MainActivity.initDialogPing(orderId, flat_no, delivery_time);
                    }
                }
            });
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

}

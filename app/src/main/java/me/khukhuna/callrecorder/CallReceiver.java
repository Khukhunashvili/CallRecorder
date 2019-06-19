package me.khukhuna.callrecorder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {

    NotificationManager mNotificationManager;

    Context mContext;
    private String CHANNEL_ID = "TEST_ID";
    private int ID = 4284;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.mContext = context;

        mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);
                switch (state){
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Call ended
                        hideNotification();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        // Call started
                        showNotification();
                        break;

                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);

    }


    private void hideNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(ID);
        }

    }


    public void showNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat
                        .Builder(mContext.getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_menu_send)
                        .setContentTitle("Call Recorder")
                        .setContentText("Would you like to stream this conversation to the server?")
                        .setPriority(Notification.PRIORITY_MAX);

        if (mNotificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "Streaming Alert!",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }

            mNotificationManager.notify(ID, mBuilder.build());

        }
    }

}

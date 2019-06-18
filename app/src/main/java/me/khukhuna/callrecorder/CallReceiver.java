package me.khukhuna.callrecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {

    public static int PHONE_RINGING = 1;

    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);
                switch (state){
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Call ended
                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        // Call started
                        Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);

    }
}

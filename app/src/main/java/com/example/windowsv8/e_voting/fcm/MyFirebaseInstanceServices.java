package com.example.windowsv8.e_voting.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceServices extends FirebaseInstanceIdService {
    private static final String REG_TOKEN = "REG_TOKEN";
    String token;

    @Override
    public void onTokenRefresh() {
        token = FirebaseInstanceId.getInstance().getToken();
        Log.d("HASILTOKEN",token);
    }

}

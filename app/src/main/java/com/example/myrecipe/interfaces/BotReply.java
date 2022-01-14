package com.example.myrecipe.interfaces;

import android.view.KeyEvent;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;

public interface BotReply extends KeyEvent.Callback {
    void callback(DetectIntentResponse returnResponse);
}

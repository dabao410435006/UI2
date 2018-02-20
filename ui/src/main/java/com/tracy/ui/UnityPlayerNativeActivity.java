package com.tracy.ui;

import android.os.Bundle;
import android.util.Log;

/**
 * @deprecated It's recommended that you base your code directly on UnityPlayerActivity2 or make your own NativeActitivty implementation.
 **/
public class UnityPlayerNativeActivity extends UnityPlayerActivity
{
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        Log.w("Unity", "UnityPlayerNativeActivity has been deprecated, please update your AndroidManifest to use UnityPlayerActivity2 instead");
        super.onCreate(savedInstanceState);
    }
}

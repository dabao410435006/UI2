package com.tracy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * @deprecated Use UnityPlayerActivity2 instead.
 */
public class UnityPlayerProxyActivity extends Activity
{
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        Log.w("Unity", "UnityPlayerNativeActivity2 has been deprecated, please update your AndroidManifest to use UnityPlayerActivity2 instead");
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, UnityPlayerActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            intent.putExtras(extras);
        startActivity(intent);
    }
}

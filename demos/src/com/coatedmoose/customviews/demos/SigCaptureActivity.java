package com.coatedmoose.customviews.demos;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.coatedmoose.customviews.SignatureView;

public class SigCaptureActivity extends Activity {

	private static final String TAG = "Signature";
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_capture);
		SignatureView sigView = (SignatureView) findViewById(R.id.signview);
		sigView.setSigColor(Color.RED);
    }    
}

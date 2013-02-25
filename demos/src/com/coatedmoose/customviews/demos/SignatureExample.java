package com.coatedmoose.customviews.demos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.coatedmoose.customviews.SignatureView;

public class SignatureExample extends Activity {
	
	SignatureView signature;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        signature = (SignatureView) this.findViewById(R.id.signatureView1);
    }
    
    public void saveSignature(View view) {
    	Bitmap image = signature.getImage();
		File sd = Environment.getExternalStorageDirectory();
		File fichero = new File(sd, "signature.jpg");

		try {
			if (sd.canWrite()) {
            	fichero.createNewFile();
				OutputStream os = new FileOutputStream(fichero);
				image.compress(Bitmap.CompressFormat.JPEG, 90, os);
				os.close();
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}

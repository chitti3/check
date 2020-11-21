package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import okio.Utf8;

public class MainActivity extends AppCompatActivity {

    EditText name,password;
    Button enter,decrpt;
    TextView encrypttext;

    String key = "e8ffc7e56311679f12b6fc91aa77a5eb";
    String iv = "qwertyuiopasdfgh";
    byte[] ivBytes ;
    String plainText;
    byte[] cipherData;
    String base64Text;
    byte[] keyBytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ivBytes = iv.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        name = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        encrypttext = (TextView) findViewById(R.id.textView);
        enter = (Button) findViewById(R.id.encrypt);
        decrpt = (Button) findViewById(R.id.decrypt);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                encryptmethod(name.getText().toString());
            }
        });




//        base64Text = "72XrlydqnUzVrDfDE7ncnQ==";
//        try {
//            cipherData = AES256ciper.decrypt(ivBytes, keyBytes, Base64.decode(base64Text.getBytes("UTF-8"), Base64.DEFAULT));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//        try {
//            plainText = new String(cipherData, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Log.d("dcrypt", plainText);
//
//

    }

    private void encryptmethod(String plainText) {
        try {
            cipherData = AES256ciper.encrypt(ivBytes, keyBytes, plainText.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        base64Text = Base64.encodeToString(cipherData, Base64.DEFAULT);
        Log.d("encrypt", base64Text);   //BhSJd4mRRJo+fGzpxIOUNg==
        System.out.println("===========getthekey"+base64Text);
encrypttext.setText(base64Text);

    }

}

package com.example.android.demo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.payUMoney.sdk.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    HashMap<String,String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn =  (Button)findViewById(R.id.btn);
        params = new HashMap<String, String>();

        //debug
        String hashSequence = "Vw997n" + "|" + "0nf7" + "|" + "190" + "|" + "product_name" + "|" + "piyush" + "|"
                + "piyush.jain@payu.in" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "4womTBoq";

        String hash = hashCal("SHA-512", hashSequence);
        Log.i("hash", hash);
        //  params.put("TxnId", "0nf7" + System.currentTimeMillis());
        params.put("TxnId", "0nf7");// debug
        params.put("MerchantId","4825269");
        params.put("SURL", "https://test.payumoney.com/mobileapp/payumoney/success.php");
        params.put("FURL", "https://test.payumoney.com/mobileapp/payumoney/failure.php");
        params.put("ProductInfo", "product_name");
        params.put("firstName", "piyush");
        params.put("Email", "piyush.jain@payu.in");
        params.put("Phone", "8882434664");
        params.put("Amount", "190");
        params.put("hash", hash);
        //test key
        params.put("key", "Vw997n");

        params.put("udf1", "");
        params.put("udf2", "");
        params.put("udf3", "");
        params.put("udf4", "");
        params.put("udf5", "");




   /*     String hashSequence = "asljdhaifffgfgyffsyuhfgsuyfsb";
        String hash = hashCal("SHA-512", hashSequence);

        Log.e("new hash key", hash);
        params.put("TxnId","106");
        params.put("hash",hash);
        params.put("key","JBZaLc");
        params.put("Amount","155");
        params.put("MerchantId","5112938");



        params.put("ProductInfo","juta");
        params.put("SURL","http://shrijay.com/recharge/register.php");
        params.put("FURL","http://shrijay.com/recharge/login.php");
        params.put("firstName","krishna");
        params.put("Email","pinch@a.com");
        params.put("Phone","9725633197");
*/





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.startPaymentProcess(MainActivity.this, params);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Session.PAYMENT_SUCCESS){
            if(resultCode == RESULT_OK){
                Toast.makeText(MainActivity.this, "Payment done", Toast.LENGTH_LONG).show();
            }

            if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this,"Payment Failed",Toast.LENGTH_LONG).show();
            }
        }



    }

    public static String hashCal(String type, String str)
    {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.dell.incarnation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SignIn extends AppCompatActivity {

    Button signIn;
    EditText userName, password;
    String u_name, u_password,JsonStr,url_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userName = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);

        signIn = (Button) findViewById(R.id.logIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u_name = userName.getText().toString();
                u_password = password.getText().toString();
                url_string = '\''+u_name+'\''+"|"+'\''+u_password+'\'';

                new RequestTask().execute();


            }
        });
    }


    class RequestTask extends AsyncTask<String, String, String> {

        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data='qwerty'|'hush'|'M'|40|'jks@jss'|'92839832'|'Delhi'|9";
        //String myurl = "http://172.16.30.124/DBMS/user_signIn.php?data="+url_string;
        //String myurl = "http://172.16.30.124/DBMS/car_available.php";
        String myurl = "http://10.0.2.2/DBMS/user_signIn.php?data="+url_string;

        @Override
        protected String doInBackground(String... uri) {
            String responseString = null;
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.connect();
                // Read the input stream into a String
                InputStream inputStream = conn.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.

                }

                JsonStr = buffer.toString(); //result

                if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    // Do normal input or output stream reading
                    responseString = "Value fed to SQL";

                }
            } catch (Exception e) {
                //TODO Handle problems..
                Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

            return JsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            if(result.contains("Welcome")){

                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=saved_values.edit();
                editor.putString("u_name",u_name);
                editor.commit();
                Intent intent = new Intent(SignIn.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            else{
                Toast.makeText(getBaseContext(),"Invalid credentials",Toast.LENGTH_SHORT).show();
            }

            //Do anything with response..
        }
    }

}

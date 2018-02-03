package com.example.dell.incarnation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class signUp extends AppCompatActivity {

    EditText eid,ename,eage,egender,eemail,econtact_no,estate,epassword;
    String id,name,age,gender,email,contact_no,state,password,query_url;
    Button submit,signIn;
    int rating = 9;
    String JsonStr;
    //ProgressDialog pdLoading = new ProgressDialog(getBaseContext());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eid = (EditText) findViewById(R.id.signUp_id);
        ename = (EditText)findViewById(R.id.signUp_name);
        eage = (EditText)findViewById(R.id.signUp_age);
        egender = (EditText)findViewById(R.id.signUp_gender);
        eemail = (EditText)findViewById(R.id.signUp_email);
        econtact_no = (EditText)findViewById(R.id.signUp_contact);
        estate = (EditText)findViewById(R.id.signUp_state);
        epassword = (EditText)findViewById(R.id.signUp_password);

        submit = (Button)findViewById(R.id.button);
        signIn = (Button)findViewById(R.id.sign_in);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = eid.getText().toString();
                name = ename.getText().toString();
                age = eage.getText().toString();
                gender = egender.getText().toString();
                email = eemail.getText().toString();
                contact_no = econtact_no.getText().toString();
                state = estate.getText().toString();
                password = epassword.getText().toString();

                query_url = '\'' + id + '\'' +'|'+ '\'' +name+ '\'' +'|'+'\''+gender+'\''+'|'+age+'|'+'\''+email+'\''+'|'+'\''+contact_no+'\''+'|'+'\''+state+'\''+'|'+rating+'|'+'\''+password+'\'';
                eid.setText(query_url);

                //pdLoading.setCancelable(false);
                //pdLoading.show();
                new RequestTask().execute();

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(signUp.this,SignIn.class);
                startActivity(intent);
                finish();

            }
        });


    }

    class RequestTask extends AsyncTask<String, String, String> {

        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data='qwerty'|'hush'|'M'|40|'jks@jss'|'92839832'|'Delhi'|9";
        String myurl = "http://10.0.2.2/DBMS/user_registration.php?data="+query_url;
        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data="+query_url;
        //String myurl = "http://10.0.2.2/DBMS/car_available.php";

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
            //pdLoading.dismiss();

//            SharedPreferences sh = getSharedPreferences("user", Context.MODE_PRIVATE);
//            SharedPreferences.Editor ed = sh.edit();
//            ed.putString("u_name",ename.getText().toString());
//            ed.apply();
//            ed.commit();

            SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor=saved_values.edit();
            editor.putString("u_name",id);
            editor.commit();

            Intent i = new Intent(signUp.this,MainActivity.class);
            startActivity(i);
            finish();
            //Do anything with response..
        }
    }

}

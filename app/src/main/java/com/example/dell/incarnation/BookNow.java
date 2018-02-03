package com.example.dell.incarnation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BookNow extends AppCompatActivity implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker,btnDatePicker2, btnTimePicker2;
    EditText txtDate, txtTime,txtDate2, txtTime2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String toDate,fromDate,toTime,fromTime;
    private int aYear, aMonth, aDay, aHour, aMinute;
    String JsonStr,car_id,u_id,query_string;
    Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        car_id = getIntent().getStringExtra("vid");

        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        u_id = saved_values.getString("u_name", "null");

        book = (Button)findViewById(R.id.cfmbkg);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        btnDatePicker2=(Button)findViewById(R.id.btn_date2);
        btnTimePicker2=(Button)findViewById(R.id.btn_time2);

        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        txtDate2=(EditText)findViewById(R.id.in_date2);
        txtTime2=(EditText)findViewById(R.id.in_time2);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnDatePicker2.setOnClickListener(this);
        btnTimePicker2.setOnClickListener(this);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fromDate = txtDate.getText().toString();
                toDate = txtDate2.getText().toString();
                fromTime = txtTime.getText().toString();
                toTime = txtTime2.getText().toString();

                query_string = '\''+u_id+'\''+'|'+'\''+car_id+'\''+'|'+'\''+fromDate+'\''+'|'+'\''+fromTime+'\''+'|'+'\''+toDate+'\''+'|'+'\''+toTime+'\'';
                //Toast.makeText(getBaseContext(),query_string,Toast.LENGTH_LONG).show();

                new RequestTask().execute();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnDatePicker2) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnTimePicker2) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime2.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    class RequestTask extends AsyncTask<String, String, String> {

        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data='qwerty'|'hush'|'M'|40|'jks@jss'|'92839832'|'Delhi'|9";
        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data="+query_url;
        //String myurl = "http://172.16.30.124/DBMS/booking_details.php?data="+query_string;
        String myurl = "http://10.0.2.2/DBMS/booking_details.php?data="+query_string;

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

                Intent intent = new Intent(BookNow.this,MainActivity.class);
//                startActivity(intent);
//                finish();
            }

            //Do anything with response..
        }
}

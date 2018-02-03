package com.example.dell.incarnation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class cost_calculator extends AppCompatActivity implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker,btnDatePicker2, btnTimePicker2;
    EditText txtDate, txtTime,txtDate2, txtTime2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String toDate,fromDate,toTime,fromTime;
    private int aYear, aMonth, aDay, aHour, aMinute;
    String JsonStr,car_id,u_id,query_string,carName;
    Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_calculator);

        book = (Button)findViewById(R.id.getCost);

        carName = getIntent().getStringExtra("car_name_textview");

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

                String fromDate_arr[] = fromDate.split("-");
                String fromTime_arr[] = fromTime.split(":");
                String toDate_arr[] = toDate.split("-");
                String toTime_arr[] = toTime.split(":");
                //Toast.makeText(getBaseContext(),"chal raha hai",Toast.LENGTH_LONG).show();
                int hours = Integer.parseInt(toTime_arr[0])-Integer.parseInt(fromTime_arr[0]);
                int ford_figo = 3700;
                int ford_figo_h = 350;
                int santro = 3000;
                int swift = 4500;
                int bmwX1 = 24000;
                int santro_h = 300;
                int swift_h = 400;
                int bmwX1_h = 900;


                if(carName.equals("Ford Figo")){

                    if(fromDate_arr[0].equals(toDate_arr[0]) && fromDate_arr[1].equals(toDate_arr[1])){

                        int estdCost = ford_figo_h*hours;
                        Toast.makeText(getBaseContext(),"Estimated cost : "+String.valueOf(estdCost),Toast.LENGTH_LONG).show();

                    }

                    else if(Integer.parseInt(toDate_arr[1])-Integer.parseInt(fromDate_arr[1])<0){

                        Toast.makeText(getBaseContext(),"To date must be after the From date",Toast.LENGTH_LONG).show();

                    }

                    else if(!(fromDate_arr[0].equals(toDate_arr[0])) && fromDate_arr[1].equals(toDate_arr[1])){

                        int days = Integer.parseInt(toDate_arr[0])-Integer.parseInt(fromDate_arr[0]);
                        int estdCost = ford_figo * days;
                        Toast.makeText(getBaseContext(),String.valueOf(estdCost),Toast.LENGTH_LONG).show();

                    }

                    else if(!fromDate_arr[1].equals(toDate_arr[1]) && (Integer.parseInt(toDate_arr[1])-Integer.parseInt(fromDate_arr[1])>=2)){

                        Toast.makeText(getBaseContext(),"Sorry, we do not rent cars for more than a month",Toast.LENGTH_LONG).show();

                    }

                    else if(!fromDate_arr[1].equals(toDate_arr[1])){

                        int days = Integer.parseInt(toDate_arr[0])-Integer.parseInt(fromDate_arr[0]);
                        int estdCost = ford_figo * 18;
                        Toast.makeText(getBaseContext(),String.valueOf(estdCost),Toast.LENGTH_LONG).show();

                    }

                    else{

                        Toast.makeText(getBaseContext(),"The entered details are incorrect",Toast.LENGTH_LONG).show();

                    }

                }
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

}

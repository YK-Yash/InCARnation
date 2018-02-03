package com.example.dell.incarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class cost_calculator2 extends AppCompatActivity {

    AutoCompleteTextView textView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_calculator2);
        Button b = (Button)findViewById(R.id.proc);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CARS);
         textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView);
        textView.setAdapter(adapter);

        intent = new Intent(cost_calculator2.this,cost_calculator.class);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = textView.getText().toString();
                intent.putExtra("car_name_textview",a);
                startActivity(intent);
            }
        });

    }

    private static final String[] CARS = new String[] {
            "Ford Figo", "BMW X1", "Hyundai Santro", "Audi A4", "Maruti Suzuki Swift"
    };
}

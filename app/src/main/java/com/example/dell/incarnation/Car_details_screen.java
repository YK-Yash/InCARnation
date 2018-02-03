package com.example.dell.incarnation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class Car_details_screen extends AppCompatActivity {

    CircularImageView circularImageView;
    Bitmap b;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details_screen);

        information ii = new information();
        ii.execute();

        circularImageView = (CircularImageView)findViewById(R.id.hhvbn);

        circularImageView.addShadow();
        circularImageView.setShadowRadius(15);
        circularImageView.setShadowColor(Color.BLACK);

        final String id  = getIntent().getStringExtra("c_id");

        String name = getIntent().getStringExtra("c_name");
        String kms = getIntent().getStringExtra("c_kms");

        TextView id_1 = (TextView)findViewById(R.id.car_details_year);
        TextView name_1 = (TextView)findViewById(R.id.car_details_name);
        TextView kms_1 = (TextView)findViewById(R.id.car_details_kms);
        Button b = (Button)findViewById(R.id.book_now);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),BookNow.class);
                intent.putExtra("vid",id);
                startActivity(intent);
            }
        });

        id_1.setText(id);
        name_1.setText(name);
        kms_1.setText(kms);




        /*if(name.equals("Ford Figo")){
            //URI uri =
            circularImageView.setImageResource(R.mipmap.figo);
        }*/

        //Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();



    }

    public class information extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... arg0) {
            try
            {
                URL url = new URL("http://10.0.2.2/Figo.jpg");
                InputStream is = new BufferedInputStream(url.openStream());
                b = BitmapFactory.decodeStream(is);
            } catch(Exception e){}
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            circularImageView.setImageBitmap(b);

        }
    }

}

package com.example.dell.incarnation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Car_costListing extends AppCompatActivity {

    String JsonStr;
    List<Car_costListing_model> data = new ArrayList<>();
    Car_costAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_cost_listing);

        new RequestTask().execute();

    }

    class RequestTask extends AsyncTask<String, String, String> {

        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data='qwerty'|'hush'|'M'|40|'jks@jss'|'92839832'|'Delhi'|9";
        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data="+query_url;
        //String myurl = "http://172.16.30.124/DBMS/car_costing.php";
        String myurl = "http://10.0.2.2/DBMS/car_costing.php";

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

            try {

                JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Car_costListing_model car = new Car_costListing_model();
                    car.car_company = json_data.getString("car_company");
                    car.car_name = json_data.getString("car_name");
                    car.cost_day = json_data.getString("cost_day");
                    data.add(car);
                }


                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.r2);
                mAdapter = new Car_costAdapter(Car_costListing.this,data);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Car_costListing.this));

                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                        //Toast.makeText(getBaseContext(),"1",Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                        //Toast.makeText(getBaseContext(),"2",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                        Toast.makeText(getBaseContext(),"3",Toast.LENGTH_LONG).show();

                    }
                });


            }catch (JSONException e) {
                Toast.makeText(Car_costListing.this, e.toString(), Toast.LENGTH_LONG).show();
            }


            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            //Do anything with response..
        }
    }
}

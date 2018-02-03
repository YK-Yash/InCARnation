package com.example.dell.incarnation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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

public class Available_Cars extends AppCompatActivity {

    String JsonStr;
    List<car_available_model> data = new ArrayList<>();
    Car_adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available__cars);

        new RequestTask().execute();

    }


    class RequestTask extends AsyncTask<String, String, String> {

        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data='qwerty'|'hush'|'M'|40|'jks@jss'|'92839832'|'Delhi'|9";
        //String myurl = "http://172.16.30.124/DBMS/user_registration.php?data="+query_url;
        //String myurl = "http://172.16.30.124/DBMS/car_available.php";
        String myurl = "http://10.0.2.2/DBMS/car_available.php";

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
                    car_available_model car = new car_available_model();
                    car.car_id = json_data.getString("car_id");
                    car.car_name = json_data.getString("car_name");
                    car.kms = json_data.getString("kms");
                    data.add(car);
                }


                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.r1);
                mAdapter = new Car_adapter(Available_Cars.this,data);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Available_Cars.this));


                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getBaseContext(),
                        recyclerView, new ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        //Values are passing to activity & to fragment as well
                        Toast.makeText(Available_Cars.this, "Single Click on position :"+position,
                               Toast.LENGTH_SHORT).show();
//                        Toast.makeText(Available_Cars.this, "Reached",Toast.LENGTH_SHORT).show();

                        car_available_model pojo;// = new car_available_model();

                        pojo = data.get(position);
                        Intent in = new Intent(Available_Cars.this,Car_details_screen.class);
                        in.putExtra("c_id", pojo.getCar_id());
                        in.putExtra("c_name",pojo.getCar_name());
                        in.putExtra("c_kms",pojo.getKms());
                        startActivity(in);

                        ImageView picture=(ImageView)view.findViewById(R.id.ivFish);
                        picture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(Available_Cars.this, "Single Click on Image :"+position,
                                        //Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Toast.makeText(Available_Cars.this, "Long press on position :"+position,
                                Toast.LENGTH_LONG).show();
                    }
                }));

            }catch (JSONException e) {
                Toast.makeText(Available_Cars.this, e.toString(), Toast.LENGTH_LONG).show();
            }


                //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            //Do anything with response..
        }
    }


    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     *
     * - creating an innerclass implementing RevyvlerView.OnItemTouchListener
     * - Pass clickListener interface as parameter
     * */

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}

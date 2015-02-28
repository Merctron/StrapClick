package com.example.nissanoz.phone_por;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by nissanoz on 2/28/2015.
 */
public class AnsSession extends ActionBarActivity implements View.OnClickListener {


    int buttonPressed = 0;
    String session = "";
    Button a ,b, c, d, e;
    TextView display;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ans_session);


        display = (TextView) findViewById(R.id.textViewStatus);

        a = (Button) findViewById(R.id.buttona);
        b = (Button) findViewById(R.id.buttonb);
        c = (Button) findViewById(R.id.buttonc);
        d = (Button) findViewById(R.id.buttond);
        e = (Button) findViewById(R.id.buttone);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);

        Intent i = getIntent();
        session = i.getStringExtra("SessionName");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttona:
                buttonPressed = 1;
                new RequestTask().execute();
                break;
            case R.id.buttonb:
                buttonPressed = 2;
                new RequestTask().execute();
                break;
            case R.id.buttonc:
                buttonPressed = 3;
                new RequestTask().execute();
                break;
            case R.id.buttond:
                buttonPressed = 4;
                new RequestTask().execute();
                break;
            case R.id.buttone:
                buttonPressed = 5;
                new RequestTask().execute();
                break;
        }
    }

    private static JSONObject getJsonObjectFromMap(Map params) throws JSONException {

        //all the passed parameters from the post request
        //iterator used to loop through all the parameters
        //passed in the post request
        Iterator iter = params.entrySet().iterator();

        //Stores JSON
        JSONObject holder = new JSONObject();

        //using the earlier example your first entry would get email
        //and the inner while would get the value which would be 'foo@bar.com'
        //{ fan: { email : 'foo@bar.com' } }

        //While there is another entry
        while (iter.hasNext())
        {
            //gets an entry in the params
            Map.Entry pairs = (Map.Entry)iter.next();

            //creates a key for Map
            String key = (String)pairs.getKey();

            //Create a new map
            Map m = (Map)pairs.getValue();

            //object for storing Json
            JSONObject data = new JSONObject();

            //gets the value
            Iterator iter2 = m.entrySet().iterator();
            while (iter2.hasNext())
            {
                Map.Entry pairs2 = (Map.Entry)iter2.next();
                data.put((String)pairs2.getKey(), (String)pairs2.getValue());
            }

            //puts email and 'foo@bar.com'  together in map
            holder.put(key, data);
        }
        return holder;
    }


    private class RequestTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            HashMap values = new HashMap();

            values.put(new String("requestType"), "client");
            values.put(new String("session"), session);

            switch (buttonPressed) {
                case 1:
                    values.put(new String("answer"), "A");
                    break;
                case 2:
                    values.put(new String("answer"), "B");
                    break;
                case 3:
                    values.put(new String("answer"), "C");
                    break;
                case 4:
                    values.put(new String("answer"), "D");
                    break;
                case 5:
                    values.put(new String("answer"), "E");
                    break;
            }
            makePostRequest(values);
            return null;
        }

        protected Object makePostRequest(Map params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://6d6ba094.ngrok.com");

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("requestType", "client"));
            pairs.add(new BasicNameValuePair("session", session));
            switch (buttonPressed) {
                case 1:
                    pairs.add(new BasicNameValuePair("answer", "A"));
                    break;
                case 2:
                    pairs.add(new BasicNameValuePair("answer", "B"));
                    break;
                case 3:
                    pairs.add(new BasicNameValuePair("answer", "C"));
                    break;
                case 4:
                    pairs.add(new BasicNameValuePair("answer", "D"));
                    break;
                case 5:
                    pairs.add(new BasicNameValuePair("answer", "E"));
                    break;
            }

            //sets the post request as the resulting string
            //httpPost.setEntity(se);
            //sets a request header so the page receving the request
            //will know what to do with it
            httpPost.setHeader("content-type", "application/json");
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            InputStream inputStream = null;
            String responseString = "";
            try {
                HttpResponse httpResponse = httpClient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                responseString = sb.toString();
                Log.d("Http Post Response:", responseString);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
           Log.d("Status", "Success");
        }
    }
}



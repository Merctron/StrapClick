package com.example.nissanoz.phone_por;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by nissanoz on 2/28/2015.
 */
public class AnsSession extends ActionBarActivity implements View.OnClickListener {

    int buttonPressed = 0;
    Bundle extras = getIntent().getExtras();
    String session, name;
    Button a ,b, c, d, e, fetch;
    TextView display;
    RadioButton toggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ans_session);
        display = (TextView) findViewById(R.id.tView);
        toggle = (RadioButton) findViewById(R.id.bRadio);
        a = (Button) findViewById(R.id.buttona);
        b = (Button) findViewById(R.id.buttonb);
        c = (Button) findViewById(R.id.buttonc);
        d = (Button) findViewById(R.id.buttond);
        e = (Button) findViewById(R.id.buttone);
        fetch = (Button) findViewById(R.id.bFetch);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        toggle.setOnClickListener(this);
        fetch.setOnClickListener(this);
        Intent intent = getIntent();
        session =  extras.getString("session");
        name = extras.getString("name");
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
            case R.id.bFetch:
                buttonPressed = 6;
                new RequestTask().execute();
                break;
            case R.id.bRadio:
                toggle.toggle();
                if(toggle.getText() == "Presentation"){
                    toggle.setText("Mobile");
                }
                else{
                    toggle.setText("Presentation");
                }
        }
    }

    public void onBackPressed()
    {
        backButtonHandler();
        return;
    }

    public void backButtonHandler()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnsSession.this);
        LayoutInflater inflater = AnsSession.this.getLayoutInflater();

        alertDialog.setTitle("Leaving?");
        alertDialog.setView(inflater.inflate(R.layout.leave2, null));

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(AnsSession.this, MainActivity.class);
                startActivity(i);
            }
        });
        alertDialog.setNegativeButton("No",	new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    private class RequestTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            HashMap values = new HashMap();
            values.put(new String("requestType"), "client");
            values.put(new String("session"), session);
            values.put(new String("name"), name);

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
                case 6:
                    values.put(new String("answer"), "F");
                    break;
            }
            makePostRequest(values);
            return null;
        }

        protected Object makePostRequest(Map params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://6d6ba094.ngrok.com");

            JSONObject holder = new JSONObject(params);

            StringEntity se = null;
            try {
                se = new StringEntity(holder.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            post.setEntity(se);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");

            InputStream inputStream = null;
            String result = "";
            try {
                HttpResponse httpResponse = client.execute(post);
                inputStream = httpResponse.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                result = sb.toString();
                Log.d("Http Post Response:", result);
                JSONObject rson = new JSONObject(result);
                String question = rson.optString("question");
                JSONArray answers = rson.optJSONArray("answers");
                if ((question != null) && (answers != null) && (toggle.getText() == "Mobile")) {
                    display.setText(question);
                    for (int i = 0; i < answers.length(); i++)
                        switch (i) {
                            case 0:
                                a.setText(answers.getString(i));
                                break;
                            case 1:
                                b.setText(answers.getString(i));
                                break;
                            case 2:
                                c.setText(answers.getString(i));
                                break;
                            case 3:
                                d.setText(answers.getString(i));
                                break;
                            case 4:
                                e.setText(answers.getString(i));
                                break;
                        }
                    Toast.makeText(getApplicationContext(), "Your selection has been received", Toast.LENGTH_SHORT).show();

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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



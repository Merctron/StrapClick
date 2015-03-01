package com.example.nissanoz.phone_por;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    Button connect, exit, about;
    EditText sess, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = (Button) findViewById(R.id.buttonconnect);
        about = (Button) findViewById(R.id.bAbout);
        exit = (Button) findViewById(R.id.bExit);
        sess = (EditText) findViewById(R.id.editTextsess);
        name = (EditText) findViewById(R.id.editTextname);
        connect.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        Builder alertDialog = new Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();

        alertDialog.setTitle("Leaving?");
        alertDialog.setView(inflater.inflate(R.layout.leave, null));

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonconnect:
                //Context context = getApplicationContext();
                //ConnectivityManager check = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
                //NetworkInfo[] info = check.getAllNetworkInfo();
                //int test = 1;
                //for (int i = 0; i < info.length; i++) {
                //if (info[i].getState() == NetworkInfo.State.DISCONNECTED) {
                //   test = 0;
                // }
                //}

                if (sess.getText().toString() != "")
                {
                    Intent i = new Intent(MainActivity.this, AnsSession.class);
                    i.putExtra("SessionName", sess.getText().toString());
                    i.putExtra("Name", name.getText().toString());
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Session must not be left blank", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bExit:
                finish();
                break;
            case R.id.bAbout:
                Builder alertDialog = new Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                alertDialog.setTitle("Leaving?");
                alertDialog.setView(inflater.inflate(R.layout.about, null));
                alertDialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                break;
        }
    }
}

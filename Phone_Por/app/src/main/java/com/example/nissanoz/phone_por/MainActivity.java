package com.example.nissanoz.phone_por;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    Button connect;
    EditText sess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = (Button) findViewById(R.id.buttonconnect);
        sess = (EditText) findViewById(R.id.editTextsess);

        connect.setOnClickListener(this);
    }

    public void onBackPressed()
    {
      backButtonHandler();
      return;
    }

    public void backButtonHandler()
    {
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
        alertDialog.setNegativeButton("No",	new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonconnect) {
            Intent i = new Intent(MainActivity.this, AnsSession.class);
            i.putExtra("SessionName",sess.getText().toString());
            startActivity(i);
        }
    }
}

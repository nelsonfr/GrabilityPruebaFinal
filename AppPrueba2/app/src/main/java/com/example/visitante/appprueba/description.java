package com.example.visitante.appprueba;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class description extends AppCompatActivity {

    TextView t;
    JsonObjectInformer obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t = (TextView)findViewById(R.id.textdescription);
        Bundle bund = getIntent().getExtras();
        obj = bund.getParcelable("obj");
        t.setText(obj.description);

    }

}

package com.example.visitante.appprueba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {

    private ProgressBar myprogressBar;
    long milisegundos = 1000;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myprogressBar = (ProgressBar)findViewById(R.id.progressBar);
        myprogressBar.setMax(100);
        AsyncActivity act = new AsyncActivity();
        act.execute();

    }

    private void ExecSplash()
    {
        new CountDownTimer(milisegundos, 100)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                setProgressBar();
            }

            @Override
            public void onFinish() {
                Intent newForm = new Intent(Splash.this, MainActivity.class);
                startActivity(newForm);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        }.start();
    }

    private void setProgressBar()
    {
        progress = progress + 10;
        myprogressBar.setProgress(progress);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private  class AsyncActivity extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params) {
            return isOnline();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(!aBoolean)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);

                builder.setTitle("Sin conexion a internet");

                builder.setMessage("Para que la aplicacion se ejecute por primera vez, necesita de internet");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Splash.this.finish();
                    }
                });
                    builder.show();
                }
            else
            {
                ExecSplash();
            }
            }
    }


}


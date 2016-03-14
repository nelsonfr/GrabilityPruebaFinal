package com.example.visitante.appprueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int j;

    List<String> categories = Arrays.asList("Games", "Photo & Video", "Social Networking", "Music", "Travel", "Education", "Entertainment", "Navigation" );
    static ArrayList<JsonObjectInformer> GamesList = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> PhotoYVideo = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> SocialNetworking = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> Music = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> Travel = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> Education = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> Productivity = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> Entertainment = new ArrayList<JsonObjectInformer>();
    static ArrayList<JsonObjectInformer> Navigation = new ArrayList<JsonObjectInformer>();
    static ProgressDialog progress;

    JSONArray array;
    String url = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    Thread worker;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if(GamesList.size() == 0) {
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();
            progress = new ProgressDialog(this);
            progress.setTitle("Loading Json");
            progress.setMessage("Wait while loading Json data from server...");
            progress.setCancelable(false);
            progress.show();
        }
        ListView view = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(this, R.layout.simplerow, categories);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<JsonObjectInformer> listToPass = null;

                switch (position) {
                    case 0:
                        listToPass = GamesList;
                        break;
                    case 1:
                        listToPass = PhotoYVideo;
                        break;
                    case 2:
                        listToPass = SocialNetworking;
                        break;
                    case 3:
                        listToPass = Music;
                        break;
                    case 4:
                        listToPass = Travel;
                        break;
                    case 5:
                        listToPass = Education;
                        break;
                    case 6:
                        listToPass = Entertainment;
                        break;
                    case 7:
                        listToPass = Navigation;
                        break;
                }

                Intent inte = new Intent(getApplicationContext(), AppList.class);
                Bundle bund = new Bundle();
                bund.putParcelableArrayList("ListOfApps", listToPass);
                inte.putExtras(bund);
                startActivityForResult(inte, 100);
                overridePendingTransition(R.anim.fadein, R.anim.zoomout);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            JsonParser parser = new JsonParser();
            JSONArray jsonArray = parser.getJSONFromUrl(url);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {

                    String str;
                    JSONObject obj = jsonArray.getJSONObject(i);
                    JsonObjectInformer informer = new JsonObjectInformer(obj);

                    switch (informer.categoria)
                    {
                        case "Games" :
                            GamesList.add(informer);
                            break;

                        case "Photo & Video" :
                            PhotoYVideo.add(informer);
                            break;

                        case "Social Networking" :
                            SocialNetworking.add(informer);
                            break;

                        case "Music" :
                            Music.add(informer);
                            break;

                        case "Travel" :
                            Travel.add(informer);
                            break;

                        case "Education" :
                            Education.add(informer);
                            break;

                        case "Entertainment" :
                            Entertainment.add(informer);
                            break;

                        case "Navigation" :
                            Navigation.add(informer);
                            break;
                    }

                } catch (JSONException e) {

                }
            }
          return "";
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progress.dismiss();
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.visitante.appprueba/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.visitante.appprueba/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

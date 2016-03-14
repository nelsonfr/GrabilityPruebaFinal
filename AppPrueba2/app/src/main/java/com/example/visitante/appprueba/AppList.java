package com.example.visitante.appprueba;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AppList extends AppCompatActivity {


    static ArrayList<JsonObjectInformer> arr;
    static LruCache<String, Bitmap> memoryCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final int maxMemorySize = (int)Runtime.getRuntime().maxMemory()/1024;
        final int cacheSize = maxMemorySize/10;
        if(memoryCache == null) {
            memoryCache = new LruCache<String, Bitmap>(cacheSize) {

                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() / 1024;
                }
            };
        }
        Bundle bund = getIntent().getExtras();
        if(savedInstanceState != null)
        {
            arr = savedInstanceState.getParcelableArrayList("saved");
        }
        else {
            arr = bund.getParcelableArrayList("ListOfApps");
        }
        if(arr != null) {
            getSupportActionBar().setTitle(arr.get(0).categoria);
            ListView view = (ListView) findViewById(R.id.appListView);
            view.setAdapter(new ListAdapter(this, arr));
            view.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Intent inte = new Intent(getApplicationContext(), description.class);
                    Bundle bund = new Bundle();
                    bund.putParcelable("obj", arr.get(position));
                    inte.putExtras(bund);
                    startActivityForResult(inte, 100);
                    overridePendingTransition(R.anim.fadein, R.anim.zoomout);
                }
            });
        }
    }
    public static Bitmap getBitmapFromMemoryCache(String key)
    {
        return memoryCache.get(key);
    }

    public static void setBitmapToMemoryCache(String key, Bitmap value)
    {
        if(getBitmapFromMemoryCache(key) == null)
        {
            memoryCache.put(key, value);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("saved", arr);
    }

}

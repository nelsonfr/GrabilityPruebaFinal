package com.example.visitante.appprueba;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by visitante on 11/03/2016.
 */
public class JsonParser {

    static InputStream iStream;
    static JSONObject JsonObject;
    static JSONArray JsonArray;
    static String Json;

    public JsonParser(){}

    public JSONArray getJSONFromUrl(String url)
    {
        StringBuilder build = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {

            HttpResponse response;
            response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if(statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;

                while((line = reader.readLine()) != null) {
                    build.append(line);
                }
            }
            else
            {
                Log.e("==>", "Failed ");
            }

        } catch(ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){

        }

        try
        {
            JsonObject = new JSONObject(build.toString());
            JSONObject ob = JsonObject.getJSONObject("feed");
            JsonArray =  ob.getJSONArray("entry");
        }
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing");
        }
        return JsonArray;
    }
}

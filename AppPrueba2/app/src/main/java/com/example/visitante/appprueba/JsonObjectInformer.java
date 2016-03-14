package com.example.visitante.appprueba;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by visitante on 11/03/2016.
 */
public class JsonObjectInformer implements Parcelable{

    JSONObject obj;
    public String title;
    public String imageUrl;
    public String description;
    public String categoria;


    public JsonObjectInformer(JSONObject obj){

        this.obj = obj;
        title = getObjectTitle();
        imageUrl = getObjectUrlImage(0);
        description = getObjectDescription();
        categoria = getObjectCategory();
    }

    private String getObjectTitle()
    {
        String str = "";
        try {
            JSONObject obje = obj.getJSONObject("title");
            str = obje.getString("label");
        }catch (JSONException e){}
        return str;
    }

    private String getObjectUrlImage(int index)
    {
        JSONArray obje;
        String[] strArray = null;

        try
        {
            obje = obj.getJSONArray("im:image");
            strArray = new String[obje.length()];

            for(int i = 0; i < obje.length(); i++)
            {
                JSONObject objecjs = obje.getJSONObject(i);
                strArray[i] = objecjs.getString("label");
            }
        }catch (JSONException e){}

        return strArray[index];

    }

    private String getObjectDescription()
    {
        String str = "";
        try {
            JSONObject obje = obj.getJSONObject("summary");
            str = obje.getString("label");
        }catch (JSONException e){}
        return str;

    }

    private String getObjectCategory()
    {
        String str = "";
        try {
            JSONObject obje = obj.getJSONObject("category");
            JSONObject obje2 = obje.getJSONObject("attributes");
            str = obje2.getString("term");
        }catch (JSONException e){}
        return str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.description);
        dest.writeString(this.categoria);
    }

    public JsonObjectInformer(Parcel in)
    {
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.description = in.readString();
        this.categoria = in.readString();
    }

    public static final Parcelable.Creator<JsonObjectInformer> CREATOR = new Parcelable.Creator<JsonObjectInformer>()
    {
        public JsonObjectInformer createFromParcel(Parcel in) {
            return new JsonObjectInformer(in);
        }

        public JsonObjectInformer[] newArray(int size) {
            return new JsonObjectInformer[size];
        }
    };
}

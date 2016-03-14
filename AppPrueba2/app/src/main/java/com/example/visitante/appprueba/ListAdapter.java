package com.example.visitante.appprueba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by visitante on 12/03/2016.
 */
public class ListAdapter extends BaseAdapter {

    private ArrayList listOfData;
    private LayoutInflater layoutInflater;

    public ListAdapter(Context context, ArrayList listData)
    {
        this.listOfData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listOfData.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  holder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.approw, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            JsonObjectInformer item = (JsonObjectInformer)listOfData.get(position);
            holder.title.setText(item.title);
            if (holder.imageView != null)
            {
                new ImageDownloaderTask(holder.imageView).execute(item.imageUrl);
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        ImageView imageView;
    }
}

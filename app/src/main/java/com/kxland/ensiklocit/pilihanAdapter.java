package com.kxland.ensiklocit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class pilihanAdapter extends ArrayAdapter<pilihan>{

    Context context;
    int layoutResourceId;
    pilihan data[] = null;

    public pilihanAdapter(Context context, int layoutResourceId, pilihan[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PilihanHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PilihanHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (PilihanHolder)row.getTag();
        }

        pilihan x = data[position];
        holder.txtTitle.setText(x.title);
        holder.imgIcon.setImageResource(x.icon);

        return row;
    }

    static class PilihanHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}

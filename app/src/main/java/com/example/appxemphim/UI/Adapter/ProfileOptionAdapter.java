package com.example.appxemphim.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appxemphim.Model.ProfileOption;
import com.example.appxemphim.R;

import java.util.List;

public class ProfileOptionAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<ProfileOption> profileOptionList;

    public ProfileOptionAdapter(Context context, int Layout, List<ProfileOption> list) {
        myContext = context;
        myLayout = Layout;
        profileOptionList = list;
    }

    @Override
    public int getCount() {
        return profileOptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater  inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);

        // anhxa
        ImageView imageView =convertView.findViewById(R.id.imageViewIcon);
        imageView.setImageResource(profileOptionList.get(position).icon);

        TextView textView = convertView.findViewById(R.id.textViewOption);
        textView.setText(profileOptionList.get(position).title);

        return convertView;
    }
}

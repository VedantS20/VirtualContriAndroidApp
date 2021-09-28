package com.vedant.virtualcontrife;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class listViewAdapter extends ArrayAdapter<Group> {
    public listViewAdapter(@NonNull Context context, int resource, @NonNull List<Group> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            if(v == null)
            {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v =inflater.inflate(R.layout.list_item , null);
            }
            Group group =getItem(position);
        ImageView imageView =v.findViewById(R.id.img);
        TextView grpname = v.findViewById(R.id.group_name);
        TextView description = v.findViewById(R.id.group_des);
        imageView.setImageURI(group.getImageId());
        grpname.setText(group.getGroupName());
        description.setText(group.getDescription());
        return v;
    }
}

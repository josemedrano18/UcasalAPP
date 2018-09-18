package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class ExpLVAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> listFacultad;
    private Map<String, ArrayList<String>> mapChild;
    private Context context;

    public ExpLVAdapter(ArrayList<String> listFacultad, Map<String, ArrayList<String>> mapChild, Context context) {
        this.listFacultad = listFacultad;
        this.mapChild = mapChild;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return listFacultad.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mapChild.get(listFacultad.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listFacultad.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mapChild.get(listFacultad.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       String tituloFacultad = (String) getGroup(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elv_group,null);
        TextView tvGroup = (TextView)convertView.findViewById(R.id.tvGroup);
        tvGroup.setText(tituloFacultad);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvGroup.setTextAppearance(android.R.style.TextAppearance_Large);
        }
        tvGroup.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String item = (String) getChild(groupPosition,childPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elv_child,null);
        TextView tvChild = (TextView) convertView.findViewById(R.id.tvChild);
        tvChild.setText(item);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvChild.setTextAppearance(android.R.style.TextAppearance_Medium);
        }
        tvChild.setTextColor(Color.DKGRAY);
        tvChild.setTypeface(null, Typeface.BOLD);

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

package com.ois.onistech.gmb;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ois.onistech.gmb.DataClass.Category;
import com.ois.onistech.gmb.DataClass.SubCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Header> _listDataHeader; // header titles
   // List<List<List<Category>>> _listDataHeader1;
    List<Category> _list1;
    // child data in format of header title, child title
    private HashMap<String, List<Subcategory>> _listDataChild;
    HashMap<String, List<SubCategory>> _listDataChild1;
    public ExpandableListAdapter(Context context, List<Category> list1,
                                 HashMap<String, List<SubCategory>> listChildData) {
        this._context = context;
        this._list1 = list1;
        this._listDataChild1 = listChildData;


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild1.get(this._list1.get(groupPosition).cat_id)
                .get(childPosititon).cat_name;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

//        Subcategory cat = _listDataChild.get(_listDataHeader.get(groupPosition).id).get(childPosition);
//        cat.image;
//        cat.sname;
        TextView txtListChild = convertView
                .findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size=0;
        try {
             size= this._listDataChild1.get(this._list1.get(groupPosition).cat_id)
                    .size();
        }
        catch (Exception e)
        {
           // Toast.makeText(_context, "exception", Toast.LENGTH_SHORT).show();
        }
       return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._list1.get(groupPosition).cat_name;
    }

    @Override
    public int getGroupCount() {

        return this._list1.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
       String s= _list1.get(groupPosition).getCat_name();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader =  convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        ImageView iv =  convertView
                .findViewById(R.id.iv);
        if (isExpanded) {

        //...
            if(getChildrenCount(groupPosition) == 0)
            {
                Toast.makeText(_context, "No Products Available", Toast.LENGTH_SHORT).show();
            }
            else
            {
                iv.setImageResource(R.drawable.ic_minus);
            }
          //  getChildrenCount(groupPosition);
              //  System.out.println( getChildrenCount(groupPosition));
        } else {
            iv.setImageResource(R.drawable.ic_add);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}
package cn.edu.cugb.information_program.go_test;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class submenuAdapter extends ArrayAdapter<submenu> {
    private int resourceId;
    public submenuAdapter(Context context, int textViewResourceId, List<submenu>objects) {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        submenu submenu =getItem(position);
        View view;
        ViewHoleder viewHoleder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHoleder=new ViewHoleder();
            viewHoleder.submenuInmage=(ImageView) view.findViewById(R.id.submenu_image);
            viewHoleder.submenuName=(TextView)view.findViewById(R.id.submenu_name);
            view.setTag(viewHoleder);//将ViewHoleder储存在View中
        }else {
            view=convertView;
            viewHoleder=(ViewHoleder) view.getTag();
        }
        viewHoleder.submenuInmage.setImageResource(submenu.getImageId());
        viewHoleder.submenuName.setText(submenu.getName());
        return view;
    }
    class ViewHoleder{
        ImageView submenuInmage;
        TextView submenuName;
    }
}

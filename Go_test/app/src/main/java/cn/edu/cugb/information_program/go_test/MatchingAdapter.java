package cn.edu.cugb.information_program.go_test;

/**
 * Created by zhangxinwen on 2018-05-05.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MatchingAdapter extends ArrayAdapter<matching> {

    private int resourceId;

    public MatchingAdapter(Context context, int textViewResourceId,
                           List<matching> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        matching match = getItem(position); // 获取当前项的match实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            //viewHolder.fruitImage = (ImageView) view.findViewById (R.id.fruit_image);
            viewHolder.userName = (TextView) view.findViewById (R.id.user_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        // viewHolder.fruitImage.setImageResource(matching.getImageId());
        viewHolder.userName.setText(match.getName());
        return view;
    }

    class ViewHolder {

        ImageView fruitImage;

        TextView userName;

    }

}
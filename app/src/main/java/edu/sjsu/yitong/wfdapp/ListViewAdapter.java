package edu.sjsu.yitong.wfdapp;

import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

/**
 * Created by yitong on 3/11/18.
 */

public class ListViewAdapter extends ArrayAdapter<String> {
    private Activity activity;
    private List<String> ingredients;


    public ListViewAdapter(Activity context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.ingredients = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getItem(position));

        //handling buttons event
        holder.btnPlus.setOnClickListener(onPlusListener(position, holder));
        holder.btnMinus.setOnClickListener(onMinusListener(position, holder));

        return convertView;
    }

    private View.OnClickListener onPlusListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient i = new Ingredient(holder.name.getText().toString());
                i.amount += 1;
                holder.name.setText(i.toString());
                if (i.amount > 0) {
                    holder.name.setPaintFlags(0);
                }
            }
        };
    }

    private View.OnClickListener onMinusListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient i = new Ingredient(holder.name.getText().toString());
                if (i.amount > 0) {
                    i.amount -= 1;
                }
                holder.name.setText(i.toString());
                if (i.amount == 0) {
                    holder.name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        };
    }

    private class ViewHolder {
        private TextView name;
        private View btnPlus;
        private View btnMinus;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v) {
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnPlus = v.findViewById(R.id.plus);
            btnMinus = v.findViewById(R.id.minus);
            name = (TextView) v.findViewById(R.id.name);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        }
    }
}

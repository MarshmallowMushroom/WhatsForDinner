package edu.sjsu.yitong.wfdapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView logo = null;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private TableLayout mTableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mTableLayout = (TableLayout) findViewById(R.id.main_screen);

        logo = (ImageView)findViewById(R.id.wfdlogo);
        logo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                viewAppInfo(view);
            }
        });

    }

    /** Called when the user taps the Meal button */
    public void viewAppInfo(View view) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.activity_app_info,null);
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);

        mPopupWindow.showAtLocation(mTableLayout, Gravity.CENTER,0,0);
        // Do something in response to button
    }

//    final ImageView mealButton = findViewById(R.id.meal);
//
//    mealButton.setOnClickListener(new View.onClickListner() {
//        public void onClick(View v) {
//
//        }
//    });




}

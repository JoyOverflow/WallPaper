package ouyj.hyena.com.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    LinearLayout defaultAlignContainer;
    LinearLayout centerAlignContainer;
    LinearLayout autoAlignContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //绑定按钮事件
        setViewAndClick();
    }
    private void setViewAndClick() {
        b1 =  findViewById(R.id.b1);
        b2 =  findViewById(R.id.b2);
        b3 =  findViewById(R.id.b3);
        b4 =  findViewById(R.id.b4);
        b5 =  findViewById(R.id.b5);
        b6 =  findViewById(R.id.b6);
        b7 =  findViewById(R.id.b7);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);

        //绑定事件
        defaultAlignContainer = findViewById(R.id.default_align_container);
        for(int i = 0; i < defaultAlignContainer.getChildCount(); i++) {
            Button b = (Button) defaultAlignContainer.getChildAt(i);
            b.setOnClickListener(this);
        }
        centerAlignContainer = findViewById(R.id.center_align_container);
        for(int i = 0; i < centerAlignContainer.getChildCount(); i++) {
            Button b = (Button) centerAlignContainer.getChildAt(i);
            b.setOnClickListener(this);
        }
        autoAlignContainer = findViewById(R.id.auto_align_container);
        for(int i = 0; i < autoAlignContainer.getChildCount(); i++) {
            Button b = (Button) autoAlignContainer.getChildAt(i);
            b.setOnClickListener(this);
        }
    }

    /**
     * 按钮点击事件处理
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v == b1) {
            // simply use showAsPointer
            create().showAsPointer(v);
        } else if(v == b2) {
            // change pointer image
            PointerPopupWindow p = create();
            p.setPointerImageRes(R.drawable.ic_arrow);
            p.showAsPointer(v);
        } else if(v == b3) {
            // set offset directly
            PointerPopupWindow p = create();
            p.showAsPointer(v, 10);
        } else if(v == b4) {
            // set offset directly
            PointerPopupWindow p = create();
            p.showAsPointer(v, 20, 20);
        } else if(v == b5) {
            // set offset directly
            PointerPopupWindow p = create();
            p.showAsPointer(v, -20, -20);
        } else if(v == b6) {
            // set screen padding
            PointerPopupWindow p = create();
            p.setPointerImageRes(R.drawable.ic_arrow);
            p.setMarginScreen(20);
            p.showAsPointer(v);
        } else if(v == b7) {
            // set margin screen
            PointerPopupWindow p = create();
            p.setPointerImageRes(R.drawable.ic_arrow);
            p.setMarginScreen(50);
            p.showAsPointer(v);
        } else if(v.getParent() == defaultAlignContainer) {
            // set align mode
            PointerPopupWindow p = create();
            p.setAlignMode(PointerPopupWindow.AlignMode.DEFAULT);//which is default
            p.showAsPointer(v);
        } else if(v.getParent() == centerAlignContainer) {
            // set align mode
            PointerPopupWindow p = create();
            p.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
            p.showAsPointer(v);
        } else if(v.getParent() == autoAlignContainer) {
            // set align mode
            PointerPopupWindow p = create();
            p.setAlignMode(PointerPopupWindow.AlignMode.AUTO_OFFSET);
            p.showAsPointer(v);
        }
    }


    private PointerPopupWindow create() {
        PointerPopupWindow p = new PointerPopupWindow(this, getResources().getDimensionPixelSize(R.dimen.popup_width));
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText("Popup");
        textView.setTextSize(40);
        textView.setTextColor(Color.WHITE);
        p.setContentView(textView);
        p.setBackgroundDrawable(new ColorDrawable(0xb3111213));
        p.setPointerImageRes(R.drawable.ic_popup_pointer);
        return p;
    }




}

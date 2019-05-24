package ouyj.hyena.com.seeleslier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //往线性布局内加入一个视图
        LinearLayout l= findViewById(R.id.sliderbox);
        Slider s=new Slider(this);
        l.addView(s.initView());
    }
}

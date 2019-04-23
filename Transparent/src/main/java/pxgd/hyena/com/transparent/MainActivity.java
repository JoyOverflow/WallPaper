package pxgd.hyena.com.transparent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button colorChanger;
    RelativeLayout r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //查找视图引用
        r1 = findViewById(R.id.r1);
        colorChanger = findViewById(R.id.button);

        //单击按钮时触发
        colorChanger.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //创建随机数（默认当前系统时间的毫秒数作为种子）
                Random rand = new Random();
                //产生随机为0-256的整数,不包括256
                int a = rand.nextInt(256);
                int r = rand.nextInt(256);
                int g = rand.nextInt(256);
                int b = rand.nextInt(256);
                //生成随机颜色（设置布局控件的背景色）
                int randomColor = Color.argb(a,r,g,b);
                r1.setBackgroundColor(randomColor);
            }
        });

        //长按按钮时触发
        colorChanger.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                //设置布局控件的背景色
                int reset = Color.argb(255,255,255,255);
                r1.setBackgroundColor(reset);
                return true;
            }
        });
    }
}

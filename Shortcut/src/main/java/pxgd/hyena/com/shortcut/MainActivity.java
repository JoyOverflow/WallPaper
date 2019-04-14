package pxgd.hyena.com.shortcut;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //查找按钮视图设置事件侦听
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        //有意图时取出意图数据（定位到另一个活动）
        Intent intent = getIntent();
        if (intent != null) {
            String tName = intent.getStringExtra("tName");
            if (tName != null) {

                //发送意图启动指定活动
                Intent redirectIntent = new Intent();
                redirectIntent.setClass(MainActivity.this,TabooActivity.class);
                redirectIntent.putExtra("tName", tName);
                startActivity(redirectIntent);
            }
        }
    }
    @Override
    public void onClick(View v) {
        //发送意图启动指定活动（传送数据）
        Intent intent = new Intent(
                MainActivity.this,
                TabooActivity.class
        );
        switch (v.getId()) {
            case R.id.button1:
                intent.putExtra("tName", "玉米吧");
                break;
            case R.id.button2:
                intent.putExtra("tName", "土豆吧");
                break;
        }
        startActivity(intent);
    }
}

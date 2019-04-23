package pxgd.hyena.com.mydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,View.OnLongClickListener{

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnTest).setOnClickListener(this);
        findViewById(R.id.btnTest).setOnLongClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick！");
    }
    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG, "nLongClick！");

        //true为仅长按操作（事件已消耗），不加短点击事件
        return true;
    }
}

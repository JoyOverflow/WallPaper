package pxgd.hyena.com.shortcut;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TabooActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private String tName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taboo);

        //获取意图数据为文本显示视图赋值
        Intent intent = getIntent();
        tName = intent.getStringExtra("tName");
        textView = findViewById(R.id.textview1);
        textView.setText("本贴吧名称: " + tName);


        //查找按钮视图（单击事件中为活动添加快截方式）
        button =  findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tName == null) {
                    tName = getString(R.string.app_name);
                }
                addShortCut(tName);
            }
        });
    }

    private void addShortCut(String tName) {
        //安装快捷方式的Intent
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        //快捷方式的名称和图标以及是否可重复（根据快捷方式的名称判断）
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, tName);
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
                this,
                R.mipmap.ic_launcher
        );
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        shortcut.putExtra("duplicate", false);

        //快捷方式关联的活动
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.putExtra("tName", tName);
        intent.setClassName(this, MainActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //发送广播
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        sendBroadcast(shortcut);
    }
}

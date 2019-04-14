package pxgd.hyena.com.somecustom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //E:\[2019]Temps\20190413\android-custom-view-master
        //https://www.cnblogs.com/mengdd/p/3837592.html

        //设置动画
        ImageView ivAnim = findViewById(R.id.ivAnim);
        ivAnim.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotale));

        //查找按钮视图设置事件侦听器（跳转到指定活动）
        Button btnTrans = findViewById(R.id.btnTrans);
        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TransActivity.class));
            }
        });
        Button btnTool = findViewById(R.id.btnToolbar);
        btnTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
            }
        });
        Button btnTips = findViewById(R.id.btnTips);
        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TipsActivity.class));
            }
        });
        Button btnShortcut = findViewById(R.id.btnShortcut);
        btnShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addShortcut();
                Toast.makeText(
                        MainActivity.this,
                        "Shortcut was created in Home Screen",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    /**
     * 添加快捷方式到手机桌面（需要申请权限）
     * 原理是向桌面应用(launcher)发送相应广播
     */
    private void addShortcut() {

        //设置关联应用
        //Intent intent = new Intent("android.intent.action.VIEW");
        //intent.setClassName(this, ShortcutActivity.class.getName());

        //设置启动的应用
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClassName(this, ShortcutActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        //开始生成快捷方式（创建安装快捷方式的意图）
        Intent intent2 = new Intent();
        intent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");

        //快捷入口的应用活动
        //intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
        intent2.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

        //名字和图标
        intent2.putExtra("android.intent.extra.shortcut.NAME", getString(R.string.app_name));
        intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE",
                Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher));

        //设置不允许重复创建（extra.shortcut.INTENT键的value）
        intent2.putExtra("duplicate", false);
        //intent2.setFlags(524288);
        //发送广播
        sendBroadcast(intent2);
    }
}

package pxgd.hyena.com.mydemo;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "外部存储未挂载！");
        }
        else
            Log.d(TAG, "外部存储已就续！");


        //DIRECTORY_DCIM 相机拍摄的图片和视频保存位置
        //DIRECTORY_PICTURES 下载图片保存的位置
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = new File(dir, "/yidian_11832519262.jpg");

        //得到（内部存储）data/data/包名/files路径
        //File dir = getFilesDir();
        //File file = new File(dir, "/104259.jpg");

        switch (item.getItemId()) {
            case R.id.action_share1:
                Log.d(TAG, file.toURI().toString());
                shareToFriend(file);
                return true;
            case R.id.action_share2:
                Log.d(TAG, file.toURI().toString());
                shareToTimeLine(file);
                return true;
            case R.id.action_share3:
                File video = new File(dir, "/test.mp4");
                shareToFriendVideo(video);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void shareToFriend(File file) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        //intent.setFlags(0x3000001);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(intent);
    }
    private void shareToFriendVideo(File file) {

        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("video/*");
        //intent.setFlags(0x3000001);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(intent);
    }
    private void shareToTimeLine(File file) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        //intent.setFlags(0x3000001);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(intent);
    }










}

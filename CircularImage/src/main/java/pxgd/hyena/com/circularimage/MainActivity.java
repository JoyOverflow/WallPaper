package pxgd.hyena.com.circularimage;

import android.app.WallpaperManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private int resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://blog.csdn.net/songzi1228/article/details/78962327
        //https://blog.csdn.net/ainiyiwan123/article/details/79788888
        RelativeLayout r_layout = findViewById(R.id.r_layout);
        //r_layout.setBackground(R.drawable.women);
        //r_layout.setImageBitmap(BitmapFactory.decodeFile(photoPath));

        //E:\AndoridWork\ImageView
        //悬浮按钮
        //https://github.com/makovkastar/FloatingActionButton
        //https://github.com/Clans/FloatingActionButton
        //https://www.jianshu.com/p/9b0304b17f26
        //https://blog.csdn.net/gaolh89/article/details/79759404

        resId=R.drawable.mountain;
        img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.mountain);

        //大于ImageView的图像被缩放到填充整个ImageView并居中显示
        //小于ImageView的图像会被拉伸到填充整个ImageView
        //非ImageView宽高比例的图像会裁剪掉多余部分（ImageView内不会有多余宽或高）
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);


        /**
         * app:fabSize="mini" FAB大小，normal=56dp或mini=40dp
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //设置手机的壁纸
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                try {
                    wallpaperManager.setResource(resId);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(
                        MainActivity.this,
                        "手机壁纸已更改！",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        /*
        CircularImageButton colorChanger = findViewById(R.id.circleButton);
        colorChanger.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        MainActivity.this,
                        "Clicked" ,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        */


        VImageButtonView btnImg1 = findViewById(R.id.pic1);
        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(
                        MainActivity.this,
                        "Click",
                        Toast.LENGTH_SHORT
                ).show();
                */
                resId=R.drawable.women;
                img.setImageResource(R.drawable.women);
            }
        });
        VImageButtonView btnImg2 = findViewById(R.id.pic2);
        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(
                        MainActivity.this,
                        "Click",
                        Toast.LENGTH_SHORT
                ).show();
                */
                resId=R.drawable.elves;
                img.setImageResource(R.drawable.elves);
            }
        });
        VImageButtonView btnImg3 = findViewById(R.id.pic3);
        btnImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(
                        MainActivity.this,
                        "Click",
                        Toast.LENGTH_SHORT
                ).show();
                */
                resId=R.drawable.centaur;
                img.setImageResource(R.drawable.centaur);
            }
        });
        VImageButtonView btnImg4 = findViewById(R.id.pic4);
        btnImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(
                        MainActivity.this,
                        "Click",
                        Toast.LENGTH_SHORT
                ).show();
                */
                resId=R.drawable.car_house;
                img.setImageResource(R.drawable.car_house);
            }
        });
        VImageButtonView btnImg5 = findViewById(R.id.pic5);
        btnImg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(
                        MainActivity.this,
                        "Click",
                        Toast.LENGTH_SHORT
                ).show();
                */
                resId=R.drawable.mountain;
                img.setImageResource(R.drawable.mountain);
            }
        });




    }

    /**
     * 沉浸模式（界面全屏，顶部状态栏和底部导航栏都不会显示）
     * @param hasFocus
     * */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}

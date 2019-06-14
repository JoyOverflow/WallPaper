package ouyj.hyena.com.roundimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ouyj.hyena.com.roundimage.view.HeadViewPager;
import ouyj.hyena.com.roundimage.view.PictureView;

public class MainActivity extends AppCompatActivity {

    private HeadViewPager headViewPager;
    private List<PictureView> pictureViews;
    private int[] images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new int[]{
                R.drawable.bg2,
                R.drawable.bigger1,
                R.drawable.text1,
                R.drawable.text2
        };

        //创建图像视图集合
        pictureViews = new ArrayList<>();
        for (int i = 0 ; i < images.length ; i ++){

            //设置图像
            PictureView v = new PictureView(this);
            v.setImage(images[i]);

            pictureViews.add(v);
        }

        //创建视图
        headViewPager = findViewById(R.id.viewpager_show);
        headViewPager.creatView(this, pictureViews);
    }
}

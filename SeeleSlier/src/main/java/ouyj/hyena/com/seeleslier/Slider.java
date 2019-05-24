package ouyj.hyena.com.seeleslier;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by wangmutian on 2018/1/11.
 */

public class Slider {
    private int currentItem = 0; // 当前图片的位置
    private ViewPager slider;

    private int[] mImageId={
            R.drawable.slider01,
            R.drawable.slider02,
            R.drawable.slider03,
            R.drawable.slider04,
            R.drawable.slider05,
            R.drawable.slider01,
            R.drawable.slider02
    };
    private ArrayList<ImageView> mimageViewList;
    private LinearLayout ll_layout;
    private int mPointDis;
    private int dis;
    //    private ImageView redpoint;
    int DELAY = 3600;//切换的延迟时间为2400毫秒
    boolean isAuto = true;//是否值自动轮播，默认为true
    boolean isFromUser = false;//用来标志用户是否滑动屏幕
    private static int  cursorpoint = 0;

    private Activity activity;

    public Slider(Activity activity){
        this.activity=activity;
    }
    public View initView(){
        View view = View.inflate(activity,R.layout.slider_item,null);

        slider= (ViewPager) view.findViewById(R.id.slider);
        ll_layout= (LinearLayout) view.findViewById(R.id.ll_layout);

        initData();
        slider.setAdapter(new sliderAdapter());
        slider.setCurrentItem(0);
        initdian(cursorpoint);

        //执行定时任务
        handler.sendEmptyMessageDelayed(1, DELAY);
        slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position >= (mImageId.length-2)){
                    position = position - mImageId.length + 2;
                }
                /*
                dis= (int) (mPointDis*positionOffset) + position*mPointDis;
                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) redpoint.getLayoutParams();
                params.leftMargin=dis;
                redpoint.setLayoutParams(params);

                ImageView image= (ImageView) ll_layout.getChildAt(position);
                image.setImageResource(R.drawable.shape_point_red);
                */
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                cursorpoint=position;
                if(position >= (mImageId.length-2)){
                    position = position - mImageId.length + 2;
                }
                initdian(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        //手指离开ViewPager的时候调用，发送延迟消息，自动轮播

                        isAuto = true;
                        if (isFromUser) {
                            isFromUser = false;
                            handler.sendEmptyMessageDelayed(1, DELAY);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //手指拖动ViewPager进行手动切换的时候，停止自动轮播

                        isAuto = false;
                        handler.removeMessages(1);
                        isFromUser = true;
                        break;
                    default:
                        break;
                }

                // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
                // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                if (state != ViewPager.SCROLL_STATE_IDLE) return;

                // 当视图在第一个时，将页面号设置为图片的最后一张。
                if (currentPosition == 0) {
                    slider.setCurrentItem(mimageViewList.size() - 2, false);

                } else if (currentPosition == mimageViewList.size() - 1) {
                    // 当视图在最后一个是,将页面号设置为图片的第一张。
                    slider.setCurrentItem(1, false);
                }
            }
        });

        return view;
    }

    private void initData(){
        mimageViewList=new  ArrayList<>();
        for(int i=0;i<mImageId.length;i++){
            ImageView view = new ImageView(activity);
            view.setBackgroundResource(mImageId[i]);
            mimageViewList.add(view);
        }
    }


    /**
     * 设置图像下方的红点点
     * @param position
     */
    private void initdian(int position){
        //暂注释，不要删除
        /*
        ll_layout.removeAllViews();
        for(int i=0;i<mImageId.length - 2;i++){
            ImageView dian = new ImageView(activity);

            if(position == i)
                dian.setImageResource(R.drawable.shape_point_red);
            else
                dian.setImageResource(R.drawable.shape_point_gray);

            LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            //设置左边距
            if(i > 0){
                param.leftMargin=10;
            }
            //设置布局参数
            dian.setLayoutParams(param);
            ll_layout.addView(dian);
        }
        */
    }

    /**
     * 自动轮播
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (isAuto) {
                    slider.setCurrentItem(cursorpoint, true);
                    cursorpoint++;
                    handler.sendEmptyMessageDelayed(1, DELAY);
                }
            }
        }
    };





    /**
     * 自定义适配器
     */
    class sliderAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mimageViewList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image=mimageViewList.get(position);
            container.addView(image);
            return image;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mimageViewList.get(position));
        }
    }

}

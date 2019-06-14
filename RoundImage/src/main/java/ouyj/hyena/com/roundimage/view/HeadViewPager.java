package ouyj.hyena.com.roundimage.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ouyj.hyena.com.roundimage.R;

public class HeadViewPager extends FrameLayout {

	private Context mContext;
	private ViewPager mViewPager;
	private List<Integer> mImageIds;
	private List<PictureView> mImageViews;
	private ViewGroup mViewGroup;
	private List<ImageView> tips;
	private int tipsChoseImgId;
	private int tipsUnchoseImgId;
	
	public HeadViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		creatView(context);
	}

	public HeadViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		creatView(context);
	}

	public HeadViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		creatView(context);
	}
	
	public HeadViewPager(Context context,List<PictureView> imgageList) {
		super(context);
		// TODO Auto-generated constructor stub
		creatView(context,imgageList);
	}
	
	public void creatView(Context context){
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.head_view_pager, this);
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		mViewGroup = (ViewGroup)findViewById(R.id.viewgroup);
		mImageViews = new ArrayList<PictureView>();
		mImageIds = new ArrayList<Integer>();
		tips = new ArrayList<ImageView>();
		tipsChoseImgId = R.drawable.img_bg_chose;
		tipsUnchoseImgId = R.drawable.img_bg_unchose;
		build();
	}
	
	public void creatView(Context context,List<PictureView> imgageList){
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.head_view_pager, this);
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		mViewGroup = (ViewGroup)findViewById(R.id.viewgroup);
		mImageViews = imgageList;
		mImageIds = new ArrayList<Integer>();
		tips = new ArrayList<ImageView>();
		tipsChoseImgId = R.drawable.img_bg_chose;
		tipsUnchoseImgId = R.drawable.img_bg_unchose;
		build();
	}
	
	public void build(){
		buildTips();
		mViewPager.setAdapter(new HeadViewPagerAdapter(mContext,mImageViews));
		//����Ĭ����ʾҳ��Ϊ��0ҳ
		mViewPager.setCurrentItem(0);
		//����ѡ��ҳ��ʱ�Ķ���
		mViewPager.setPageTransformer(true, new HeadViewPagerTransformer());
		//���û���View�ĸ�����Ĭ����3�������ʾ������5��
		mViewPager.setOffscreenPageLimit(4);
		//ҳ�淢���ı�ļ�����
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			//ѡ�����ı�
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				changeTips(arg0);
			}
			//�л�������
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			//����������ѡ��ı�
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	//��ʼ���ײ�����Բ����
	public void buildTips(){
		for (int i = 0 ; i < mImageViews.size() ; i ++){
        	ImageView imageView = new ImageView(mContext);  
            imageView.setLayoutParams(new LayoutParams(10,10));   
            if(i == 0){  
            	imageView.setBackgroundResource(tipsChoseImgId);  
            }else{  
            	imageView.setBackgroundResource(tipsUnchoseImgId);  
            }  
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(12,12));  
            layoutParams.leftMargin = 5;  
            layoutParams.rightMargin = 5;  
            tips.add(imageView); 
            mViewGroup.addView(imageView, layoutParams);  
        }
	}
	//��ѡ����ҳ�淢���ı�ʱ��������Ҳ��Ӧ�ı�
	public void changeTips(int index){
		for (int i = 0 ; i < tips.size() ; i ++){  
            if(i == index){  
                tips.get(i).setBackgroundResource(tipsChoseImgId);  
            }else{  
            	tips.get(i).setBackgroundResource(tipsUnchoseImgId);  
            }  
        }
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public ViewPager getmViewPager() {
		return mViewPager;
	}

	public void setmViewPager(ViewPager mViewPager) {
		this.mViewPager = mViewPager;
	}

	public List<PictureView> getmImageViews() {
		return mImageViews;
	}
	//�ı�ͼƬ����ʱ��Ҫ��������viewPager
	public void setmImageViews(List<PictureView> mImageViews) {
		this.mImageViews = mImageViews;
		this.mViewPager.notify();
		this.mViewPager.setCurrentItem(0);
		
	}

	public ViewGroup getmViewGroup() {
		return mViewGroup;
	}

	public void setmViewGroup(ViewGroup mViewGroup) {
		this.mViewGroup = mViewGroup;
	}

	public int getTipsChoseImgId() {
		return tipsChoseImgId;
	}

	public void setTipsChoseImgId(int tipsChoseImgId) {
		this.tipsChoseImgId = tipsChoseImgId;
	}

	public int getTipsUnchoseImgId() {
		return tipsUnchoseImgId;
	}

	public void setTipsUnchoseImgId(int tipsUnchoseImgId) {
		this.tipsUnchoseImgId = tipsUnchoseImgId;
	}
	
	
	
	
	
	
}

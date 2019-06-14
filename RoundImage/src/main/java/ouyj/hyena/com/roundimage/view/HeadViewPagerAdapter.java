package ouyj.hyena.com.roundimage.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HeadViewPagerAdapter extends PagerAdapter {

	private Context mContext;
	private List<PictureView> mList;
	
	public HeadViewPagerAdapter(Context context,List<PictureView> list){
		this.mContext = context;
		this.mList = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//Log.d("remove", mImageViews[position].hashCode() + "");
		container.removeView(mList.get(position));
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(mList.get(position),0);
		return mList.get(position);
	}

}

package ouyj.hyena.com.roundimage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import ouyj.hyena.com.roundimage.R;

/**
 * 存放一张图片
 */
public class PictureView extends RelativeLayout {

	private RoundImage roundView;

    /**
     * 构造方法
     * @param context
     * @param attrs
     * @param defStyle
     */
	public PictureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public PictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public PictureView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.picture_view, this);
		roundView = findViewById(R.id.myImage);
	}

    /**
     * 设置图像资源
     * @param id
     */
	public void setImage(int id){
		roundView.setImageResource(id);
	}
}

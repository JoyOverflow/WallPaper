package pxgd.hyena.com.nightbook.utils;

import android.graphics.drawable.Drawable;
import pxgd.hyena.com.nightbook.entity.Subject;
/**
 */
public class NavigationItem {
    private Subject mSubject;
    private Drawable mDrawable;
    public NavigationItem(Subject subject, Drawable drawable) {
        mSubject = subject;
        mDrawable = drawable;
    }
    public Subject getSubject() {
        return mSubject;
    }
    public void setText(Subject subject) {
        mSubject = subject;
    }
    public Drawable getDrawable() {
        return mDrawable;
    }
    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}

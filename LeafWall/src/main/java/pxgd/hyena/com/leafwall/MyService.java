package pxgd.hyena.com.leafwall;


import android.service.wallpaper.WallpaperService;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyService extends WallpaperService {

    private MyEngine engine;
    private static final String TAG = MainActivity.class.getSimpleName();
    public MyService() {
        //https://mcl-123.github.io/2019/01/28/aosp-Wallpaper-%E9%9D%99%E6%80%81%E5%A3%81%E7%BA%B8%E5%88%86%E6%9E%90/
    }

    /**
     * 返会一个Engine类对象（WallpaperService中的一个内部类）
     * @return
     */
    @Override
    public Engine onCreateEngine() {
        this.engine = new MyEngine();
        return engine;
    }

    private class MyEngine extends Engine implements GestureDetector.OnGestureListener {

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
        }
        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float
                yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset,
                    yPixelOffset);
        }
        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        /**
         * 壁纸可见和不可见
         * @param visible
         */
        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
        }

        /**
         * 返回SurfaceView的SurfaceHolder
         * @return
         */
        @Override
        public SurfaceHolder getSurfaceHolder() {
            return super.getSurfaceHolder();
        }

        //-----------------------------------------
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
        @Override
        public void onShowPress(MotionEvent e) {
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }
        @Override
        public void onLongPress(MotionEvent e) {
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}

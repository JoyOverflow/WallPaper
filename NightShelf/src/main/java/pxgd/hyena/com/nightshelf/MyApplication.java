package pxgd.hyena.com.nightshelf;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePalApplication;

public class MyApplication extends Application {
    private static Context context;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(context);
        requestQueue = Volley.newRequestQueue(context);
    }
    public static Context getContext() {
        return context;
    }
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}

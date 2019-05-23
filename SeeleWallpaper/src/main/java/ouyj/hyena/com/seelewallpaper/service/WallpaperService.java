package ouyj.hyena.com.seelewallpaper.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WallpaperService extends Service {
    public static final String SETTING_FILENAME = "Setting.xml";




    public WallpaperService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

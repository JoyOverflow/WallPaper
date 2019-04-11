package pxgd.hyena.com.leafwall;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setResource(R.raw.dragon);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        //发送系统Intent.ACTION_ATTACH_DATA
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("mimeType", "image/*");
        Uri uri = Uri.parse(MediaStore.Images.Media
                .insertImage(getActivity().getContentResolver(),
                        ((BitmapDrawable) wallpaper).getBitmap(), null, null));
        intent.setData(uri);
        startActivityForResult(intent, SET_WALLPAPER);
        */
    }
}

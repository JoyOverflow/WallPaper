package pxgd.hyena.com.snowbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pxgd.hyena.com.snowbook.utils.NavigationDrawerCallbacks;

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}

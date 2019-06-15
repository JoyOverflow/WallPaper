package ouyj.hyena.com.bannerimage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ouyj.hyena.com.bannerimage.mz.StatusBarUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarUtils.setColor(
                this,getResources().getColor(R.color.colorPrimary),
                0
        );

        //工具条的设置
        mToolbar = findViewById(R.id.tool_bar);
        mToolbar.inflateMenu(R.menu.setting);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() ==R.id.banner_mode){
                    switchBannerMode();
                }else if(item.getItemId() == R.id.viewPager_mode){
                    switchViewPagerMode();
                }else if(item.getItemId() == R.id.remote_mode){
                    switchRemoteMode();
                }else if(item.getItemId() == R.id.mz_mode_not_cover){
                    switchMZModeNotCover();
                }
                return true;
            }
        });


        Fragment fragment = BannerViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.home_container,fragment).commit();
    }



    public void switchBannerMode(){
        Fragment fragment = BannerViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
    }
    public void switchViewPagerMode(){
        Fragment fragment = NormalViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
    }
    public void switchRemoteMode(){
        Fragment fragment = RemoteViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
    }
    public void switchMZModeNotCover(){
        Fragment fragment = UncoverViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
    }




}

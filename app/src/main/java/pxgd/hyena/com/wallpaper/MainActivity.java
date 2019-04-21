package pxgd.hyena.com.wallpaper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //NavigationView（header和menu布局）
        //必需填加依赖'com.android.support:design:26.1.0'


        //使用ToolBar替换掉以前的ActionBar（清单Manifest文件中@style/AppTheme样式已更新，否则会异常）
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //查找到抽屉布局视图
        //DrawerLayout.fitsSystemWindows="true" 设置根布局延伸到状态栏
        mDrawerLayout = findViewById(R.id.drawer_layout);

        //配置DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //添加抽屉布局的监听器方法
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //app:itemTextColor="@drawable/navigation_item_tx" 选中项的文本颜色
        //查找导航条的视图引用
        NavigationView navigation = findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(navSelectedListener);
        //设置导航条的选中项
        navigation.setCheckedItem(R.id.home);
        //使菜单项图标显示原始的颜色（不进行偏色处理）
        //navigation.setItemIconTintList(null);

        //当NavigationView的菜单项很多时，右边会出现难看的灰色滚动条
        //滚动条本身不在NavigationView上，而是在其的一个子View上（可将其禁用）
        View menuView = navigation.getChildAt(0);
        if(menuView != null && menuView instanceof NavigationMenuView){
            menuView.setVerticalScrollBarEnabled(false);
        }
        //获取到头部视图
        View headerView = navigation.getHeaderView(0);

    }
    /**
     * 如果有抽屉打开则关闭抽屉
     * 再次点击后退键（抽屉关闭状态）才退出当前活动
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null
                && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            //关闭抽屉
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


    /**
     * 菜单项点击事件（点击菜单项才触发）
     */
    NavigationView.OnNavigationItemSelectedListener navSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d(TAG, "onNavigationItemSelected！");
            return false;
        }
    };


}

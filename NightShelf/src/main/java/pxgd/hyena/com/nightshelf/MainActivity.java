package pxgd.hyena.com.nightshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pxgd.hyena.com.nightshelf.view.PagerIndicator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;
    private PagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("全部", "收藏");
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置工具栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //右下角浮动菜单
        final FloatingActionMenu fabMenu = findViewById(R.id.fabmenu);
        fabMenu.setClosedOnTouchOutside(true);

        //右下角（扫一扫）
        final FloatingActionButton fabBtnScanner = findViewById(R.id.fab_scanner);
        fabBtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        //右下角（添加）
        FloatingActionButton fabBtnAdd =  findViewById(R.id.fab_add);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //左上角Menu按钮
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //菜单
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ViewPager
        viewPager = findViewById(R.id.view_pager);

        //ViewPagerIndicator
        viewPagerIndicator = findViewById(R.id.indicator);
        viewPagerIndicator.setTabItemTitles(titles);

        //Fragment
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_ALL));
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE));


        //PagerAdapter
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };
        //设置适配器
        viewPager.setAdapter(pagerAdapter);
        viewPagerIndicator.setViewPager(viewPager, 0);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_scanner) {
            //扫一扫
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add) {
            //添加
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("search_type", SearchActivity.SEARCH_NET);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            //关于
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            //查找
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("search_type", SearchActivity.SEARCH_LOCAL);
            startActivity(intent);
        }

        //关闭抽屉布局的抽屉
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



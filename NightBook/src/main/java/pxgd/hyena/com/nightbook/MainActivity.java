package pxgd.hyena.com.nightbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.nightbook.entity.Book;
import pxgd.hyena.com.nightbook.entity.Subject;
import pxgd.hyena.com.nightbook.utils.BookManager;
import pxgd.hyena.com.nightbook.utils.CallBack;
import pxgd.hyena.com.nightbook.utils.NavigationDrawerCallbacks;
import pxgd.hyena.com.nightbook.utils.NavigationItem;

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {

    public static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private TempFragment mNavigationDrawerFragment;
    private List<NavigationItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置工具栏
        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //侧滑布局
        mNavigationDrawerFragment = (TempFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(
                R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer),
                mToolbar
        );
        items = new ArrayList<>();
        items.add(new NavigationItem(
                new Subject(
                        "REC000000",
                        "Recommend",
                        "推荐",
                        "https://raw.githubusercontent.com/snowdream/android-books/master/docs/test/recomend.json"
                ),
                getResources().getDrawable(R.drawable.ic_menu_check))
        );
        items.add(new NavigationItem(new Subject("COM000000","Computers","计算机技术","https://raw.githubusercontent.com/snowdream/android-books/master/docs/test/computer.json"), getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem(new Subject("FOR000000","Foreign Language Study","外语学习","https://raw.githubusercontent.com/snowdream/android-books/master/docs/test/foreign.json"), getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem(new Subject("EDU000000","Education","教育","https://raw.githubusercontent.com/snowdream/android-books/master/docs/test/education.json"), getResources().getDrawable(R.drawable.ic_menu_check)));
        mNavigationDrawerFragment.setMenu(items);
        mNavigationDrawerFragment.refresh();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        //得到具体的主题
        NavigationItem item  = items.get(position);
        Subject subject = item.getSubject();

        BookManager.getBooks(subject.getUrl(), new CallBack<List<Book>>() {
            @Override
            public void callback(List<Book> books) {

                if (books != null && !books.isEmpty()) {

                    //加载主题片段并传递数据
                    SubjectFragment fragment = new SubjectFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(SubjectFragment.KEY_SUBJECT_BOOKS, Parcels.wrap(books));
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().getFragments().size();

                    List<Fragment> list = getSupportFragmentManager().getFragments();
                    if (list == null || list.isEmpty())
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    else
                        getSupportFragmentManager().beginTransaction().add(
                                R.id.container, fragment
                        ).addToBackStack(null).commit();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}

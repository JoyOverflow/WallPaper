package ouyj.hyena.com.treadbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import okhttp3.Call;
import ouyj.hyena.com.treadbook.adapter.ShelfAdapter;
import ouyj.hyena.com.treadbook.db.BookList;
import ouyj.hyena.com.treadbook.util.CommonUtil;
import ouyj.hyena.com.treadbook.util.DisplayUtils;
import ouyj.hyena.com.treadbook.view.DragGridView;
import ouyj.hyena.com.treadbook.view.animation.ContentScaleAnimation;
import ouyj.hyena.com.treadbook.view.animation.Rotate3DAnimation;
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Animation.AnimationListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.bookShelf)
    DragGridView bookShelf;


    //书列表和适配器对象
    private List<BookList> bookLists;
    private ShelfAdapter adapter;



    private WindowManager mWindowManager;
    private AbsoluteLayout wmRootView;
    private View rootView;
    private Typeface typeface;

    //点击书本的位置
    private int itemPosition;
    private TextView itemTextView;
    //点击书本在屏幕中的x，y坐标
    private int[] location = new int[2];

    private static TextView cover;
    private static ImageView content;
    //书本打开动画缩放比例
    private float scaleTimes;
    //书本打开缩放动画
    private static ContentScaleAnimation contentAnimation;
    private static Rotate3DAnimation coverAnimation;
    //书本打开缩放动画持续时间
    public static final int ANIMATION_DURATION = 800;
    //打开书本的第一个动画是否完成
    private boolean mIsOpen = false;
    //动画加载计数器  0 默认  1一个动画执行完毕   2二个动画执行完毕
    private int animationCount=0;

    private static Boolean allowExit = false;

    private Config config;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        config = Config.getInstance();
        setSupportActionBar(toolbar);

        //设置导航图标
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);


        getWindow().setBackgroundDrawable(null);//删除窗口背景


        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wmRootView = new AbsoluteLayout(this);
        rootView = getWindow().getDecorView();
//        SQLiteDatabase db = Connector.getDatabase();  //初始化数据库

        //typeface = config.getTypeface();

        //为书柜视图设置适配器
        bookLists = DataSupport.findAll(BookList.class);
        adapter = new ShelfAdapter(MainActivity.this,bookLists);
        bookShelf.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,FileChooserActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        bookShelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //保证点击位置的有效
                if (bookLists.size() > position) {

                    //点击位置
                    itemPosition = position;

                    //将当前点击的书移至书柜的第一位
                    adapter.setItemToFirst(itemPosition);

                    //得到当前书本对象
                    final BookList bookList = bookLists.get(itemPosition);
                    bookList.setId(bookLists.get(0).getId());

                    //得到书本所在路径
                    final String path = bookList.getBookpath();
                    File file = new File(path);
                    if (!file.exists()){

                        //书本不存在
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(MainActivity.this.getString(R.string.app_name))
                                .setMessage(path + "文件不存在,是否删除该书本？")
                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //删除数据库记录
                                        DataSupport.deleteAll(BookList.class, "bookpath = ?", path);
                                        bookLists = DataSupport.findAll(BookList.class);
                                        adapter.setBookList(bookLists);
                                    }
                                }).setCancelable(true).show();
                        return;
                    }
                    //打开书本
                    ReadActivity.openBook(
                            bookList,
                            MainActivity.this
                    );
                }
            }
        });
    }


    @Override
    protected void onRestart(){
        super.onRestart();
        DragGridView.setIsShowDeleteButton(false);
        bookLists = DataSupport.findAll(BookList.class);
        adapter.setBookList(bookLists);
        closeBookAnimation();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onStop() {
        DragGridView.setIsShowDeleteButton(false);
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        DragGridView.setIsShowDeleteButton(false);
        super.onDestroy();
    }


    /**
     * 2秒内连续按返回键两次才会退出应用
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(Gravity.LEFT)) {
                drawer.closeDrawers();
            }
            else
                exitBy2Click();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exitBy2Click() {
        Timer timer;
        if (!allowExit) {
            allowExit = true; // ready to exit
            if(DragGridView.getShowDeleteButton()) {
                DragGridView.setIsShowDeleteButton(false);
                //要保证是同一个adapter对象,否则在Restart后无法notifyDataSetChanged
                adapter.notifyDataSetChanged();
            }else {
                Toast.makeText(
                        this,
                        this.getResources().getString(R.string.press_twice_to_exit),
                        Toast.LENGTH_SHORT).show();
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    allowExit = false;
                }
            }, 2000);

        } else {
            finish();
            System.exit(0);
        }
    }




    //初始化dialog动画
    private void initAnimation() {
        AccelerateInterpolator interpolator = new AccelerateInterpolator();

        float scale1 = DisplayUtils.getScreenWidthPixels(this) / (float) itemTextView.getMeasuredWidth();
        float scale2 = DisplayUtils.getScreenHeightPixels(this) / (float) itemTextView.getMeasuredHeight();
        scaleTimes = scale1 > scale2 ? scale1 : scale2;  //计算缩放比例

        contentAnimation = new ContentScaleAnimation( location[0], location[1],scaleTimes, false);
        contentAnimation.setInterpolator(interpolator);  //设置插值器
        contentAnimation.setDuration(ANIMATION_DURATION);
        contentAnimation.setFillAfter(true);  //动画停留在最后一帧
        contentAnimation.setAnimationListener(this);

        coverAnimation = new Rotate3DAnimation(0, -180, location[0], location[1], scaleTimes, false);
        coverAnimation.setInterpolator(interpolator);
        coverAnimation.setDuration(ANIMATION_DURATION);
        coverAnimation.setFillAfter(true);
        coverAnimation.setAnimationListener(this);
    }

    public void closeBookAnimation() {

        if (mIsOpen && wmRootView!=null) {
            //因为书本打开后会移动到第一位置，所以要设置新的位置参数
            contentAnimation.setmPivotXValue(bookShelf.getFirstLocation()[0]);
            contentAnimation.setmPivotYValue(bookShelf.getFirstLocation()[1]);
            coverAnimation.setmPivotXValue(bookShelf.getFirstLocation()[0]);
            coverAnimation.setmPivotYValue(bookShelf.getFirstLocation()[1]);

            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(
                    itemTextView.getLayoutParams());
            params.x = bookShelf.getFirstLocation()[0];
            params.y = bookShelf.getFirstLocation()[1];//firstLocation[1]在滑动的时候回改变,所以要在dispatchDraw的时候获取该位置值
            wmRootView.updateViewLayout(cover,params);
            wmRootView.updateViewLayout(content,params);
            //动画逆向运行
            if (!contentAnimation.getMReverse()) {
                contentAnimation.reverse();
            }
            if (!coverAnimation.getMReverse()) {
                coverAnimation.reverse();
            }
            //清除动画再开始动画
            content.clearAnimation();
            content.startAnimation(contentAnimation);
            cover.clearAnimation();
            cover.startAnimation(coverAnimation);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //有两个动画监听会执行两次，所以要判断
        if (!mIsOpen) {
            animationCount++;
            if (animationCount >= 2) {
                mIsOpen = true;
                adapter.setItemToFirst(itemPosition);
//                bookLists = DataSupport.findAll(BookList.class);
                BookList bookList = bookLists.get(itemPosition);
                bookList.setId(bookLists.get(0).getId());
                ReadActivity.openBook(bookList,MainActivity.this);
            }

        } else {
            animationCount--;
            if (animationCount <= 0) {
                mIsOpen = false;
                wmRootView.removeView(cover);
                wmRootView.removeView(content);
                mWindowManager.removeView(wmRootView);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    //获取dialog属性
    private WindowManager.LayoutParams getDefaultWindowParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0, 0,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,//windown类型,有层级的大的层级会覆盖在小的层级
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.RGBA_8888);

        return params;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }else if (id == R.id.action_select_file){
//            Intent intent = new Intent(MainActivity.this, FileChooserActivity.class);
//            startActivity(intent);
//        }

        if (id == R.id.action_select_file){
            Intent intent = new Intent(MainActivity.this, FileChooserActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 抽屉滑出菜单项的处理
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //点击的菜单项
        int id = item.getItemId();
        if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_checkupdate) {
            //检测是否有应用更新
            checkUpdate(false);
        }else if (id == R.id.nav_about) {
            //启动关于活动
            Intent intent = new Intent(
                    MainActivity.this,
                    AboutActivity.class
            );
            startActivity(intent);
        }
        return true;
    }
    /**
     * 检测应用更新
     * @param showMessage
     */
    public void checkUpdate(final boolean showMessage){
        String url = "http://api.fir.im/apps/latest/57be8d56959d6960d5000327";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("api_token", "a48b9bbcef61f34c51160bfed26aa6b2")
                .build()
                .execute(new StringCallback()
                {
                    //请求失败时回调
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (showMessage) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "检查更新失败！",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                    //请求成功时回调
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            //解析请求成功时的返回数据
                            JSONObject jsonObject = new JSONObject(response);
                            String version = jsonObject.getString("version");
                            //返回当前的版本号并比较
                            String versionCode = CommonUtil.getVersionCode(MainActivity.this) + "";
                            if (versionCode.compareTo(version) < 0){
                                //显示对话框
                                showUpdateDialog(
                                        jsonObject.getString("name"),
                                        jsonObject.getString("versionShort"),
                                        jsonObject.getString("changelog"),
                                        jsonObject.getString("update_url"),
                                        MainActivity.this
                                );
                            }else{
                                if (showMessage) {
                                    Toast.makeText(
                                            MainActivity.this,
                                            "这已经是最新版本了！",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        } catch (JSONException e) {
                            if (showMessage) {
                                Toast.makeText(
                                        MainActivity.this,
                                        "检查应用更新时失败！",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 显示发现新版本应用的对话框
     * @param name
     * @param version
     * @param changelog
     * @param updateUrl
     * @param context
     */
    public static void showUpdateDialog(
            final String name, String version, String changelog, final String updateUrl, final Context context)
    {
        String title = "发现新版" + name + "，版本号：" + version;

        new android.support.v7.app.AlertDialog.Builder(context).setTitle(title)
                .setMessage(changelog)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //指定新应用所在Url
                        Uri uri = Uri.parse(updateUrl);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        //发送隐式意图并启动活动
                        context.startActivity(intent);
                    }
                })
                .show();
    }
}

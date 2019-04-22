package pxgd.hyena.com.wallpaper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 片段类
 */
public class HomeFragment extends Fragment {

    private ListView mListView;

    /**
     * 构造方法
     */
    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = view.findViewById(R.id.listView);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //得到泛型数据源
        List<String> locales = new ArrayList<>();
        Locale[] data = Locale.getAvailableLocales();
        for (Locale locale : data) {
            locales.add(locale.getDisplayName());
        }
        //为列表视设置数组适配器
        mListView.setAdapter(
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1,
                locales)
        );
        //列表视的滚动事件（前提是列表视必需获得焦点）
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //状态改变时触发
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d(MainActivity.TAG, String.format("onScrollStateChanged %d！", scrollState));
            }
            //不断触发
            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount)
            {
                Log.d(
                        MainActivity.TAG,
                        String.format("onScroll %d,%d,%d！",
                                firstVisibleItem,
                                visibleItemCount,
                                totalItemCount
                        )
                );


            }
        });
    }


}

package ouyj.hyena.com.treadbook.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ouyj.hyena.com.treadbook.R;
import ouyj.hyena.com.treadbook.adapter.MarkAdapter;
import ouyj.hyena.com.treadbook.db.BookMarks;
import ouyj.hyena.com.treadbook.util.PageFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookMarkFragment extends BaseFragment {

    public static final String ARGUMENT = "argument";

    @Bind(R.id.lv_bookmark)
    ListView lv_bookmark;

    private String bookpath;
    private String mArgument;
    private List<BookMarks> bookMarksList;
    private MarkAdapter markAdapter;
    private PageFactory pageFactory;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_book_mark;
    }

    @Override
    protected void initData(View view) {
        pageFactory = PageFactory.getInstance();
        Bundle bundle = getArguments();
        if (bundle != null) {
            bookpath = bundle.getString(ARGUMENT);
        }
        bookMarksList = new ArrayList<>();
        bookMarksList = DataSupport.where("bookpath = ?", bookpath).find(BookMarks.class);
        markAdapter = new MarkAdapter(getActivity(), bookMarksList);
        lv_bookmark.setAdapter(markAdapter);
    }

    @Override
    protected void initListener() {
        lv_bookmark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pageFactory.changeChapter(bookMarksList.get(position).getBegin());
                getActivity().finish();
            }
        });
        lv_bookmark.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("是否删除书签？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataSupport.delete(BookMarks.class,bookMarksList.get(position).getId());
                                bookMarksList.clear();
                                bookMarksList.addAll(DataSupport.where("bookpath = ?", bookpath).find(BookMarks.class));
                                markAdapter.notifyDataSetChanged();
                            }
                        }).setCancelable(true).show();
                return false;
            }
        });
    }

    /**
     * 用于从Activity传递数据到Fragment
     * @param bookpath
     * @return
     */
    public static BookMarkFragment newInstance(String bookpath)
    {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, bookpath);
        BookMarkFragment bookMarkFragment = new BookMarkFragment();
        bookMarkFragment.setArguments(bundle);
        return bookMarkFragment;
    }





}

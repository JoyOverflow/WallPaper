package pxgd.hyena.com.nightshelf;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pxgd.hyena.com.nightshelf.adapter.BookGridAdapter;
import pxgd.hyena.com.nightshelf.model.bean.Book;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookGridFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final int TYPE_ALL = 1;
    public static final int TYPE_FAVORITE = 2;
    private static final String ARG_TYPE = "type";

    private int type = TYPE_ALL; // 显示的数据（全部、收藏）
    private GridView gridView; // 网格列表
    private BookGridAdapter bookGridAdapter; // 数据适配器
    private int gridPosition = -1; // 选中项的position

    public BookGridFragment() { }
    public static BookGridFragment newInstance(int type) {
        BookGridFragment fragment = new BookGridFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            if (isVisibleToUser) {
                fetchData();
                bookGridAdapter.notifyDataSetChanged();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    public void fetchData() {
        if (type == TYPE_FAVORITE)
            bookGridAdapter.setData(DataSupport.where("favourite = ?", "1").order("id desc").find(Book.class));
        else
            bookGridAdapter.setData(DataSupport.order("id desc").find(Book.class));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        fetchData();
        bookGridAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_grid, container, false);

        gridView = view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridPosition = position;
                return false;
            }
        });
        registerForContextMenu(gridView);

        View emptyView = view.findViewById(R.id.empty);
        ImageView ivIcon = emptyView.findViewById(R.id.iv_icon);
        TextView tvText = emptyView.findViewById(R.id.tv_text);
        if (type == TYPE_FAVORITE) {
            ivIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_favorite).colorRes(R.color.grid_empty_icon).sizeDp(40));
            tvText.setText("暂无收藏");
        } else {
            ivIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_import_contacts).colorRes(R.color.grid_empty_icon).sizeDp(48));
            tvText.setText("暂无图书");
        }
        gridView.setEmptyView(emptyView);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bookGridAdapter = new BookGridAdapter(getContext());
        fetchData();
        bookGridAdapter.notifyDataSetChanged();
        gridView.setAdapter(bookGridAdapter);
        super.onViewCreated(view, savedInstanceState);
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), BookActivity.class);
        intent.putExtra("id", (int) bookGridAdapter.getItemId(position));
        startActivity(intent);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "删除选中");
        menu.add(1, 2, 1, "删除全部");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1 && gridPosition != -1) {
            // 删除选中
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("确定删除这本图书吗")
                    .setContentText("删除后将无法恢复。")
                    .setConfirmText("是的")
                    .setCancelText("取消")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // 刷新数据
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DataSupport.delete(Book.class, bookGridAdapter.getItemId(gridPosition));
                                    fetchData();
                                    bookGridAdapter.notifyDataSetChanged();
                                }
                            }, 800);

                            sDialog
                                    .setTitleText("删除成功")
                                    .setContentText("该本图书已被成功删除。")
                                    .setConfirmText("确定")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        } else if (item.getItemId() == 2) {
            // 删除全部
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("确定删除全部图书吗")
                    .setContentText("删除后将无法恢复。")
                    .setConfirmText("是的")
                    .setCancelText("取消")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // 刷新数据
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DataSupport.deleteAll(Book.class);
                                    fetchData();
                                    bookGridAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                            sDialog
                                    .setTitleText("删除成功")
                                    .setContentText("全部图书已被成功删除。")
                                    .setConfirmText("确定")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }



}

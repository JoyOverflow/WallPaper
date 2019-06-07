package ouyj.hyena.com.treadbook.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import ouyj.hyena.com.treadbook.R;
import ouyj.hyena.com.treadbook.adapter.CatalogueAdapter;
import ouyj.hyena.com.treadbook.db.BookCatalogue;
import ouyj.hyena.com.treadbook.util.PageFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends BaseFragment {





    public static final String ARGUMENT = "argument";

    private PageFactory pageFactory;
    ArrayList<BookCatalogue> catalogueList = new ArrayList<>();

    @Bind(R.id.lv_catalogue)
    ListView lv_catalogue;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_catalog;
    }

    @Override
    protected void initData(View view) {
        pageFactory = PageFactory.getInstance();
        catalogueList.addAll(pageFactory.getDirectoryList());
        CatalogueAdapter catalogueAdapter = new CatalogueAdapter(getContext(), catalogueList);
        catalogueAdapter.setCharter(pageFactory.getCurrentCharter());
        lv_catalogue.setAdapter(catalogueAdapter);
        catalogueAdapter.notifyDataSetChanged();
    }
    @Override
    protected void initListener() {
        lv_catalogue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pageFactory.changeChapter(catalogueList.get(position).getBookCatalogueStartPos());
                getActivity().finish();
            }
        });
    }
    /**
     * 用于从Activity传递数据到Fragment
     * @param bookpath
     * @return
     */
    public static CatalogFragment newInstance(String bookpath)
    {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, bookpath);
        CatalogFragment catalogFragment = new CatalogFragment();
        catalogFragment.setArguments(bundle);
        return catalogFragment;
    }






}

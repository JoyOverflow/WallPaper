package pxgd.hyena.com.nightbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.parceler.Parcels;
import java.util.List;
import java.util.Locale;
import pxgd.hyena.com.nightbook.entity.Book;
import pxgd.hyena.com.nightbook.utils.SubjectAdapter;

/**
 */
public class SubjectFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String KEY_SUBJECT_BOOKS = "key_subject_books";
    private ListView listview = null;
    private String language = null;

    public SubjectFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }
    private void initView(View rootview) {
        listview = rootview.findViewById(R.id.listview);
        listview.setOnItemClickListener(this);
    }
    private void initData() {
        language = Locale.getDefault().getLanguage();
        Bundle bundle = getArguments();

        //取出数据
        List<Book> books = Parcels.unwrap(bundle.getParcelable(SubjectFragment.KEY_SUBJECT_BOOKS)) ;
        if (books != null && !books.isEmpty()){
            SubjectAdapter adapter = new SubjectAdapter(getActivity(),books);
            listview.setAdapter(adapter);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SubjectAdapter adapter = (SubjectAdapter) listview.getAdapter();
        Book book = adapter.getItem(position);
        if (book != null && !TextUtils.isEmpty(book.getUrl(language))){

            BookFragment fragment = new BookFragment();
            Bundle bundle = new Bundle();
            bundle.putString(BookFragment.BOOK_URL_KEY,book.getUrl(language));
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,fragment)
                    .addToBackStack(null).commit();
        }
    }
}

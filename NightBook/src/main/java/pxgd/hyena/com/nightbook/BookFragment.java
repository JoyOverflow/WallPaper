package pxgd.hyena.com.nightbook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {
    private static final String TEST_DEVICE_ID = "INSERT_YOUR_TEST_DEVICE_ID_HERE";
    public static final String BOOK_URL_KEY = "BOOK_URL_KEY";

    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

}

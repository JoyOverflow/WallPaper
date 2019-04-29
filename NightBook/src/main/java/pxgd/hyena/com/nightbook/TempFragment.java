package pxgd.hyena.com.nightbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pxgd.hyena.com.nightbook.utils.NavigationDrawerAdapter;
import pxgd.hyena.com.nightbook.utils.NavigationDrawerCallbacks;
import pxgd.hyena.com.nightbook.utils.NavigationItem;

import static pxgd.hyena.com.nightbook.MainActivity.TAG;
/**
 * A simple {@link Fragment} subclass.
 */
public class TempFragment extends Fragment implements NavigationDrawerCallbacks {

    private RecyclerView mDrawerList;
    private NavigationDrawerCallbacks mCallbacks;

    public TempFragment() { }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallbacks = (NavigationDrawerCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mDrawerList = view.findViewById(R.id.drawerList);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated！");
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated！");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart！");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume！");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onAttach！");
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }





    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.myPrimaryDarkColor));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded())
                    return;
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "true");
                }

                getActivity().supportInvalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState)
            mDrawerLayout.openDrawer(mFragmentContainerView);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }
    public void setMenu(List<NavigationItem> list){
        items = list;
    }
    public void refresh(){
        final List<NavigationItem> navigationItems = getMenu();
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItems);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectedPosition);
    }
    public List<NavigationItem> getMenu() {
        return items;
    }
    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }
    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }
}

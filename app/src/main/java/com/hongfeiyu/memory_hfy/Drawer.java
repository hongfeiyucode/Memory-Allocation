package com.hongfeiyu.memory_hfy;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Drawer extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static Context context;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     * 片段管理导航抽屉的行为，相互作用和演示。
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     * 用于存储的最后一个屏幕称号。对于使用{@link#restoreActionBar（）}。
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        context = this.getApplicationContext();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        if(position==0)
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new introduce())
                    .commit();
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new wave1())
                    .commit();
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            // 操作栏中的相关与这个屏幕只显示项目如果抽屉没有显示。否则，让抽屉决定如何操作栏中显示。
            getMenuInflater().inflate(R.menu.drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //手柄动作栏项目点击这里。操作栏将在主页/向上按钮自动处理的点击，只要你指定在AndroidManifest.xml中父母的活动。
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     * 占位符片段包含一个简单的观点。
     */
    public static class PlaceholderFragment extends Fragment {
            /**
             * The fragment argument representing the section number for this
             * fragment.该片段的说法表示此片段中的部分号码。
             */
            private static final String ARG_SECTION_NUMBER = "section_number";

            /**
             * Returns a new instance of this fragment for the given section
             * number.返回该片段对于给定部分数的新实例。
             */
            public static PlaceholderFragment newInstance(int sectionNumber) {
                PlaceholderFragment fragment = new PlaceholderFragment();
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;
            }

            public PlaceholderFragment() {
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
                return rootView;
            }

            @Override
            public void onAttach(Activity activity) {
                super.onAttach(activity);
                ((Drawer) activity).onSectionAttached(
                        getArguments().getInt(ARG_SECTION_NUMBER));
            }
        }

}

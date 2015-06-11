package com.kuailedian.happytouch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kuailedian.applictionservice.INavigationService;
import com.kuailedian.applictionservice.MenuProvider;
import com.kuailedian.domain.Account;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyBaseActivity extends ActionBarActivity implements OnMenuItemClickListener,
        OnMenuItemLongClickListener , INavigationService{

    private FragmentManager fragmentManager;
    private DialogFragment mMenuDialogFragment;

    @InjectView(R.id.toolbar)
     Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        ButterKnife.inject(this);
        initView() ;

        getHtAppliction().SetSystemDomain(INavigationService.class, this);
        this.Push(HomeFragment.newInstance());
    }



    private void initView() {
        //Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        fragmentManager = getSupportFragmentManager();
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText(R.string.app_name);



        mMenuDialogFragment = ContextMenuDialogFragment.newInstance((int) getResources().getDimension(R.dimen.tool_bar_height), MenuProvider.getMenuObjects(this));

    }

    protected HTApplication getHtAppliction()
    {
        HTApplication application = (HTApplication)getApplication();
        return application;
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my_base, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position)
        {
            case 0:
                mMenuDialogFragment.dismiss();
                break;
            case 1:
                this.Push(ProductsFragment.newInstance());
                break;
            case 2:
                this.Push(ReservationFragment.newInstance()) ;

                break;
            case 3:
                this.Push(PersonalCenterFragment.newInstance());
                break;
            case 4:
                SharedPreferences sp = this.getSharedPreferences("com.kuailedian.happytouch", Context.MODE_PRIVATE);
                SharedPreferences.Editor editer = sp.edit();
                editer.clear();
                editer.apply();
                getAppliction().SetSystemDomain(Account.class,null);
                INavigationService inav = getAppliction().GetSystemDomain(INavigationService.class);

                inav.Navigate(HomeFragment.newInstance());
                break;

        }



    }


    private HTApplication getAppliction()
    {

        HTApplication app =(HTApplication)this.getApplication();
        return app;
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {


       // popup.
        //Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Navigate(android.support.v4.app.Fragment fragment) {
        addFragment(fragment, false, R.id.container);
    }


    @Override
    public void Push(Fragment fragment) {
        addFragment(fragment, true, R.id.container);
    }

    @Override
    public void SetTitleContent(String title) {
        CharSequence cs = title;
        TextView tv = (TextView)this.findViewById(R.id.text_view_toolbar_title);
        tv.setText(cs);
    }
}

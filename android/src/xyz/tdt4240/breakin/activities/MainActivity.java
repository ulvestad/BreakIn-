package xyz.tdt4240.breakin.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import xyz.tdt4240.breakin.R;
import xyz.tdt4240.breakin.adapters.ViewPagerAdapter;
import xyz.tdt4240.breakin.fragments.MultiplayerFragment;
import xyz.tdt4240.breakin.fragments.SingleplayerFragment;

/**
 * Controller class for the main activity
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private SingleplayerFragment singleplayerFragment;

    private MultiplayerFragment multiplayerFragment;

    private MainActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_people_white_24dp);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        singleplayerFragment = new SingleplayerFragment();
        singleplayerFragment.setParentActivity(thisActivity);

        multiplayerFragment = new MultiplayerFragment();
        multiplayerFragment.setParentActivity(thisActivity);

        adapter.addFragment(singleplayerFragment, getString(R.string.main_activity_tab_single_player));
        adapter.addFragment(multiplayerFragment, getString(R.string.main_activity_tab_multi_player));

        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_about){
            startActivity(new Intent(this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}

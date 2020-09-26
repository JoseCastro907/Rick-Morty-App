package cr.ac.ucr.rickmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.adapters.MainViewPagerAdapter;
import cr.ac.ucr.rickmorty.fragments.CharacterFragment;


public class MainActivity extends AppCompatActivity {

    private ViewPager vPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tToolbar = findViewById(R.id.t_toolbar);
        setSupportActionBar(tToolbar);

        vPager = findViewById(R.id.vp_pager);

        setupViewPager();
    }

    private void setupViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(CharacterFragment.newInstance());

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);

        vPager.setAdapter(mainViewPagerAdapter);
    }
}
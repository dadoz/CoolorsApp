package com.application.dev.david.coolorsapp;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.application.dev.david.coolorsapp.modules.colorPalette.ui.ColorPaletteFragment;
import com.application.dev.david.coolorsapp.modules.storedPalette.ui.StoredPaletteFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.toolbarId)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        onInitView();
    }

    private void onInitView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new ColorPaletteFragment());
                    return true;
                case R.id.navigation_dashboard:
                    loadFragment(new StoredPaletteFragment());
                    return true;
            }
            return false;

        });

    }

    /**
     *
     * @param fragment
     */
    protected void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.colorFragmentContainerId, fragment);
        transaction.commit();
    }

}

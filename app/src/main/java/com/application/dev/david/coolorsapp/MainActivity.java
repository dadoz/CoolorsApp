package com.application.dev.david.coolorsapp;

//powered by https://www.colourlovers.com/api/palettes/random

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.application.dev.david.coolorsapp.modules.colorPalette.ui.ColorPaletteFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitView(savedInstanceState);
    }

    private void onInitView(Bundle savedInstanceState) {
        loadFragment(new ColorPaletteFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu_id:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
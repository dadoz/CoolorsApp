package com.application.dev.david.coolorsapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.modules.colorGrid.ColorGridPresenter;
import com.application.dev.david.coolorsapp.modules.colorGrid.ColorGridView;
import com.application.dev.david.coolorsapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.application.dev.david.coolorsapp.data.ColorsRepository.COOLORS_BASE_URL;
//powered by https://www.colourlovers.com/api/palettes/random

public class MainActivity extends AppCompatActivity implements ColorGridView {
    private ColorGridPresenter presenter;
    @BindView(R.id.coolorsContainerLayoutId)
    LinearLayout colorContainerLayout;
    @BindView(R.id.toolbarId)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("bllbalalall");

        presenter = new ColorGridPresenter(this, new ColorsRepository());
        onInitView();
    }

    /**
     * init view
     */
    private void onInitView() {
        presenter.retrieveData();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;

        });
    }

    @Override
    public void onColorGrid(List<String> list) {
        int index = 0;
        for (String color : list) {
            initBox((TextView) colorContainerLayout.getChildAt(index), color);
            index ++;
        }
    }

    @Override
    public void onColorGridError(String error) {
    }

    /**
     *
     * @param box
     * @param hexColor
     */
    private void initBox(TextView box, String hexColor) {
        box.setBackgroundColor(Color.parseColor(hexColor));
        box.setText(hexColor);
    }

}

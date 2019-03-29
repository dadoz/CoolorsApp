package com.application.dev.david.coolorsapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.modules.colorGrid.ColorGridPresenter;
import com.application.dev.david.coolorsapp.modules.colorGrid.ColorGridView;
import com.application.dev.david.coolorsapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ColorGridView {
    private ColorGridPresenter presenter;
    @BindView(R.id.coolorsContainerLayoutId)
    LinearLayout colorContainerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new ColorGridPresenter(this, new ColorsRepository());
        onInitView();
    }

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
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
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
     * @param color
     */
    private void initBox(TextView box, String color) {
        String hexColor = Utils.parseColorFromRGB(color);
        box.setBackgroundColor(Color.parseColor(hexColor));
        box.setText(hexColor);
    }

}

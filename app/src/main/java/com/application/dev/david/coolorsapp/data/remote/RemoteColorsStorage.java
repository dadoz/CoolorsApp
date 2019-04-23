package com.application.dev.david.coolorsapp.data.remote;

import android.util.Log;

import com.application.dev.david.coolorsapp.data.ColorsDataSource;
import com.application.dev.david.coolorsapp.models.ColorPalette;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@Remote
public class RemoteColorsStorage implements ColorsDataSource {
    private static final String COOLORS_BASE_URL = "https://www.colourlovers.com/api/palettes/random";

    public Observable<List<String>> getColors(int position) {
        List<String> list = new ArrayList<>();
        try {
            String html = Jsoup.connect(COOLORS_BASE_URL).get().html();
            Document doc = Jsoup.parse(html, "", Parser.xmlParser());
            Elements els = doc.getElementsByTag("hex");
            for (Element element : els) {
                list.add(element.html());
                Log.i(getClass().getName(), element.html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Observable.just(list);
    }

    @Override
    public boolean hasColors(int position) {
        return false;
    }

    @Override
    public void addColors(List<String> list, int position) {
    }

    @Override
    public Observable<Boolean> removeColor(ColorPalette colorPalette) {
        throw new UnsupportedOperationException("Not implemented");
    }
}

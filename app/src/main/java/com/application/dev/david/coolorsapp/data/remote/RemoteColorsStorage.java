package com.application.dev.david.coolorsapp.data.remote;

import android.util.Log;

import com.application.dev.david.coolorsapp.data.ColorsDataSource;

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

    public Observable<List<String>> getColors() {
        List<String> list = new ArrayList<>();
        try {
            String html = Jsoup.connect(COOLORS_BASE_URL).get().html();
            Document doc = Jsoup.parse(html, "", Parser.xmlParser());
            Elements els = doc.getElementsByTag("hex");
            for (Element element : els) {
                list.add(element.html());
                Log.e(getClass().getName(), element.html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Observable.just(list);
    }

    @Override
    public boolean hasColors() {
        return false;
    }
}

package com.application.dev.david.coolorsapp.data;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;

public class ColorsRepository implements ColorsDataSource {

    public static final String COOLORS_BASE_URL = "https://www.colourlovers.com/api/palettes/random";

    public Observable<List<String>> getColors() {
        ArrayList<String> list = new ArrayList<>();
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

    private void mock3(ArrayList<String> list) {
        list.add("3F51B1");
        list.add("550033");
        list.add("FFD9aa");
        list.add("ECECcc");
        list.add("FFFF00");
    }

    private void mock2(ArrayList<String> list) {
        list.add("3F51B5");
        list.add("5500aa");
        list.add("FFD900");
        list.add("ECECEC");
        list.add("FFFFFF");
    }

    private void mock1(ArrayList<String> list) {
        list.add("rgb(222, 39, 39)");
        list.add("rgb(80, 80, 80)");
        list.add("rgb(122, 114, 101)");
        list.add("rgb(192, 183, 177)");
        list.add("rgb(142, 110, 83)");
    }
}

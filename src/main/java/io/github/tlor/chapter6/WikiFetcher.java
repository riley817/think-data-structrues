package io.github.tlor.chapter6;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WikiFetcher {

    private long lastRequestTime = -1;
    private long minInterval = 1000;

    /**
     * URL을 파싱하고 본문을 가져옵니다.
     * 단락 요소의 리스트를 반환합니다.
     */
    public Elements fetchWikipedia(String url) throws IOException {

        sleepIfNeeded();

        // 문서를 다운로드하고 파싱한다.
        Connection connection = Jsoup.connect(url);
        Document doc = connection.get();

        Element content = doc.getElementById("mw-content-text");

        Elements paras = content.select("p");
        return paras;
    }

    private void sleepIfNeeded() {
        if (lastRequestTime != -1) {
            long currentTime = System.currentTimeMillis();
            long nextRequestTime = lastRequestTime + minInterval;
            if( currentTime < nextRequestTime ) {
                try {
                    Thread.sleep(nextRequestTime - currentTime);
                } catch (InterruptedException e) {
                    System.err.println("Warning: sleep interrupted in fetchWikipedia.");
                }
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }

}

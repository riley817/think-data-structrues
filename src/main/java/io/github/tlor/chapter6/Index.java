package io.github.tlor.chapter6;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Index {

    private Map<String, Set<TermCounter>> index = new HashMap<>();

    public void add(String term, TermCounter tc) {

        Set<TermCounter> set = get(term);

        // 어떤 검색어를 처음 찾으면 새로운 Set을 생성한다.
        if(set == null) {
            set = new HashSet<>();
            index.put(term, set);
        }
        // 그렇지 않으면 기존 set을 변경한다.
        set.add(tc);
    }

    public Set<TermCounter> get(String term) {
        return index.get(term);
    }

    /**
     * Index의 자료구조 내부를 출력한다.
     */
    public void printIndex() {
        for(String term : keySet()) {
            System.out.println(term);

            // 단어별 등장하는 페이지와 등장 횟수를 표시합니다.
            Set<TermCounter> tcs = get(term);
            for(TermCounter tc : tcs) {
                Integer counter = tc.get(term);
                System.out.println(" " + tc.getLabel() + " " + counter);
            }
        }
    }

    public Set<String> keySet() {
        return index.keySet();
    }

    /**
     * 페이지에 Index 를 추가한다.
     * @param url               페이지의 URL
     * @param paragraphs        인덱싱 되어야 하는 요소들
     */
    public void indexPage(String url, Elements paragraphs) {

        // TermCounter 객체를 만들고 단락에 있는 단어를 셉니다.
        TermCounter termCounter = new TermCounter(url);
        termCounter.processElements(paragraphs);

        // TermCounter 에 있는 각 검색어에 대해 TermCounter 객체를 인덱스에 추가합니다.
        for(String term : termCounter.keySet()) {
            add(term, termCounter);
        }
    }

    public static void main(String[] args) throws IOException {

        WikiFetcher wf = new WikiFetcher();
        Index indexer = new Index();

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);

        url = "https://en.wikipedia.org/wiki/Programming_language";
        paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);

        indexer.printIndex();
    }

}

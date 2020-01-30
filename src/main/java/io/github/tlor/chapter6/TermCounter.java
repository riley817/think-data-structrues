package io.github.tlor.chapter6;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TermCounter {

    private Map<String, Integer> map;       // 검색어와 등장 횟수를 매핑
    private String label;                   // label 검색어의 출처가 되는 문서를 식별한다. (URL을 저장)

    public TermCounter(String label) {
        this.label = label;
        this.map = new HashMap<>();
    }

    public String getLabel() {
        return label;
    }

    public void put(String term, int count) {
        map.put(term, count);
    }

    public Integer get(String term) {
        Integer count = map.get(term);
        return count == null ? 0 : count;
    }

    public void incrementTerm(String term) {
        put(term, get(term) + 1);
    }

    /**
     * 전달 받은 Elements 컬렉션에 반복문을 실행하고
     * Node 객체 각각에 대해 processTree() 를 호출한다.
     * @param paragraphs
     */
    public void processElements(Elements paragraphs) {
        for(Node node : paragraphs) {
            processTree(node);
        }
    }

    /**
     * DOM 트리의 루트를 인자로 받아 트리를 순회하면서 텍스트를 포함한 노드를 찾은 다음
     * 텍스트를 추출하여 processText() 메서드로 전달
     * @param root
     */
    public void processTree(Node root) {
        for(Node node : new WikiNodeIterable(root)) {
            if(node instanceof TextNode) {
                processText(((TextNode) node).text());
            }
        }
    }

    /**
     * 1. 구두점은 공백으로 대체하고 나머지 글자는 소문자로 변환한 다음 텍스트를 나눈다.
     * 2. 각 단어에 반목문을 실행하여 단어별로 incrementTermCount 메서드를 호출 한다.
     * @param text
     */
    public void processText(String text) {
        String[] array = text.replaceAll("\\pP", " ")
                .toLowerCase()
                .split("\\s+");

        for(int i = 0; i < array.length; i++) {
            String term = array[i];
            incrementTerm(term);
        }
    }

    public void printCounts() {
        for(String key : keySet()) {
            Integer count = get(key);
            System.out.println(key + "," + count);
        }
        System.out.println("Total of all counts = " + size());
    }

    public int size() {
        int total = 0;
        for(Integer value : map.values()) {
            total += value;
        }
        return total;
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        WikiFetcher wf = new WikiFetcher();
        Elements paragraph = wf.fetchWikipedia(url);

        TermCounter counter = new TermCounter(url);
        counter.processElements(paragraph);
        counter.printCounts();
    }
}

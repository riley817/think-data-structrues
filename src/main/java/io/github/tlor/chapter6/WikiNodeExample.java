package io.github.tlor.chapter6;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class WikiNodeExample {

    public static void main(String[] args) throws IOException {

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        // 문서를 다운로드 하고 파싱하기
        Connection connection = Jsoup.connect(url);
        Document document = connection.get();

        // 내용을 선택하고 단락 추출하기
        Element content = document.getElementById("bodyContent");
        Elements paragraphs = content.select("p");
        Element firstPara = paragraphs.get(1);

        System.out.println(firstPara.childNodeSize());

        Iterable<Node> iter = new WikiNodeIterable(firstPara);
        for (Node node: iter) {
            if (node instanceof TextNode) {
                System.out.print(node);
            }
        }
    }

    private static void recursiveDFS(Node node) {
        if (node instanceof TextNode) {
            System.out.println(node);
        }
        for(Node child : node.childNodes()) {
            recursiveDFS(child);
        }
    }

    private static void iterativeDFS(Node root) {
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if(node instanceof TextNode) {
                System.out.println(node);
            }
            List<Node> nodeList = new ArrayList<>(node.childNodes());
            Collections.reverse(nodeList);

            for(Node child : nodeList) {
                stack.push(child);
            }
        }
    }
}

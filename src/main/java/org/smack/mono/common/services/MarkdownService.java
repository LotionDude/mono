package org.smack.mono.common.services;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {
    private final Parser parser;
    private final TextCollectingVisitor textCollector;

    public MarkdownService() {
        this.textCollector = new TextCollectingVisitor();
        this.parser = Parser.builder().build();
    }

    public String getPlainTextFromMarkdown(String markdown) {
        Node document = parser.parse(markdown);
        return this.textCollector.collectAndGetText(document);
    }
}

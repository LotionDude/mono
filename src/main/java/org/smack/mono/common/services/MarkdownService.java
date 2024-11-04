package org.smack.mono.common.services;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {
    private final Parser parser;

    public MarkdownService() {
        MutableDataSet options = new MutableDataSet();
        this.parser = Parser.builder(options).build();
    }

    public String getPlainTextFromMarkdown(String markdown) {
        Document document = parser.parse(markdown);
        return document.getChars().toString();
    }
}

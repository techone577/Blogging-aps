package com.blogging.aps.support.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;


public class MarkDownUtils {

    public static String markDownToHtml(String md){
        DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(true,
                Extensions.ALL
        );

        Parser parser = Parser.builder(OPTIONS).build();
        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();
        Node document = parser.parse(md);
        return renderer.render(document);
    }
}

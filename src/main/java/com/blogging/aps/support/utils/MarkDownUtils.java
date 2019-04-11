package com.blogging.aps.support.utils;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;


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

    public static String generateTOCForMD(String md){
        MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                TablesExtension.create(),
                JekyllTagExtension.create(),
                TocExtension.create(),
                SimTocExtension.create()
        )).set(
                TocExtension.LEVELS, 255).set(
                TocExtension.TITLE, "Table of Contents").set(
                TocExtension.DIV_CLASS, "toc");
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse("[TOC]\n" + md);
        String html = renderer.render(document);
        if (html.indexOf("<div class=\"toc\">") == -1) {
            return "";
        }
        html = html.substring(0, 6 + html.indexOf("</div>"));
        html = html.substring(html.indexOf("<ul>"),html.lastIndexOf("</ul>"));
        return html;
    }
}

package com.blogging.aps.web.api;

import com.blogging.aps.model.dto.SignInReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.annotation.Json;
import com.blogging.aps.support.annotation.ServiceInfo;
import com.blogging.aps.support.bsp.ServiceClient;
import com.blogging.aps.support.utils.JsonUtil;
import com.blogging.aps.support.utils.ResponseBuilder;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author techoneduan
 * @date 2018/12/13
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ServiceClient caller;

    @RequestMapping(value = "/test")
    @ServiceInfo(name = "BLOG.TestController.test", description = "测试方法注册")
    public Response test () {
        SignInReqDTO reqDTO = new SignInReqDTO();
        reqDTO.setRootPassword("tetetete");
        reqDTO.setRootUserName("rererer");
        return caller.call("blogging.test", JsonUtil.toString(reqDTO));
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ServiceInfo(name = "BLOG.TestController.test2", description = "测试方法注册")
    public String test2 () {
        return null;
    }

    @RequestMapping(value = "/sss")
    public Response test3 (@Json SignInReqDTO dto) {
        String s = dto.getRootPassword();
        return ResponseBuilder.build(true, dto);
    }


    public static void main(String []args){
        String md = "---\n" +
                "title: MarkDown基本语法\n" +
                "tags: MarkDown\n" +
                "categories: 语言\n" +
                "---\n" +
                "为了顺畅的在github pages上书写自己的blog，需要学习一些基本的MarkDown语法。\n" +
                "## 常用语法规则\n" +
                "\n" +
                "### 标题\n" +
                "总共六级标题，建议在井号后加一个空格，这是最标准的 Markdown 语法。快捷键：command + 1/2/3/4/5/6\n" +
                "\n" +
                "### 换行\n" +
                "只需在行末加两个空格键和一个回车键即可换行。快捷键：control + 回车键\n" +
                "\n" +
                "### 文本样式\n" +
                "```\n" +
                "加粗 - 快捷键：command + B   \n" +
                "斜体 - 快捷键：command + I  \n" +
                "删除线 - 快捷键：command + U  \n" +
                "底纹  - 快捷键：command + K  \n" +
                "下划线 - html：<u>text</u>\n" +
                "分割线 - 用***\n" +
                "```\n" +
                "\n" +
                "** 加粗 ** : **hello**   \n" +
                "\\* 斜体 \\*  :*hello*  \n" +
                "~~ 删除线 ~~  :~~hello~~  \n" +
                "\\` 底纹 \\`  :`hello`  \n" +
                "分割线：\n" +
                "***\n" +
                "### 列表\n" +
                "列表的显示只需要在文字前加上 - 或 * 即可变为无序列表，有序列表则直接在文字前加1. 2. 3. <u>**符号要和文字之间加上一个字符的空格**</u>。  \n" +
                "\n" +
                "##### 无序列表\n" +
                "* a\n" +
                "* b\n" +
                "* c  \n" +
                "\n" +
                "##### 有序列表\n" +
                "1. a\n" +
                "2. b\n" +
                "3. c\n" +
                "\n" +
                "### 引用\n" +
                "只需要在文本前加入 > 这种尖括号（大于号）即可\n" +
                "\n" +
                "> this is a quote\n" +
                "\n" +
                "\n" +
                "### 图片与链接\n" +
                "\n" +
                "图片为：`![]()`\n" +
                "\n" +
                "链接为：`[]()`  \n" +
                "\n" +
                "##### 在线插入：  \n" +
                "插入图片的地址需要图床，这里推荐[围脖图床修复计划](http://weibotuchuang.sinaapp.com/)，生成URL  \n" +
                "\n" +
                "##### 本地插入：  \n" +
                "`![](本地路径)`  \n" +
                "\n" +
                "##### 插入图片\n" +
                "![icon](/img/1.png )  \n" +
                "\n" +
                "### 代码块\n" +
                "使用三个`包裹代码即可  \n" +
                "\n" +
                "```javascript\n" +
                "var a = \"hello world\";\n" +
                "\n" +
                "var b = \"hello world\";\n" +
                "\n" +
                "```\n";
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
        Node document2 = parser.parse("[TOC]\n" + md);
        String html2 = renderer.render(document2);
        html2 = html2.substring(0, 6 + html2.indexOf("</div>"));
        html2 = html2.substring(html2.indexOf("<ul>"),html2.lastIndexOf("</ul>"));


    }

}

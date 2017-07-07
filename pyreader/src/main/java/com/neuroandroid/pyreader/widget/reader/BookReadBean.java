package com.neuroandroid.pyreader.widget.reader;

import com.neuroandroid.pyreader.model.response.BookMixAToc;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/7.
 */

public class BookReadBean {
    private String title;  // 书籍名称
    private List<String> lines;  // 存储每一行文本的集合
    private BookMixAToc.MixToc.Chapters currentChapter;  // 当前章节信息

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public BookMixAToc.MixToc.Chapters getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(BookMixAToc.MixToc.Chapters currentChapter) {
        this.currentChapter = currentChapter;
    }
}

package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.ChapterRead;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IBookReadContract {
    interface Presenter extends IPresenter {
        /**
         * 获取xxx
         */
        void getBookMixAToc(String bookId);

        /**
         * 获取某章节的内容
         */
        void getChapterRead(String url, final int chapter);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示xxx
         */
        void showBookToc(List<BookMixAToc.MixToc.Chapters> list);

        /**
         * 展示某章节的内容
         */
        void showChapterRead(ChapterRead.Chapter data, int chapter);
    }
}

package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.ChapterRead;
import com.neuroandroid.pyreader.mvp.contract.IBookReadContract;
import com.neuroandroid.pyreader.mvp.model.impl.BookReadModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/6.
 */

public class BookReadPresenter extends BasePresenter<BookReadModelImpl, IBookReadContract.View>
        implements IBookReadContract.Presenter {
    public BookReadPresenter(IBookReadContract.View view) {
        super(view);
        mModel = new BookReadModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getBookMixAToc(String bookId) {
        getModelFilteredFactory(BookMixAToc.class).compose(mModel.getBookMixAToc(bookId, "chapters"))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<BookMixAToc>() {
                    @Override
                    protected void onHandleSuccess(BookMixAToc bookMixAToc) {
                        List<BookMixAToc.MixToc.Chapters> list = bookMixAToc.getMixToc().getChapters();
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showBookToc(list);
                        }
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }

    @Override
    public void getChapterRead(String url, int chapter) {
        getModelFilteredFactory(ChapterRead.class).compose(mModel.getChapterRead(url))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<ChapterRead>() {
                    @Override
                    protected void onHandleSuccess(ChapterRead chapterRead) {
                        if (chapterRead.getChapter() != null) {
                            mView.showChapterRead(chapterRead.getChapter(), chapter);
                        } else {
                            mView.showTip("数据为空");
                        }
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}

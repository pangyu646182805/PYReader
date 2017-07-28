package com.neuroandroid.pyreader.event;

/**
 * Created by NeuroAndroid on 2017/7/26.
 */

public class JumpToTargetChapterEvent extends BaseEvent {
    private int page;

    public JumpToTargetChapterEvent() {
        setEventFlag(EVENT_JUMP_TO_TARGET_CHAPTER);
    }

    public int getPage() {
        return page;
    }

    public JumpToTargetChapterEvent setPage(int page) {
        this.page = page;
        return this;
    }
}

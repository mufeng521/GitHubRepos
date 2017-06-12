package com.mufeng.githubrepos.model.biz;

import android.content.Context;

/**
 * Created by zhangqing on 2017/6/9.
 */
public interface IRepositoryBiz {
    public void search(String username, OnSearchListener searchListener, Context context);
}

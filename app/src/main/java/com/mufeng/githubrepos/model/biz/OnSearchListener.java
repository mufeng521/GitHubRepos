package com.mufeng.githubrepos.model.biz;

import com.mufeng.githubrepos.model.bean.Repository;

import java.util.List;

/**
 * Created by zhangqing on 2017/6/9.
 */
public interface OnSearchListener {
    void searchSuccess(List<Repository> repositories);

    void searchFailed();
}

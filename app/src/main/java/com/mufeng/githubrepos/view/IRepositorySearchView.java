package com.mufeng.githubrepos.view;

import com.mufeng.githubrepos.model.bean.Repository;

import java.util.List;

/**
 * Created by zhangqing on 2017/6/9.
 */
public interface IRepositorySearchView {
    String getUserName();

    void clearUserName();

    void showLoading();

    void hideLoading();

    void showInfo(List<Repository> repositories);

    void showFailedError();
}

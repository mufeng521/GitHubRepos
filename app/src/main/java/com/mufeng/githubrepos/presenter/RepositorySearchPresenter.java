package com.mufeng.githubrepos.presenter;

import android.content.Context;
import android.os.Handler;

import com.mufeng.githubrepos.model.bean.Repository;
import com.mufeng.githubrepos.model.biz.IRepositoryBiz;
import com.mufeng.githubrepos.model.biz.OnSearchListener;
import com.mufeng.githubrepos.model.biz.RepositoryBiz;
import com.mufeng.githubrepos.view.IRepositorySearchView;

import java.util.List;

/**
 * Created by zhangqing on 2017/6/9.
 */
public class RepositorySearchPresenter {
    private IRepositoryBiz repositoryBiz;
    private IRepositorySearchView repositorySearchView;
    private Handler mHandler = null;
    private Context context;

    public RepositorySearchPresenter(IRepositorySearchView repositorySearchView, Context context) {
        this.repositorySearchView = repositorySearchView;
        this.repositoryBiz = new RepositoryBiz();
        this.context = context;
        mHandler = new Handler();
    }

    /**
     * 查询信息
     */
    public void search() {
        repositorySearchView.showLoading();
        repositoryBiz.search(repositorySearchView.getUserName(), new OnSearchListener() {
            @Override
            public void searchSuccess(final List<Repository> repositories) {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        repositorySearchView.showInfo(repositories);
                        repositorySearchView.hideLoading();
                    }
                });
            }

            @Override
            public void searchFailed() {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        repositorySearchView.showFailedError();
                        repositorySearchView.hideLoading();
                    }
                });
            }
        }, context);
    }

    /**
     * 清除输入内容
     */
    public void clear() {
        repositorySearchView.clearUserName();
    }
}

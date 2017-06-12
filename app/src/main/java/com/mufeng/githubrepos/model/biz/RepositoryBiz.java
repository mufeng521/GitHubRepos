package com.mufeng.githubrepos.model.biz;

import android.content.Context;

import com.mufeng.githubrepos.model.bean.Repository;
import com.mufeng.githubrepos.util.API;
import com.mufeng.githubrepos.util.AsyncHttpGET;
import com.mufeng.githubrepos.util.JSONUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqing on 2017/6/9.
 */
public class RepositoryBiz implements IRepositoryBiz {
    @Override
    public void search(final String username, final OnSearchListener searchListener, final Context context) {
        new Thread() {
            @Override
            public void run() {
                String url = API.getRepositoryList() + username + "/repos";
                try {
                    String result = new AsyncHttpGET().getResponseInfo(url, context);
                    System.out.println("--查询结果--：" + result);
                    List<Repository> repositories;
                    if (result != null && !"".equals(result)) {
                        repositories = new JSONUtil().parseJsonContacts(result);
                    } else {
                        repositories = new ArrayList<Repository>();
                    }
                    searchListener.searchSuccess(repositories);
                } catch (IOException e) {
                    e.printStackTrace();
                    searchListener.searchFailed();
                }
            }
        }.start();
    }
}

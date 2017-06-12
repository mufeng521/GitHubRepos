package com.mufeng.githubrepos.util;

/**
 * Created by zhangqing on 2017/6/9.
 */
public class API {
    public static final String IP = "https://api.github.com";

    public static String getRepositoryList() {
        return IP + "/users/";
    }
}

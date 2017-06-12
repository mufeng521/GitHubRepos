package com.mufeng.githubrepos.model.bean;

/**
 * Created by zhangqing on 2017/6/9.
 */
public class Repository {
    private String username;//用户名
    private String url;//用户图像地址
    private String repository;//仓库名称
    private String preference;//语言偏好

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}

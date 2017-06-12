package com.mufeng.githubrepos.util;

import com.mufeng.githubrepos.model.bean.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqing on 2017/6/9.
 */
public class JSONUtil {


    public List<Repository> parseJsonContacts(String result) {
        List<Repository> repositories = new ArrayList<>();
        Repository repository;
        JSONArray jsonArrays;
        JSONObject jsonObjects;
        JSONObject jsonObject;
        try {
            jsonArrays = new JSONArray(result);
            for (int i = 0; i < jsonArrays.length(); i++) {
                repository = new Repository();

                jsonObjects = (JSONObject) jsonArrays.opt(i);

                repository.setRepository(jsonObjects.optString("name"));

                jsonObject = jsonObjects.optJSONObject("owner");

                repository.setUsername(jsonObject.optString("login"));
                repository.setUrl(jsonObject.optString("avatar_url"));
                repository.setPreference(jsonObjects.optString("language"));
                repositories.add(repository);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            repositories = new ArrayList<>();
        }
        return repositories;
    }
}

package com.mufeng.githubrepos.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Proxy;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.mufeng.githubrepos.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AsyncHttpGET {
    private String result;
    private static final String TAG = "AsyncHttpGET";

    public String getResponseInfo(String url, Context context)
            throws ClientProtocolException, IOException {
        System.out.println("--url--:" + url);

        AndroidHttpClient client = AndroidHttpClient.newInstance(prepareUserAgent(context));
        String httpProxy = Proxy.getDefaultHost();
        if (httpProxy != null && httpProxy.length() > 1 && client != null) {
            Log.v(TAG, "proxy name is " + httpProxy);
            int port = Proxy.getDefaultPort();
            HttpHost proxy = new HttpHost(httpProxy, port);
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        } else {
            Log.v(TAG, "proxy name is null");
        }

        // 将URL与参数拼接
        HttpGet getMethod = new HttpGet(url);
        if (client != null) {
            HttpParams httpParams = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
            HttpConnectionParams.setSoTimeout(httpParams, 50000);


            // 发起GET方法
            HttpResponse response = client.execute(getMethod);
            // 得到http响应结果的状态码
            int responseCode = response.getStatusLine().getStatusCode();
            String message = response.getStatusLine().getReasonPhrase();
            HttpEntity entity = response.getEntity();

            if (responseCode == 200 && entity != null) {
                result = EntityUtils.toString(entity, "utf-8");// 获取服务器响应内容
            } else {
                result = String
                        .format("Http error!\n %d:%s", responseCode, message);
            }
        }

        if (client != null) {
            client.close();
        }
        return result;
    }

    /**
     * Prepare the internal User-Agent string for use. This requires a
     * {@link Context} to pull the package name and version number for this
     * application.
     *
     * @param context Context对象
     * @return Http请求使用的User-Agent
     */
    public static String prepareUserAgent(Context context) {
        Log.v(TAG, "enter prepareUserAgent");

        String agent = null;

        try {
            // Read package name and version number from manifest
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            agent = String.format(context.getString(R.string.template_user_agent),
                    info.packageName, info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG,
                    "Couldn't find package information in PackageManager");
        }

        Log.v(TAG, "agent =" + agent);
        Log.v(TAG, "exit prepareUserAgent");

        return agent;
    }
}

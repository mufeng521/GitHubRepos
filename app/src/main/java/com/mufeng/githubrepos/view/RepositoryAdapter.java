package com.mufeng.githubrepos.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mufeng.githubrepos.R;
import com.mufeng.githubrepos.model.bean.Repository;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by zhangqing on 2017/6/9.
 */
public class RepositoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<Repository> mRepositories;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    public RepositoryAdapter(Context context, List<Repository> repositories) {
        mContext = context;
        mRepositories = repositories;
        mInflater = LayoutInflater.from(mContext);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public int getCount() {
        return mRepositories.size();
    }

    @Override
    public Repository getItem(int position) {
        return mRepositories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepositoryHolder holder;
        if (convertView == null) {
            holder = new RepositoryHolder();
            convertView = mInflater.inflate(R.layout.repository_item, null);

            holder.headIv = (ImageView) convertView.findViewById(R.id.head_url);
            holder.usernameTv = (TextView) convertView.findViewById(R.id.username_tv);
            holder.languageTv = (TextView) convertView.findViewById(R.id.language_tv);
            holder.repositoryTv = (TextView) convertView.findViewById(R.id.repository_tv);

            convertView.setTag(holder);
        } else {
            holder = (RepositoryHolder) convertView.getTag();
        }

        Repository repository = getItem(position);
        String url = repository.getUrl();
        if (!TextUtils.isEmpty(url)) {
            mImageLoader.displayImage(url, holder.headIv);
        } else {
            holder.headIv.setImageResource(R.drawable.icon_gsm);
        }
        holder.usernameTv.setText("用户名: " + repository.getUsername());
        holder.languageTv.setText("语言偏好: " + repository.getPreference());
        holder.repositoryTv.setText("仓库名: " + repository.getRepository());
        return convertView;
    }

    private class RepositoryHolder {
        ImageView headIv;
        TextView usernameTv;
        TextView languageTv;
        TextView repositoryTv;
    }
}

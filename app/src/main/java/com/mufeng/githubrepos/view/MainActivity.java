package com.mufeng.githubrepos.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mufeng.githubrepos.R;
import com.mufeng.githubrepos.model.bean.Repository;
import com.mufeng.githubrepos.presenter.RepositorySearchPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, IRepositorySearchView {
    private EditText mSearchEt;
    private ImageButton mClearBtn;
    private ListView mSearchLv;
    private RelativeLayout mEmptyRl;

    public static ProgressDialog mProgressDialog;
    private RepositorySearchPresenter mRepositorySearchPresenter;
    private RepositoryAdapter mAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initWidget();
        createProgressDialog();
        mRepositorySearchPresenter = new RepositorySearchPresenter(this, this);
    }

    /**
     * 绑定控件
     */
    private void initWidget() {
        mSearchEt = (EditText) findViewById(R.id.query);
        mClearBtn = (ImageButton) findViewById(R.id.search_clear);
        mEmptyRl = (RelativeLayout) findViewById(R.id.empty_rl);

        mSearchLv = (ListView) findViewById(R.id.search_lv);

        mClearBtn.setOnClickListener(this);

        // 根据输入框输入值的改变来过滤搜索
        mSearchEt.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_clear:
                mRepositorySearchPresenter.clear();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    // 根据输入框输入值的改变来过滤搜索
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && !"".equals(s.toString().trim())) {
            mClearBtn.setVisibility(View.VISIBLE);
            mRepositorySearchPresenter.search();
        } else {
            mEmptyRl.setVisibility(View.VISIBLE);
            mSearchLv.setVisibility(View.GONE);
            mClearBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public String getUserName() {
        return mSearchEt.getText().toString();
    }

    @Override
    public void clearUserName() {
        mSearchEt.setText("");
    }

    @Override
    public void showLoading() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("数据加载中...");
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showInfo(List<Repository> repositories) {
        if (mEmptyRl.getVisibility() == View.VISIBLE) {
            mEmptyRl.setVisibility(View.GONE);
            mSearchLv.setVisibility(View.VISIBLE);
        }
        if (mContext != null && repositories != null && repositories.size() != 0) {
            mAdapter = new RepositoryAdapter(this, repositories);
            mSearchLv.setAdapter(mAdapter);
        } else {
            mEmptyRl.setVisibility(View.VISIBLE);
            mSearchLv.setVisibility(View.GONE);
        }

    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "查询失败!", Toast.LENGTH_SHORT).show();
        if (mEmptyRl.getVisibility() == View.GONE) {
            mEmptyRl.setVisibility(View.VISIBLE);
            mSearchLv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置进度条
     */
    protected void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        //设置进度条是否为不明确
        mProgressDialog.setIndeterminate(true);
        //设置进度条是否可以按退回键取消
        mProgressDialog.setCancelable(false);
        //设置点击进度对话框外的区域对话框不消失
        mProgressDialog.setCanceledOnTouchOutside(false);
    }
}

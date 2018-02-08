package com.ittianyu.bcdnwatcher.features.web;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;

import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.base.BaseActivity;
import com.ittianyu.bcdnwatcher.databinding.ActivityWebBinding;


public class WebActivity extends BaseActivity {
    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";

    private String url;
    private String title;
    private ActivityWebBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_detail);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_web);


        initData();
        initView();
        initEvent();
    }

    private void initView() {
        // load website
        WebSettings settings = bind.wv.getSettings();
        settings.setJavaScriptEnabled(true);
        bind.wv.loadUrl(url);

        // init title
        if (!TextUtils.isEmpty(title))// set title
            bind.tvTitle.setText(title);

        setSupportActionBar(bind.tb);
    }

    private void initData() {
        Intent intent = getIntent();
        // get url title description
        url = intent.getStringExtra(EXTRA_URL);
        title = intent.getStringExtra(EXTRA_TITLE);
    }

    private void initEvent() {
        bind.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * menu selected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i_refresh:
                bind.wv.reload();
                break;
            case R.id.i_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}

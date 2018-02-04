package com.ittianyu.bcdnwatcher.features.me;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.utils.ClipboardUtils;
import com.ittianyu.bcdnwatcher.common.utils.DialogUtils;
import com.ittianyu.bcdnwatcher.databinding.FragmentMeBinding;
import com.ittianyu.bcdnwatcher.features.web.WebActivity;

/**
 * Created by 86839 on 2018/1/28.
 */

public class MeFragment extends Fragment {
    private static final String OPEN_SOURCE_URL = "https://github.com/ItTianYuStudio/BcdnWatcher";
    private static final String MINER_INSTALL_URL = "https://github.com/ItTianYuStudio/BcdnWatcher/tree/master/minerInstall";

    private FragmentMeBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        bind = DataBindingUtil.bind(view);

        initEvent();

    }

    private void initEvent() {
        bind.siDaiGua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtils.copyText(getContext(), "qq", "1125316526");
                DialogUtils.showInfoDialog(getContext(), getString(R.string.tips), getString(R.string.tips_dai_gua));
            }
        });

        bind.siMinerInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(WebActivity.EXTRA_TITLE, getString(R.string.miner_install));
                intent.putExtra(WebActivity.EXTRA_URL, MINER_INSTALL_URL);
                startActivity(intent);
            }
        });

        bind.siOpenSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(WebActivity.EXTRA_TITLE, getString(R.string.open_source));
                intent.putExtra(WebActivity.EXTRA_URL, OPEN_SOURCE_URL);
                startActivity(intent);
            }
        });

        bind.siAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showInfoDialog(getContext(), getString(R.string.tips), getString(R.string.tips_about_us));
            }
        });
    }

}

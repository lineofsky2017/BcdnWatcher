package com.ittianyu.bcdnwatcher.features.wool;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ittianyu.bcdnwatcher.R;
import com.ittianyu.bcdnwatcher.common.utils.ClipboardUtils;
import com.ittianyu.bcdnwatcher.databinding.FragmentWoolBinding;

/**
 * Created by 86839 on 2018/1/28.
 */

public class WoolFragment extends Fragment {

    private FragmentWoolBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wool, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
    }

    private void initView(View view) {
        bind = DataBindingUtil.bind(view);
    }

    private void initEvent() {
        bind.ivAlipayRedPacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 复制红包码
                ClipboardUtils.copyText(getContext(), "code", "EEP7Kx93ID");

                // 打开支付宝
                try {
                    PackageManager packageManager = getContext().getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                    startActivity(intent);
                }catch (Exception e) {
                    Toast.makeText(getContext(), R.string.tips_open_alipay_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

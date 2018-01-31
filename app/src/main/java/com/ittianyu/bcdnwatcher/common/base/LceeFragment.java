package com.ittianyu.bcdnwatcher.common.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.ittianyu.bcdnwatcher.common.bean.ListStatus;
import com.ittianyu.bcdnwatcher.common.bean.Status;

/**
 * Created by 86839 on 2018/1/28.
 */

public class LceeFragment extends Fragment {
    private Status status;
    private ListStatus listStatus = ListStatus.Content;
    // view
    private View vContent;
    private View vError;
    private View vLoading;
    private View vEmpty;

    public void setLcee(View vContent, View vError, View vLoading, View vEmpty) {
        this.vContent = vContent;
        this.vError = vError;
        this.vLoading = vLoading;
        this.vEmpty = vEmpty;

        setReloadListener(vError, vEmpty);
    }

    private void setReloadListener(View vError, View vEmpty) {
        View.OnClickListener reloadClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload();
            }
        };
        vError.setOnClickListener(reloadClickListener);
        vEmpty.setOnClickListener(reloadClickListener);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ListStatus getListStatus() {
        return listStatus;
    }

    public void setListStatus(ListStatus listStatus) {
        this.listStatus = listStatus;
    }

    public void showContent() {
        vContent.setVisibility(View.VISIBLE);
        vEmpty.setVisibility(View.GONE);
        vError.setVisibility(View.GONE);
        vLoading.setVisibility(View.GONE);
    }

    public void showEmpty() {
        vContent.setVisibility(View.GONE);
        vEmpty.setVisibility(View.VISIBLE);
        vError.setVisibility(View.GONE);
        vLoading.setVisibility(View.GONE);
    }

    public void showError() {
        vContent.setVisibility(View.GONE);
        vEmpty.setVisibility(View.GONE);
        vError.setVisibility(View.VISIBLE);
        vLoading.setVisibility(View.GONE);
    }

    public void showLoading() {
        vContent.setVisibility(View.GONE);
        vEmpty.setVisibility(View.GONE);
        vError.setVisibility(View.GONE);
        vLoading.setVisibility(View.VISIBLE);
    }

    public boolean isLoading() {
        return status == Status.Loading;
    }

    public void reload() {

    }

}

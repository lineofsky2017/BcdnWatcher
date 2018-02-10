package com.ittianyu.bcdnwatcher.common.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.ittianyu.bcdnwatcher.R;

import java.io.File;

/**
 * Created by 86839 on 2018/1/31.
 */

public class UpdateUtils {
    private static final String FILE_NAME = "BcdnWatcher.apk";


    public static void showUpdateDialog(final Context context, String title, String content, final String url, final boolean granted) {
        final Context appContext = context.getApplicationContext();
        DialogUtils.showOkCancelDialog(context, title, content, false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (granted) {
                    Toast.makeText(appContext, R.string.tips_download_start, Toast.LENGTH_SHORT).show();
                    startDownload(appContext, url);
                } else {
                    Toast.makeText(appContext, R.string.tips_no_permission_external, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static BroadcastReceiver startDownload(Context context, String url) {
        // delete old download file
        try {
            File file = new File(Environment.DIRECTORY_DOWNLOADS, FILE_NAME);
            file.delete();
        } catch (Exception e) {
        }

        // download
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,FILE_NAME);//保存到公共图片文件夹
        request.allowScanningByMediaScanner();//允许被扫描
        request.setVisibleInDownloadsUi(true);//通知栏一直显示
//        request.setTitle("文件下载");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//下载完成也会持续显示

        long taskId = downloadManager.enqueue(request);//得到下载文件的唯一id
//        initNotificationClickReceiver(context, id);
        DownLoadBroadcastReceiver receiver = new DownLoadBroadcastReceiver(taskId);
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return receiver;
    }

    public static class DownLoadBroadcastReceiver extends BroadcastReceiver {
        private long taskId;

        public DownLoadBroadcastReceiver(long taskId) {
            this.taskId = taskId;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (taskId == downloadId) {
                context.unregisterReceiver(this);
                Toast.makeText(context, R.string.tips_download_success, Toast.LENGTH_SHORT).show();

                String serviceString = Context.DOWNLOAD_SERVICE;
                DownloadManager dManager = (DownloadManager) context.getSystemService(serviceString);
                Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadId);
                installApk(context, downloadFileUri);
            }
        }

    }

    public static void installApk(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }
}

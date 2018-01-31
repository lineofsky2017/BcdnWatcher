package com.ittianyu.bcdnwatcher.common.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;

import java.io.File;

/**
 * Created by 86839 on 2018/1/31.
 */

public class UpdateUtils {

    public static void showUpdateDialog(final Context context, String title, String content, final String url) {
        final Context appContext = context.getApplicationContext();
        DialogUtils.showOkCancelDialog(context, title, content, false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startDownload(appContext, url);
            }
        });
    }

    private static void startDownload(Context context, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,"huge.jpg");//保存到公共图片文件夹
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "QQ.apk");//公共Download文件夹
        request.allowScanningByMediaScanner();//允许被扫描
        request.setVisibleInDownloadsUi(true);//通知栏一直显示
//        request.setTitle("文件下载");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//下载完成也会持续显示

        long id = downloadManager.enqueue(request);//得到下载文件的唯一id
        initNotificationClickReceiver(context, id);
    }

    private static void initNotificationClickReceiver(Context context, final long taskId) {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        BroadcastReceiver clickedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String extraId = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
                long[] references = intent.getLongArrayExtra(extraId);
                for (long refer : references) {
                    if (refer == taskId) {
                        DownloadStatus status = initDownLoadUri(context, taskId);
                        if (null == status)
                            continue;
                        if (status.getStatus() == DownloadManager.STATUS_SUCCESSFUL) {
                            installFile(context, status.getFileName());
                        }
                    }
                }
            }
        };
        context.registerReceiver(clickedReceiver, intentFilter);
    }


    private static void installFile(Context context, String fileName) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
        context.startActivity(i);
    }

    //下载管理查询,得到文件下载地址
    private static DownloadStatus initDownLoadUri(Context context, long taskId) {
        DownloadStatus result = null;

        DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
        myDownloadQuery.setFilterById(taskId);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(myDownloadQuery);
        if (cursor.moveToFirst()) {
            int fileNameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
//            int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
            //文件名称    /storage/sdcard0/DCIM/huge-5.jpg
            String fileName = cursor.getString(fileNameIdx);
            //文件地址   file:///storage/sdcard0/DCIM/huge-5.jpg
//            String fileUri = myDownload.getString(fileUriIdx);
            //得到当前状态
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            result = new DownloadStatus(status, fileName);
        }
        cursor.close();

        return result;
    }

    public static class DownloadStatus {
        private int status;// DownloadManager.STATUS_SUCCESSFUL
        private String fileName;

        public DownloadStatus(int status, String fileName) {
            this.status = status;
            this.fileName = fileName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String toString() {
            return "DownloadStatus{" +
                    "status=" + status +
                    ", fileName='" + fileName + '\'' +
                    '}';
        }
    }
}

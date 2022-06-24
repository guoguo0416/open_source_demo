package com.kwai.sdk.opensource.download;

import android.text.TextUtils;

public class DownloadTask implements Runnable {
  private final RequestInfo mRequestInfo;
  private IDownloadStatusListener mStatusListener;

  public DownloadTask(RequestInfo requestInfo) {
    mRequestInfo = requestInfo;
  }

  public IDownloadStatusListener getStatusListener() {
    return mStatusListener;
  }

  public void setStatusListener(IDownloadStatusListener statusListener) {
    mStatusListener = statusListener;
  }

  public boolean isCache(String url) {
    return mRequestInfo != null && !TextUtils.isEmpty(url) && mRequestInfo.getUrl().equals(url);
  }

  public void setStatus(int status) {
    if (mRequestInfo != null) {
      mRequestInfo.setStatus(status);
    }
  }

  public int getStatus() {
    return mRequestInfo != null ? mRequestInfo.getStatus() : 0;
  }

  @Override
  public void run() {
    // download task completed
    if (mStatusListener != null && mRequestInfo != null) {
      mStatusListener.onSuccess(this, mRequestInfo.getListener());
    }
  }
}

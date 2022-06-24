package com.kwai.sdk.opensource.download;

public interface IDownloadStatusListener {
  void onSuccess(DownloadTask task, DownloadListener listener);

  void onFailed(DownloadTask task, DownloadListener listener);
}

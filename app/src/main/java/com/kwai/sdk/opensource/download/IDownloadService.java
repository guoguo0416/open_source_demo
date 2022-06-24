package com.kwai.sdk.opensource.download;

public interface IDownloadService {
  void download(String downloadUrl, DownloadListener listener);
}

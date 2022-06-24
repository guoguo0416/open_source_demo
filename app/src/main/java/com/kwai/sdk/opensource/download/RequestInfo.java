package com.kwai.sdk.opensource.download;

import java.io.Serializable;

public class RequestInfo implements Serializable {

  private static final long serialVersionUID = -7266957566506676327L;
  private String key;
  private String url;
  private int status;
  private DownloadListener listener;

  public RequestInfo(String url, int status,
      DownloadListener listener) {
    this.url = url;
    this.status = status;
    this.listener = listener;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }


  public DownloadListener getListener() {
    return listener;
  }

  public void setListener(DownloadListener listener) {
    this.listener = listener;
  }
}

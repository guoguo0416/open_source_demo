package com.kwai.sdk.opensource.download;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.os.Looper;

import okhttp3.internal.Util;

public class DownloadScheduler {
  private final ThreadPoolExecutor mExecutor;
  private final Handler mHandler;

  public DownloadScheduler() {
    mExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
    mHandler = new Handler(Looper.getMainLooper());
  }

  public void enqueueTask(Runnable task) {
    mExecutor.execute(task);
  }

  public void postResponse(DownloadListener listener, boolean success) {
    if (listener != null) {
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          if (success) {
            listener.onSuccess("");
          } else {
            listener.onFailure("");
          }
        }
      });
    }
  }
}

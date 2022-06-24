package com.kwai.sdk.opensource.download;

import java.util.ArrayList;
import java.util.List;

public class DownloadManager {
  private IDownloadService mDownloadService;
  private List<DownloadTask> mReadyTasks;
  private List<DownloadTask> mRunningTasks;
  private int mMaxConcurrentCounts = 2; // 并发同时下载任务上限数目,默认 2
  private int mRunningCounts;
  private DownloadScheduler mDownloadScheduler;

  private DownloadManager() {
    mDownloadService = new DownloadImpl();
    mRunningTasks = new ArrayList<>(mMaxConcurrentCounts);
    mReadyTasks = new ArrayList<>();
    mDownloadScheduler = new DownloadScheduler();
  }

  public int getMaxConcurrentCounts() {
    return mMaxConcurrentCounts;
  }

  public void setMaxConcurrentCounts(int maxConcurrentCounts) {
    mMaxConcurrentCounts = maxConcurrentCounts;
  }

  private static class Holder {
    static final DownloadManager instance = new DownloadManager();
  }

  public static DownloadManager getInstance() {
    return Holder.instance;
  }

  public boolean checkCache(String url) {
    boolean ret = false;
    boolean checked = false;
    for (DownloadTask task : mRunningTasks) {
      if (task == null) {
        continue;
      }
      if (task.isCache(url)) {
        checked = true;
        ret = true;
      }
    }
    if (checked) {
      return ret;
    }
    for (DownloadTask task : mReadyTasks) {
      if (task == null) {
        continue;
      }
      if (task.isCache(url)) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public interface DownloadStatus {
    int IDLE = 0x01;
    int RUNNING = 0x02;
    int COMPLETED = 0x04;
    int ERROR = 0x08;
  }

  private final IDownloadStatusListener mDownloadStatusListener = new IDownloadStatusListener() {
    @Override
    public void onSuccess(DownloadTask task, DownloadListener listener) {
      mRunningCounts--;
      mRunningTasks.remove(task);
      mDownloadScheduler.postResponse(listener, true);
      flushNextTask();
    }

    @Override
    public void onFailed(DownloadTask task, DownloadListener listener) {
      mRunningCounts--;
      mRunningTasks.remove(task);
      mDownloadScheduler.postResponse(listener, false);
      flushNextTask();
    }
  };

  private DownloadTask getNext() {
    DownloadTask next = null;
    if (!mRunningTasks.isEmpty()) {
      next = mRunningTasks.get(mRunningCounts - 1);
    } else if (!mReadyTasks.isEmpty()) {
      next = mReadyTasks.get(mReadyTasks.size() - 1);
    }
    return next;
  }

  private void flushNextTask() {
    DownloadTask task = getNext();
    if (task != null) {
      doEnqueueTask(task);
    }
  }

  public void addTask(String downloadUrl, DownloadListener listener) {
    if (checkCache(downloadUrl) && listener != null) {
      listener.onSuccess("running");
      return;
    }

    RequestInfo requestInfo = new RequestInfo(downloadUrl, DownloadStatus.IDLE, listener);
    DownloadTask task = new DownloadTask(requestInfo);
    if (mRunningCounts >= mMaxConcurrentCounts) {
      mReadyTasks.add(task);
      flushNextTask();
    } else {
      doEnqueueTask(task);
    }
  }

  private void doEnqueueTask(DownloadTask task) {
    mRunningCounts++;
    task.setStatus(DownloadStatus.RUNNING);
    task.setStatusListener(mDownloadStatusListener);
    mRunningTasks.add(task);
    mDownloadScheduler.enqueueTask(task);
  }

}

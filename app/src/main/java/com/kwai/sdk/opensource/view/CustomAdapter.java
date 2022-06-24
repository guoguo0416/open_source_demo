package com.kwai.sdk.opensource.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kwai.sdk.opensource.R;
import com.kwai.sdk.opensource.data.BizDataModel;
import com.kwai.sdk.opensource.download.DownloadListener;
import com.kwai.sdk.opensource.download.DownloadManager;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

  private List<BizDataModel> mListData = new ArrayList<>();

  public CustomAdapter(List<BizDataModel> listData) {
    mListData = listData;
  }

  public void updateDataSet(final List<BizDataModel> listData) {
    if (mListData.size() > 0) {
      mListData.clear();
    }
    mListData = new ArrayList<>(listData);
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
    return new ViewHolder(root);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    doBindView(holder, position);
  }

  @Override
  public int getItemCount() {
    return mListData.size();
  }

  private void doBindView(@NonNull ViewHolder holder, int position) {
    BizDataModel dataModel = mListData.get(position);
    if (dataModel == null) {
      return;
    }
    Context context = holder.getIcon().getContext();
    Glide.with(context).load(dataModel.getIcon()).into(holder.getIcon());
    String title = !TextUtils.isEmpty(dataModel.getTitle()) ? dataModel.getTitle() : "No name";
    holder.getAppName().setText(title);

    bindDownloadView(holder, dataModel);
  }

  private void bindDownloadView(@NonNull ViewHolder holder, BizDataModel dataModel) {
    String url = dataModel.getDownload_link();
    if (TextUtils.isEmpty(url)) {
      return;
    }
    if (DownloadManager.getInstance().checkCache(url)) {
      // toast msg is downloading

    } else {
      Button btn = holder.getDownloadBtn();
      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          doDownload(url, holder, dataModel);
        }
      });
    }
  }

  private void doDownload(String url, ViewHolder holder, BizDataModel dataModel) {
    String status = holder.getTvStatus().getText().toString();
    if (!TextUtils.isEmpty(status) && status.equals("IDLE")) {
      holder.getTvStatus().setText("Downloading...");
    }
    DownloadManager.getInstance().addTask(url, new DownloadListener() {
      @Override
      public void onProgressUpdate(int progress) {
        String status = holder.getTvStatus().getText().toString();
        if (!TextUtils.isEmpty(status) && status.equals("IDLE")) {
          holder.getTvStatus().setText("Downloading...");
        }
      }

      @Override
      public void onSuccess(String file) {
        String status = holder.getTvStatus().getText().toString();
        if (!TextUtils.isEmpty(status)) {
          holder.getTvStatus().setText("download success");
        }
      }

      @Override
      public void onFailure(String reason) {
        String status = holder.getTvStatus().getText().toString();
        if (!TextUtils.isEmpty(status)) {
          holder.getTvStatus().setText("download error!");
        }
      }
    });
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private final ImageView icon;
    private final Button downloadBtn;
    private final TextView appName;
    private final TextView tvStatus;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      icon = itemView.findViewById(R.id.iv_app_icon);
      downloadBtn = itemView.findViewById(R.id.btn_download);
      appName = itemView.findViewById(R.id.tv_app_name);
      tvStatus = itemView.findViewById(R.id.tv_download_status);
    }

    public ImageView getIcon() {
      return icon;
    }

    public Button getDownloadBtn() {
      return downloadBtn;
    }

    public TextView getAppName() {
      return appName;
    }

    public TextView getTvStatus() {
      return tvStatus;
    }
  }


}

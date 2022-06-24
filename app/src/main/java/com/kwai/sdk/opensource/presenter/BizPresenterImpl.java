package com.kwai.sdk.opensource.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.util.Log;

import com.kwai.sdk.opensource.data.BizDataModel;
import com.kwai.sdk.opensource.data.NetResponseData;
import com.kwai.sdk.opensource.net.RetrofitManager;
import com.kwai.sdk.opensource.view.BaseViewInteract;
import com.kwai.sdk.opensource.view.CustomAdapter;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BizPresenterImpl implements BizPresenter {
  private static final String TAG = "BizPresenterImpl";
  private final WeakReference<BaseViewInteract> mViewRef;
  private CustomAdapter mAdapter;

  public BizPresenterImpl(BaseViewInteract viewRef) {
    mViewRef = new WeakReference<>(viewRef);
  }

  public void setDataObserver(CustomAdapter adapter) {
    mAdapter = adapter;
  }

  @Override
  public void start() {
    BaseViewInteract view = mViewRef.get();
    if (view != null) {
      view.showLoading();
    }
    Call<List<BizDataModel>> call = RetrofitManager.getInstance().getApi().getBizData();
    call.enqueue(new Callback<List<BizDataModel>>() {
      @Override
      public void onResponse(Call<List<BizDataModel>> call, Response<List<BizDataModel>> response) {
        List<BizDataModel> list = null;
        if (response.code() == 200) {
          Log.d(TAG, "presenter get data success");
          list = response.body();
        } else {
          Log.d(TAG, "presenter get data failed, code = " + response.code());
        }
        onGotResult(list);
      }

      @Override
      public void onFailure(Call<List<BizDataModel>> call, Throwable t) {
        Log.e(TAG, "presenter get data error, msg = " + t.getMessage());
        onGotResult(null);
      }
    });
  }

  private void onGotResult(List<BizDataModel> list) {
    BaseViewInteract view = mViewRef.get();
    if (view != null) {
      view.finishLoading();
    }
    if (list != null) {
      mAdapter.updateDataSet(list);
    }
  }
}

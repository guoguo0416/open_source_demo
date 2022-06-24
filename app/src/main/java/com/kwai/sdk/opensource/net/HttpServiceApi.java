package com.kwai.sdk.opensource.net;

import java.util.List;

import com.kwai.sdk.opensource.data.BizDataModel;
import com.kwai.sdk.opensource.data.NetResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpServiceApi {

  @GET("apps")
  Call<List<BizDataModel>> getBizData();

}

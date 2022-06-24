package com.kwai.sdk.opensource;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kwai.sdk.opensource.presenter.BasePresenter;
import com.kwai.sdk.opensource.presenter.BizPresenterImpl;
import com.kwai.sdk.opensource.view.BaseViewInteract;
import com.kwai.sdk.opensource.view.CustomAdapter;

public class MainActivity extends AppCompatActivity implements BaseViewInteract {

  private RecyclerView mRecyclerView;
  private CustomAdapter mCustomAdapter;
  private BasePresenter mPresenter;
  private TextView mTvLoading;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();
  }

  private void initViews() {
    mTvLoading = findViewById(R.id.tv_loading);
    mRecyclerView = findViewById(R.id.recycler_list);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mCustomAdapter = new CustomAdapter(new ArrayList<>());
    mRecyclerView.setAdapter(mCustomAdapter);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mPresenter = new BizPresenterImpl(this);
    ((BizPresenterImpl) mPresenter).setDataObserver(mCustomAdapter);
    mPresenter.start();
  }


  @Override
  public void showLoading() {
    mTvLoading.setVisibility(View.VISIBLE);
  }

  @Override
  public void finishLoading() {
    mTvLoading.setVisibility(View.INVISIBLE);
  }

  @Override
  public void toastMsg(String msg) {

  }
}
package com.kwai.sdk.opensource.data;

import java.io.Serializable;

public class NetResponseData<T> implements Serializable {
  private static final long serialVersionUID = 1826457540765733188L;
  private int code;
  private String message;
  private T data;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}

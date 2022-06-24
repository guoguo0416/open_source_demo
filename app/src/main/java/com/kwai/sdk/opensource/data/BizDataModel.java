package com.kwai.sdk.opensource.data;

import java.io.Serializable;

public class BizDataModel implements Serializable {

  private static final long serialVersionUID = -4862027527898299277L;


  /**
   * package_name : com.YsoCorp.MonsterBall
   * title : Monster Box
   * icon : https://play-lh.googleusercontent
   * .com/XR8M2NDT-0PxcBtyaln0dvWh0COzvZAHWuxV-Za5XicEM7JMRVqNlvFmPp5Ub_rA0FQ=s180
   * download_link : https://apks.dev.al-array.com/com.YsoCorp.MonsterBall/v0.3.4/com.YsoCorp
   * .MonsterBall_34v_armeabi-v7a_0dpi_21aal
   * .zip?Expires=1656490200&KeyName=apk-url-sign&Signature=uBpMOftyO7JrltS3bthNyhuSb9A=
   * file_size : 59505545
   */

  private String package_name;
  private String title;
  private String icon;
  private String download_link;
  private int file_size;

  public String getPackage_name() { return package_name;}

  public void setPackage_name(String package_name) { this.package_name = package_name;}

  public String getTitle() { return title;}

  public void setTitle(String title) { this.title = title;}

  public String getIcon() { return icon;}

  public void setIcon(String icon) { this.icon = icon;}

  public String getDownload_link() { return download_link;}

  public void setDownload_link(String download_link) { this.download_link = download_link;}

  public int getFile_size() { return file_size;}

  public void setFile_size(int file_size) { this.file_size = file_size;}
}

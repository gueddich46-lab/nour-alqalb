package com.bachir.nouralqalb;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.webkit.DownloadListener;
import android.app.DownloadManager;
import android.os.Environment;
import android.content.Context;

public class MainActivity extends Activity {
  private WebView web; private FrameLayout root; private View fullView; private WebChromeClient.CustomViewCallback fullCallback; private GeolocationPermissions.Callback geoCallback; private String geoOrigin;
  private static final String HOME="https://gueddich46-lab.github.io/nour-alqalb/";
  @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(0xff081b25);getWindow().setNavigationBarColor(0xff081b25);root=new FrameLayout(this);setContentView(root);web=new WebView(this);root.addView(web,new FrameLayout.LayoutParams(-1,-1));WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setAllowFileAccess(false);s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);s.setSupportZoom(true);s.setBuiltInZoomControls(true);s.setDisplayZoomControls(false);web.setWebViewClient(new WebViewClient(){@Override public boolean shouldOverrideUrlLoading(WebView v,WebResourceRequest r){Uri u=r.getUrl();if("https".equals(u.getScheme())&&"gueddich46-lab.github.io".equals(u.getHost())&&u.getPath().startsWith("/nour-alqalb/"))return false;try{startActivity(new Intent(Intent.ACTION_VIEW,u));}catch(Exception e){Toast.makeText(MainActivity.this,"تعذر فتح الرابط",Toast.LENGTH_SHORT).show();}return true;}});web.setWebChromeClient(new WebChromeClient(){@Override public void onGeolocationPermissionsShowPrompt(String origin,GeolocationPermissions.Callback callback){geoCallback=callback;geoOrigin=origin;if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){callback.invoke(origin,true,false);}else requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},23);}@Override public void onShowCustomView(View v,CustomViewCallback cb){if(fullView!=null){cb.onCustomViewHidden();return;}fullView=v;fullCallback=cb;web.setVisibility(View.GONE);root.addView(v,new FrameLayout.LayoutParams(-1,-1));}@Override public void onHideCustomView(){if(fullView==null)return;root.removeView(fullView);fullView=null;web.setVisibility(View.VISIBLE);if(fullCallback!=null){fullCallback.onCustomViewHidden();fullCallback=null;}}});web.setDownloadListener((url,userAgent,contentDisposition,mimetype,contentLength)->{try{if(!url.startsWith("https://")){Toast.makeText(this,"يمكن حفظ الصور من المتصفح",Toast.LENGTH_LONG).show();return;}DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);request.setDestinationInExternalFilesDir(this,Environment.DIRECTORY_DOWNLOADS,"nour-alqalb-download");((DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);}catch(Exception e){Toast.makeText(this,"تعذر التنزيل",Toast.LENGTH_SHORT).show();}});web.loadUrl(HOME);}
  @Override public void onRequestPermissionsResult(int code,String[] p,int[] results){super.onRequestPermissionsResult(code,p,results);if(code==23&&geoCallback!=null){geoCallback.invoke(geoOrigin,results.length>0&&results[0]==PackageManager.PERMISSION_GRANTED,false);geoCallback=null;}}
  @Override public void onBackPressed(){if(fullView!=null){web.getWebChromeClient().onHideCustomView();}else if(web.canGoBack()){web.goBack();}else super.onBackPressed();}
  @Override protected void onDestroy(){web.destroy();super.onDestroy();}
}

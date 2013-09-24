/*
 * Copyright (C) 2013 YROM.NET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tv.acfun.a63;

import java.io.IOException;

import tv.acfun.a63.swipe.SwipeSherlockActivity;
import tv.acfun.a63.util.ActionBarUtil;
import tv.acfun.a63.util.Connectivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.umeng.analytics.MobclickAgent;

/**
 * @author Yrom
 *
 */
public class BaseWebViewActivity extends SwipeSherlockActivity {
    protected WebView mWeb;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        ActionBarUtil.setXiaomiFilterDisplayOptions(getSupportActionBar(), false);
        setContentView(R.layout.activity_article);
        mWeb = (WebView) findViewById(R.id.webview);
        mWeb.getSettings().setAllowFileAccess(true);
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.getSettings().setUserAgentString(Connectivity.UA);
        initView(savedInstanceState);
        mWeb.loadUrl("file:///android_asset/loading.html");
        initData();
    }
    protected DialogInterface.OnClickListener mErrorDialogListener = new DialogInterface.OnClickListener() {
        
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if(which == DialogInterface.BUTTON_POSITIVE){
                initData();
            }else{
                finish();
            }
            
        }
    };
    protected void showErrorDialog(){
        try {
            Drawable icon = Drawable.createFromStream(getAssets().open("emotion/ais/27.gif"),"27.gif");
            icon.setBounds(0,0,icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
            new AlertDialog.Builder(this).setTitle("加载失败！").setIcon(icon).setMessage("是否重试？").setPositiveButton("重试", mErrorDialogListener ).setNegativeButton("算了", mErrorDialogListener).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void initView(Bundle savedInstanceState) {
        
    }

    protected void initData(){
        setSupportProgressBarIndeterminateVisibility(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

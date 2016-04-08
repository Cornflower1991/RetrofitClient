package com.myjiashi.common;

import android.app.Application;


/**
 * 公共的Application
 * 
 * @author yexiuliang  <p/>
 *
 */
public abstract class CommonApplication extends Application {
	
	static CommonApplication APP;
	
	private boolean accountChanged;
	
	public static CommonApplication getApplication() {
		return APP;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		if (APP == null) {
			APP = this;
		}
	}
	
	/** 切换账号有些数据要刷新  */
    
    
    public void setAccountChanged(boolean change) {
        accountChanged = change;
    }
    
    public boolean isAccountChanged() {
        return accountChanged;
    }

    public abstract String getAppId();
	
}

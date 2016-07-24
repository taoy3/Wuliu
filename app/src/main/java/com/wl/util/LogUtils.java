package com.wl.util;

import android.os.Handler;
import android.os.Message;

import com.wl.BaseApp;
import com.wl.R;
import com.wl.constant.Config;
import com.wl.constant.UrlConstant;

import org.json.JSONObject;

public class LogUtils {
    public static NotifyLoginMsg notifyLoginMsg;
    public static void login(String userName, String passWord) {
        JSONObject obj = new JSONObject();
        try {
            obj.put(Config.USERNAME, userName);
            obj.put(Config.PASSWORD, passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppHttpUtils.postUtils(UrlConstant.getLogInUrl(), "POST",obj.toString(), new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what/100==2){
                    BaseApp.loginStatus =BaseApp.LOGINOK;
                    Messaging.post(R.string.login,0);
                }else {
                    BaseApp.loginStatus=BaseApp.LOGINFAIL;
                    Messaging.post(R.string.login_fail,0);
                }
                if (notifyLoginMsg != null) {
                    notifyLoginMsg.logMsg(msg.what, msg.obj.toString());
                    notifyLoginMsg = null;
                }
            }
        });
    }
    public interface NotifyLoginMsg{
        void logMsg(int state,String result);
    }

    public static void setMsg(NotifyLoginMsg notifyLoginMsg) {
        LogUtils.notifyLoginMsg = notifyLoginMsg;
    }
}

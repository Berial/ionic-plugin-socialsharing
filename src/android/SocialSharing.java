package xyz.berial.socialsharing;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * This class echoes a string called from JavaScript.
 */
public class SocialSharing extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        ShareSDK.initSDK(cordova.getActivity());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("shareWeibo".equals(action)) {
            _share(SinaWeibo.NAME, args.getString(0), callbackContext);
        } else if ("shareQQ".equals(action)) {
            _share(QQ.NAME, args.getString(0), callbackContext);
        } else if ("shareWeixin".equals(action)) {
            _share(Wechat.NAME, args.getString(0), callbackContext);
        } else if ("shareWeixinMoment".equals(action)) {
            _share(WechatMoments.NAME, args.getString(0), callbackContext);
        } else if ("shareSMS".equals(action)) {
            _share(ShortMessage.NAME, args.getString(0), callbackContext);
        }
        return false;
    }

    private void _share(String platName, String message, final CallbackContext callbackContext) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setPlatform(platName);
        oks.setDialogMode();
        oks.setText(message);
        oks.setShareFromQQAuthSupport(false);
        oks.setSite("幸福钱庄");
        oks.setVenueName("幸福钱庄");
        oks.setTitle("幸福钱庄");
        oks.setCallback(new PlatformActionListener() {

            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                callbackContext.success(88);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                callbackContext.success(1);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                callbackContext.success(0);
            }
        });
        oks.show(cordova.getActivity());
    }
}
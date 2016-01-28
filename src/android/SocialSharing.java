package xyz.berial.socialsharing;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * This class echoes a string called from JavaScript.
 */
public class SocialSharing extends CordovaPlugin {

    private PlatformActionAdapter _listener;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        ShareSDK.initSDK(cordova.getActivity());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        _listener = new PlatformActionAdapter(callbackContext);
        if ("shareWeibo".equals(action)) {
            _shareWeibo(args.getString(0));
            return true;
        } else if ("shareQQ".equals(action)) {
            _shareQQ(args.getString(0));
            return true;
        } else if ("shareWeixin".equals(action)) {
            _shareWechat(args.getString(0));
            return true;
        } else if ("shareWeixinMoments".equals(action)) {
            _shareWechatMoments(args.getString(0));
            return true;
        } else if ("shareSMS".equals(action)) {
            _shareSMS(args.getString(0));
            return true;
        }
        return false;
    }

    private void _shareWechat(String message) {
        Wechat wechat = (Wechat) ShareSDK.getPlatform(Wechat.NAME);
        Wechat.ShareParams params = new Wechat.ShareParams();
        params.setShareType(Platform.SHARE_TEXT);
        params.setText(message);
        wechat.setPlatformActionListener(_listener);
        wechat.share(params);
    }

    private void _shareWechatMoments(String message) {
        WechatMoments wechatMoments = (WechatMoments) ShareSDK.getPlatform(WechatMoments.NAME);
        WechatMoments.ShareParams params = new WechatMoments.ShareParams();
        params.setShareType(Platform.SHARE_TEXT);
        params.setText(message);
        wechatMoments.setPlatformActionListener(_listener);
        wechatMoments.share(params);
    }

    private void _shareQQ(String message) {
        QQ qq = (QQ) ShareSDK.getPlatform(QQ.NAME);
        QQ.ShareParams params = new QQ.ShareParams();
        params.setText(message);
        qq.setPlatformActionListener(_listener);
        qq.share(params);
    }

    private void _shareWeibo(String message) {
        SinaWeibo weibo = (SinaWeibo) ShareSDK.getPlatform(SinaWeibo.NAME);
        SinaWeibo.ShareParams params = new SinaWeibo.ShareParams();
        params.setText(message);
        weibo.setPlatformActionListener(_listener);
        weibo.share(params);
    }

    private void _shareSMS(String message) {
        ShortMessage sms = (ShortMessage) ShareSDK.getPlatform(ShortMessage.NAME);
        ShortMessage.ShareParams params = new ShortMessage.ShareParams();
        params.setText(message);
        sms.setPlatformActionListener(_listener);
        sms.share(params);
    }

    private static class PlatformActionAdapter implements PlatformActionListener {

        private WeakReference<CallbackContext> _callback;

        PlatformActionAdapter(CallbackContext callbackContext) {
            _callback = new WeakReference<CallbackContext>(callbackContext);
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            _callback.get().success("success");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            _callback.get().error("error");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            _callback.get().error("cancel");
        }
    }
}
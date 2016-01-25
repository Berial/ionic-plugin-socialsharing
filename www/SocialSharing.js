var exec = require('cordova/exec');

exports.shareWeibo = function(message, success, error) {
    exec(success, error, "SocialSharing", "shareWeibo", [message]);
};

exports.shareQQ = function(message, success, error) {
    exec(success, error, "SocialSharing", "shareQQ", [message]);
};

exports.shareWeixin = function(message, success, error) {
    exec(success, error, "SocialSharing", "shareWeixin", [message]);
};

exports.shareWeixinMoments = function(message, success, error) {
    exec(success, error, "SocialSharing", "shareWeixinMoments", [message]);
};

exports.shareSMS = function(message, success, error) {
    exec(success, error, "SocialSharing", "shareSMS", [message]);
};
package com.davdian.service.dvdshare.exception;


import com.davdian.service.dvdshare.shareinterface.CommonSharePlatform;

public class ShareClientNotFoundException extends Exception {

    private int platType;

    private ShareClientNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public int getPlatType() {
        return platType;
    }

    public static ShareClientNotFoundException newInstanceByPlatType(int pPlatType) {
        String msg;
        switch (pPlatType) {
            case CommonSharePlatform.TYPE_QQ:
            case CommonSharePlatform.TYPE_Q_ZONE:
                msg = "QQ没有安装";
                break;
            case CommonSharePlatform.TYPE_WX:
            case CommonSharePlatform.TYPE_WX_MOMENTS:
                msg = "微信没有安装";
                break;
            default:
                msg = "无法分享,请联系客服[type=" + pPlatType + "]";
                break;
        }
        ShareClientNotFoundException exception = new ShareClientNotFoundException(msg);
        exception.platType = pPlatType;
        return exception;
    }


}

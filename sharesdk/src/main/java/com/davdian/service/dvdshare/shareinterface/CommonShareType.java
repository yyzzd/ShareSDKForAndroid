package com.davdian.service.dvdshare.shareinterface;

import cn.sharesdk.framework.Platform;

public interface CommonShareType {
    /**
     * 图片分享
     */
    int SHARE_IMAGE = Platform.SHARE_IMAGE;
    /**
     * 图文链接分享
     */
    int SHARE_WEBPAGE = Platform.SHARE_WEBPAGE;
    /**
     * 文字分享
     */
    int SHARE_TEXT = Platform.SHARE_TEXT;
}

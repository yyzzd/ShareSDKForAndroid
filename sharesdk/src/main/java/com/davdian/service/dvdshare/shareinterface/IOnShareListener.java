package com.davdian.service.dvdshare.shareinterface;

import cn.sharesdk.framework.Platform;

interface IOnShareListener {
    void onSuccess(Platform platform);

    void onError(Exception exception, Platform platform);

    void onCancel(Platform platform);
}

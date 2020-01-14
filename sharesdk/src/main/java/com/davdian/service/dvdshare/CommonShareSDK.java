package com.davdian.service.dvdshare;


import android.os.Handler;
import android.text.TextUtils;

import com.davdian.service.dvdshare.bean.SimpleShareData;
import com.davdian.service.dvdshare.exception.ShareClientNotFoundException;
import com.davdian.service.dvdshare.exception.ShareUnSupportPlatformException;
import com.davdian.service.dvdshare.exception.MisfitArgumentException;
import com.davdian.service.dvdshare.shareinterface.CommonSharePlatform;
import com.davdian.service.dvdshare.shareinterface.CommonShareResultListener;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by dengyizheng on 2017/10/20.
 * 这是对sdk的一层封装
 */
class CommonShareSDK implements CommonSharePlatform {
    /**
     * 分享平台
     */
    private int platType;
    /**
     * 分享类型
     */
    private int shareAction;
    /**
     * 分享数据
     */
    private SimpleShareData dvdShareData;
    /**
     * 将回调切回到主线程
     */
    private Handler mHandler = new Handler();

    /**
     * 构造 默认设置分享类型为图文链接
     *
     * @param dvdShareData 分享数据
     * @param platType     分享渠道
     */
    CommonShareSDK(SimpleShareData dvdShareData, int platType) {
        shareAction = Platform.SHARE_WEBPAGE;
        this.platType = platType;
        this.dvdShareData = dvdShareData;
    }

    void setShareAction(int pShareAction) {
        switch (pShareAction) {
            case Platform.SHARE_IMAGE:
            case Platform.SHARE_WEBPAGE:
            case Platform.SHARE_TEXT:
                shareAction = pShareAction;
                break;
            default:
                throw new IllegalArgumentException("unknown share type!!!");
        }
    }

    /**
     * 设置分享数据
     */
    private Platform.ShareParams buildShareParams(SimpleShareData dvdShareInfoBean) throws MisfitArgumentException {
        if (shareAction == Platform.SHARE_WEBPAGE) {
            checkWebPageShareData(dvdShareInfoBean);
        } else if (shareAction == Platform.SHARE_IMAGE) {
            checkImageShareData(dvdShareInfoBean);
        }
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setTitle(dvdShareInfoBean.getTitle());
        shareParams.setTitleUrl(dvdShareInfoBean.getTitleUrl());
        shareParams.setText(dvdShareInfoBean.getText());
        shareParams.setUrl(dvdShareInfoBean.getUrl());
        if (TextUtils.isEmpty(dvdShareInfoBean.getImageUrl())) {
            shareParams.setImagePath(dvdShareInfoBean.getImagePath());
        } else {
            shareParams.setImageUrl(dvdShareInfoBean.getImageUrl());
        }
        shareParams.setSite(dvdShareInfoBean.getSite());
        shareParams.setSiteUrl(dvdShareInfoBean.getSiteUrl());
        shareParams.setShareType(shareAction);
        return shareParams;
    }

    /**
     * 检查单图分享的数据是否合法
     *
     * @param dvdShareInfoBean 分享数据
     */
    private void checkImageShareData(SimpleShareData dvdShareInfoBean) throws MisfitArgumentException {
        if (dvdShareInfoBean == null) {
            throw new MisfitArgumentException("shareInfo is null !!!");
        }
        if (TextUtils.isEmpty(dvdShareInfoBean.getImageUrl())
                && TextUtils.isEmpty(dvdShareInfoBean.getImagePath())) {
            throw new MisfitArgumentException("image url is empty !!!");
        }
    }

    /**
     * 检查图文链接分享的数据
     *
     * @param dvdShareInfoBean 分享数据
     */
    private void checkWebPageShareData(SimpleShareData dvdShareInfoBean) throws MisfitArgumentException {
        if (dvdShareInfoBean == null) {
            throw new MisfitArgumentException("shareInfo is null !!!");
        }

        if (TextUtils.isEmpty(dvdShareInfoBean.getUrl())) {
            throw new MisfitArgumentException("url is empty !!!");
        }

        if (TextUtils.isEmpty(dvdShareInfoBean.getTitle())) {
            throw new MisfitArgumentException("title is empty !!!");
        }

        if (TextUtils.isEmpty(dvdShareInfoBean.getTitleUrl())) {
            throw new MisfitArgumentException("titleUrl is empty !!!");
        }

        if (TextUtils.isEmpty(dvdShareInfoBean.getSiteUrl())) {
            throw new MisfitArgumentException("siteUrl is empty !!!");
        }
    }

    /**
     * 执行分享
     *
     * @param pShareData 分享数据
     * @param listener   结果回调
     */
    private void onShare(SimpleShareData pShareData, final CommonShareResultListener listener) throws ShareClientNotFoundException, ShareUnSupportPlatformException, MisfitArgumentException {
        Platform.ShareParams shareParams = buildShareParams(pShareData);
        Platform platform = getPlatform();
        // 设置分享事件回调
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
                if (listener != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(platform);
                        }
                    });
                }
            }

            @Override
            public void onError(final Platform platform, int i, final Throwable throwable) {
                if (listener != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(new Exception(throwable == null ? "分享失败" : throwable.getMessage()), platform);
                        }
                    });
                }
            }

            @Override
            public void onCancel(final Platform platform, int i) {
                if (listener != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onCancel(platform);
                        }
                    });
                }
            }
        });
        // 执行分享
        platform.share(shareParams);
    }

    /**
     * 获取分享渠道
     *
     * @return 分享渠道
     */
    private Platform getPlatform() throws ShareUnSupportPlatformException, ShareClientNotFoundException {
        Platform platform = null;
        switch (platType) {
            case TYPE_QQ:
                platform = cn.sharesdk.framework.ShareSDK.getPlatform(QQ.NAME);
                checkClientValid(platform);
                break;
            case TYPE_Q_ZONE:
                platform = cn.sharesdk.framework.ShareSDK.getPlatform(QZone.NAME);
                checkClientValid(platform);
                break;
            case TYPE_WX:
                platform = cn.sharesdk.framework.ShareSDK.getPlatform(Wechat.NAME);
                checkClientValid(platform);
                break;
            case TYPE_WX_MOMENTS:
                platform = cn.sharesdk.framework.ShareSDK.getPlatform(WechatMoments.NAME);
                checkClientValid(platform);
                break;
        }
        if (platform == null) {
            throw new ShareUnSupportPlatformException("Did not find platform[type=" + platType + "]");
        }
        return platform;
    }

    /**
     * 检查分享渠道
     */
    private void checkClientValid(Platform pPlatform) throws ShareClientNotFoundException {
        if (pPlatform != null && !pPlatform.isClientValid()) {
            throw ShareClientNotFoundException.newInstanceByPlatType(platType);
        }
    }

    /**
     * 执行分享
     *
     * @param listener 结果回调
     */
    void toShare(CommonShareResultListener listener) {
        try {
            onShare(dvdShareData, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

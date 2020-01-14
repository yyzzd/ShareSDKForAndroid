package com.davdian.service.dvdshare;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;

import com.davdian.service.dvdshare.bean.SimpleShareData;
import com.davdian.service.dvdshare.shareinterface.CommonSharePlatform;
import com.davdian.service.dvdshare.shareinterface.CommonShareResultListener;
import com.mob.MobSDK;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;

/**
 * Created by dengyizheng on 2017/10/17.
 * 分享服务
 */

public class CommonShareService {

    /**
     * 分享结果监听
     */
    private CommonShareResultListener resultListener;
    /**
     * 分享类型
     */
    private int shareAction = Platform.SHARE_WEBPAGE;

    /**
     * 获取一个分享服务对象
     */
    public static CommonShareService getInstance() {
        return new CommonShareService();
    }

    /**
     * 分享服务初始化
     */
    public void init(Context context, String appKey, String AppSecret) {
        MobSDK.init(context, appKey, AppSecret);
    }

    /**
     * 设置分享类型
     *
     * @param shareAction 分享类型
     */
    public CommonShareService setShareAction(int shareAction) {
        this.shareAction = shareAction;
        return this;
    }

    /**
     * 设置结果回调
     *
     * @param listener 结果回调
     */
    public CommonShareService setShareResultListener(CommonShareResultListener listener) {
        this.resultListener = listener;
        return this;
    }

    /**
     * 直接调起分享
     *
     * @param shareData 分享数据
     * @param platType  分享平台
     */
    public void toShare(SimpleShareData shareData, int platType) {
        if (shareData != null) {
            //直接调起分享
            CommonShareSDK shareSDK = new CommonShareSDK(shareData, platType);
            shareSDK.setShareAction(shareAction);
            shareSDK.toShare(resultListener);
        }
    }

    /**
     * 分享图片（支持多图分享）以及描述
     * 1、分享到微信朋友圈，描述会输入到朋友圈文案部分
     * 2、分享到微信好友，将多张图片一次性发送给好友
     * 3、分享到qq好友，将多张图片一次性发送给好友
     *
     * @param context     上下文对象
     * @param files       图片文件集合
     * @param description 文案描述
     * @param shareType   渠道：1、微信好友 2、微信朋友圈 3、qq好友
     */
    public void shareImgList(Context context, List<File> files, String description, int shareType) throws Exception {
        Intent intent = new Intent();
        ComponentName comp;
        switch (shareType) {
            case CommonSharePlatform.TYPE_WX:
                if (!CommonShareUtil.isWeChatAvailable(context)) {
                    if (resultListener != null) {
                        resultListener.onError(new Exception("微信未安装"), null);
                    }
                    return;
                }
                comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                break;
            case CommonSharePlatform.TYPE_WX_MOMENTS:
                if (!CommonShareUtil.isWeChatAvailable(context)) {
                    if (resultListener != null) {
                        resultListener.onError(new Exception("微信未安装"), null);
                    }
                    return;
                }
                comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                break;
            case CommonSharePlatform.TYPE_QQ:
                if (!CommonShareUtil.isQQClientAvailable(context)) {
                    if (resultListener != null) {
                        resultListener.onError(new Exception("QQ未安装"), null);
                    }
                    return;
                }
                comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                break;
            default:
                return;
        }
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (File f : files) {
            imageUris.add(Uri.fromFile(f));
        }
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        fixShareFileOnN();
        intent.setType("image/*");
        if (files.size() == 1) {
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, imageUris.get(0));
        } else {
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        }
        intent.putExtra("Kdescription", description);
        context.startActivity(intent);
    }

    /**
     * 解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
     * 因为微信朋友圈分享多张图片需要用到这个，所以需要加下面的代码
     */
    private static void fixShareFileOnN() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
}

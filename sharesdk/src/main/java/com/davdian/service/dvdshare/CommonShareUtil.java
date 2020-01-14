package com.davdian.service.dvdshare;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by dengyizheng on 2018/5/4.
 * <p>
 * 分享服务util
 */

class CommonShareUtil {

    /**
     * 检查微信是否安装
     */
    static boolean isWeChatAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfo.size(); i++) {
            String pn = packageInfo.get(i).packageName;
            if (pn.equalsIgnoreCase("com.tencent.mm")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfo.size(); i++) {
            String pn = packageInfo.get(i).packageName;
            if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                return true;
            }
        }
        return false;
    }
}

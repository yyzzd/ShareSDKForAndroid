package com.davdian.service.dvdshare;

import com.davdian.service.dvdshare.bean.SimpleShareData;

public class SimpleShareDataFactory {

    /**
     * 构建图文链接分享数据
     *
     * @param pTitle    分享标题
     * @param pText     描述
     * @param pImageUrl 图片icon
     * @param pLink     跳转链接
     * @return 分享数据
     */
    public static SimpleShareData createShareData(String pTitle, String pText, String pImageUrl, String pLink) {
        return new SimpleShareData.Builder()
                .setTitle(pTitle)
                .setText(pText)
                .setLink(pLink)
                .setImageUrl(pImageUrl)
                .build();
    }

    /**
     * 构建单图分享数据
     *
     * @param pImageUrl 图片地址
     * @return 分享数据
     */
    public static SimpleShareData createShareData(String pImageUrl) {
        return new SimpleShareData.Builder()
                .setImageUrl(pImageUrl)
                .build();
    }
}

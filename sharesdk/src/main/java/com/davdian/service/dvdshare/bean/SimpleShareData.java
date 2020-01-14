package com.davdian.service.dvdshare.bean;

/**
 * @author bigniu
 */
public class SimpleShareData {
    /**
     * 分享标题
     */
    private String title;
    /**
     * 分享描述
     */
    private String text;
    /**
     * 分享图片
     */
    private String imageUrl;
    /**
     * 分享链接
     */
    private String link;
    /**
     * 分享本地图片地址
     */
    private String imagePath;
    /**
     * 网站名称QQ空间使用
     */
    private String site;

    private SimpleShareData(Builder pBuilder) {
        this.imageUrl = pBuilder.imageUrl;
        this.title = pBuilder.title;
        this.text = pBuilder.text;
        this.link = pBuilder.link;
        this.imagePath = pBuilder.imagePath;
        this.site = pBuilder.site;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitleUrl() {
        return link;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getUrl() {
        return link;
    }

    public String getSite() {
        return site;
    }

    public String getSiteUrl() {
        return link;
    }

    public static class Builder {
        private String title;
        private String text;
        private String imageUrl;
        private String link;
        private String imagePath;

        /**
         * 网站名称QQ空间使用
         */
        private String site;

        public Builder setTitle(String pTitle) {
            title = pTitle;
            return this;
        }

        public Builder setText(String pText) {
            text = pText;
            return this;
        }

        public Builder setImageUrl(String pImageUrl) {
            this.imageUrl = pImageUrl;
            return this;
        }

        public Builder setLink(String pLink) {
            link = pLink;
            return this;
        }

        public Builder setImagePath(String pImagePath) {
            imagePath = pImagePath;
            return this;
        }

        public Builder setSite(String pSite) {
            site = pSite;
            return this;
        }

        public SimpleShareData build() {
            return new SimpleShareData(this);
        }
    }
}

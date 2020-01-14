# ShareSDKForAndroid

对Mob ShareSDK做了一层简单封装，目前项目中只提供了单图、文案、图文链接、多图分享功能，需要更多分享能力可以自行扩展。

## 接入步骤

1、将工程项目中的sharesdk文件夹拷贝到你的项目里并在app下的build.gradle加入依赖（也可以将sharesdk上传至maven仓库进行远程依赖）
```java
implementation project(path: ':sharesdk')
```
2、将app/src/main/assets目录下的ShareSDK.xml文件拷贝到你项目中对应的assets目录下，并将其中各渠道的AppKey、AppId等内容替换为你的项目在对应平台上注册的AppKey。（只需要替换使用的渠道，比如只用微信分享就只需要填写微信的key）

3、在AndroidManifest.xml中加入必要权限、第三方分享的结果回调的activity页面，具体可参考app目录下的AndroidManifest.xml。


## 使用
在Application的onCreate()中进行初始化
```java
CommonShareService.getInstance().init(this, "mob平台注册的appKey", "mob平台注册的appSecret");
```
调起分享
```java
SimpleShareData dvdSimpleShareData = new SimpleShareData.Builder()
                        .setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg")
                        .build();
                CommonShareService.getInstance()
                        .setShareAction(CommonShareType.SHARE_IMAGE)
                        .toShare(dvdSimpleShareData, CommonSharePlatform.TYPE_Q_ZONE);
```

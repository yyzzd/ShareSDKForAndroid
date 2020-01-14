package com.example.sharesdkforandroid;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.davdian.service.dvdshare.CommonShareService;
import com.davdian.service.dvdshare.bean.SimpleShareData;
import com.davdian.service.dvdshare.shareinterface.CommonSharePlatform;
import com.davdian.service.dvdshare.shareinterface.CommonShareType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_to_share_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleShareData dvdSimpleShareData = new SimpleShareData.Builder()
                        .setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg")
                        .build();
                CommonShareService.getInstance()
                        .setShareAction(CommonShareType.SHARE_IMAGE)
                        .toShare(dvdSimpleShareData, CommonSharePlatform.TYPE_QQ);
            }
        });


        findViewById(R.id.tv_to_share_q_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleShareData dvdSimpleShareData = new SimpleShareData.Builder()
                        .setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg")
                        .build();
                CommonShareService.getInstance()
                        .setShareAction(CommonShareType.SHARE_IMAGE)
                        .toShare(dvdSimpleShareData, CommonSharePlatform.TYPE_Q_ZONE);
            }
        });

        findViewById(R.id.tv_to_share_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleShareData dvdSimpleShareData = new SimpleShareData.Builder()
                        .setText("分享文案")
                        .setTitle("分享标题")
                        .setLink("http://mob.com")
                        .setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg")
                        .build();
                CommonShareService.getInstance()
                        .setShareAction(CommonShareType.SHARE_WEBPAGE)
                        .toShare(dvdSimpleShareData, CommonSharePlatform.TYPE_WX);

            }
        });

        findViewById(R.id.tv_to_share_wx_moment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleShareData dvdSimpleShareData = new SimpleShareData.Builder()
                        .setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg")
                        .build();
                CommonShareService.getInstance()
                        .setShareAction(CommonShareType.SHARE_IMAGE)
                        .toShare(dvdSimpleShareData, CommonSharePlatform.TYPE_WX_MOMENTS);
            }
        });
    }
}

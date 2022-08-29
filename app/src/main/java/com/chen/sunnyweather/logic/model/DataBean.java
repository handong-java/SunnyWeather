package com.chen.sunnyweather.logic.model;



import com.chen.sunnyweather.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataBean implements Serializable {
    public String imageUrl;
    public String title;
    public String url;
    public int imageId;


    public DataBean(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;

    }

    public DataBean(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static List<DataBean> getTestData() {
        return new ArrayList<DataBean>(){
            {
                add(new DataBean("标题一", R.drawable.guide_1));
                add(new DataBean("标题二", R.drawable.guide_2));
                add(new DataBean("标题三", R.drawable.guide_3));
                add(new DataBean("标题四", R.drawable.guide_4));
            }
        };
//        list.add(new DataBean("https://dfzximg02.dftoutiao.com/news/20210401/20210401222253_acbd3f414dc150c4cf25d91dcac0cf36_1_mwpm_03201609.jpeg", "标题0" ));
//        list.add(new DataBean("https://dfzximg02.dftoutiao.com/news/20210401/20210401222253_acbd3f414dc150c4cf25d91dcac0cf36_2_mwpm_03201609.jpeg", "标题1" ));
//        list.add(new DataBean("https://dfzximg02.dftoutiao.com/news/20210401/20210401221040_d2957dc25d3878b4fdf0816289cde4fd_1_mwpm_03201609.png", "标题2"));
//        list.add(new DataBean("http://cn.bing.com/th?id=OHR.FooledYa_EN-CN7497696381_1920x1080.jpg", "标题3"));
//        list.add(new DataBean("https://dfzximg02.dftoutiao.com/news/20210401/20210401222006_3a2f89e1dbaa16f8a7f7754e00d8bf77_1_mwpm_03201609.jpeg", "标题4"));
    }
}

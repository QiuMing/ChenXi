package com.hunter.chenxi.bean.PartnerBean;

/**
 * 创建人：SunShine
 * 功能说明：Partner的Sliders
 */
public class Sliders {

    public String titie;
    public String url;
    public String pic_url;

    public void setTitie(String titie) {
        this.titie = titie;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    @Override
    public String toString() {
        return "Sliders{" +
                "titie='" + titie + '\'' +
                ", url='" + url + '\'' +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }

    public String getTitie() {
        return titie;
    }

    public String getUrl() {
        return url;
    }

    public String getPic_url() {
        return pic_url;
    }
}

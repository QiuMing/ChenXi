package com.hunter.chenxi.bean.PartnerBean;

/**
 * 创建人：SunShine
 * 功能说明：
 */
public class Categories {

    public int id;
    public String title;
    public String more_url;
    public boolean show_more;
    public String banner_link;
    public String banner_pic_url;
    public boolean events;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMore_url() {
        return more_url;
    }

    public void setMore_url(String more_url) {
        this.more_url = more_url;
    }

    public Boolean getShow_more() {
        return show_more;
    }

    public void setShow_more(Boolean show_more) {
        this.show_more = show_more;
    }

    public String getBanner_link() {
        return banner_link;
    }

    public void setBanner_link(String banner_link) {
        this.banner_link = banner_link;
    }

    public String getBanner_pic_url() {
        return banner_pic_url;
    }

    public void setBanner_pic_url(String banner_pic_url) {
        this.banner_pic_url = banner_pic_url;
    }

    public boolean isEvents() {
        return events;
    }

    public void setEvents(boolean events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", more_url='" + more_url + '\'' +
                ", show_more=" + show_more +
                ", banner_link='" + banner_link + '\'' +
                ", banner_pic_url='" + banner_pic_url + '\'' +
                ", events=" + events +
                '}';
    }


}

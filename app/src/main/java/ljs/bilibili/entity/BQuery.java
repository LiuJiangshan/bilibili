package ljs.bilibili.entity;

import java.util.HashMap;

public class BQuery extends HashMap<String, Object> {
    private String area;//all
    private int page;
    private String order;//live_time

    public String getArea() {
        return (String) get("area");
    }

    public void setArea(String area) {
        put("area", area);
    }

    public int getPage() {
        return (int) get("page");
    }

    public void setPage(int page) {
        put("page", page);
    }

    public String getOrder() {
        return (String) get("order");
    }

    public void setOrder(String order) {
        put("order", order);
    }
}

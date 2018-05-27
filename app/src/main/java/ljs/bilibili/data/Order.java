package ljs.bilibili.data;

public enum Order {
    online("online", "人气直播"),
    liveTime("live_time", "最新开播");
    public String value;
    public String title;

    Order(String value, String title) {
        this.value = value;
        this.title = title;
    }
}

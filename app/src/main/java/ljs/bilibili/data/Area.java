package ljs.bilibili.data;

public enum Area {
    subject("subject", "萌宅推荐"),
    all("all", "热门直播"),
    singDance("sing-dance", "唱见舞见"),
    otaku("otaku", "御宅文化"),
    draw("draw", "绘画专区"),
    entLife("ent-life", "生活娱乐"),
    single("single", "单机联机"),
    online("online", "网络游戏"),
    mobileGame("mobile-game", "手游直播"),
    movie("movie", "放映厅"),
    eSports("e-sports", "电子竞技");

    public String title;
    public String value;

    Area(String value, String title) {
        this.value = value;
        this.title = title;
    }
}

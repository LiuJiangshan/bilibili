package ljs.bilibili.entity;

public class BLive {
    //分类
    private String areaName;
    //主播昵称
    private String uname;
    private String cover;
    //主播头像
    private String face;
    //直播题目
    private String title;
    private int stream_id;
    //关键帧
    private String system_cover;
    //直播房间id
    private String roomid;

    @Override
    public String toString() {
        return getTitle();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStream_id() {
        return stream_id;
    }

    public void setStream_id(int stream_id) {
        this.stream_id = stream_id;
    }

    public String getSystem_cover() {
        return system_cover;
    }

    public void setSystem_cover(String system_cover) {
        this.system_cover = system_cover;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
}

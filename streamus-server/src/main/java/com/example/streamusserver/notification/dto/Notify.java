package com.example.streamusserver.notification.dto;


public class Notify {

    private long id, itemId, fromUserId;
    private int fromUserState, subType = 0;
    private String fromUserUsername,type, fromUserFullname, fromUserPhotoUrl, timeAgo,createAt;

    public Notify() {

    }


    public void setId(long id) {

        this.id = id;
    }

    public long getId() {

        return this.id;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getType() {

        return this.type;
    }

    public void setSubType(int subType) {

        this.subType = subType;
    }

    public int getSubType() {

        return this.subType;
    }

    public void setItemId(long itemId) {

        this.itemId = itemId;
    }

    public long getItemId() {

        return this.itemId;
    }

    public void setFromUserId(long fromUserId) {

        this.fromUserId = fromUserId;
    }

    public long getFromUserId() {

        return this.fromUserId;
    }

    public void setFromUserState(int fromUserState) {

        this.fromUserState = fromUserState;
    }

    public int getFromUserState() {

        return this.fromUserState;
    }

    public void setTimeAgo(String timeAgo) {

        this.timeAgo = timeAgo;
    }

    public String getTimeAgo() {

        return this.timeAgo;
    }

    public void setFromUserUsername(String fromUserUsername) {

        this.fromUserUsername = fromUserUsername;
    }

    public String getFromUserUsername() {

        return this.fromUserUsername;
    }

    public void setFromUserFullname(String fromUserFullname) {

        this.fromUserFullname = fromUserFullname;
    }

    public String getFromUserFullname() {

        return this.fromUserFullname;
    }

    public void setFromUserPhotoUrl(String fromUserPhotoUrl) {

        this.fromUserPhotoUrl = fromUserPhotoUrl;
    }

    public String getFromUserPhotoUrl() {

        return this.fromUserPhotoUrl;
    }

    public void setCreateAt(String createAt) {

        this.createAt = createAt;
    }

    public String getCreateAt() {

        return this.createAt;
    }


}

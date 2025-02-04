package com.example.streamusserver.dto;




public class ProfileDto {

    private long id;

    private int mood, state, sex, year, month, day, verify, pro, itemsCount, galleryItemsCount, likesCount, giftsCount, friendsCount, followingsCount, followersCount, allowComments, allowMessages, lastAuthorize, accountType;

    private int allowVideoCalls = 0, allowShowMyInfo, allowShowMyFriends, allowShowMyGallery, allowShowMyGifts;

    private int allowGalleryComments;

    private double distance = 0;

    private int reaction = 0;

    private String username, fullname, lowPhotoUrl, bigPhotoUrl, normalPhotoUrl, normalCoverUrl, location, facebookPage, instagramPage, bio, lastAuthorizeDate, lastAuthorizeTimeAgo;

    private Boolean blocked = false;

    private Boolean inBlackList = false;

    private Boolean follower = false;

    private Boolean follow = false;

    private Boolean friend = false;

    private Boolean online = false;


    public void setDistance(double distance) {

        this.distance = distance;
    }

    public double getDistance() {

        return this.distance;
    }

    public void setId(long profile_id) {

        this.id = profile_id;
    }

    public long getId() {

        return this.id;
    }

    public void setState(int profileState) {

        this.state = profileState;
    }

    public int getState() {

        return this.state;
    }

    public void setType(int accountType) {

        this.accountType = accountType;
    }

    public int getType() {

        return this.accountType;
    }

    public void setSex(int sex) {

        this.sex = sex;
    }

    public int getSex() {

        return this.sex;
    }

    public void setYear(int year) {

        this.year = year;
    }

    public int getYear() {

        return this.year;
    }

    public void setMonth(int month) {

        this.month = month;
    }

    public int getMonth() {

        return this.month;
    }

    public void setDay(int day) {

        this.day = day;
    }

    public int getDay() {

        return this.day;
    }

    public void setProMode(int proMode) {

        this.pro = proMode;
    }

    public int getProMode() {

        return this.pro;
    }

    public Boolean isProMode() {

        if (this.pro > 0) {

            return true;
        }

        return false;
    }

    public void setVerify(int profileVerify) {

        this.verify = profileVerify;
    }

    public int getVerify() {

        return this.verify;
    }

    public Boolean isVerify() {

        if (this.verify > 0) {

            return true;
        }

        return false;
    }

    public void setUsername(String profile_username) {

        this.username = profile_username;
    }

    public String getUsername() {

        return this.username;
    }

    public void setFullname(String profile_fullname) {

        this.fullname = profile_fullname;
    }

    public String getFullname() {

        if (fullname == null) {

            fullname = "";
        }

        return this.fullname;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getLocation() {

        if (this.location == null) {

            this.location = "";
        }

        return this.location;
    }

    public void setFacebookPage(String facebookPage) {

        this.facebookPage = facebookPage;
    }

    public String getFacebookPage() {

        return this.facebookPage;
    }

    public void setInstagramPage(String instagramPage) {

        this.instagramPage = instagramPage;
    }

    public String getInstagramPage() {

        return this.instagramPage;
    }

    public void setBio(String bio) {

        this.bio = bio;
    }

    public String getBio() {

        return this.bio;
    }


    public String getLowPhotoUrl() {

        if (this.lowPhotoUrl == null) {

            this.lowPhotoUrl = "";
        }

        return this.lowPhotoUrl;
    }

    public void setBigPhotoUrl(String bigPhotoUrl) {

        this.bigPhotoUrl = bigPhotoUrl;
    }

    public String getBigPhotoUrl() {

        return this.bigPhotoUrl;
    }

    public void setNormalPhotoUrl(String normalPhotoUrl) {

        this.normalPhotoUrl = normalPhotoUrl;
    }

    public String getNormalPhotoUrl() {

        return this.normalPhotoUrl;
    }

    public void setNormalCoverUrl(String normalCoverUrl) {

        this.normalCoverUrl = normalCoverUrl;
    }

    public String getNormalCoverUrl() {

        return this.normalCoverUrl;
    }

    public void setFollowersCount(int followersCount) {

        this.followersCount = followersCount;
    }

    public int getFollowersCount() {

        return this.followersCount;
    }

    public void setItemsCount(int itemsCount) {

        this.itemsCount = itemsCount;
    }

    public int getItemsCount() {

        return this.itemsCount;
    }

    public void setGalleryItemsCount(int galleryItemsCount) {

        this.galleryItemsCount = galleryItemsCount;
    }

    public int getGalleryItemsCount() {

        return this.galleryItemsCount;
    }

    public void setLikesCount(int likesCount) {

        this.likesCount = likesCount;
    }

    public int getLikesCount() {

        return this.likesCount;
    }

    public void setGiftsCount(int giftsCount) {

        this.giftsCount = giftsCount;
    }

    public int getGiftsCount() {

        return this.giftsCount;
    }

    public void setFollowingsCount(int followingsCount) {

        this.followingsCount = followingsCount;
    }

    public int getFollowingsCount() {

        return this.followingsCount;
    }

    public void setFriendsCount(int friendsCount) {

        this.friendsCount = friendsCount;
    }

    public int getFriendsCount() {

        return this.friendsCount;
    }

    public void setAllowComments(int allowComments) {

        this.allowComments = allowComments;
    }

    public int getAllowComments() {

        return this.allowComments;
    }

    public void setAllowMessages(int allowMessages) {

        this.allowMessages = allowMessages;
    }

    public int getAllowMessages() {

        return this.allowMessages;
    }

    public void setMood(int mood) {

        this.mood = mood;
    }

    public int getMood() {

        return this.mood;
    }

    public void setLastActive(int lastAuthorize) {

        this.lastAuthorize = lastAuthorize;
    }

    public int getLastActive() {

        return this.lastAuthorize;
    }

    public void setLastActiveDate(String lastAuthorizeDate) {

        this.lastAuthorizeDate = lastAuthorizeDate;
    }

    public String getLastActiveDate() {

        return this.lastAuthorizeDate;
    }

    public void setLastActiveTimeAgo(String lastAuthorizeTimeAgo) {

        this.lastAuthorizeTimeAgo = lastAuthorizeTimeAgo;
    }

    public String getLastActiveTimeAgo() {

        return this.lastAuthorizeTimeAgo;
    }

    public void setBlocked(Boolean blocked) {

        this.blocked = blocked;
    }

    public Boolean isBlocked() {

        return this.blocked;
    }

    public void setFollower(Boolean follower) {

        this.follower = follower;
    }

    public Boolean isFollower() {

        return this.follower;
    }

    public void setFollow(Boolean follow) {

        this.follow = follow;
    }

    public Boolean isFollow() {

        return this.follow;
    }

    public void setFriend(Boolean friend) {

        this.friend = friend;
    }

    public Boolean isFriend() {

        return this.friend;
    }

    public void setOnline(Boolean online) {

        this.online = online;
    }

    public Boolean isOnline() {

        return this.online;
    }

    public void setInBlackList(Boolean inBlackList) {

        this.inBlackList = inBlackList;
    }

    public Boolean isInBlackList() {

        return this.inBlackList;
    }

    // Privacy

    public void setAllowVideoCalls(int allowVideoCalls) {

        this.allowVideoCalls = allowVideoCalls;
    }

    public int getAllowVideoCalls() {

        return this.allowVideoCalls;
    }

    public void setAllowShowMyInfo(int allowShowMyInfo) {

        this.allowShowMyInfo = allowShowMyInfo;
    }

    public int getAllowShowMyInfo() {

        return this.allowShowMyInfo;
    }

    public void setAllowShowMyFriends(int allowShowMyFriends) {

        this.allowShowMyFriends = allowShowMyFriends;
    }

    public int getAllowShowMyFriends() {

        return this.allowShowMyFriends;
    }

    public void setAllowShowMyGallery(int allowShowMyGallery) {

        this.allowShowMyGallery = allowShowMyGallery;
    }

    public int getAllowShowMyGallery() {

        return this.allowShowMyGallery;
    }

    public void setAllowShowMyGifts(int allowShowMyGifts) {

        this.allowShowMyGifts = allowShowMyGifts;
    }

    public int getAllowShowMyGifts() {

        return this.allowShowMyGifts;
    }

    public int getAllowGalleryComments() {

        return this.allowGalleryComments;
    }

    public void setAllowGalleryComments(int allowGalleryComments) {

        this.allowGalleryComments = allowGalleryComments;
    }

    public void setReaction(int reaction) {

        this.reaction = reaction;
    }

    public int getReaction() {

        return this.reaction;
    }

}

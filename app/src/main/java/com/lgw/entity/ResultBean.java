package com.lgw.entity;

import com.lgw.bean.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultBean implements Serializable {

    private List<BannerInfoBean> banner_info;
    private ArrayList<MenuItem> channel_info;

    public List<BannerInfoBean> getBanner_info() {
        return banner_info;
    }

    public void setBanner_info(List<BannerInfoBean> banner_info) {
        this.banner_info = banner_info;
    }

    public ArrayList<MenuItem> getChannel_info() {
        return channel_info;
    }

    public void setChannel_info(ArrayList<MenuItem> channel_info) {
        this.channel_info = channel_info;
    }

    public static class BannerInfoBean {

        private String desc;
        private int id;
        private String imagePath;
        private int isVisible;
        private int order;
        private String title;
        private int type;
        private String url;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getIsVisible() {
            return isVisible;
        }

        public void setIsVisible(int isVisible) {
            this.isVisible = isVisible;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "BannerInfoBean{" +
                    "desc='" + desc + '\'' +
                    ", id=" + id +
                    ", imagePath='" + imagePath + '\'' +
                    ", isVisible=" + isVisible +
                    ", order=" + order +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
    public static class MenuItem{
        private String ActionName = "";

        private String DisplayType = "";

        private String ActionId = "";

        private String ActionImage = "";


        private String Clickable = "";

        private String Usable = "";

        private String EntryType = "";


        private String ActionUrl = "";

        private String VersionCode = "";

        private String Params = "";

        private ArrayList<MenuItem> MenuList;

        private boolean isReadOnly = false;

        private boolean addToMenu = false;


        private void copy(MenuItem myButtonItem) {
            this.ActionName = myButtonItem.ActionName;
            this.ActionId = myButtonItem.ActionId;
            this.DisplayType = myButtonItem.DisplayType;
            this.ActionImage = myButtonItem.ActionImage;
            this.Clickable = myButtonItem.Clickable;
            this.Usable = myButtonItem.Usable;
            this.EntryType = myButtonItem.EntryType;
            this.ActionUrl = myButtonItem.ActionUrl;
            this.VersionCode = myButtonItem.VersionCode;
            this.Params = myButtonItem.Params;
            this.isReadOnly = myButtonItem.isReadOnly;
            this.addToMenu = myButtonItem.addToMenu;
        }

        public String getActionName() {
            return ActionName;
        }

        public void setActionName(String actionName) {
            ActionName = actionName;
        }

        public String getActionId() {
            return ActionId;
        }

        public void setActionId(String actionId) {
            ActionId = actionId;
        }

        public String getActionImage() {
            return ActionImage;
        }

        public void setActionImage(String actionImage) {
            ActionImage = actionImage;
        }

        public String getClickable() {
            return Clickable;
        }

        public void setClickable(String clickable) {
            Clickable = clickable;
        }

        public String getUsable() {
            return Usable;
        }

        public void setUsable(String usable) {
            Usable = usable;
        }

        public String getEntryType() {
            return EntryType;
        }

        public void setEntryType(String entryType) {
            EntryType = entryType;
        }

        public String getDisplayType() {
            return DisplayType;
        }

        public void setDisplayType(String displayType) {
            DisplayType = displayType;
        }

        public String getActionUrl() {
            return ActionUrl;
        }

        public void setActionUrl(String actionUrl) {
            ActionUrl = actionUrl;
        }

        public String getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(String versionCode) {
            VersionCode = versionCode;
        }

        public String getParams() {
            return Params;
        }

        public void setParams(String params) {
            Params = params;
        }

        public void addButton(MenuItem fromItem) {
            if (MenuList == null) {
                MenuList = new ArrayList<>();
            }
            MenuList.add(fromItem);
        }

        public boolean isReadOnly() {
            return isReadOnly;
        }

        public void setReadOnly(boolean isReadOnly) {
            this.isReadOnly = isReadOnly;
        }

        public boolean isAddToMenu() {
            return addToMenu;
        }

        public void setAddToMenu(boolean addToMenu) {
            this.addToMenu = addToMenu;
        }

        @Override
        public String toString() {
            return "FavoritesButton{" +
                    "ActionName='" + ActionName + '\'' +
                    ", DisplayType='" + DisplayType + '\'' +
                    ", ActionId='" + ActionId + '\'' +
                    ", ActionImage='" + ActionImage + '\'' +
                    ", Clickable='" + Clickable + '\'' +
                    ", Usable='" + Usable + '\'' +
                    ", EntryType='" + EntryType + '\'' +
                    ", ActionUrl='" + ActionUrl + '\'' +
                    ", VersionCode='" + VersionCode + '\'' +
                    ", Params='" + Params + '\'' +
                    ", MenuList=" + MenuList +
                    ", isReadOnly=" + isReadOnly +
                    ", addToMenu=" + addToMenu +
                    '}';
        }
    }
}

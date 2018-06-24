package com.lgw.bean;

import java.io.Serializable;

public class Home implements Serializable{
    /**
     * ActionId : 20105
     * ActionImage : cm_tzlc
     * ActionName : 投资理财
     * ActionUrl :
     * Clickable : true
     * DisplayType : icon
     * EntryType : activity
     * Params :
     * Usable : 1
     */

    private String ActionId;
    private String ActionImage;
    private String ActionName;
    private String ActionUrl;
    private String Clickable;
    private String DisplayType;
    private String EntryType;
    private String Params;
    private String Usable;

    public String getActionId() {
        return ActionId;
    }

    public void setActionId(String ActionId) {
        this.ActionId = ActionId;
    }

    public String getActionImage() {
        return ActionImage;
    }

    public void setActionImage(String ActionImage) {
        this.ActionImage = ActionImage;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String ActionName) {
        this.ActionName = ActionName;
    }

    public String getActionUrl() {
        return ActionUrl;
    }

    public void setActionUrl(String ActionUrl) {
        this.ActionUrl = ActionUrl;
    }

    public String getClickable() {
        return Clickable;
    }

    public void setClickable(String Clickable) {
        this.Clickable = Clickable;
    }

    public String getDisplayType() {
        return DisplayType;
    }

    public void setDisplayType(String DisplayType) {
        this.DisplayType = DisplayType;
    }

    public String getEntryType() {
        return EntryType;
    }

    public void setEntryType(String EntryType) {
        this.EntryType = EntryType;
    }

    public String getParams() {
        return Params;
    }

    public void setParams(String Params) {
        this.Params = Params;
    }

    public String getUsable() {
        return Usable;
    }

    public void setUsable(String Usable) {
        this.Usable = Usable;
    }
}

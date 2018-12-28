package com.yuntian.basecomponent.tablayout.listener;


public class CustomTab implements CustomTabEntity {

    private String tabTitle;
    private int tabSelectedIcon;
    private int tabUnselectedIcon;

    public CustomTab(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public void setTabSelectedIcon(int tabSelectedIcon) {
        this.tabSelectedIcon = tabSelectedIcon;
    }

    public void setTabUnselectedIcon(int tabUnselectedIcon) {
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }
}
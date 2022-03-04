package com.vkontakte.miracle.engine.fragment.tabs;

import com.vkontakte.miracle.engine.fragment.FragmentFabric;

public abstract class NestedMiracleFragmentFabric implements FragmentFabric {

    private final String title;
    private final int icon;

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public NestedMiracleFragmentFabric(String title, int icon){
        this.title = title;
        this.icon = icon;
    }
}

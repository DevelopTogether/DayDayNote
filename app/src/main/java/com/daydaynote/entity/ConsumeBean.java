package com.daydaynote.entity;

/**
 * Created by ${王sir} on 2017/8/29.
 * application
 * 消费类型的实体类
 */

public class ConsumeBean {

    private String type_name;//类型名称
    private Integer marker;//图标
    private Integer  type_bg;//每种类型的背景

    public ConsumeBean(String type_name, Integer marker, Integer type_bg) {
        this.type_name = type_name;
        this.marker = marker;
        this.type_bg = type_bg;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getMarker() {
        return marker;
    }

    public void setMarker(Integer marker) {
        this.marker = marker;
    }

    public Integer getType_bg() {
        return type_bg;
    }

    public void setType_bg(Integer type_bg) {
        this.type_bg = type_bg;
    }
}

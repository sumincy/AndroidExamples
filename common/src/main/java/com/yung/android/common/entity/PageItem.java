package com.yung.android.common.entity;

import android.content.Context;
import android.text.TextUtils;

import com.yung.android.common.util.AssetsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class PageItem {

    private String name;
    private String path;

    private String listJson;
    private String group;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getListJson() {
        return listJson;
    }

    public void setListJson(String listJson) {
        this.listJson = listJson;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * 获取列表数据
     *
     * @return
     */
    public static List<PageItem> getPageItems(Context context, String jsonFileName) {

        //"xx.json"
        String json = AssetsUtil.readAssets(context, jsonFileName);

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        JSONArray jsonArray = null;

        List<PageItem> pageItems = new ArrayList<>();

        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.optString("name");
                String path = jsonObject.optString("path");
                String group = jsonObject.optString("group");
                String listJson = jsonObject.optString("list_json");
                String description = jsonObject.getString("description");

                PageItem pageItem = new PageItem();
                pageItem.setName(name);
                pageItem.setPath(path);
                pageItem.setGroup(group);
                pageItem.setListJson(listJson);
                pageItem.setDescription(description);

                pageItems.add(pageItem);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return pageItems;
    }

}

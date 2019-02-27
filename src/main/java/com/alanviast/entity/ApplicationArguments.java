package com.alanviast.entity;

import com.alanviast.utils.ApplicationUtils;

import java.util.Collections;
import java.util.Map;

/**
 * 当前应用所需要的参数
 *
 * @author AlanViast
 */
public class ApplicationArguments {


    private String projectDirectory;
    private Integer day;
    private boolean release;
    private Map<String, String> params;


    public ApplicationArguments(String[] arguments) {
        params = ApplicationUtils.argToMap(arguments);
        if (params.containsKey("-d")) {
            this.projectDirectory = params.get("-d");
        }
        if (params.containsKey("-t")) {
            this.day = Integer.parseInt(params.get("-t"));
        }
        this.release = params.containsKey("-r");
    }

    public boolean containsKey(String key) {
        return params.containsKey(key);
    }

    public String get(String key) {
        return params.get(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        String value = params.get(key);
        if (null == value) {
            return defaultValue;
        }
        return value;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public Integer getDay() {
        return day;
    }

    public boolean isRelease() {
        return release;
    }
}

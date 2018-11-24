package com.alanviast.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 定制的Utils
 *
 * @author AlanViast
 */
public class ApplicationUtils {

    private ApplicationUtils() {
    }

    /**
     * 把应用传进来的参数转成Map
     *
     * @param args 参数数组
     * @return 参数键值对
     */
    public static Map<String, String> argToMap(String[] args) {
        int index = 0;
        Map<String, String> paramMap = new HashMap<>(args.length);
        while (index < args.length) {
            if (args[index].startsWith("-") && index + 1 < args.length && !args[index + 1].startsWith("-")) {
                paramMap.put(args[index], args[index + 1]);
                index += 2;
            } else {
                paramMap.put(args[index], null);
                index += 1;
            }
        }
        return paramMap;
    }

    /**
     * 如果当前目录不是git对应的目录则查找子目录
     *
     * @param filename Git项目目录地址
     * @return 对应的Git仓库文件夹
     */
    public static File searchGitDirectory(String filename) {
        File file = new File(filename);
        if (file.exists() && !".git".equals(file.getName())) {
            // 查找子目录
            file = new File(file, ".git");
        }

        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("当前文件非Git项目目录");
        }
        return file;
    }
}

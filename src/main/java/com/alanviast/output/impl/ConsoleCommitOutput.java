package com.alanviast.output.impl;

import com.alanviast.entity.ApplicationArguments;
import com.alanviast.entity.CommitInfo;
import com.alanviast.output.CommitOutput;
import com.alanviast.type.CommitMessageType;
import com.alanviast.type.ConsoleColor;
import com.alanviast.utils.DateTimeUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 控制台输出
 *
 * @author AlanViast
 */
public class ConsoleCommitOutput implements CommitOutput {

    private final static String KEY = "--console";

    @Override
    public void handler(ApplicationArguments applicationArguments, Map<CommitMessageType, List<CommitInfo>> commitMessageTypeListMap) {
        if (applicationArguments.containsKey(KEY)) {
            commitMessageTypeListMap.forEach((key, list) -> {
                System.out.println(ConsoleColor.RED + "### " + key.name() + " - " + key.getDesc() + ConsoleColor.BLACK);
                list.forEach(item -> {
                    System.out.println(String.format("ID: %s, 作者: %s[%s]%s, 时间: %s , 描述: %s",
                            item.getCommitId(),
                            ConsoleColor.BLUE,
                            item.getAuthor(),
                            ConsoleColor.BLACK,
                            DateTimeUtils.fromTimestamp(item.getCommitTime().getTime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
                            item.getShortMessage()
                    ));
                });
            });
        }
    }
}

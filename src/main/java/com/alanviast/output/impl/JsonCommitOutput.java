package com.alanviast.output.impl;

import com.alanviast.entity.ApplicationArguments;
import com.alanviast.entity.CommitInfo;
import com.alanviast.output.CommitOutput;
import com.alanviast.type.CommitMessageType;
import com.alanviast.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 输出内容成为JSON
 *
 * @author AlanViast
 */
public class JsonCommitOutput implements CommitOutput {

    private final static String KEY = "--json";

    @Override
    public void handler(ApplicationArguments applicationArguments, Map<CommitMessageType, List<CommitInfo>> commitMessageTypeListMap) {
        if (applicationArguments.containsKey(KEY)) {
            String filename = applicationArguments.getOrDefault(KEY, "catelog.json");
            try {
                FileUtils.write(FileUtils.getFile(filename), JsonUtils.format(commitMessageTypeListMap), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(String.format("写出到 [%s] 文件失败", filename));
            }
        }
    }
}

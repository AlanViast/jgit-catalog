package com.alanviast.output;

import com.alanviast.entity.ApplicationArguments;
import com.alanviast.entity.CommitInfo;
import com.alanviast.type.CommitMessageType;

import java.util.List;
import java.util.Map;

/**
 * RevCommite 输出接口
 *
 * @author AlanViast
 */
public interface CommitOutput {

    /**
     * 处理消息, 传递ApplicationArguments提供给该方法判断自己是否要执行, 实现了责任链模式
     *
     * @param applicationArguments     传递当前应用的所有参数
     * @param commitMessageTypeListMap 分类号的日志消息
     */
    void handler(ApplicationArguments applicationArguments, Map<CommitMessageType, List<CommitInfo>> commitMessageTypeListMap);

}

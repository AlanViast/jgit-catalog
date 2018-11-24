package com.alanviast.type;

/**
 * 日志输出前缀类型
 *
 * @author AlanViast
 */
public enum CommitMessageType {

    /**
     *
     */
    refactor("项目重构"),
    feat("新功能"),
    fix("修复Bug"),
    docs("文档"),
    style("代码样式修改"),
    chore("修改构建工具"),
    test("代码测试/测试用例"),
    other("其他"),
    release("发布版本");

    private String desc;

    CommitMessageType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static CommitMessageType findByName(String name) {
        for (CommitMessageType value : CommitMessageType.values()) {
            if (value.name().equals(name.trim().toLowerCase())) {
                return value;
            }
        }
        return CommitMessageType.other;
    }
}

package com.alanviast.jgit;

import com.alanviast.entity.ApplicationArguments;
import com.alanviast.entity.CommitInfo;
import com.alanviast.output.CommitOutput;
import com.alanviast.output.impl.ConsoleCommitOutput;
import com.alanviast.output.impl.JsonCommitOutput;
import com.alanviast.type.CommitMessageType;
import com.alanviast.utils.ApplicationUtils;
import com.alanviast.utils.DateTimeUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author AlanViast
 */
public class Main {
    private final static List<CommitOutput> COMMIT_OUTPUT_ARRAY_LIST = new ArrayList<>();

    static {
        COMMIT_OUTPUT_ARRAY_LIST.add(new ConsoleCommitOutput());
        COMMIT_OUTPUT_ARRAY_LIST.add(new JsonCommitOutput());
    }

    public static void main(String[] args) throws IOException {

        System.out.println(Arrays.toString(args));

        ApplicationArguments applicationArguments = new ApplicationArguments(args);
        assert applicationArguments.getProjectDirectory() == null;

        Repository repository = new FileRepositoryBuilder().setGitDir(ApplicationUtils.searchGitDirectory(applicationArguments.getProjectDirectory())).build();

        Git git = new Git(repository);
        LogCommand logs = git.log();

        List<CommitInfo> revCommitList;

        if (applicationArguments.containsKey("-t")) {
            revCommitList = getLogByDay(logs, applicationArguments.getDay());
        } else if (applicationArguments.containsKey("-r")) {
            revCommitList = getLogByRelease(logs);
        } else {
            revCommitList = getLogByDay(logs, 15);
        }

        Map<CommitMessageType, List<CommitInfo>> revCommitMap = groupByPrefix(revCommitList);


        // out put to file
        COMMIT_OUTPUT_ARRAY_LIST.forEach(handler -> {
            try {
                handler.handler(applicationArguments, revCommitMap);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
    }


    /**
     * 将对应的提交信息集合转成TreeMap
     *
     * @param revCommitList CommitMessage
     * @return 排好序的Map
     */
    private static Map<CommitMessageType, List<CommitInfo>> groupByPrefix(List<CommitInfo> revCommitList) {
        return revCommitList.stream().filter(item -> !item.getShortMessage().startsWith("Merge branch"))
                .collect(
                        Collectors.groupingBy(item -> {
                                    int index = item.getShortMessage().indexOf(":");
                                    if (-1 == index) {
                                        return CommitMessageType.other;
                                    }
                                    String type = item.getShortMessage().substring(0, index);
                                    return CommitMessageType.findByName(type);
                                },
                                TreeMap::new,
                                Collectors.toList()));
    }

    /**
     * 返回某个天数之内的日志
     *
     * @param logs 返回几天之内的日志
     * @param day  天数内
     */
    private static List<CommitInfo> getLogByDay(LogCommand logs, int day) {
        try {
            return StreamSupport.stream(logs.all().call().spliterator(), true).filter(revCommit -> {
                LocalDateTime commitDateTime = DateTimeUtils.fromTimestamp(revCommit.getCommitTime() * 1000L);
                return DateTimeUtils.inFewDay(commitDateTime, day);
            }).map(CommitInfo::new).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回距离上个release版本的日志
     *
     * @param logs 返回距离上一次release前缀的日志
     */
    private static List<CommitInfo> getLogByRelease(LogCommand logs) {
        try {
            List<CommitInfo> revCommitList = new LinkedList<>();
            for (RevCommit revCommit : logs.all().call()) {
                if (revCommit.getShortMessage().startsWith("release:")) {
                    break;
                }
                revCommitList.add(new CommitInfo(revCommit));
            }
            return revCommitList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

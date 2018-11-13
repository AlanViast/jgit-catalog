package com.alanviast.jgit;

import com.alanviast.utils.DateTimeUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author AlanViast
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(args));

        Map<String, String> params = argToMap(args);

        assert params.containsKey("-d");
        assert params.containsKey("-t");

        Repository repository = new FileRepositoryBuilder()
                .setGitDir(new File(params.get("-d")))
                .build();

        Git git = new Git(repository);
        LogCommand logs = git.log();


        for (RevCommit revCommit : getLogByDay(logs, Integer.parseInt(params.get("-t")))) {
            System.out.print(revCommit.getId().name());
            System.out.print(" -> ");
            System.out.print(revCommit.getShortMessage());
            System.out.print(" , ");
            LocalDateTime commitDateTime = DateTimeUtils.fromTimestamp(revCommit.getCommitTime() * 1000L);
            System.out.println(commitDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        }
    }

    /**
     * 返回某个天数之内的日志
     *
     * @param logs 返回几天之内的日志
     * @param day  天数内
     */
    private static List<RevCommit> getLogByDay(LogCommand logs, int day) {
        try {
            return StreamSupport.stream(logs.all().call().spliterator(), true).filter(revCommit -> {
                LocalDateTime commitDateTime = DateTimeUtils.fromTimestamp(revCommit.getCommitTime() * 1000L);
                return DateTimeUtils.inFewDay(commitDateTime, day);
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 把应用传进来的参数转成Map
     *
     * @param args 参数数组
     * @return 参数键值对
     */
    private static Map<String, String> argToMap(String[] args) {
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

}
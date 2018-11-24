package com.alanviast.entity;

import org.eclipse.jgit.revwalk.RevCommit;

import java.util.Date;

/**
 * 提交的信息
 *
 * @author AlanViast
 */
public class CommitInfo {


    private String commitId;
    private Date commitTime;

    private String author;
    private String authorEmail;

    private String shortMessage;


    public CommitInfo(RevCommit revCommit) {
        this.shortMessage = revCommit.getShortMessage();

        this.commitId = revCommit.getId().getName();
        this.commitTime = new Date(revCommit.getCommitTime() * 1000L);

        this.author = revCommit.getAuthorIdent().getName();
        this.authorEmail = revCommit.getAuthorIdent().getEmailAddress();
    }


    public String getCommitId() {
        return commitId;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getShortMessage() {
        return shortMessage;
    }

}

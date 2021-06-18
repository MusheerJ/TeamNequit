package com.teamnequit.Models;

public class PollReplies {
    String replyBy,reply;


    public PollReplies() {
    }

    public PollReplies(String replyBy, String reply) {
        this.replyBy = replyBy;
        this.reply = reply;
    }

    public String getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(String replyBy) {
        this.replyBy = replyBy;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

}

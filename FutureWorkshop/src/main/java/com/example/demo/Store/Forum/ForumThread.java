package com.example.demo.Store.Forum;



import com.example.demo.GlobalSystemServices.IdGenerator;

import java.util.*;

public class ForumThread {
    private String threadId;
    private String userId;
    private String title;
    private List<Pair<String,String>> messages;



    public ForumThread(String title, String userId) {
        this.userId = userId;
        this.title = title;
        this.messages = new LinkedList<>();
        this.threadId = IdGenerator.getInstance().getForumThreadId();
    }

    public ForumThread(String title,  String userId, List<Pair<String,String>> messages, String threadId) {
        this.userId = userId;
        this.title = title;
        this.messages = messages;
        this.threadId = threadId;
    }

    public void postMessage(String userId, String message){//only managers of store or user that opened the thread
        messages.add( Pair.create(userId, message));
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getThreadId() {
        return threadId;
    }

    public List<Pair<String, String>> getMessages() {
        return Collections.unmodifiableList(messages);
    }


}

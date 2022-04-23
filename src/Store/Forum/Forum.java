package Store.Forum;

import Store.Store;
import jdk.jshell.spi.ExecutionControl;

import java.util.HashMap;
import java.util.List;

public class Forum {

    private HashMap<String,ForumThread> forumThreads; //<userId,forumThread>

    public Forum(HashMap<String, ForumThread> forumThreads) {
        this.forumThreads = forumThreads;
    }

    public Forum() {
        this.forumThreads = new HashMap<>();
    }

    public String addNewThread(String title, String userId){
        ForumThread newTread = new ForumThread(title,userId);
        forumThreads.put(title, newTread);
        return newTread.getThreadId();
    }
    public void postMessage(String threadId, String userId, String message){
        ForumThread Op = forumThreads.get(threadId);
        if(Op == null){
            throw new RuntimeException("no forum thread with this id");
        }
        Op.postMessage(userId, message);
    }

    public String getUserIdOfTread(String ThreadId){
        return forumThreads.get(ThreadId).getUserId();
    }

    public List<String> getThreadsOfUserId(String UserId){
        throw new RuntimeException("notImplemented");
    }





}

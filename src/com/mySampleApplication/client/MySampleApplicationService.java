package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.icepush.gwt.client.GWTPushContext;

import java.util.ArrayList;

@RemoteServiceRelativePath("MySampleApplicationService")
public interface MySampleApplicationService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);
    ArrayList<String> returnTalk(String s);
     ArrayList<String> getTalksId();
    String addToDatabase(String m);

    String sendMessageTo(String m);

    String addMessage(String m);

    Boolean checkUser(String userName,String password);

    /**
     * Utility/Convenience class.
     * Use MySampleApplicationService.App.getInstance() to access static instance of MySampleApplicationServiceAsync
     */
    public static class App {
        private static MySampleApplicationServiceAsync ourInstance = GWT.create(MySampleApplicationService.class);

        public static synchronized MySampleApplicationServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

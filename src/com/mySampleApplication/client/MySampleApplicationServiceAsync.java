package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.icepush.gwt.client.GWTPushContext;

import java.util.ArrayList;

public interface MySampleApplicationServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);

    void addToDatabase(String m, AsyncCallback<String> async);
    void sendMessageTo(String m,  AsyncCallback<String> async);

    void addMessage(String m,  AsyncCallback<String> async);

    void returnTalk(String s, AsyncCallback<ArrayList<String>> async);

    void getTalksId(AsyncCallback<ArrayList<String>> async);



    void checkUser(String userName,String password,AsyncCallback<Boolean> async);
}

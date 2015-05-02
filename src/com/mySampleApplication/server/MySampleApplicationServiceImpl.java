package com.mySampleApplication.server;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.bulk.UpdateRequest;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mySampleApplication.client.Listeners;
import com.mySampleApplication.client.MySampleApplicationService;
import org.bson.BsonArray;
import org.bson.BsonString;
import org.bson.Document;
import org.icepush.PushContext;
import org.icepush.gwt.client.GWTPushContext;
import sun.security.util.Password;

import java.sql.Time;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;


public class MySampleApplicationServiceImpl extends RemoteServiceServlet implements MySampleApplicationService {
    //Implementation of sample interface method

    private String message = "";
    private String talk = "";

    public String getMessage(String msg) {
//
//        MongoClient mongoClient = new MongoClient("localhost");
//
//        MongoDatabase database = mongoClient.getDatabase("test");
//
//        MongoCollection<Document> collection   = database.getCollection("people");


        return message;
    }


    @Override
    public String addToDatabase(String m) {


        return "dodane do bazy";
    }


    public String sendMessageTo(String msg) {

        MongoClient mongoClient = new MongoClient("localhost");
//
//
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("people");
        Document updateDocument;
        if (talk.equals("")) {
            talk = String.valueOf(System.currentTimeMillis());
            Document firstDocument = new Document("name", talk);
            collection.insertOne(firstDocument);

            updateDocument = collection.find(eq("name", talk)).first();
            BsonArray a = new BsonArray();
            a.add(new BsonString(msg));
            updateDocument.append("messages", a);
            collection.updateOne(eq("name", talk), new Document("$set", updateDocument));
        } else {
            BsonArray bsonArrayMessages = new BsonArray();
            updateDocument = collection.find(eq("name", talk)).first();
            ArrayList<String> messagesList = (ArrayList<String>) updateDocument.get("messages");

            for (String message : messagesList) {
                bsonArrayMessages.add(new BsonString(message));
            }

            bsonArrayMessages.add(new BsonString(msg));
            updateDocument.append("messages", bsonArrayMessages);

            collection.updateOne(eq("name", talk), new Document("$set", updateDocument));

        }

        PushContext pushContext = PushContext.getInstance(this.getServletContext());

        pushContext.push("new-user");

        message = msg;


        return "";

    }

    @Override
    public String addMessage(String m) {
        return "to jest wiadomosc do wszystkich";
    }
    public ArrayList<String> returnTalk(String idTalk) {

        MongoClient mongoClient = new MongoClient("localhost");
//
//
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("people");
        Document updateDocument = collection.find(eq("name", idTalk)).first();
        ArrayList<String> messagesList = (ArrayList<String>) updateDocument.get("messages");

        return messagesList;
    }

    public ArrayList<String> getTalksId()
    {
        MongoClient mongoClient = new MongoClient("localhost");
//
//
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("people");
ArrayList<String> talksIdsList = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                talksIdsList.add(cursor.next().get("name").toString());
            }
        } finally {
            cursor.close();
        }
return talksIdsList;
    }

    public Boolean checkUser(String name, String password)
    {
        MongoClient mongoClient = new MongoClient("localhost");
//
//
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("users");
     Document user=   collection.find(eq("name", name)).first();
     if(!"".equals(user.get("name").toString())) {
         String passwordFromDatabase = (String) user.get("password");
         if (password.equals(passwordFromDatabase))
             return true;
     }
        return false;
    }


}
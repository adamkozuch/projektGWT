package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.view.client.CellPreviewEvent;
import org.icepush.gwt.client.GWTPushContext;
import org.icepush.gwt.client.PushEventListener;

import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {


    private DeckPanel deckPanel = new DeckPanel();

    private HorizontalPanel mainPanel = new HorizontalPanel();

    private VerticalPanel chatPanel = new VerticalPanel();
    private VerticalPanel buttonBar = new VerticalPanel();
    private HorizontalPanel historyPanel = new HorizontalPanel();
    private VerticalPanel thirdPanel = new VerticalPanel();
    private VerticalPanel messagePanel = new VerticalPanel();
    private VerticalPanel messagePanel1 = new VerticalPanel();
    private String user = "";

    final CellTable<String> talksTable = new CellTable<>();

    private String userName ;

    public void onModuleLoad() {


        messagePanel.setSize("200px","300px");
//region pola




        final Button nawigatorHistoria = new Button("Historia");
        final Button aktywnyCzat = new Button("Aktywny czat");
        buttonBar.add(aktywnyCzat);


        final Button wyslij = new Button("Wyslij");


        final Label label = new Label();
        final Label userLabel = new Label();
        final TextBox poleTekstowe = new TextBox();




        GWTPushContext pushContext = GWTPushContext.getInstance();
//endregion

//region style
        label.addStyleName("mojaKlasa2");
        poleTekstowe.setWidth("200px");
        poleTekstowe.setHeight("50px");
//endregion


        userLabel.setVisible(false);
        nawigatorHistoria.setVisible(false);
        aktywnyCzat.setVisible(false);

//region zdarzenia



nawigatorHistoria.addClickHandler(new ClickHandler() {
    @Override
    public void onClick(ClickEvent event) {
        deckPanel.showWidget(2);
        MySampleApplicationService.App.getInstance().getTalksId(new MyAsyncCallback3());
    }
});

        aktywnyCzat.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                deckPanel.showWidget(1);
            }
        });




        pushContext.addPushEventListener(new PushEventListener() {

            public void onPushEvent() {

                Label l = new Label();
                MySampleApplicationService.App.getInstance().getMessage("ss", new callbackMessages(l));
                l.addStyleName("mojaKlasa1");

                messagePanel1.add(l);
            }
        }, "new-user");

        wyslij.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                MySampleApplicationService.App.getInstance().sendMessageTo(poleTekstowe.getText() + "  " + user, new callbackMessages(label));
                poleTekstowe.setText("");

            }
        });




        //endregion
//region celltable

        TextColumn<String> idTalksCollumn = new TextColumn<String>() {
            @Override
            public String getValue(String object) {
                return object;
            }
        };

        talksTable.addColumn(idTalksCollumn);
talksTable.setTitle("Tabela rozmow");

        talksTable.addCellPreviewHandler(new CellPreviewEvent.Handler<String>() {
            @Override
            public void onCellPreview(CellPreviewEvent<String> event) {
                label.setText(event.getType().toString());
                if (event.getNativeEvent().getType().contains("click")) {
                    Window.alert(event.getValue());
                    MySampleApplicationService.App.getInstance().returnTalk(event.getValue(), new callbackTalksId());

                }
            }
        });

        //endregion

   final   loginPanel l=  new loginPanel();
        l.logButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                l.checkUser(MySampleApplication.this);
                userName = l.getUserName().getText();
                userLabel.setText(userName);

                userLabel.setVisible(true);
                nawigatorHistoria.setVisible(true);
                aktywnyCzat.setVisible(true);

            }
        });



        RootPanel.get("slot1").add(userLabel);
        buttonBar.add(nawigatorHistoria);
        buttonBar.add(aktywnyCzat);




        chatPanel.add(messagePanel1);
        chatPanel.add(poleTekstowe);
        chatPanel.add(wyslij);



        historyPanel.add(talksTable);
        historyPanel.add(messagePanel);

        deckPanel.add(l);
        deckPanel.add(chatPanel);
        deckPanel.add(historyPanel);
        deckPanel.showWidget(0);
        mainPanel.add(buttonBar);
        mainPanel.add(deckPanel);


        RootPanel.get("slot1").add(mainPanel);






//endregion
    }


    private  class callbackMessages implements AsyncCallback<String> {
        private Label label;

        public callbackMessages(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.setText( result+"  : "+MySampleApplication.this.userName);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }

    private  class callbackTalksId implements AsyncCallback<ArrayList<String>> {

        public void onFailure(Throwable caught) {
            Window.alert("nie udalo sie");
        }

        public void onSuccess(ArrayList<String> result) {

            messagePanel.clear();
            for(String s:result)
            MySampleApplication.this.messagePanel.add(new Label(s));
        }

    }
    private  class MyAsyncCallback3 implements AsyncCallback<ArrayList<String>> {



        public void onFailure(Throwable caught) {
            Window.alert("nie udalo sie");
        }

        public void onSuccess(ArrayList<String> result) {
            Window.alert(result.toString());

            for(String s:result)
            MySampleApplication.this.talksTable.setRowData(result);
        }

    }

    public void zalogujGO()
    {
        deckPanel.showWidget(1);
    }
}

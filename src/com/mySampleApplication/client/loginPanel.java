package com.mySampleApplication.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Created by adam on 01.05.15.
 */
public class loginPanel extends HorizontalPanel {

   public Button logButton = new Button("Zaloguj");
   private TextBox userName = new TextBox();
   private TextBox password= new TextBox();
    private Boolean mozeBycZalogowany=false;
private MySampleApplication mySampleApplication;
    public TextBox getPassword() {
        return password;
    }

    public TextBox getUserName() {

        return userName;
    }


    public loginPanel()
{
    this.add(userName);
    this.add(password);
    this.add(logButton);
}


    public void checkUser(MySampleApplication app)
    {
        mySampleApplication = app;
        MySampleApplicationService.App.getInstance().checkUser(userName.getText(), password.getText(), new MyAsyncCallback4());
        ;
    }
    public   class MyAsyncCallback4 implements AsyncCallback<Boolean> {



        public void onFailure(Throwable caught) {
            Window.alert("Nieprawidłowy login lub haslo");
        }

        public void onSuccess(Boolean result) {

            if(result)
          mySampleApplication.zalogujGO();
            else
                Window.alert("Nieprawidlowe haslo");
            }
        }
}


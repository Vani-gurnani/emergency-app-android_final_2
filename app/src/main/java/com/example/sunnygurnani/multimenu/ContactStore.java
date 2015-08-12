package com.example.sunnygurnani.multimenu;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sunnygurnani on 7/7/15.
 */
public class ContactStore {

    public ContactStore(){}

    public ContactStore(android.app.Activity activity) {
        readFromFile(activity);
    }

    public Contact getContact_1() {
        return contact_1;
    }

    public void setContact_1(Contact contact_1) {
        this.contact_1 = contact_1;
    }

    public Contact getContact_2() {
        return contact_2;
    }

    public void setContact_2(Contact contact_2) {
        this.contact_2 = contact_2;
    }

    private Contact contact_1;
    private Contact contact_2;

    private final String fileName = "ContactsFile";
    public void saveToFile(android.app.Activity activity) throws IOException{
        FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
        Gson gson = new Gson();
       String data = gson.toJson(this);
        fos.write(data.getBytes());
        fos.close();
    }

    public void readFromFile(android.app.Activity activity)  {

        try {
            FileInputStream fis = activity.openFileInput(fileName);
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = fis.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, n));
            }
            fis.close();
            Gson gson = new Gson();
            ContactStore contactStore = gson.fromJson(fileContent.toString(), ContactStore.class);
            this.contact_1 = contactStore.contact_1;
            this.contact_2 = contactStore.contact_2;

        }
        catch (IOException e){

            this.contact_1 = new Contact();
            this.contact_2 = new Contact();
        }
    }
}

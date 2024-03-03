package com.usbbog.cbs_app.modelHelper;

public class MailHolder {

    private static MailHolder instance;
    private Holder holder;

    private MailHolder(){}

    public static synchronized MailHolder getInstance(){
        if (instance == null){
            instance = new MailHolder();
        }
        return instance;
    }

    public void setHolder(Holder holder){
        this.holder = holder;
    }

    public Holder getHolder(){
        return holder;
    }

}

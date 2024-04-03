package com.usbbog.cbs_app.modelHelper;

public class MailHolder {

    private static MailHolder instance;
    private UsuariosHolder usuariosHolder;

    private MailHolder(){}

    public static synchronized MailHolder getInstance(){
        if (instance == null){
            instance = new MailHolder();
        }
        return instance;
    }

    public void setHolder(UsuariosHolder usuariosHolder){
        this.usuariosHolder = usuariosHolder;
    }

    public UsuariosHolder getHolder(){
        return usuariosHolder;
    }

}

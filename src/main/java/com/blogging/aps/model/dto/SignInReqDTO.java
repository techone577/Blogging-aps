package com.blogging.aps.model.dto;


/**
 * @author techoneduan
 * @date 2018/11/22
 */
public class SignInReqDTO {

    private String rootUserName;

    private String rootPassword;

    public String getRootUserName () {
        return rootUserName;
    }

    public void setRootUserName (String rootUserName) {
        this.rootUserName = rootUserName;
    }

    public String getRootPassword () {
        return rootPassword;
    }

    public void setRootPassword (String rootPassword) {
        this.rootPassword = rootPassword;
    }
}

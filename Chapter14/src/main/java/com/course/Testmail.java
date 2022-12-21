package com.course;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Testmail {
    public static void main(String[] args) throws MessagingException, GeneralSecurityException, IOException {
        SendEmail se = new SendEmail();
        se.sendHtml();
        //ceshidaidsjfk

    }

}

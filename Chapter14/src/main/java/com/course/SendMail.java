package com.course;

import com.sun.mail.util.MailSSLSocketFactory;
import org.testng.annotations.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import static org.testng.reporters.Files.readFile;

public class SendMail {

    @Test
    public void sendText() throws IOException, MessagingException, GeneralSecurityException {
       //1。创建一个程序与邮件服务器会话对象session 解决 Could not connect to SMTP host
        MailSSLSocketFactory sf = new MailSSLSocketFactory();

        //设置信任所有的主机
        sf.setTrustAllHosts(true);
        // 定义属性配置对象
        final Properties properties = new Properties();
       // properties.setProperty( "mail.transport.protocol", "SMTP" );
       // properties.setProperty( "mail.host", "smtp.163.com" );
       // properties.setProperty( "mail.smtp.auth", "true" );// 指定验证为true是否需要身份验证
        //properties.setProperty( "mail.smtp.ssl.enable", "true" );
        properties.put( "mail.smtp.ssl.socketFactory",sf );

        //从配置文件中加载配置属性
        properties.load(SendMail.class.getResourceAsStream("/config.properties"));
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //根据邮箱用户名和密码创建一个认证对象
                return new PasswordAuthentication(properties.getProperty("mail.account"), properties.getProperty("mail.password"));
            }
        };

        //创建一个默认的会话实例，需要一个认证对象
        Session session = Session.getDefaultInstance(properties, auth);

        //创建一个消息对象，相当于一封邮件
        MimeMessage message = new MimeMessage(session);

        //设置邮件从哪里发出去
        message.setFrom(properties.getProperty("mail.account"));

        //设置邮件收件人地址

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(properties.getProperty("mail.to")));

        //设置邮件主题
        message.setSubject("测试邮件");

        //设置邮件正文内容
        message.setText("Hello World!");

        //发送邮件
        Transport.send(message);

    }

    @Test
    public void sendHtml() throws MessagingException, IOException {
        // 定义属性配置对象
        final Properties properties = new Properties();
        //协议

        //从配置文件中加载配置属性
        properties.load(SendMail.class.getResourceAsStream("/config.properties"));
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //根据邮箱用户名和密码创建一个认证对象
                return new PasswordAuthentication(properties.getProperty("mail.account"), properties.getProperty("mail.password"));
            }
        };

        //创建一个默认的会话实例，需要一个认证对象
        Session session = Session.getDefaultInstance(properties, auth);

        //创建一个消息对象，相当于一封邮件
        MimeMessage message = new MimeMessage(session);

        //设置邮件从哪里发出去
        message.setFrom(properties.getProperty("mail.account"));

        //设置邮件收件人地址

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(properties.getProperty("mail.to")));

        //设置邮件主题
        message.setSubject("测试邮件");

        //创建一个部件集合，用于组装邮件正文内容
        MimeMultipart multipart = new MimeMultipart();

        //创建邮件主题部件，内容是个html文档
        //读取HTML文档内容
        MimeBodyPart bodyPart = new MimeBodyPart();
        String content = readFile(new File(SendMail.class.getResource("/test.html").getFile()));

        //设置主题内容
        bodyPart.setContent(content,"text/html;charset=UTF-8");
        multipart.addBodyPart(bodyPart);

        //创建邮件主体部件中引用的资源部件  引用图片的时候用
//        MimeBodyPart resourcePart = new MimeBodyPart();
//        resourcePart.setDataHandler();

        //创建邮件附件的部件
        MimeBodyPart filePart = new MimeBodyPart();

        //设置附件内容
        //filePart.attachFile();







        //设置消息正文内容
        message.setContent(multipart);



        //发送邮件
        Transport.send(message);

    }
}











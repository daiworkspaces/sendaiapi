package com.course;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static org.testng.reporters.Files.readFile;


public class SendEmail {

    //Email读取配置完成登录
    public static Session toConfig() throws GeneralSecurityException, IOException {
        //1.创建一个程序与邮件服务器会话对象session 解决 Could not connect to SMTP host
        MailSSLSocketFactory sf = new MailSSLSocketFactory();

        //设置信任所有的主机
        sf.setTrustAllHosts(true);

        // 定义属性配置对象
        final Properties properties = new Properties();
        properties.put("mail.smtp.ssl.socketFactory", sf);
        properties.load(SendEmail.class.getResourceAsStream("/config.properties"));

        Authenticator auth = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //根据邮箱用户名和密码创建一个认证对象
                return new PasswordAuthentication(properties.getProperty("mail.account"), properties.getProperty("mail.password"));
            }
        };
        //创建一个默认的会话实例，需要一个认证对象
        Session session = Session.getDefaultInstance(properties, auth);
        return session;
    }

    //Email 发送Text邮件
    public  void sendText() throws GeneralSecurityException, IOException, MessagingException {
        //创建一个消息对象，相当于一封邮件
        MimeMessage message = new MimeMessage(toConfig());

        //设置邮件从哪里发出去
        message.setFrom(toConfig().getProperty("mail.account"));

        //设置邮件收件人地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toConfig().getProperty("mail.to")));

        //设置邮件多个收件人地址
//        List toAddress = new ArrayList();
//        toAddress.add(0,toConfig().getProperty("mail.to1"));
//        toAddress.add(0,toConfig().getProperty("mail.to2"));
//        toAddress.add(0,toConfig().getProperty("mail.to3"));
//        toAddress.add(0,toConfig().getProperty("mail.to4"));
//        toAddress.add(0,toConfig().getProperty("mail.to5"));
//        String toList = null;
//        toList = getMailList(toAddress);
//        InternetAddress[] iaToList = new InternetAddress().parse(toList);
//        message.setRecipients(Message.RecipientType.TO,iaToList);//多个收件人

        //设置抄送人地址


        //设置邮件主题
        message.setSubject("测试邮件主题1");

        //设置邮件正文内容
        message.setText("邮件正文内容");

        //发送邮件
        Transport.send(message);




    }
//       List toCC = new ArrayList();
//       toCC.add(0,toConfig().getProperty("mail.to1"));
//        toCC.add(1,toConfig().getProperty("mail.to2"));
//        String toList = getMailList(toCC);
//        InternetAddress[] isToList = InternetAddress.parse(toList);
//
//        //单个用setRecipient   多个用setRecipients
//        message.setRecipients(Message.RecipientType.CC,isToList);



        //发送HTMl文件
        //  public void senHtml(){}

        //遍历list返回字符串
        private static String getMailList (List mailArray){
            StringBuffer toList = new StringBuffer();
            int length = mailArray.size();
            if (mailArray != null && length < 2) {
                toList.append(mailArray.get(0));
            } else {
                for (int i = 0; i < length; i++) {
                    toList.append(mailArray.get(i));
                    if (i != (length - 1)) {
                        toList.append(",");
                    }
                }
            }
            return toList.toString();

        }

        //返回多人的组合包
//        private static InternetAddress[] addressesList() throws GeneralSecurityException, IOException, AddressException {
//            List toAddress = new ArrayList();
//        toAddress.add(0,toConfig().getProperty("mail.to1"));
//        toAddress.add(0,toConfig().getProperty("mail.to2"));
//        toAddress.add(0,toConfig().getProperty("mail.to3"));
//        toAddress.add(0,toConfig().getProperty("mail.to4"));
//        toAddress.add(0,toConfig().getProperty("mail.to5"));
//        String toList = null;
//        toList = getMailList(toAddress);
//        InternetAddress[] iaToList = new InternetAddress().parse(toList);
//        return iaToList;

    //Email 发送Html邮件
    public void sendHtml() throws GeneralSecurityException, IOException, MessagingException {

        //创建一个消息对象，相当于一封邮件
        MimeMessage message = new MimeMessage(toConfig());

        //设置邮件从哪里发出去
        message.setFrom(toConfig().getProperty("mail.account"));

        //设置邮件收件人地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toConfig().getProperty("mail.to")));

        //设置邮件主题
        message.setSubject("测试邮件主题1");

        //创建一个部件集合，用于组装邮件正文内容
        MimeMultipart multipart = new MimeMultipart();
        //创建邮件主题部件，内容是个html文档 ,读取HTML文档内容
        //读取HTML文档内容
        MimeBodyPart bodyPart = new MimeBodyPart();
       // String content = readFile(new File(SendMail.class.getResource("/test.html").getFile()));
        String content = readFile(new File(SendEmail.class.getResource("/index.html").getFile()));
        bodyPart.setContent(content,"text/html;charset=utf-8");
        multipart.addBodyPart(bodyPart);

        message.setContent(multipart);
        Transport.send(message);






    }

 }



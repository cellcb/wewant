package me.cell.wewant.core.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class Email {
    public static void main(String[] args) {
        try {
            send("test","abc");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static void send(String subject, String content) throws MessagingException, GeneralSecurityException {
        //创建一个配置文件并保存
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.163.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        EmailAuthInfo emailAuthInfo = new EmailAuthInfo();
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAuthInfo.getUserCount(), emailAuthInfo.getAuthCode());
            }
        });
        //开启debug模式
        session.setDebug(false);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器
        transport.connect(emailAuthInfo.getSmtpAdr(), emailAuthInfo.getUserCount(), emailAuthInfo.getAuthCode());
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(emailAuthInfo.getUserCount()));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAuthInfo.getSendTo()));
        //邮件标题
        mimeMessage.setSubject(subject);
        //邮件内容
        mimeMessage.setContent(content, "text/html;charset=UTF-8");
        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        //关闭连接
        transport.close();
//        log.info("邮件发送成功");
        System.out.println("邮件发送成功");
    }
}
package per.neal.blog.util;

import org.springframework.beans.factory.annotation.Value;
import per.neal.blog.entity.EmailInfo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件发送工具类
 *
 * @author neal
 */
public final class SendEmail {

    @Value("${crowBlog.email.smtpServer}")
    private String smtpServer;
    @Value("${crowBlog.email.port}")
    private String port;
    @Value("${crowBlog.email.sender}")
    private String sender;
    @Value("${crowBlog.email.senderPwd}")
    private String senderPwd;

    public void sendHtmlEmail(EmailInfo emailInfo) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpServer);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        // 使用JSSE的SSL
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // 只处理SSL的连接,对于非SSL的连接不做处理
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socketFactory.port", port);
        Session session = Session.getInstance(properties);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        // 发件人
        Address address = new InternetAddress(sender);
        message.setFrom(address);
        // 收件人
        Address toAddress = new InternetAddress(emailInfo.getReceiver());
        // 设置收件人,并设置其接收类型为TO
        message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
        // 主题
        message.setSubject(emailInfo.getSubject());
        // 时间
        message.setSentDate(new Date());
        Multipart multipart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(emailInfo.getContent(), "text/html; charset=utf-8");
        multipart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        message.setContent(multipart);
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(smtpServer, sender, senderPwd);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}

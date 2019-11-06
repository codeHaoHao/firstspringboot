package cn.lijiahao.email.sender;

public interface MailService {
    void sendMail(String to, String subject, String content);
}

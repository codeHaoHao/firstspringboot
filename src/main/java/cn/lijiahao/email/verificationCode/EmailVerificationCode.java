package cn.lijiahao.email.verificationCode;

public interface EmailVerificationCode {
    boolean verifyCode(String sessionId, String code);
    String getEmailVerificationCode(String sessionId,String to);
}

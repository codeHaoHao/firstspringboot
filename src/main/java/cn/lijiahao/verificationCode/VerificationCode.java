package cn.lijiahao.verificationCode;

public interface VerificationCode {
	String getVerificationCode(String sessionId);
	boolean verifyCode(String sessionId, String code);
}

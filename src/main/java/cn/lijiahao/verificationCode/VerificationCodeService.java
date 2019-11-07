package cn.lijiahao.verificationCode;

public interface VerificationCodeService {
	String getVerificationCode(String sessionId);
	boolean verifyCode(String sessionId, String code);
}

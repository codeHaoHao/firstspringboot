package cn.lijiahao.verificationCode;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("VerificationCode")
public class CodeVersion1 implements VerificationCode {

	@Autowired
	private static StringRedisTemplate stringRedisTemplate; // redis operation

	private static final char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private final static String PREX = "verificationCode:";

	@Override
	public String getVerificationCode(String sessionId) {
		String code = genCaptcha(4);
		stringRedisTemplate.opsForSet().add(PREX + sessionId, code);
		return code;
	}

	@Override
	public boolean verifyCode(String sessionId, String code) {
		String verifyCode = stringRedisTemplate.opsForValue().get(PREX + sessionId);
		if (code.equals(verifyCode)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成一个位数为count的随机验证码
	 * 
	 * @param count
	 * @return
	 */
	private synchronized String genCaptcha(int count) {
		StringBuilder captcha = new StringBuilder();

		for (int i = 0; i < count; i++) {
			char c = chars[ThreadLocalRandom.current().nextInt(chars.length)];// 随机选取一个字母或数字
			captcha.append(c);
		}
		return captcha.toString();
	}

}

package cn.lijiahao.email.verificationCode;

import cn.lijiahao.email.sender.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component("EmailVerificationCode")
public class EmailVerificationCode1 implements EmailVerificationCode {

    @Autowired
    private StringRedisTemplate stringRedisTemplate; // redis operation

    @Autowired
    private MailService mailService;

    private static final char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private final static String PREX = "emailVerificationCode:";
    private final static long EXPIRE_TIME = 300000; // 1 minutes, 验证码在redis缓存中的过期时间

    private final String EMAIL_SUBJECT = "Email Verification Code";


    /**
     * this method can verify the email verification code
     * @param sessionId
     * @param code email verification code
     * @return
     */
    @Override
    public boolean verifyCode(String sessionId, String code) {
        boolean isRight = false;
        String key = PREX + sessionId;
        String vCode = stringRedisTemplate.opsForValue().get(key);
        if (code.equalsIgnoreCase(vCode)){
            isRight = true;
        }
        return isRight;
    }

    /**
     * this method can set and send the verification with the email
     * @param sessionId
     * @param to user's email
     * @return
     */
    @Override
    public String getEmailVerificationCode(String sessionId, String to) {
        String code = genCaptcha(6);
        String key = PREX + sessionId;
        stringRedisTemplate.opsForValue().set(key,code);
        stringRedisTemplate.expire(key,EXPIRE_TIME, TimeUnit.MILLISECONDS);
        String content = "Your Email Verification Code :" + code + ". Valid for five minutes";
        mailService.sendMail(to,EMAIL_SUBJECT,content);
        return code;
    }

    /**
     * Generate a random verification code with a count of count
     *
     * @param count
     * @return
     */
    private synchronized String genCaptcha(int count) {
        StringBuilder captcha = new StringBuilder();

        for (int i = 0; i < count; i++) {
            char c = chars[ThreadLocalRandom.current().nextInt(chars.length)];
            captcha.append(c);
        }
        return captcha.toString();
    }
}

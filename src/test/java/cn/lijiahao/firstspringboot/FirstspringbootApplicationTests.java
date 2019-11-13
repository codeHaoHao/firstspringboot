package cn.lijiahao.firstspringboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.lijiahao.md5.Md5Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstspringbootApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("1024  MD5:   "+Md5Utils.encrypt("1024", "1024"));
		System.out.println("test");
	}

}

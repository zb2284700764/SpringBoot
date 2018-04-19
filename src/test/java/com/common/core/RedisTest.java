package com.common.core;

import org.apache.shiro.codec.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RedisTest {


    @Test
    public void testAES() throws NoSuchAlgorithmException {
//        KeyGenerator keygen = KeyGenerator.getInstance("AES");
//        SecretKey deskey = keygen.generateKey();
//        System.out.println(Base64.encodeToString(deskey.getEncoded()));

        System.out.println(System.getProperty("user.home"));

    }

}

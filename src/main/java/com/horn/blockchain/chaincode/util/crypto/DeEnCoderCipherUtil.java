package com.horn.blockchain.chaincode.util.crypto;

import org.testng.util.Strings;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DeEnCoderCipherUtil {
    //加密 ，解密模式
    private final static String CIPHER_MODE="DES";

    //DES密钥
    public static String DEFAULT_DES_KEY = "区块链是分布式数据存储、点对点传输、共识机制、加密算法等计算机技术的新型应用模式。";

    /**
     * function 加密通用算法
     *
     * @param originalContent:明文
     * @param key 加密秘钥
     * @return 密文
     * */
    public static String commonEncrypt(String originalContent, String key){
        //明文或者加密密钥为空时
        if(Strings.isNullOrEmpty(originalContent) || Strings.isNullOrEmpty(key)) return null;
        //明文或者加密密钥不为空时
        try{
            byte[] byteContent = encrypt(originalContent.getBytes(),  key.getBytes());
            return new BASE64Encoder().encode(byteContent);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * function 解密通用方法
     * @param ciphertest 密文
     * @param key DES 解密密钥
     * @return 明文
     * */
    public static String commonDecrypt(String ciphertest, String key){
        //密文或者加密密钥为空时
        if(Strings.isNullOrEmpty(ciphertest) || Strings.isNullOrEmpty(key)){
            return null;
        }
        try{
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bufCiphertest = decoder.decodeBuffer(ciphertest);
            byte[] contentByte = decrypt(bufCiphertest, key.getBytes());
            return new String(contentByte);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * function 字节加密方法
     * @param originalContent : 明文
     * @param key 加密密钥的byte数组
     * @return 密文的byte数组
     * */
    public static byte[] encrypt(byte[] originalContent, byte[] key) throws Exception{
        //步骤1: 生成可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();

        //步骤2:基于密钥数组创建DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(key);

        //步骤3:创建密钥工厂，将DESKeySpec转换成SecretKey对象来保存对称密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CIPHER_MODE);
        SecretKey securekey = keyFactory.generateSecret(desKeySpec);

        //步骤4:Cipher对象实际完成加密操作，指定其支持指定的加密和解密算法
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);

        //步骤5:用密钥初始化Cipher对象，ENCRYPT_MODE表示加密模式
        cipher.init(Cipher.ENCRYPT_MODE, securekey, secureRandom);

        return cipher.doFinal(originalContent);
    }

    /**
     * funtion 字节解密方法
     * @param ciphertextByte:字节密文
     * @param key 解密密钥
     * return 明文byte数组
     * */
    public static byte[] decrypt(byte[] ciphertextByte, byte[] key) throws Exception{
        //1.生成可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();

        //2.从原始密钥数据创建DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(key);

        //3.创建密钥工厂，将DESKeySpec转换成SecretKey对象来保存对称密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CIPHER_MODE);
        SecretKey securekey = keyFactory.generateSecret(desKeySpec);

        //4.Cipher对象实际完成解密操作，指定其支持响应好的加密和解密算法
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);

        //5.用密钥初始化Cipher对象，DECRYPT_MODE表示解密模式
        cipher.init(Cipher.DECRYPT_MODE, securekey, secureRandom);

        //6.返回明文
        return cipher.doFinal(ciphertextByte);
    }
}

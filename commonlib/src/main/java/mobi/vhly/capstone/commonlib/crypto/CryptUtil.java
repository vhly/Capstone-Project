package mobi.vhly.capstone.commonlib.crypto;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 15/6/10
 * Email: vhly@163.com
 */

import android.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Crypt utils
 */
public final class CryptUtil {

    public static final String ALG_AES = "AES";
    public static final String ALG_DES = "DES";
    public static final String ALG_RSA = "RSA";
    public static final String ALG_MD5 = "MD5";
    public static final String ALG_SHA1 = "SHA1";

    public static final int KEY_SIZE_AES = 16;
    public static final int KEY_SIZE_DES = 8;
    public static final int KEY_SIZE_RSA_MIN = 1024;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    private CryptUtil() {

    }

    public static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder();

        if (data != null) {
            for (byte b : data) {
                int h = (b >> 4) & 0x0f;
                int l = b & 0x0f;
                if (h > 9) {
                    sb.append((char) ('A' + h - 10));
                } else {
                    sb.append((char) ('0' + h));
                }

                if (l > 9) {
                    sb.append((char) ('A' + l - 10));
                } else {
                    sb.append((char) ('0' + l));
                }
            }
        }

        return sb.toString();
    }

    public static byte[] fromHex(String hex) {
        byte[] ret = null;
        if (hex != null) {

            int len = hex.length();

            if (len % 2 == 0) {

                ret = new byte[len / 2];

                for (int i = 0, j = 0; i < len - 1; i += 2, j++) {
                    char ch = hex.charAt(i);
                    char cl = hex.charAt(i + 1);

                    int ih;
                    int il;

                    if (ch >= 'A') {
                        ih = ch - 'A' + 10;
                    } else {
                        ih = ch - '0';
                    }

                    if (cl >= 'A') {
                        il = cl - 'A' + 10;
                    } else {
                        il = cl - '0';
                    }

                    int iv = (ih << 4) | il;

                    ret[j] = (byte) iv;
                }
            }

        }
        return ret;
    }

    ///////////////////////////
    // MessageDigest

    public static String md5Hex(String content, String charset) {
        String ret = null;
        if (content != null) {
            if (charset == null) {
                charset = "UTF-8";
            }
            byte[] data = null;
            try {
                data = content.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                data = content.getBytes();
            }
            ret = md5Hex(data);
        }
        return ret;
    }

    public static String md5Hex(byte[] data) {
        String ret = null;
        if (data != null && data.length > 0) {
            ret = toHex(md5(data));
        }
        return ret;
    }

    public static String sha1Hex(byte[] data) {
        String ret = null;
        if (data != null && data.length > 0) {
            ret = toHex(sha1(data));
        }
        return ret;
    }

    public static byte[] md5(byte[] data) {
        byte[] ret = null;
        if (data != null && data.length > 0) {
            data = digest(data, ALG_MD5);
            if (data != null) {
                ret = data;
            }
        }
        return ret;
    }

    public static byte[] sha1(byte[] data) {
        byte[] ret = null;
        if (data != null && data.length > 0) {
            data = digest(data, ALG_SHA1);
            if (data != null) {
                ret = data;
            }
        }
        return ret;
    }

    public static byte[] digest(byte[] data, String alg) {
        byte[] ret = null;
        if (data != null && alg != null) {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance(alg);
                ret = digest.digest(data);
                digest.reset();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    ///////////////////////////
    // RSA

    /**
     * Load base64 encoded string to PublicKey
     *
     * @param keyAlg
     * @param base64Encoded
     * @return
     */
    public static PublicKey loadPublicKey(String keyAlg, String base64Encoded) {
        PublicKey ret = null;
        if (keyAlg != null && base64Encoded != null) {
            X509EncodedKeySpec keySpec =
                    new X509EncodedKeySpec(Base64.decode(base64Encoded, Base64.NO_WRAP));

            try {
                KeyFactory factory = KeyFactory.getInstance(keyAlg);

                ret = factory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

        }
        return ret;
    }

    /**
     * 根据指定的算法，加载经过Base64编码的字符串，形成实际的私钥,
     * 为了将来从已经存在的私钥编码中加载内容
     *
     * @param keyAlg
     * @param base64Encoded
     * @return
     */
    public static PrivateKey loadPrivateKey(String keyAlg, String base64Encoded) {
        PrivateKey ret = null;
        if (keyAlg != null) {
            if (base64Encoded != null) {
                // 转换为实际的字节数组，设置私钥规范为  PKCS8EncodedKeySpec
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(base64Encoded, Base64.NO_WRAP));
                // KeyFactory 用于根据已有的内容，生成公钥和私钥
                try {
                    KeyFactory factory = KeyFactory.getInstance(keyAlg);

                    ret = factory.generatePrivate(keySpec);

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }


    /**
     * 随机生成 RSA 密钥对，指定尺寸，尺寸应该 >= 1024
     *
     * @param keySize
     * @return
     */
    public static KeyPair generateRSA(int keySize) {
        KeyPair ret = null;
        if (keySize >= 1024) {
            try {
                KeyPairGenerator generator = KeyPairGenerator.getInstance(ALG_RSA);
                generator.initialize(keySize, SECURE_RANDOM);
                ret = generator.generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private static byte[] rsaCrypt(int mode, byte[] data, Key key) {
        byte[] ret = null;
        if (data != null && key != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALG_RSA);

                cipher.init(mode, key);

                ret = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * RSA 加密，支持公钥加密和私钥加密<br/>
     * 传递的 Key 参数，需要是 PrivateKey PublicKey 这两种之一。
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] rsaEncrypt(byte[] data, Key key) {
        return rsaCrypt(Cipher.ENCRYPT_MODE, data, key);
    }

    /**
     * RSA 解密，支持公钥解密和私钥解密<br/>
     * 传递的 Key 参数，需要是 PrivateKey PublicKey 这两种之一。
     * 如果加密采用的是公钥，那么解密方法必须传递私钥参数。
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] rsaDecrypt(byte[] data, Key key) {
        return rsaCrypt(Cipher.DECRYPT_MODE, data, key);
    }

    // ---------------
    // 密钥封装和解包,用于传递安全的密钥数据

    /**
     * 使用RSA算法,进行密钥的封装,也可以理解为,将密钥加密一次,
     * 这样的目的是为了保护密钥在传递的过程中,不会被截获
     *
     * @param secretKey 待封装的密钥
     * @param key       使用这个密钥进行封装操作
     * @return byte[] 经过封装之后的数据
     */
    public static byte[] rsaWrapKey(Key secretKey, Key key) {
        byte[] ret = null;
        if (secretKey != null && key != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALG_RSA);

                cipher.init(Cipher.WRAP_MODE, key);

                ret = cipher.wrap(secretKey);

                cipher = null;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 使用RSA 算法,进行密钥的解封,在解封的时候,需要传递几个信息
     * <ol>
     * <li>byte[] data : 需要解封的数据</li>
     * <li>int dataKeyType : 数据内部包含的密钥的类型 参见 Cipher中的密钥类型</li>
     * <li>Key key : 用于解封的密钥,这个密钥应该与封装时使用的密钥对应(根据使用的密钥算法不同)</li>
     * </ol>
     *
     * @param data        被封装的密钥的数据
     * @param dataKeyAlg  被封装的密钥的算法
     * @param dataKeyType 被封装的密钥的类型,公钥/私钥/安全密钥
     * @param key         解封用的密钥
     * @return
     * @see Cipher#PRIVATE_KEY
     * @see Cipher#PUBLIC_KEY
     * @see Cipher#SECRET_KEY
     */
    public static Key rsaUnwrapKey(byte[] data, String dataKeyAlg, int dataKeyType, Key key) {
        Key ret = null;
        if (data != null && key != null && dataKeyAlg != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALG_RSA);

                cipher.init(Cipher.UNWRAP_MODE, key);

                ret = cipher.unwrap(data, dataKeyAlg, dataKeyType);

                cipher = null;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    // ---------------


    ///////////////////////////
    // AES加密、解密

    // 带有 Iv参数密码的 AES 相当于两套密码，强度更高

    private static byte[] aesIvProcess(int mode, byte[] data, byte[] password, byte[] iv) {
        byte[] ret = null;
        if (data != null && password != null && iv != null) {
            if (password.length == KEY_SIZE_AES && iv.length == KEY_SIZE_AES) {

                // 1. 初始化 Cipher，当使用Iv参数的AES加密，算法要指定 "AES/方式/填充" 这种方式
                //    支持的和建议的算法采用 AES/CBC/PKCS5Padding 或者 AES/ECB/PKCS5Padding
                try {
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

                    // 2. 生成 SecretKeySpec 密钥
                    SecretKeySpec key = new SecretKeySpec(password, ALG_AES);

                    // 3. 准备 Iv 参数，这个参数会在第一个数据块加密的时候,实现 "数据 + 密码 + Iv" 进行加密
                    //    生成第一个数据块的密文,然后用密文 与 第下一个数据块 进行加密
                    //    实现 "上一个密文 + 当前数据块 + 密码" 实现加密,生成当前密文,当前密文还会用在下一次加密
                    //    这种方式就是  AES/CBC 模式; 因此 即使密码相同, Iv 不同, 那么加密结果也是不同的
                    //    因此 也称 Iv 为 第二个密码
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

                    // 4. 初始化 Cipher
                    cipher.init(mode, key, ivParameterSpec);

                    // 5. 进行处理
                    ret = cipher.doFinal(data);

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }


            }
        }
        return ret;
    }

    /**
     * 采用 AES 并且包含 Iv 密码参数的加密方法，
     * 算法采用 AES/CBC/PKCS5Padding
     *
     * @param data
     * @param password
     * @param iv
     * @return
     */
    public static byte[] aesIvEncrypt(byte[] data, byte[] password, byte[] iv) {
        return aesIvProcess(Cipher.ENCRYPT_MODE, data, password, iv);
    }

    /**
     * 采用 AES 并且包含 Iv 密码参数的加密方法，
     * 算法采用 AES/CBC/PKCS5Padding
     *
     * @param data
     * @param password
     * @param iv
     * @return
     */
    public static byte[] aesIvDecrypt(byte[] data, byte[] password, byte[] iv) {
        return aesIvProcess(Cipher.DECRYPT_MODE, data, password, iv);
    }


    // 普通的AES

    /**
     * 普通的 AES 加密，每一中加密对应特定的解密方式。
     *
     * @param data
     * @param password 一定要 16个字节。
     * @return
     */
    public static byte[] aesEncrypt(byte[] data, byte[] password) {
        byte[] ret = null;

        if (data != null && password != null && password.length == KEY_SIZE_AES) {

            // 1. 初始化 Cipher 加密引擎
            //    AES 对于加密有一些特点，Cipher 的算法可以支持多种 AES的类型
            //    每一种算法类型，都代表不同的加密方式
            try {
                Cipher cipher = Cipher.getInstance(ALG_AES);

                // 2. 准备 AES 的Key，AES Key生成，就直接创建 SecretKeySpec
                //    就好了，直接指定密码
                SecretKeySpec key = new SecretKeySpec(password, ALG_AES);

                // 3. 初始化 Cipher
                cipher.init(Cipher.ENCRYPT_MODE, key);

                // 4. 进行加密
                ret = cipher.doFinal(data);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 普通的 AES 解密，每一中加密对应特定的解密方式。
     *
     * @param data
     * @param password 一定要 16个字节。
     * @return
     */
    public static byte[] aesDecrypt(byte[] data, byte[] password) {
        byte[] ret = null;

        if (data != null && password != null && password.length == KEY_SIZE_AES) {

            // 1. 初始化 Cipher 加密引擎
            //    AES 对于加密有一些特点，Cipher 的算法可以支持多种 AES的类型
            //    每一种算法类型，都代表不同的解密方式
            try {
                Cipher cipher = Cipher.getInstance(ALG_AES);

                // 2. 准备 AES 的Key，AES Key生成，就直接创建 SecretKeySpec
                //    就好了，直接指定密码
                SecretKeySpec key = new SecretKeySpec(password, ALG_AES);

                // 3. 初始化 Cipher
                cipher.init(Cipher.DECRYPT_MODE, key);

                // 4. 进行解密
                ret = cipher.doFinal(data);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static SecretKey genSecretKey(String alg) {
        SecretKey ret = null;
        if (alg != null) {
            try {

                KeyGenerator keyGenerator = KeyGenerator.getInstance(alg);

                keyGenerator.init(SECURE_RANDOM);

                ret = keyGenerator.generateKey();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static SecretKey genSecretKey(String alg, byte[] data) {
        SecretKey ret = null;
        if (alg != null && data != null && data.length > 0) {
            try {

                if (alg.toUpperCase().equals(ALG_AES)) {
                    ret = new SecretKeySpec(data, alg);
                } else {
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(alg);
                    ret = keyFactory.generateSecret(new SecretKeySpec(data, alg));
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    //////////////////////////////
    // DES 加密、解密

    /**
     * 进行DES解密
     *
     * @param data
     * @param password 8个字节
     * @return
     */
    public static byte[] desDecrypt(byte[] data, byte[] password) {
        byte[] ret = null;

        if (data != null && password != null) {
            // DES 8字节密码
            if (data.length > 0 && password.length == KEY_SIZE_DES) {
                // getInstance 的参数，就是算法的名称
                try {
                    Cipher cipher = Cipher.getInstance(ALG_DES);
                    // 2. 初始化 Cipher 加密引擎
                    //    实际上就是设置密码
                    // DES 密码需要生成为 Key 对象
                    // 需要使用工厂
                    SecretKeyFactory factory = SecretKeyFactory.getInstance(ALG_DES);

                    // 工厂生成Key，就需要使用  KeySpec 密码的规则，代表的就是密码的数据
                    DESKeySpec keySpec = new DESKeySpec(password);
                    // 生成秘密的密码Key对象
                    SecretKey secretKey = factory.generateSecret(keySpec);

                    // 初始化为加密，并且指定密码
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);

                    // 加密数据
                    ret = cipher.doFinal(data);

                } catch (NoSuchAlgorithmException e) {
                    // 找不到指定的算法
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    // 数据的内容过短，并且没有填充
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    // 非法的密钥数据异常
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    // 非法的密钥异常，密钥与算法不匹配
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    // 错误的填充异常
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    // 非法的块尺寸异常
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    /**
     * 进行DES加密
     *
     * @param data
     * @param password 8个字节
     * @return
     */
    public static byte[] desEncrypt(byte[] data, byte[] password) {
        byte[] ret = null;

        if (data != null && password != null) {
            // DES 8字节密码
            if (data.length > 0 && password.length == KEY_SIZE_DES) {
                // getInstance 的参数，就是算法的名称
                try {
                    Cipher cipher = Cipher.getInstance(ALG_DES);
                    // 2. 初始化 Cipher 加密引擎
                    //    实际上就是设置密码
                    // DES 密码需要生成为 Key 对象
                    // 需要使用工厂
                    SecretKeyFactory factory = SecretKeyFactory.getInstance(ALG_DES);

                    // 工厂生成Key，就需要使用  KeySpec 密码的规则，代表的就是密码的数据
                    DESKeySpec keySpec = new DESKeySpec(password);
                    // 生成秘密的密码Key对象
                    SecretKey secretKey = factory.generateSecret(keySpec);

                    // 初始化为加密，并且指定密码
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                    // 加密数据
                    ret = cipher.doFinal(data);

                } catch (NoSuchAlgorithmException e) {
                    // 找不到指定的算法
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    // 数据的内容过短，并且没有填充
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    // 非法的密钥数据异常
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    // 非法的密钥异常，密钥与算法不匹配
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    // 错误的填充异常
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    // 非法的块尺寸异常
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }


    /**
     * 采用异或算法进行加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] xor(byte[] data, byte key) {
        byte[] ret = null;
        if (data != null) {
            int len = data.length;

            ret = new byte[len];

            for (int i = 0; i < len; i++) {
                ret[i] = (byte) (data[i] ^ key);
            }

        }
        return ret;
    }

}

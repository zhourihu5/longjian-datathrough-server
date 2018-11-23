package com.longfor.longjian.datathrough.app.util;

import com.longfor.drogon.common.encrypt.LFEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by Wang on 2018/11/23.
 */
@Slf4j
public class HexPasswordUtil {

    /**
     * 明文SHA加密生成墨菲加密格式一样的密码
     * @param pass
     * @param pubKey
     * @return
     */
    public static String getMurphyShaPass(String pass,String pubKey){

        byte[] encryptBytes = new byte[0];
        try {
            encryptBytes = LFEncrypt.encryptRSAByPublicKey(pubKey, pass.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("密文：" + Hex.encodeHexString(encryptBytes));
        return Hex.encodeHexString(encryptBytes);

    }

}

package com.pig4cloud.pig.dc.biz.utils;

/**
 * WxUtils
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2022/2/9 11:12
 * Copyright :  版权所有
 **/

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

@Slf4j
public class WxUtils {


	public static final String AES = "AES";
	public static final String AES_CBC_PADDING = "AES/CBC/PKCS7Padding";

	/**
	 *    加密数据解密算法
	 *   接口如果涉及敏感数据（如wx.getUserInfo当中的 openId 和 unionId），接口的明文内容将不包含这些敏感数据。开发者如需要获取敏感数据，需要对接口返回的加密数据(encryptedData) 进行对称解密。 解密算法如下：
	 *   对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
	 *   对称解密的目标密文为 Base64_Decode(encryptedData)。
	 *   对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
	 *   对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
	 *
	 *    * @param encrypted 加密数据
	 *    * @param session_key 会话密钥 session_key
	 *    * @param iv 加密算法的初始向量
	 *
	 */

	public static String wxDecrypt(String encrypted, String session_key, String iv) {
		String result = null;
		log.info("encrypted:{},session_key:{},iv:{}",encrypted,session_key,iv);
		byte[] encrypted64 = Base64.decodeBase64(encrypted);
		byte[] key64 = Base64.decodeBase64(session_key);
		byte[] iv64 = Base64.decodeBase64(iv);
		try {
			init();
			result = new String(decrypt(encrypted64, key64, generateIV(iv64)),"UTF-8");
		} catch (Exception e) {
			log.error("解密失败:"+e.getMessage());
		}
		return result;
	}

	/**
	 *    * 初始化密钥
	 *
	 */

	public static void init() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyGenerator.getInstance( AES).init(128);
	}

	/**
	 *    * 生成iv
	 *
	 */
	public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
		// iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
		// Arrays.fill(iv, (byte) 0x00);
		AlgorithmParameters params = AlgorithmParameters.getInstance( AES);
		params.init(new IvParameterSpec(iv));
		return params;
	}

	/**
	 *    * 生成解密
	 *
	 */
	public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)
			throws Exception {
		Key key = new SecretKeySpec(keyBytes, AES);
		Cipher cipher = Cipher.getInstance( AES_CBC_PADDING);
		// 设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		return cipher.doFinal(encryptedData);
	}

/*

	public ResultInfo getWxPhone(JSONObject json) {
		String encryptedData = json.getString("encryptedData");
		String session_key = json.getString("session_key");
		String iv = json.getString("iv");
		String phone = null;
		try {
			String result = WxUtils.wxDecrypt(encryptedData, session_key, iv);
			JSONObject resultjson = JSONObject.parseObject(result);
			logger.info("========微信返回手机号信息======="+result);
			if (resultjson.containsKey("phoneNumber")) {
				phone = resultjson.getString("phoneNumber");
				String appid = resultjson.getJSONObject("watermark").getString("appid");
				if (StringUtils.isNotBlank(phone)) {

				} else {
					logger.info("该用户未绑定手机号");
					return  new ResultInfo(ServerMsgEnum.FAIL.getServerCode(), "该用户未绑定手机号");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return   new ResultInfo(ServerMsgEnum.SUCCESS.getServerCode(), ServerMsgEnum.SUCCESS.getServerMsg(), phone);
	}
*/

}


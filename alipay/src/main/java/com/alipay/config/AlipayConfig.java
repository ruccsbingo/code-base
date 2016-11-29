package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088021421466945";
	// 商户的私钥
	public static final String private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMfYCs5BZ01GaAh1" +
			"I/g66f55h5quXnrlFAjgaRSB1F0paPpalaZ40pMPwrlRtwI+GE/XXwCzPLiQ/Gz7" +
			"/mP0xJBAKKB7mrKVxSFaaQbtIYgBd4optMITMLLLpbf8LdoL7M5ikT+IkAH16inw" +
			"mRvrFEnZG26MWyQ/5wx/KMwMvm0hAgMBAAECgYEAizhejsHRjWv3Ha09mbixOq6/" +
			"YWsINuVPx79OdRLdNumaUI66onaZfAoGxcO/krhfh3+ql3pGWu3mbKZA2rGyHPDv" +
			"sY8PZoQFOvVfKmbgXop7fz117SFPWvKdsKTl5r73HRQe267nhGiccac/k8bfqBfl" +
			"2w5xEaZ9wK8MCjq3rWECQQDiMim7Kd/eZ0GZdAsb5Snce4lZOpN4BHSSmpRgpsg7" +
			"yIbN20v+zgCJ+LaO493YlijBOOdxslU8cCyJvvb9lKOHAkEA4iz/COyv+kXKnIVh" +
			"VxL6op4yd0WvQsds0YJw4cg6Bc2MSLNZx7uHDO5MSlSaZaOASGMSaA9Oi4UCbUpK" +
			"XiVkFwJBAK1ASNBvmWYK5qb3yA58r1yOTrxGbm+N0g/8UEvbUyDfTclc/89Rz/3d" +
			"v06g3+ol0V92C2f9dOXO8X4sA5hlAf8CQQCaHdEW0rlDD6V1LEnc3HjesG9w6JH5" +
			"LgzPYlPq9dXbuGlb7KJDwFvRCtv/VlDy6wtxx9h2AeAin9q8f//W0SG3AkBLiGj9" +
			"n3uVaDTjSxzHZbgtyo5pHAgtKqicN4wFAuJoq2L+Vyk6QyB8GZNp5/WxsYzU2w+Q" +
			"bGJDXg9SRuxQBZ2U";

	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}

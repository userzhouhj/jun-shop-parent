package com.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092000554426";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCAXsnQsjcaK7yaLXc5zMozttnu0MZlUbSrryg3MQiZrlkmDnX6rP8eBmBkcYaja0IDN5THHkVkcJengWppQnvpIw3YhbXNz5m5oPQWoZCf1/nhDPTJy12HUuxWsmjqLvWvLOWi6hd7ICn2gsfewqYJ6PvKVyf5oQ7U2rgn5/1y9JMaU2e/zWTtkPsAQ76cWtUA7thNgIMNWS9Z3VVDtoLNSb5A+Bns8EgLHeFzDD+LimJsZ7vYAPMBjV7EwlycgDnFrp0WDB7uxChhBxs74JRqCipNyR7cByVr7YHMfvp5RDKYSqWZVpwReuO74WsiDi8UsXbNBsOGLnV6phpIMNCLAgMBAAECggEAXEdHXj1uPOLp3g8G4MblJ82u1ezAYX8dmDj+eMZHv2nEbgYBK2UGs4ez7SBqv7jPqE5hJc4N5gjGC8SNQQNo3Yu0XUypBzzP7c/VBxu3QiBYYT4WDaWiIzAp8pynrIh7ViS5IqaSoJDbeXhDw087Wm5f6fFDLEBpz/s2XIF5ARPmxUsemuJX3RuydAw9MVgzKbtBY3NB0OrEpJlqPIEwuNiciRgE4cdn4xPM7CSif4FP3EoI/alSK4P7WUYMBu4XhBRPLr4ZTicSDbz6Mbo6S0iyDHwH+MnCWkHsB8srx6H4zrig/ZhgIkQ3NEtTIH05QFzO//iCNgrkJYvp7S3qaQKBgQDMyPGsY5v64moz7LOIT1JmIdbVsDqtR/IAEbNd+QbiySJzC5cd8wlj8mIGhj069MTlyAHUl2o/CgZOemPdL3qcGIrlc87vsv/c60f1I427Uk+yFg/ljf1lxFOj7l1MTQsyg45qahgdVV0fRvyojOCpjRf6RtB6/TjawuUWPz5fFQKBgQCgeYG/2TwImVxp0eadzz1ePQodaS9ELBdVtuLj9qnhT4gi0cKceEBK5jzR2Fzu/lran/x6LYIgHWE9DHwgqbPWbYZhbctPUz1QTt8xfd2zx72G8VkB5URenr9i/wca6FvtcbKFsam4P/3UUjB5jRWwiVJhgftNTverEllPq4FZHwKBgG+ZdnLO2dswdlYTwYcgSzRTbqOHZYK3OX+mJcypgIIowHhXsaPlpZFtLtSFZWrypzbd93lmhsUR7nvoIPISBMx6Z39BzF0+fEdlhRJnrL6PzdSsRz9/UL+b7UTiCsAhIGAH553AcaHSumvoIJquI2Ti+lUvMEBGLSyyl2u3TYM9AoGARhTqeitX0XWQa1JUwcR4y4giu7dtAfuzcSZ+rgnSJOjDzlkHAahug4dLNmWkKGeCrAnEjGMFehr6lVu3ddhHBrUsQ65cZNc4ZrMbPBT41oO7nIwYUACdPCld8dDofHLPesH1sfdl5GZl5uzB6MOZnlBKXvUSF7AN73MC+A0xXwcCgYA3Y8I6ltjcLwIVU/s6CKxbUPZM9hzAOLvKfGcq1LroaXGWGe20Y2AIBAu51C5fALgiyyuFhmukXkmVVBbkMaINiOem2/IPau1yQlXxQJBcQxSpSshjWX+7qaGDm5RNenfjoKwysJXNBD1Q3SL23ox2q/YHtgL1vyFMWIbjUubZLQ==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVyLaW+MqLXkyFCMEDK1+qDq4nY1q3qmLZfg8XEHmSWlrdo0tcD1/PySLsQxIkWQFnnMSi9ItXnaAJYz9HzUnXwJouF6emYT2C0La9DGjz4B3uW1TYjcqgLULKvT/Ci0Q29sR79FrxvNCaYnwVUiWYcJABossAuneBnENkL+cDlGArnAPJZbOdwVO4chcJd6FdbJM/dNSbSlNUQcVAa02B5VXIPq5Hblzk1FlegSizpi8LGyvAy8NBvp4nQd0Zeo6EC363fM6mBLMLYTA4NlQ7dgyh7YQg01sMcGpJBytloZnHP3rVGgnIiE5kNalHgEHdHDYYDe1M22v6bUT1dyiQIDAQAB+qz/HgZgZHGGo2tCAzeUxx5FZHCXp4FqaUJ76SMN2IW1zc+ZuaD0FqGQn9f54Qz0yctdh1LsVrJo6i71ryzlouoXeyAp9oLH3sKmCej7ylcn+aEO1Nq4J+f9cvSTGlNnv81k7ZD7AEO+nFrVAO7YTYCDDVkvWd1VQ7aCzUm+QPgZ7PBICx3hcww/i4pibGe72ADzAY1exMJcnIA5xa6dFgwe7sQoYQcbO+CUagoqTcke3Acla+2BzH76eUQymEqlmVacEXrju+FrIg4vFLF2zQbDhi51eqYaSDDQiwIDAQAB";

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://zhouhjpro.natapp1.cc/pay/asyncallback";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://zhouhjpro.natapp1.cc/pay/syncallback";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "D:\\";

    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

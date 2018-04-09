package com.lovejobs.utils;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class HttpsUtils {


    /**
     * 开发环境或者生产环境
     */
    private static String profile ="PRO";
    /**
     * 密钥库路径
     */
    //private static String keyStorePath="/Users/fengxin/Downloads/214565901500664/214565901500664.jks";
    private static String keyStorePath="/Users/fengxin/Downloads/cet/keystore.jks";
    /**
     * 密钥库密码
     */
    private static  String keyStorepass="123456";

    private static SSLContext sc = null;

    private static PoolingHttpClientConnectionManager pool = null;

    /**
     * 设置信任自签名证书
     * @return SSLContext
     */
    public static SSLContext init(){
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            if(!StringUtils.isEmpty(profile)&&!"DEV".equals(profile.toUpperCase())){
                trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                instream = new FileInputStream(new File(keyStorePath));
                trustStore.load(instream, keyStorepass.toCharArray());
                sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            }else{
                sc = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                }).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sc;
    }

    /**
     * 创建连接池
     * @return
     */
    public static PoolingHttpClientConnectionManager createPool(){
        if(pool == null){
            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(init())).build();
            pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            pool.setMaxTotal(200);
            pool.setDefaultMaxPerRoute(50);
        }
        return pool;
    }
    /**
     * 创建连接客户端
     * @return
     */
    public CloseableHttpClient createHttpClient(){
        return  HttpClients.custom().setConnectionManager(createPool()).build();
    }

    /**
     * 创建连接客户端
     * @return
     */
    @Bean
    public RestTemplate createRestTemplate(){
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(HttpClients.custom().setConnectionManager(createPool()).build());
        return new RestTemplate(requestFactory);
    }




}

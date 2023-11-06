package com.xiaolinzi.lostandfound;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@SpringBootTest
class LostandfoundApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public static void main(String[] args) throws Exception {

        String result = requestData("https://webapi.sms.mob.com/sms/verify",
                "appkey=381f810fc478e&phone=13794916353&zone=86&code=131157");
        System.out.println(result);
    }

    /**
     * 发起https 请求
     * @param address
     * @param
     * @return
     */
    public  static String requestData(String address ,String params){

        HttpURLConnection conn = null;
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                public X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());

            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // set params ;post params
            if (params!=null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();
            //get result
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                String result = parsRtn(conn.getInputStream());
                int len = -1;//初始值，起标志位作用
                byte buf[] = new byte[128];//缓冲区
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//捕获内存缓冲区的数据转换为字节数组
                while ((len=conn.getInputStream().read(buf))!=-1){//循环读取内容,将输入流的内容放进缓冲区中
                    baos.write(buf,0,len);//将缓冲区内容写进输出流，0是从起始偏移量，len是指定的字符个数
                }
                String result = new String(baos.toByteArray());//最终结果，将字节数组转换成字符串
//                String result = url.parsRtn(conn.getInputStream());
                return result;
            } else {
                System.out.println(conn.getResponseCode() + " "+ conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }
}

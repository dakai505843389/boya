package com.hd.clc.boya.common;

import com.alibaba.fastjson.JSONObject;
import com.hd.clc.boya.db.entity.PaySign;
import com.hd.clc.boya.vo.AccessToken;
import com.hd.clc.boya.vo.UserCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class WxUtil {

    //private static String appid = "wxfe0d2b4095047837";
    //private static String secret = "99895fbe4170ed1f03be2adb72ecef12";
    //小程序id
    private static final String appid = "wxd02f55b5d6326765";
    //小程序密钥
    private static final String secret = "eccf854d51c084e9530d3588b9b17777";
    //商户id
    private static final String mch_id = "1497705142";
    //支付密钥
    private static final String api_key = "Vf92RkQqZullmdWi7GgxXkqxgz92F3F2";
    //获取openid的url
    private static final String SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?";
    //获取access_token的url
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?";
    //获取二维码的url
    private static final String WXACODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    //微信统一下单接口地址
    private static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //交易类型，小程序支付的固定值为JSAPI
    private static final String TRADETYPE = "JSAPI";

    private static String access_token = null;
    private static long access_token_ExpirationTime;

    public static String getApi_key(){
        return api_key;
    }

    /**
     * 获取微信服务器支付签名
     * @param openid 用户openid
     * @param out_trade_no 支付订单标识
     * @param total_fee 支付金额
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> getWxPaySign(String openid, String out_trade_no, Integer total_fee, HttpServletRequest request) throws Exception {
        //随机字符串
        String nonce_str = StringUtil.getRandomStringByLength(32);
        //商品名称
        String body = "博雅学堂-课程";
        //获取客户端的ip地址
        String spbill_create_ip = IPUtil.getIp(request);
        //组装参数，用户生成统一下单接口的签名
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee.toString());
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", BaseVar.WX_PAY_NOTIFY_URL);
        packageParams.put("trade_type", TRADETYPE);
        packageParams.put("openid", openid);
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String prestr = StringUtil.createLinkString(packageParams);
        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mysign = StringUtil.sign(prestr, api_key, "utf-8").toUpperCase();
        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + appid + "</appid>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<mch_id>" + mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + BaseVar.WX_PAY_NOTIFY_URL + "</notify_url>"
                + "<openid>" + openid + "</openid>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                + "<total_fee>" + total_fee.toString() + "</total_fee>"
                + "<trade_type>" + TRADETYPE + "</trade_type>"
                + "<sign>" + mysign + "</sign>"
                + "</xml>";
        //调用统一下单接口，并接受返回的结果
        String result = httpRequest(pay_url, "POST", xml);
        // 将解析结果存储在HashMap中
        Map map = XmlUtil.doXMLParse(result);
        //返回状态码
        String return_code = (String) map.get("return_code");
        //返回给小程序端需要的参数
        Map<String, String> sign = new HashMap<String, String>();
        if("SUCCESS".equals(return_code)){
            //随机字符串
            sign.put("nonceStr", nonce_str);

            //返回的预付单信息
            String prepay_id = (String) map.get("prepay_id");
            sign.put("package", "prepay_id=" + prepay_id);

            //时间戳
            Long timeStamp = System.currentTimeMillis() / 1000;
            //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
            sign.put("timeStamp", timeStamp + "");

            //签名
            String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
            //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
            String paySign = StringUtil.sign(stringSignTemp, api_key, "utf-8").toUpperCase();
            sign.put("paySign", paySign);

            //appid
            sign.put("appid", appid);
            return sign;
        } else {
            return null;
        }
    }

    public static Map<String, String> getWxPaySignByRecombination(PaySign paySign) {
        Map<String, String> sign = new HashMap<String, String>();
        sign.put("nonceStr", paySign.getNonceStr());
        sign.put("package", paySign.getPrepay_id());
        sign.put("timeStamp", paySign.getTimeStamp());
        sign.put("paySign", paySign.getPaySign());
        sign.put("appid", appid);
        return sign;
    }

    /**
     * 获取二维码
     * @param sceneStr  扫码得到的字符串
     * @param wxPath    扫码打开的页面
     * @param pathName  二维码保存路径
     * @return
     */
    public static boolean getminiqrQr(String sceneStr, String wxPath, String pathName) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = WXACODE_URL + getAccess_token();
            Map<String,Object> param = new HashMap<>();
            param.put("scene", sceneStr);
            param.put("page", wxPath);
            param.put("width", 430);
            param.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", "0");
            line_color.put("g", "0");
            line_color.put("b", "0");
            param.put("line_color", line_color);
            JSONObject json = (JSONObject) JSONObject.toJSON(param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(json.toString(), headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            System.out.println(entity.getBody());
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            File file = new File(pathName);
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            return false;
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    /**
     * 获取微信小程序用户openid
     * @param code
     * @return
     */
    public static UserCode getUserCode(String code){
        String finalUrl = SESSION_URL + "appid=" + appid + "&secret=" + secret
                + "&js_code=" + code + "&grant_type=authorization_code";
        try {
            String result = doGet(finalUrl);
            JSONObject resJsonObject = JSONObject.parseObject(result);
            UserCode userCode = JSONObject.toJavaObject(resJsonObject, UserCode.class);
            return userCode;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取access_token
     * @return
     */
    public static String getAccess_token(){
        if (access_token == null || System.currentTimeMillis() > access_token_ExpirationTime){
            return getAccess_tokenFromWx().getAccess_token();
        }else {
            return access_token;
        }
    }

    /**
     * 从微信服务器获取access_token
     * @return
     */
    private static AccessToken getAccess_tokenFromWx(){
        String finalUrl = ACCESS_TOKEN_URL + "grant_type=client_credential"
                + "&appid=" + appid + "&secret=" + secret;
        try {
            String result = doGet(finalUrl);
            JSONObject resJsonObject = JSONObject.parseObject(result);
            AccessToken accessKoken = JSONObject.toJavaObject(resJsonObject, AccessToken.class);
            access_token_ExpirationTime = System.currentTimeMillis() + 7000;
            return accessKoken;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * GET方式请求服务器
     * @param url
     * @return
     * @throws Exception
     */
    private static String doGet(String url) throws Exception{
        java.net.URL localUrl = new URL(url);
        // 打开此URL的连接，并返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
        URLConnection connection = localUrl.openConnection();
        // 将URLConnection连接转为HttpURLConnetion连接
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        // 设置HttpURLConnection参数
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/text");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        // 若响应码大于300，则表示请求失败，抛出异常
        if (httpURLConnection.getResponseCode() > 300){
            throw new Exception("连接微信服务器失败，错误代码："+httpURLConnection.getResponseCode());
        }
        try {
            // 打开的连接读取的输入流
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            // 写入输入流
            while((tempLine = reader.readLine()) != null){
                resultBuffer.append(tempLine);
            }
        } finally {
            if (reader != null){
                reader.close();
            }
            if (inputStreamReader != null){
                inputStreamReader.close();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        // 返回该URL指定的资源
        return resultBuffer.toString();
    }

    /**
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法
     * @param outputStr 参数
     */
    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        // 创建SSLContext
        StringBuffer buffer = null;
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容
            if(null !=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }
            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

}

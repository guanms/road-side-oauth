package com.tingbei.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tingbei.common.constant.RequestParamType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
public class CommonUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper;
    private XmlMapper xmlMapper;

    /**
     * 初始化
     */
    @PostConstruct
    public void initCommonUtil(){
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        this.xmlMapper = new XmlMapper();
        this.xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 实体转Json
     * @param object 实体
     * @return json字符串
     * @throws JsonProcessingException 出现异常
     */
    public String object2Json(Object object) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(object);
    }

    /**
     * 实体转Xml
     * @param object 实体
     * @return xml字符串
     * @throws JsonProcessingException 出现异常
     */
    public String object2Xml(Object object) throws JsonProcessingException {
        return this.xmlMapper.writeValueAsString(object);
    }

    /**
     * xml转对象
     * @param xml xml字符串
     * @param clazz 实体类型
     * @param <T> 泛型
     * @return 转换的实体类
     * @throws IOException 出现异常
     */
    public<T> T xml2Object(String xml,Class<T> clazz) throws IOException {
        return this.xmlMapper.readValue(xml,clazz);
    }

    /**
     * xml转map
     * @param xml xml字符串
     * @param clazz 实体类型
     * @param <T> 泛型
     * @return map
     * @throws IOException 出现异常
     */
    public <T> Map<String,T> xml2Map(String xml, Class<T> clazz) throws IOException {
        return this.xmlMapper.readValue(xml, new TypeReference<HashMap<String,T>>(){});
    }

    /**
     * json转对象
     * @param json json字符串
     * @param clazz 实体类型
     * @param <T> 泛型
     * @return 转换的实体类
     * @throws IOException 出现异常
     */
    public <T> T json2Object(String json,Class<T> clazz) throws IOException{
        return this.objectMapper.readValue(json,clazz);
    }

    /**
     * 实体转Map
     * @param object 实体
     * @return 转换出的map
     * @throws IOException 出现异常
     */
    public Map<String,String> object2Map(Object object) throws IOException {
        return this.objectMapper.readValue(this.objectMapper.writeValueAsString(object), new TypeReference<HashMap<String,String>>() {});
    }

    /**
     * 日期转字符串
     * @return 格式化字符串
     */
    public String date2String(Date date,String formatString) throws Exception{
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatString);
    }

    /**
     * string转map
     * @param str string
     * @return 转除的map
     * @throws Exception 异常
     */
    public Map<String,Object> string2Map(String str) throws Exception{
        return this.objectMapper.readValue(str, new TypeReference<HashMap<String,Object>>() {});
    }


    /**
     * 字符串转16进制
     * @param source 源字符串
     * @return 十六进制表示
     */
    public String encodeHex(String source,Charset charset){
        String hexString = "0123456789ABCDEF";
        //根据默认编码获取字节数组
        byte[] bytes = source.getBytes(charset);
        StringBuilder sb = new StringBuilder(bytes.length*2);
        //将字节数组中每个字节拆解成2位16进制整数
        for (byte aByte : bytes) {
            sb.append(hexString.charAt((aByte & 0xf0) >> 4));
            sb.append(hexString.charAt((aByte & 0x0f)));
        }
        return sb.toString();
    }

    /**
     * 字节转字符串
     * @param digest 字节数组
     * @return 字符串
     */
    private String byte2String(byte[] digest) {
        StringBuilder str = new StringBuilder();
        for (byte aDigest : digest) {
            String tempStr = (Integer.toHexString(aDigest & 0xff));
            if (tempStr.length() == 1) {
                str.append("0").append(tempStr);
            } else {
                str.append(tempStr);
            }
        }
        return str.toString().toLowerCase();
    }

    /**
     * 生成UUID
     * @return UUID
     */
    public String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 通用httpRest客户端
     * @param clazz 返回值class对象
     * @param <T> 返回值类型
     * @return 调用结果
     * @throws IOException 出现异常
     */
    public<T> T rest(RestTemplate restTemplate, String url, HttpMethod httpMethod, RequestParamType requestParamType, Object param, Class<T> clazz) throws IOException {
        T result = null;
        HttpEntity httpEntity = null;
        if(requestParamType == RequestParamType.JSON){
            httpEntity = this.queryHttpEntity(this.object2Json(param), MediaType.APPLICATION_JSON);
        }else if(requestParamType == RequestParamType.XML){
            httpEntity = this.queryHttpEntity(this.object2Xml(param), MediaType.APPLICATION_XML);
        }

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            if(null != responseEntity.getBody()){
                if(requestParamType == RequestParamType.JSON){
                    result = this.json2Object(responseEntity.getBody(),clazz);
                }else if(requestParamType == RequestParamType.XML){
                    result = this.xml2Object(responseEntity.getBody(),clazz);
                }
            }
        }
        return result;
    }


    /**
     * 根据参数获取HttpEntity
     * @param param 参数
     * @return 调用结果
     */
    public HttpEntity queryHttpEntity(Object param, MediaType mediaType){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return new HttpEntity<>(param, headers);
    }

}

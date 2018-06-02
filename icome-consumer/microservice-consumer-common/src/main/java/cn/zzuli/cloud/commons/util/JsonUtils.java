package cn.zzuli.cloud.commons.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Json工具类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static String writeValueAsString(Object obj) {
        if (obj == null) {
            return null;
        }
        String result;
        try {
            result = new ObjectMapper().writeValueAsString(obj);
            return result;
        } catch (JsonProcessingException e) {
            logger.warn("JSON转换失败！obj:" + obj.toString(), e);
            return null;
        }
    }

    public static <T> T readObject(String jsonString, Class<T> valueType) {
        if (jsonString == null) {
            return null;
        }
        T result;
        try {
            result = new ObjectMapper().readValue(jsonString, valueType);
        } catch (IOException e) {
            logger.warn("JSON字符串解析报错！json:" + jsonString, e);
            result = null;
        }
        return result;
    }

    /**
     * @param jsonString
     * @param elementClasses
     * @return
     */
    public static <T> List<T> readArray(String jsonString, Class<T> elementClasses) {
        if (jsonString == null) {
            return null;
        }
        List<T> result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class,
                    elementClasses);
            result = mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("JSON字符串解析报错！json:" + jsonString, e);
            result = null;
        }
        return result;
    }

    public static String readAsStringExcept(Object obj,String... properties){

        if(null == obj){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        FilterProvider filters = new SimpleFilterProvider().addFilter("dynamicFilter", SimpleBeanPropertyFilter.serializeAllExcept(properties));
        mapper.setFilterProvider(filters);
        if(obj instanceof List){
            mapper.addMixIn(((List) obj).get(0).getClass(),DynamicFilter.class);
        }else{
            mapper.addMixIn(obj.getClass(),DynamicFilter.class);
        }

        String content;
        try {
            content = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            logger.error("对象转换JSON字符串报错！"+obj.getClass().getName(),ex);
            content = null;
        }
        return content;
    }

    public static JsonNode readTree(String content){
        if(null == content){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(content);
        } catch (IOException ex) {
            logger.error("Json字符串转换为树形结构报错！json:" + content,ex);
            return null;
        }
    }
    @JsonFilter("dynamicFilter")
    private interface DynamicFilter{}

}

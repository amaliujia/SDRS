package com.cxyapi.oo;

/**
 * Created with IntelliJ IDEA.
 * User: skydragon
 * Date: 13-9-27
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */
import java.util.HashMap;
import java.util.Map;

/** 类型识别工具
 * @author cxy @ www.cxyapi.com
 */
public class TypeTools
{
    //获得类型
    public static Map<String,String> getType(Object o)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", o.getClass().getSimpleName());
        typeInfo.put("描述", "引用类型");
        return typeInfo;
    }

    public static Map<String,String> getType(int i)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", "int");
        typeInfo.put("描述", "整形");
        return typeInfo;
    }

    public static Map<String,String> getType(long l)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", "long");
        typeInfo.put("描述", "长整型");
        return typeInfo;
    }

    public static Map<String,String> getType(boolean b)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", "boolean");
        typeInfo.put("描述", "布尔类型");
        return typeInfo;
    }

    public static Map<String,String> getType(char b)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", "char");
        typeInfo.put("描述", "字符");
        return typeInfo;
    }

    public static Map<String,String> getType(float f)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", "float");
        typeInfo.put("描述", "单精度浮点型");
        return typeInfo;
    }

    public static Map<String,String> getType(double d)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", "double");
        typeInfo.put("描述", "双精度浮点型");
        return typeInfo;
    }

    public static Map<String,String> getType(String s)
    {
        Map<String,String> typeInfo=new HashMap<String,String>();
        typeInfo.put("类型", "String");
        typeInfo.put("描述", "字符串类型");
        return typeInfo;
    }

}

package com.itheima.factory;

import jdk.internal.dynalink.beans.StaticClass;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {



    private static Map<String,Object> map = new HashMap<>();
    static {
        try {
            //1.解析xml根据传入的id获取对应标签的class属性的值(类的全限定名)
            SAXReader reader = new SAXReader();
            //将配置文件转换成流
            InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
            Document document = reader.read(is);

            //获取所有的bean标签，并且获取bean标签中的id和class属性值
            List<Element> beans = document.selectNodes("//bean");
            //遍历出每一个bean标签
            for (Element bean : beans) {
                //获取该标签的id属性值和class属性值
                String id = bean.attributeValue("id");
                String className = bean.attributeValue("class");
                //根据类的全限定名使用反射创建对象
                Object obj = Class.forName(className).newInstance();

                //将id作为key，obj作为value存放到map中
                map.put(id,obj);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    public Object getBean(String id){

        return  map.get(id);

    }
}

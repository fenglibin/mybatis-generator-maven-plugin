package com.eeeffff.generator;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * YamlUtil.
 *
 * @author: fenglibin
 */
public class YamlUtil {
    private static Yaml yaml;

    private Map<String,Object> yamlMap;


    public YamlUtil(List<String> filePath)  {
        yaml = new Yaml();
        yamlMap = new HashMap<String,Object>();
        try {
            for (String path : filePath) {
                System.out.println(path);
                Map<String,Object> map =  yaml.loadAs(new FileInputStream(path),Map.class);
                yamlMap.putAll(map);
            }
        }catch (Exception ex){

        }

    }

    public YamlUtil(String filePath)  {
        yaml = new Yaml();
        yamlMap = new HashMap<String,Object>();
        try {
                System.out.println(filePath);
                yamlMap =  yaml.loadAs(new FileInputStream(filePath),Map.class);
        }catch (Exception ex){

        }

    }

    public  String getKey(String key){

        String[]  strarray =  key.split("\\.");

        Map<String,Object> result = new HashMap<String,Object>();
        String val ="";

        for (int i = 0; i < strarray.length; i++){

            if(i==0){
                result = (Map<String,Object>)yamlMap.get(strarray[i]);
            } else if(i==strarray.length-1){
                val = result.get(strarray[i]).toString();
            }else {
                result = (Map<String,Object>)result.get(strarray[i]);
            }

        }


        return val;

    }

}


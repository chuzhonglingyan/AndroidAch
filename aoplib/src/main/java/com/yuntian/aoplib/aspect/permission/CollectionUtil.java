package com.yuntian.aoplib.aspect.permission;

import java.util.List;

/**
 * @author chulingyan
 * @time 2019/01/03 21:47
 * @describe
 */
public class CollectionUtil {


    public static String[]  listToArray(List<String> list){
        String[] strings = new String[list.size()];
        return list.toArray(strings);
    }

}

package com.zhy.submit.teacher.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID(){
        UUID id=UUID.randomUUID();
        String [] idd=id.toString().split("-");
        return idd[0];
    }
}

package com.example.ebtesam.educationexchange.Utils;

/**
 * Created by ebtesam on 07/02/2018 AD.
 */

public class StringManipulation {

    public static String expandUsername(String username){
        return username.replace(".", " ");
    }

    public static String condenseUsername(String username){
        return username.replace(" " , ".");
    }
}

package br.com.nexus.plugin.util;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListUtil {

    public String convertArrayListInString(ArrayList<String> listArraylist) {
        if(listArraylist.isEmpty()) return "";
        String listString = listArraylist.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(listString);
        return stringBuilder.substring(1, stringBuilder.length()-1);
    }

    public ArrayList<String> convertStringInArrayList(String stringList) {
        if(stringList.isEmpty()) return new ArrayList<>();
        String[] splint = stringList.split(", ");
        return new ArrayList<>(Arrays.asList(splint));
    }

}

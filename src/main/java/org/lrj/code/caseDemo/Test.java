package org.lrj.code.caseDemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        StringBuffer b = new StringBuffer("china chinachina");
        int index = b.lastIndexOf("china");
        System.out.println(index);
        b.replace(index , b.length(),"");
        System.out.println(b);

    }
}

package org.jboss.aerogear.controller.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    public static String decapitalize(String name) {
        if (name.length() == 1) {
            return name.toLowerCase();
        }
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    public static String downCaseFirst(String name) {
        // common case: SomeClass -> someClass
        if (name.length() > 1 && Character.isLowerCase(name.charAt(1))) {
            return decapitalize(name);
        }

        // different case: URLClassLoader -> urlClassLoader
        for (int i = 1; i < name.length(); i++) {
            if (Character.isLowerCase(name.charAt(i))) {
                return name.substring(0, i - 1).toLowerCase() + name.substring(i - 1, name.length());
            }
        }

        // all uppercase: URL -> url
        return name.toLowerCase();
    }

}

package org.jboss.aerogear.controller.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;

/**
 * Taken with small modifications
 * <p/>
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
public class TypeNameExtractor {
    public String nameFor(Type generic) {
        if (generic instanceof ParameterizedType) {
            return nameFor((ParameterizedType) generic);
        }

        if (generic instanceof WildcardType) {
            return nameFor((WildcardType) generic);
        }

        if (generic instanceof TypeVariable<?>) {
            return nameFor(((TypeVariable<?>) generic));
        }

        return nameFor((Class<?>) generic);
    }

    private String nameFor(Class<?> raw) {
        if (raw.isArray()) {
            return nameFor(raw.getComponentType()) + "List";
        }

        String name = raw.getSimpleName();

        return downCaseFirst(name);
    }

    private String nameFor(TypeVariable<?> variable) {
        return downCaseFirst(variable.getName());
    }

    private String nameFor(WildcardType wild) {
        if ((wild.getLowerBounds().length != 0)) {
            return nameFor(wild.getLowerBounds()[0]);
        } else {
            return nameFor(wild.getUpperBounds()[0]);
        }
    }

    private String nameFor(ParameterizedType type) {
        Class<?> raw = (Class<?>) type.getRawType();
        if (Collection.class.isAssignableFrom(raw)) {
            return nameFor(type.getActualTypeArguments()[0]) + "List";
        }
        return nameFor(raw);
    }

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

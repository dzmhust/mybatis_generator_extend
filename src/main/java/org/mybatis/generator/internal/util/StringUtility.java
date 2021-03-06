/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.internal.util;

import java.util.StringTokenizer;

/**
 * 1.增加将下划线型 转 驼峰型
 * @author Jeff Butler
 */
public class StringUtility {

    /**
     * Utility class. No instances allowed
     */
    private StringUtility() {
        super();
    }

    public static boolean stringHasValue(String s) {
        return s != null && s.length() > 0;
    }
    
    private static final String UNDERLINE = "_";
    
    /**
     * 下划线型 转 驼峰型
     * @author dzm
     * @param param
     * @return
     */
    public static String underlineToCame(String param){
        String[] strs = param.split(UNDERLINE);
        StringBuilder came = new StringBuilder();
        if (strs!=null && strs.length>0){
            for (int i=0; i<strs.length; i++){
                if (i == 0){
                    came.append(strs[i]);
                } else{
                    came.append(toUpperCaseFirstOne(strs[i]));
                }
            }
        }
        return came.toString();
    }
    
    /**
     * 首字母转大写
     * @author dzm
     * @param str 待处理的字符串
     * @return 首字母大写的字符串
     */
    public static String toUpperCaseFirstOne(String str) {
      if (Character.isUpperCase(str.charAt(0))) {
        return str ;
      } else {
        return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0)))
            .append(str.substring(1)).toString() ;
      }
    }

    public static String composeFullyQualifiedTableName(String catalog,
            String schema, String tableName, char separator) {
        StringBuilder sb = new StringBuilder();

        if (stringHasValue(catalog)) {
            sb.append(catalog);
            sb.append(separator);
        }

        if (stringHasValue(schema)) {
            sb.append(schema);
            sb.append(separator);
        } else {
            if (sb.length() > 0) {
                sb.append(separator);
            }
        }

        sb.append(tableName);

        return sb.toString();
    }

    public static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }

    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static String escapeStringForXml(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("&quot;"); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static boolean isTrue(String s) {
        return "true".equalsIgnoreCase(s); //$NON-NLS-1$
    }

    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }
}

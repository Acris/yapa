package com.reorz.yapa.utils;

import com.reorz.yapa.annotations.Delete;
import com.reorz.yapa.annotations.Insert;
import com.reorz.yapa.annotations.Select;
import com.reorz.yapa.annotations.Update;
import com.reorz.yapa.enums.SqlCommandType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * SQL命令工具类
 *
 * @author Acris
 */
public class SqlCommandUtils {
    private static final Set<Class<? extends Annotation>> ANNOTATION_TYPES = new HashSet<>();

    static {
        ANNOTATION_TYPES.add(Select.class);
        ANNOTATION_TYPES.add(Insert.class);
        ANNOTATION_TYPES.add(Update.class);
        ANNOTATION_TYPES.add(Delete.class);
    }

    /**
     * 获取要执行的SQL类型
     *
     * @param method 方法对象
     * @return SQL类型枚举
     * @see SqlCommandType
     */
    public static SqlCommandType getSqlCommandType(Method method) {
        Class<? extends Annotation> type = null;
        for (Class<? extends Annotation> annotationType : ANNOTATION_TYPES) {
            Annotation annotation = method.getAnnotation(annotationType);
            if (annotation != null) {
                type = annotationType;
                break;
            }
        }
        if (type == null) {
            return SqlCommandType.UNKNOWN;
        }
        return SqlCommandType.valueOf(type.getSimpleName().toUpperCase());
    }
}

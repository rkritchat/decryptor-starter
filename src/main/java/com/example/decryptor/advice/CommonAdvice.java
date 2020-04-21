package com.example.decryptor.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class CommonAdvice {

    private static final String GET = "get";
    private static final String SET = "set";

    @Around("@annotation(com.example.decryptor.advice.DecryptValue)")
    public Object decrypt(ProceedingJoinPoint point) throws Throwable {
        try {
            Object[] args = point.getArgs();
            decrypt(args[0]);
        } catch (Exception e) {
            log.error("Exception occurred while decrypt",e);
        }
        return point.proceed();
    }

    private void decrypt(Object arg) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> aClass = arg.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for(Field f : fields){
            Class<?> type = f.getType();
            /*
             * for detect model inside model
             */
            if (type.getClassLoader() != null) {
                Method method = aClass.getMethod(GET + upperCaseFirstCharacter(f.getName()));
                decrypt(method.invoke(arg));
            } else {
                Encrypt annotation = f.getAnnotation(Encrypt.class);
                if (annotation != null) {
                    Object field = getter(f, arg.getClass(), arg);
                    log.info(String.valueOf(field));
                    if (field instanceof String) {
                        String data = (String) field;
                        data = data + "-----";
                        setter(f, arg.getClass(), arg, data);
                    }
                }
            }
        }
    }


    private Object getter(Field field, Class<?> o, Object object){
        for (Method method : o.getMethods()){
            if ((method.getName().startsWith(GET)) && method.getName().toLowerCase().endsWith(field.getName().toLowerCase())){
                try{
                    return method.invoke(object);
                }catch (Exception e) {
                    log.error("Could not determine method {}",  method.getName(), e);
                }
             }
         }
        return null;
    }

    private void setter(Field field, Class<?> o, Object object, String value){
        for (Method method : o.getMethods()) {
            if ((method.getName().startsWith(SET)) &&method.getName().toLowerCase().endsWith(field.getName().toLowerCase())){
                try{
                     method.invoke(object, value);
                }catch (Exception e) {
                    log.error("Could not determine method {}",  method.getName(), e);
                }
            }
        }
    }

    private String upperCaseFirstCharacter(String input){
        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1);
    }

}

package tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {

	public static Class getSuperClassGenricType(Class clazz,int index){
		
		Type genType = clazz.getGenericSuperclass();
		if(!(genType  instanceof ParameterizedType))
			return Object.class;
		
		Type [] params = ((ParameterizedType)genType).getActualTypeArguments();
		
		if(index >= params.length || index < 0)
			return Object.class;
		
		if(!(params[index] instanceof Object))
			return Object.class;
		
		return (Class)params[index];
	}
	
	public static <T> Class<T> getSupperGenricType(Class clazz){
		return getSuperClassGenricType(clazz,0);
	}
	
	public static Method getDeclaredMethod (Object object,String methodName, Class<?> parameterTypes){
		for(Class<?> supperClass=object.getClass();supperClass!= Object.class;supperClass = supperClass.getClass()){
			try {
				return supperClass.getMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
			}
		}
		return null;
	}
	
	public static void makeAccessible(Field field){
		if(!Modifier.isPublic(field.getModifiers())){
			field.setAccessible(true);	
		}
	}
	
	public static Field getDeclaredField(Object object, String FieldName){
		for(Class<?> supperClass = object.getClass();supperClass != Object.class;supperClass = supperClass.getClass()){
			try {
				return supperClass.getDeclaredField(FieldName);
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
			}
		}
		return null;
	}
	public static Object invokeMethod(Object object,String methodName,Class<?> parameterTypes,Object[] params){
		Method method = getDeclaredMethod(object,methodName,parameterTypes);
		if(method == null)
			throw new IllegalArgumentException("could not find method ["+methodName+"] on target["+object +"]");
		method.setAccessible(true);
		
		try {
			return method.invoke(object, params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("no possible to throw this exception");
		}
		return null;
	}
	
	public static void setFieldValue (Object object,String fieldName,Object value){
		Field field = getDeclaredField(object,fieldName);
		if(field == null)
			throw new IllegalArgumentException("could not find method ["+fieldName+"] on target["+object +"]");
		field.setAccessible(true);
		
		try {
			field.set(object, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.println("no possible to throw this exception");
		}
	}
	public static Object getFieldValue (Object object,String fieldName){
		Field field = getDeclaredField(object, fieldName);
		if(field == null)
			throw new IllegalArgumentException("could not find method ["+fieldName+"] on target["+object +"]");
		
		field.setAccessible(true);
		Object result = null;
		try {
			 return result = field.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.println("no possible to throw this exception");
		}
		return result;
		
	}
}

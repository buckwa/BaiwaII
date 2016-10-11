package com.buckwa.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BeanUtils extends org.springframework.beans.BeanUtils {

	public static String getBeanString(Object bean) {
		if (bean == null)
			return null;
		PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(bean.getClass());
		StringBuilder sb = new StringBuilder(bean.getClass().getName() + " = [");
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String attributeName = propertyDescriptor.getName();
			if (attributeName.equals("class"))
				continue;
			String value = null;
			try {
				value = ""
						+ propertyDescriptor.getReadMethod().invoke(bean,
								(Object[]) null);
			} catch (Exception e) {/* e.printStackTrace(); */
			}
			sb.append(attributeName + " = " + value + ", ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static BeanUtils getInstance() {
		return new BeanUtils();
	}
	/*
	 * For sorting List of bean by given property (support only property
	 * level1).
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> sort(Collection<T> inputCol, String[] fieldNames,
			boolean isAscending) {
		if (inputCol == null) {
			return null;
		}
		if (inputCol.size() == 0) {
			return new ArrayList<T>();
		}
		Iterator<T> inputIt = inputCol.iterator();
		Class<? extends Object> instanceCl = inputIt.next().getClass();
		List<T> inputL = new ArrayList<T>(inputCol);
		Collections.sort(inputL, new ReflectiveComparator(instanceCl,
				fieldNames, isAscending));
		return inputL;
	}

	@SuppressWarnings("unchecked")
	public static class ReflectiveComparator implements Comparator {
		private static Object[] emptyParam = new Object[0];
		private boolean isAscending;
		@SuppressWarnings("unused")
		private Class<?> instanceCl;
		private Method[] getterMethod;

		/**
		 * Constructor for ReflectiveComparator.
		 */
		public ReflectiveComparator(Class<?> instanceCl, String[] attrNames,
				boolean isAscending) {
			this.instanceCl = instanceCl;
			this.getterMethod = new Method[attrNames.length];
			this.isAscending = isAscending;
			try {
				for (int methodIdx = 0; methodIdx < attrNames.length; methodIdx++) {
					String attrName = attrNames[methodIdx];
					this.getterMethod[methodIdx] = instanceCl.getMethod("get"
							+ Character.toUpperCase(attrName.charAt(0))
							+ ((attrName.length() > 1) ? attrName.substring(1)
									: ""), new Class[0]);
				}

			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * @see java.util.Comparator#compare(Object, Object)
		 */
		public int compare(Object o1, Object o2) {
			try {
				for (int methodIdx = 0; methodIdx < getterMethod.length; methodIdx++) {
					Object obj1 = getterMethod[methodIdx]
							.invoke(o1, emptyParam);
					Object obj2 = getterMethod[methodIdx]
							.invoke(o2, emptyParam);
					if (obj1 == null && obj2 == null) {
						// comment the "return 0;" statement because the process
						// terminate
						// when meet the property which return null and skip
						// other fields
						// return 0;
					} else if (obj1 == null && obj2 != null) {

						return (isAscending ? -1 : +1);
					} else if (obj1 != null && obj2 == null) {
						return (isAscending ? +1 : -1);
					} else {
						int result = 0;
						if (obj1 instanceof String) {
							result = ((String) obj1).compareTo((String) obj2);
						} else if (obj1 instanceof Character) {
							result = ((Character) obj1)
									.compareTo((Character) obj2);
						} else if (obj1 instanceof Integer) {
							result = ((Integer) obj1).compareTo((Integer) obj2);
						} else if (obj1 instanceof Long) {
							result = ((Long) obj1).compareTo((Long) obj2);
						} else if (obj1 instanceof Float) {
							result = ((Float) obj1).compareTo((Float) obj2);
						} else if (obj1 instanceof Double) {
							result = ((Double) obj1).compareTo((Double) obj2);
						} else if (obj1 instanceof java.util.Date) {
							result = ((java.util.Date) obj1)
									.compareTo((java.util.Date) obj2);
						} else if (obj1 instanceof java.math.BigDecimal) {
							result = ((java.math.BigDecimal) obj1)
									.compareTo((java.math.BigDecimal) obj2);
						}

						if (result != 0) {
							return (isAscending ? result : -result);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return 0;
		}

	}
	

	public static boolean isEmpty(String st){
		boolean b = true;
		if(st!=null && st.trim().length()>0){
			b = false;
		}
		return (b);

	}

	public static boolean isEmpty(StringBuilder st){
		boolean b = true;
		if(st!=null && st.toString().trim().length()>0){
			b = false;
		}
		return (b);

	}

	public static boolean isEmpty(List<?> ls ){
		boolean b = true;
		if(ls!=null && !ls.isEmpty()){
			b = false;
		}
		return (b);

	}

	public static boolean isEmpty(Map<?, ?> map){
		boolean b = true;
		if(map!=null && !map.isEmpty()){
			b = false;
		}
		return(b);
	}

	public static boolean isEmpty(Integer integer){
		boolean b = isNull(integer);
		return (b);

	}

	public static boolean isEmpty(Byte byt){
		boolean b = isNull(byt);
		return (b);

	}

	public static boolean isEmpty(Short s){
		boolean b = isNull(s);
		return (b);

	}

	public static boolean isEmpty(Long l){
		boolean b = isNull(l);
		return (b);

	}

	public static boolean isEmpty(Character c){
		boolean b = isNull(c);
		return (b);

	}

	public static boolean isEmpty(Float f){
		boolean b = isNull(f);
		return (b);

	}

	public static boolean isEmpty(Double d){
		boolean b = isNull(d);
		return (b);

	}

	public static boolean isEmpty(Number num){
		boolean b = isNull(num);
		return (b);

	}

	public static boolean isEmpty(BigDecimal d){
		boolean b = isNull(d);
		return (b);

	}

	public static boolean isNull(Object obj){
		boolean b = true;
		if(obj != null){
			b = false;
		}
		return (b);
	}

	public static Object getDefaultValueIfNull(Object value, Object defaultValue){
		Object result = defaultValue;
		if(value != null){
			result = value;
		}
		return(result);
	}

	public static boolean isNotEmpty(String st){
		boolean b = true;
		if(st ==null || st.trim().length()==0){
			b = false;
		}
		return (b);

	}

	public static boolean isNotEmpty(List<?> ls ){
		boolean b = false;
		if(ls!=null && !ls.isEmpty()){
			b = true;
		}
		return (b);

	}

	public static boolean isNotEmpty(Map<?,?> map){
		boolean b = false;
		if(map!=null && !map.isEmpty()){
			b = true;
		}
		return(b);
	}

	public static boolean isNotEmpty(Integer integer){
		boolean b = isNotNull(integer);
		return (b);

	}

	public static boolean isNotEmpty(Byte byt){
		boolean b = isNotNull(byt);
		return (b);

	}

	public static boolean isNotEmpty(Short s){
		boolean b = isNotNull(s);
		return (b);

	}

	public static boolean isNotEmpty(Long l){
		boolean b = isNotNull(l);
		return (b);

	}

	public static boolean isNotEmpty(Character c){
		boolean b = isNotNull(c);
		return (b);

	}

	public static boolean isNotEmpty(Float f){
		boolean b = isNotNull(f);
		return (b);

	}

	public static boolean isNotEmpty(Double d){
		boolean b = isNotNull(d);
		return (b);

	}

	public static boolean isNotEmpty(Number num){
		boolean b = isNotNull(num);
		return (b);

	}

	public static boolean isNotEmpty(BigDecimal d){
		boolean b = isNotNull(d);
		return (b);

	}

	public static boolean isNotEmpty(Object obj){
		boolean b = false;
		if(obj != null){
			b = true;
		}
		return (b);
	}

	public static boolean isNotNull(Object obj){
		boolean b = false;
		if(obj != null){
			b = true;
		}
		return (b);
	}
	
}

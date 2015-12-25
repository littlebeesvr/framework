/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package leap.lang.reflect;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.WeakHashMap;

import leap.lang.Args;

public class ReflectEnum {
	
	private static final String VALUE_FIELD_NAME = "value";

	private static final Map<Class<?>, ReflectEnum> cache = 
		java.util.Collections.synchronizedMap(new WeakHashMap<Class<?>, ReflectEnum>());
	
	public static ReflectEnum of(Class<?> enumType){
		Args.notNull(enumType,"enumType");
		Args.assertTrue(enumType.isEnum(),"'" + enumType.getName() + "' is not an enum type");
		
		ReflectEnum reflectEnum = cache.get(enumType);
		
		if(null == reflectEnum){
			reflectEnum = new ReflectEnum(enumType);
			cache.put(enumType, reflectEnum);
		}
		
		return reflectEnum;
	}
	
	private final ReflectClass reflectiveClass;
	private ReflectField 	  valueField;
	private Class<?>        	  valueType;
	private boolean				  hasValueField;
	private Field[]				  enumConstantFields;
	
	protected ReflectEnum(Class<?> enumClass){
		this.reflectiveClass = ReflectClass.of(enumClass);
		this.initialize();
	}
	
	public boolean isValued(){
		return hasValueField;
	}
	
	public Class<?> getValueType(){
		return valueType;
	}
	
	public Object getValue(Object enumConstant) {
		return null != valueField ? valueField.getValue(enumConstant) : enumConstant.toString();	
	}
	
	public Enum<?>[] getEnumConstants(){
		return (Enum<?>[])reflectiveClass.getReflectedClass().getEnumConstants();
	}
	
	public Field[] getEnumConstantFields(){
		if(null == enumConstantFields){
			Enum<?>[] enums = (Enum<?>[])reflectiveClass.getReflectedClass().getEnumConstants();
			
			enumConstantFields = new Field[enums.length];
			
			for(int i=0;i<enums.length;i++){
				enumConstantFields[i] = reflectiveClass.getField(enums[i].name()).getReflectedField();
			}
		}
		return enumConstantFields;
	}
	
	private void initialize(){
		this.valueField    = reflectiveClass.getField(VALUE_FIELD_NAME);
		this.hasValueField = null != valueField;
		this.valueType     = hasValueField ? valueField.getType() : null;
	}
}
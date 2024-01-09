package com.softserve.edu.sprint4.task7;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

class ClassForAnnot {
	@CamelCase
	public static void example() {
	}

	@CamelCase
	public void Example() {
	}

	public static void _main(String args[]) {
	}
}

class Class1 {
	@CamelCase
	public void correct() {
	}

	@CamelCase
	public void InCorrect() {
	}

	@CamelCase
	public void JustMethod() {
	}
}

class Class2 {
	@CamelCase
	public void correct() {
	}

	@CamelCase
	public void oneMoreCorrect() {
	}
}

public class CheckCamelCase {
	public static final String CAMELCASE_PATTERN = "^[a-z]+[a-zA-Z0-9]*$";

	public static boolean checkAndPrint(Class<?> clazz) {
		boolean result = true;
		Pattern pattern = Pattern.compile(CAMELCASE_PATTERN);
		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {
			if (method.isAnnotationPresent(CamelCase.class)) {
				if (!pattern.matcher(method.getName()).matches()) {
					System.out.printf("method %s.%s doesn't satisfy camelCase naming convention.%n",
							clazz.getSimpleName(), method.getName());
					result = false;
				}
			}
		}

		return result;
	}

	// constants CAMELCASE_PATTERN and ERROR_MESSAGE_TEMPLATE

	// checkAndPrint(...) method

}

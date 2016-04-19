package com.websystique.spring;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.BaseEntity;
import com.websystique.spring.service.BaseService;

public class AppMain {

	public static void main(String args[]) throws Exception {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		BaseService baseService = (BaseService) context.getBean("baseService");

		try {
			GenerateSourceUsingCodeModel codeModel = new GenerateSourceUsingCodeModel();
			ReadExcelDemo excelDemo = new ReadExcelDemo();
			DynamicClass dynamicClass = excelDemo.readModel();
			codeModel.setDynamicClass(dynamicClass);
			codeModel.generateSource();
			BlogixCompiler blogixCompiler = new BlogixCompiler();
			blogixCompiler.setSourceDir(new File("src/main/java/com/websystique/spring/model/generated"));
			File clsDir = new File("target/classes/com/websystique/spring/model/generated");
			blogixCompiler.setClassesDir(clsDir);
			blogixCompiler.compile();
			URL url = clsDir.toURL();
			URL[] urls = new URL[] { url };
			ClassLoader loader = new URLClassLoader(urls);
			Class thisClass = loader.loadClass("com.websystique.spring.model.generated." + dynamicClass.getName());

			List<Row> dataRows = excelDemo.getDataRows();
			BaseEntity instance = null;
			for (Row row : dataRows) {
				instance = (BaseEntity) thisClass.newInstance();
				Iterator<Cell> cellIterator = row.cellIterator();
				for (Field field : dynamicClass.getFields()) {
					Method thisMethod = thisClass.getDeclaredMethod("set" + toProperCase(field.getName()),
							String.class);
					System.out.println(thisMethod);
					if (cellIterator.hasNext()) {
						Cell columnCell = cellIterator.next();
						thisMethod.invoke(instance, columnCell.getStringCellValue());
					}
				}
				baseService.saveBaseEntity(instance);
			}

		} catch (JClassAlreadyExistsException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		context.close();
	}

	public static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}

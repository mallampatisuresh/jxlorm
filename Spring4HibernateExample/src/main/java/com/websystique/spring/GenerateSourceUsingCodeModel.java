package com.websystique.spring;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Locale;

import javax.persistence.Entity;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.websystique.spring.model.BaseEntity;

public class GenerateSourceUsingCodeModel {

	public static final String PACKAGE_NAME = "com.websystique.spring.model.generated";
	public static final String PACKAGE_SEPERATOR = ".";
	private static String classOutputFolder = "C:/Users/Mallampati Suresh/Desktop/Spring4HibernateExample/target/classes";

	private DynamicClass dynamicClass;

	public DynamicClass getDynamicClass() {
		return dynamicClass;
	}

	public void setDynamicClass(DynamicClass dynamicClass) {
		this.dynamicClass = dynamicClass;
	}

	public static void main(String... args) throws URISyntaxException {
		try {
			GenerateSourceUsingCodeModel codeModel = new GenerateSourceUsingCodeModel();
			ReadExcelDemo excelDemo = new ReadExcelDemo();
			DynamicClass dynamicClass = excelDemo.readModel();
			codeModel.setDynamicClass(dynamicClass);
			codeModel.generateSource();

		} catch (JClassAlreadyExistsException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void generateSource() throws JClassAlreadyExistsException, IOException {
		// Instantiate an instance of the JCodeModel class
		JCodeModel codeModel = new JCodeModel();

		// JDefinedClass will let you create a class in a specified package.
		JDefinedClass classToBeCreated = codeModel._class(PACKAGE_NAME + PACKAGE_SEPERATOR + dynamicClass.getName());

		classToBeCreated._extends(BaseEntity.class);

		classToBeCreated.annotate(Entity.class);

		for (Field field : dynamicClass.getFields()) {
			JFieldVar field1 = classToBeCreated.field(JMod.PRIVATE, field.getType(), field.getName());

			// Create getter and setter methods for the fields
			JMethod field1GetterMethod = classToBeCreated.method(JMod.PUBLIC, field1.type(),
					"get" + GenerateSourceUsingCodeModel.toProperCase(field.getName()));
			// code to create a return statement with the field1
			field1GetterMethod.body()._return(field1);
			JMethod field1SetterMethod = classToBeCreated.method(JMod.PUBLIC, codeModel.VOID, "set" + field.getName());
			// code to create an input parameter to the setter method which will
			// take a variable of type field1
			field1SetterMethod.param(field1.type(),
					"input" + GenerateSourceUsingCodeModel.toProperCase(field.getName()));
			// code to create an assignment statement to assign the input
			// argument
			// to the field1
			field1SetterMethod.body().assign(JExpr._this().ref(field.getName()),
					JExpr.ref("input" + GenerateSourceUsingCodeModel.toProperCase(field.getName())));
		}

		// This will generate the code in the specified file path.
		codeModel.build(new File("src/main/java"));

	}

	/** compile your files by JavaCompiler */
	public static void compile(Iterable<? extends JavaFileObject> files) {
		// get system compiler:
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// for compilation diagnostic message processing on compilation
		// WARNING/ERROR
		MyDiagnosticListener c = new MyDiagnosticListener();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(c, Locale.ENGLISH, null);
		// specify classes output folder
		Iterable options = Arrays.asList("-d", classOutputFolder);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, c, options, null, files);
		Boolean result = task.call();
		if (result == true) {
			System.out.println("Succeeded");
		}
	}

	public static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
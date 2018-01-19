package com.kovalenko.coursework;

import com.kovalenko.coursework.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseworkApplication extends AbstractJavaFxApplicationSupport{

	public static void main(String[] args) {
		SpringApplication.run(CourseworkApplication.class, args);
		launchApp(CourseworkApplication.class, MainView.class, args);
	}
}

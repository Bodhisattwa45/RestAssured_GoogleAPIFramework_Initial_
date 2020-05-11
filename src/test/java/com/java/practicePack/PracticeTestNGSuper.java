package com.java.practicePack;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class PracticeTestNGSuper {
	
	@BeforeSuite
	public static void beforeSuite()
	{
		System.out.println("PracticeTestNGSuper");
		System.out.println("this method executes before all tests in suite");
	}
	
	@AfterSuite
	public static void afterSuite()
	{
		System.out.println("PracticeTestNGSuper");
		System.out.println("this method executes after all tests in suite");
	}

}

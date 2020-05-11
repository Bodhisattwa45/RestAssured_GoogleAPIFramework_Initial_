package com.java.practicePack;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PracticeTestNGSubClass extends PracticeTestNGSuper{
	
	@Test(groups= {"sanity","functional"})
	public void loginTest()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this is login test");
	}
	
	@Test(groups= "functional")
	public void userRegistrationTest()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this is user registration test");
	}
	@BeforeTest
	public void beforeTest()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this method executes before any tests in the classes under test tag");
	}
	
	@AfterTest
	public void afterTest()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this method executes after all tests in the classes under test tag");
	}
	
	@BeforeClass
	public void beforeClass()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this method executes before any tests in the current class");
	}
	
	@AfterClass
	public void afterClass()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this method executes after all tests in the current class");
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this method executes everytime before a test");
	}
	
	@AfterMethod
	public void afterMethod()
	{
		System.out.println("PracticeTestNGSubClass");
		System.out.println("this method executes everytime after a test");
	}
	
}

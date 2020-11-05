package com.datadriven.framework.test.LoginTest;

import java.io.IOException;

import org.testng.annotations.Test;

import com.datadriven.framework.base.BaseUI;

public class loginTest extends BaseUI {

	@Test
	public void testOne() throws InterruptedException, IOException {
		invokeBrowser("chrome");
		openURL("websiteURL");
		elementClick("cab");
		elementClick("oneway");
		enterlocation("from","New Delhi");
		enterlocation("to", "Manali");
		elementClick("select_date");
		elementscrollclick("date");
		elementClick("time");
		elementClick("search");
		multiclick("more-click");
		xlsx("cartype","carrent");
		quitBrowser();

	}

}

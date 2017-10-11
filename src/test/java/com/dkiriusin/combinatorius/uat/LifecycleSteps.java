/**
 * Copyright (c) 2015 denis.kiriushin@gmail.com. All rights reserved.
 *
 * @author Denis Kiriusin
 */

package com.dkiriusin.combinatorius.uat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.web.selenium.PerStoriesWebDriverSteps;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriverException;

import com.dkiriusin.combinatorius.CombinatoriusServlet;
import com.dkiriusin.combinatorius.UIControllerFilter;

public class LifecycleSteps extends PerStoriesWebDriverSteps {

    private final WebDriverProvider webDriverProvider;

    public LifecycleSteps(WebDriverProvider webDriverProvider) {
    	super(webDriverProvider);
        this.webDriverProvider = webDriverProvider;
    }

    @BeforeScenario
    public void deleteCookies() {
        try {
        	System.out.println("Deleting cookies");
            webDriverProvider.get().manage().deleteCookieNamed(CombinatoriusServlet.combinatoriusTheme);
            webDriverProvider.get().manage().deleteCookieNamed(UIControllerFilter.combinatoriusEvent);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }
    
    @BeforeScenario
    public void maximizeWindow() {
        try {
            //webDriverProvider.get().manage().window().maximize();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }
    
    @AfterStories
    public void clearCache() throws IOException {
    	File cssCacheDir = new File("src/main/webapp/css_cache");
    	File jsCacheDir = new File("src/main/webapp/js_cache");
    	clearDirectory(cssCacheDir);
    	clearDirectory(jsCacheDir);
    }
    
    private void clearDirectory(File directory) throws IOException {
    	File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        for (File file : files) {
        	if (!file.isHidden()) {
	            try {
	            	FileUtils.forceDelete(file);
	            } catch (IOException ioe) {
	                throw new IOException("Failed to delete file " + file.getAbsolutePath());
	            }
        	}
        }
    }
    
}

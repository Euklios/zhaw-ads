package ch.zhaw.ads; /**
 * @author K. Rege
 * @version 1.0 -- ch.zhaw.ads.ExBoxJUnit
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */

import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.util.LinkedList;

public class ExBoxJUnit implements CommandExecutor {

	@Override
	public String execute(String testFile) throws Exception {
		final java.util.List<String> failed = new LinkedList<String>();
		final java.util.List<String> finished = new LinkedList<String>();
		StringBuilder output = new StringBuilder();
        
		JUnitCore runner = new JUnitCore();
		output.append(
				"\nRUN TESTS " + new File(testFile).getName().split("\\.")[0] + "\n");	
		runner.addListener(new RunListener() {
			@Override
			public void testFinished(Description description) throws Exception {
				finished.add(description.getDisplayName());			
			}		
    
			@Override
			public void testFailure(Failure failure) throws Exception {
				failed.add(failure.getDescription().getDisplayName());
			}	
		});
    		
		Class testClass = ServerFactory.loadClass(testFile);
		Result result = runner.run(testClass);
    	    
		for (String test : finished) {
			if (!failed.contains(test)) {
				output.append(test + ": OK\n");
			}
		}
		for (Failure failure : result.getFailures()) {
			output.append(failure.toString() + " ERROR\n");
		}
		output.append(
				"TESTS " + (result.wasSuccessful() ? "PASSED" : "FAILED") + ": "
				+ (result.wasSuccessful()
						? "OK \u263a"
						: result.getFailures().size() + " ERRORS")
						+ "\n");
		return output.toString();
	}
}

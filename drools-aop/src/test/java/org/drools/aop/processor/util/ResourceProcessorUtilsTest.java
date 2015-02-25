package org.drools.aop.processor.util;

import org.drools.aop.model.AOPResource;
import org.drools.aop.processor.util.ResourceProcessorUtils;
import org.junit.Assert;
import org.junit.Test;

public class ResourceProcessorUtilsTest {
	@Test
	public void testPackageRegExValid() {
		AOPResource aop = new AOPResource();
		aop.setAfterPackage("import some.imports");
		String ruleContent = "blah sdfsdfsdf\npackage com.test.myrules\nimport com.ok.blah"; 
		String ruleResult = ResourceProcessorUtils.applyAfterPackage(aop, ruleContent);
		
		Assert.assertEquals("The rule content should have the new import", ruleResult,"blah sdfsdfsdf\npackage com.test.myrules\nimport some.imports\nimport com.ok.blah");
		
	}
	
	@Test
	public void testWhenRegExValid() {
		AOPResource aop = new AOPResource();
		aop.setAfterWhen("   eval(trace(\"blah\"))");
		String ruleContent = "blah sdfsdfsdf\npackage com.test.myrules\nimport com.ok.blah\n\t\twhen\n\t\t   found the begining"; 
		String ruleResult = ResourceProcessorUtils.applyAfterWhen(aop, ruleContent);
		
		Assert.assertEquals("The rule content should have the new after when", ruleResult,"blah sdfsdfsdf\npackage com.test.myrules\nimport com.ok.blah\n\t\twhen\n   eval(trace(\"blah\"))\n\t\t   found the begining");
	}
}

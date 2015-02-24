package org.drools.compiler.builder.impl;

import org.drools.core.definitions.aop.impl.AOPImpl;
import org.junit.Assert;
import org.junit.Test;

public class AOPProcessBuilderTest {
	
	@Test
	public void testPackageRegExValid() {
		AOPProcessBuilderImpl impl = new AOPProcessBuilderImpl();
		AOPImpl aop = new AOPImpl();
		aop.setImports("import some.imports");
		String ruleContent = "blah sdfsdfsdf\npackage com.test.myrules\nimport com.ok.blah"; 
		String ruleResult = impl.applyImports(aop, ruleContent);
		
		Assert.assertEquals("The rule content should have the new import", ruleResult,"blah sdfsdfsdf\npackage com.test.myrules\nimport some.imports\nimport com.ok.blah");
		
	}
	
	@Test
	public void testWhenRegExValid() {
		AOPProcessBuilderImpl impl = new AOPProcessBuilderImpl();
		AOPImpl aop = new AOPImpl();
		aop.setAfterWhen("   eval(trace(\"blah\"))");
		String ruleContent = "blah sdfsdfsdf\npackage com.test.myrules\nimport com.ok.blah\n\t\twhen\n\t\t   found the begining"; 
		String ruleResult = impl.applyAfterWhen(aop, ruleContent);
		
		Assert.assertEquals("The rule content should have the new after when", ruleResult,"blah sdfsdfsdf\npackage com.test.myrules\nimport com.ok.blah\n\t\twhen\n   eval(trace(\"blah\"))\n\t\t   found the begining");
	}
}

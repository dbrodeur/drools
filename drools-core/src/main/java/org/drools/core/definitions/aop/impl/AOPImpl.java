package org.drools.core.definitions.aop.impl;

import java.util.regex.Pattern;

import org.kie.api.definition.aop.AOP;

public class AOPImpl implements AOP {
	private String pattern;
	private String imports;
	private String afterRule;
	private String afterWhen;
	private Pattern compiledPattern;
	
	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public String getImports() {
		return imports;
	}

	@Override
	public String getAfterRule() {
		return afterRule;
	}
	
	@Override
	public Pattern getCompiledPattern() {
		return compiledPattern;
	}

	@Override
	public String getAfterWhen() {
		return afterWhen;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setImports(String imports) {
		this.imports = imports;
	}

	public void setAfterRule(String afterRule) {
		this.afterRule = afterRule;
	}

	public void setCompiledPattern(Pattern compiledPattern) {
		this.compiledPattern = compiledPattern;
	}

	public void setAfterWhen(String afterWhen) {
		this.afterWhen = afterWhen;
	}

	@Override
	public String toString() {
		return "AOPImpl [pattern=" + pattern + ", imports=" + imports
				+ ", afterRule=" + afterRule + ", afterWhen=" + afterWhen
				+ ", compiledPattern=" + compiledPattern + "]";
	}


}

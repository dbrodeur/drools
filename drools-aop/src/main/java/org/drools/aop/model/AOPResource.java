package org.drools.aop.model;

import java.util.regex.Pattern;

public class AOPResource {
	private String rulePattern;
	private String afterPackage;
	private String afterRule;
	private String afterWhen;
	private String beforeThen;
	private String afterThen;
	private String beforeEnd;
	private Pattern compiledRulePattern;
	public String getRulePattern() {
		return rulePattern;
	}
	public void setRulePattern(String rulePattern) {
		this.rulePattern = rulePattern;
	}
	public String getAfterRule() {
		return afterRule;
	}
	public void setAfterRule(String afterRule) {
		this.afterRule = afterRule;
	}
	public String getAfterWhen() {
		return afterWhen;
	}
	public void setAfterWhen(String afterWhen) {
		this.afterWhen = afterWhen;
	}
	public String getBeforeThen() {
		return beforeThen;
	}
	public void setBeforeThen(String beforeThen) {
		this.beforeThen = beforeThen;
	}
	public String getAfterThen() {
		return afterThen;
	}
	public void setAfterThen(String afterThen) {
		this.afterThen = afterThen;
	}
	public String getBeforeEnd() {
		return beforeEnd;
	}
	public void setBeforeEnd(String beforeEnd) {
		this.beforeEnd = beforeEnd;
	}
	public Pattern getCompiledRulePattern() {
		return compiledRulePattern;
	}
	public void setCompiledRulePattern(Pattern compiledRulePattern) {
		this.compiledRulePattern = compiledRulePattern;
	}
	public String getAfterPackage() {
		return afterPackage;
	}
	public void setAfterPackage(String afterPackage) {
		this.afterPackage = afterPackage;
	}
	@Override
	public String toString() {
		return "AOPResource [rulePattern=" + rulePattern + ", afterPackage="
				+ afterPackage + ", afterRule=" + afterRule + ", afterWhen="
				+ afterWhen + ", beforeThen=" + beforeThen + ", afterThen="
				+ afterThen + ", beforeEnd=" + beforeEnd
				+ ", compiledRulePattern=" + compiledRulePattern + "]";
	}

}

package org.drools.aop.processor.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.drools.aop.model.AOPResource;
import org.kie.api.io.Resource;
import org.yaml.snakeyaml.Yaml;

public class ResourceProcessorUtils {

	private static final String YAML_AOP_RULE_PATTERN = "pattern";
	private static final String YAML_AOP_AFTER_PACKAGE = "afterPackage";
	private static final String YAML_AOP_AFTER_RULE = "afterRule";
	private static final String YAML_AOP_AFTER_WHEN = "afterWhen";
	private static final String YAML_AOP_AFTER_THEN = "afterThen";
	private static final String YAML_AOP_BEFORE_THEN = "beforeThen";
	private static final String YAML_AOP_BEFORE_END = "beforeEnd";

	private static final Pattern PACKAGE_PATTERN = Pattern
			.compile("(?m)(^package\\s+.+$)");
	private static final Pattern AFTER_RULE = Pattern
			.compile("(?m)(^\\s*rule\\s+\".+\"\\s*$)");
	private static final Pattern AFTER_WHEN = Pattern
			.compile("(?m)(^\\s*when\\s*$)");
	private static final Pattern AFTER_THEN = Pattern
			.compile("(?m)(^\\s*then\\s*$)");
	private static final Pattern BEFORE_THEN = Pattern
			.compile("(?m)(^\\s*then\\s*$)");
	private static final Pattern BEFORE_END = Pattern
			.compile("(?m)(^\\s*end\\s*$)");
	
	public static String applyAfterPackage(AOPResource resource, String ruleContent) {
		return PACKAGE_PATTERN.matcher(ruleContent).replaceAll(
				"$1\n" + resource.getAfterPackage());
	}
	
	public static String applyAfterRule(AOPResource resource, String ruleContent) {
		return AFTER_RULE.matcher(ruleContent).replaceAll(
				"$1\n" + resource.getAfterRule());
	}

	public static String applyAfterWhen(AOPResource resource, String ruleContent) {
		return AFTER_WHEN.matcher(ruleContent).replaceAll(
				"$1\n" + resource.getAfterWhen());
	}
	
	public static String applyAfterThen(AOPResource resource, String ruleContent) {
		return AFTER_THEN.matcher(ruleContent).replaceAll(
				"$1\n" + resource.getAfterThen());
	}
	
	public static String applyBeforeThen(AOPResource resource, String ruleContent) {
		return BEFORE_THEN.matcher(ruleContent).replaceAll(
				resource.getBeforeThen() + "\n$1");
	}
	
	public static String applyBeforeEnd(AOPResource resource, String ruleContent) {
		return BEFORE_END.matcher(ruleContent).replaceAll(
				resource.getBeforeEnd() + "\n$1");
	}
	
	private static String generateStringFromList(List<Object> objects) {
		StringBuffer buffer = new StringBuffer();
		for( Object obj: objects ) {
			buffer.append(obj.toString());
			buffer.append("\n");
		}
		return buffer.deleteCharAt(buffer.length()-1).toString();
	}
	
	private static String loadYamlContent(String key,
			Map<String, ArrayList<Object>> map) {
		if (map.containsKey(key)) {
			return generateStringFromList(map.get(key));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static AOPResource loadAOPResource(Resource resource)
			throws IOException {
		Map<String, ArrayList<Object>> map = (Map<String, ArrayList<Object>>) new Yaml()
				.load(resource.getInputStream());

		AOPResource aopResource = new AOPResource();
		aopResource.setRulePattern(loadYamlContent(YAML_AOP_RULE_PATTERN, map));
		aopResource.setAfterPackage(loadYamlContent(YAML_AOP_AFTER_PACKAGE, map));
		aopResource.setAfterRule(loadYamlContent(YAML_AOP_AFTER_RULE, map));
		aopResource.setAfterWhen(loadYamlContent(YAML_AOP_AFTER_WHEN, map));
		aopResource.setAfterThen(loadYamlContent(YAML_AOP_AFTER_THEN, map));
		aopResource.setBeforeThen(loadYamlContent(YAML_AOP_BEFORE_THEN, map));
		aopResource.setBeforeEnd(loadYamlContent(YAML_AOP_BEFORE_END, map));

		if (aopResource.getRulePattern() != null) {
			aopResource.setCompiledRulePattern(Pattern.compile(aopResource
					.getRulePattern()));
		}

		return aopResource;
	}
}

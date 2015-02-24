package org.drools.compiler.builder.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.core.definitions.aop.impl.AOPImpl;
import org.drools.core.io.impl.ByteArrayResource;
import org.drools.core.util.IoUtils;
import org.kie.api.definition.aop.AOP;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.AOPKnowledgeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class AOPProcessBuilderImpl implements AOPKnowledgeBuilder {

	private static final Pattern PACKAGE_PATTERN = Pattern
			.compile("(?m)(^package\\s+.+$)");
	private static final Pattern AFTER_WHEN = Pattern
			.compile("(?m)(^\\s*when\\s*$)");

	protected static final transient Logger logger = LoggerFactory
			.getLogger(AOPProcessBuilderImpl.class);
	private List<AOP> aops = new ArrayList<AOP>();

	@Override
	public void readAOPResources(List<Resource> resources) {
		try {
			for (Resource resource : resources) {
				if (ResourceType
						.determineResourceType(resource.getSourcePath())
						.equals(ResourceType.AOP)) {
					Map<String, ArrayList<Object>> map = (Map<String, ArrayList<Object>>) new Yaml()
							.load(resource.getInputStream());
					AOPImpl aop = new AOPImpl();
					aop.setPattern(map.get("pattern").get(0).toString());
					aop.setCompiledPattern(Pattern.compile(aop.getPattern()));
					aop.setImports(map.get("imports").get(0).toString());
					aop.setAfterRule(map.get("afterrule").get(0).toString());
					aop.setAfterWhen(map.get("afterwhen").get(0).toString());
					aops.add(aop);
					logger.debug("AOPImpl found: {}", aop);
				}
			}
		} catch (IOException e) {
			logger.error("An error occured while processing AOP resources", e);
		}
	}
	
	protected String applyImports( AOP aop, String ruleContent ) {
		return PACKAGE_PATTERN.matcher(ruleContent).replaceAll("$1\n" + aop.getImports());
	}
	
	protected String applyAfterWhen( AOP aop, String ruleContent ) {
		return AFTER_WHEN.matcher(ruleContent).replaceAll("$1\n" + aop.getAfterWhen());
	}

	@Override
	public List<Resource> applyAOPToResource(List<Resource> resources) {
		List<Resource> result = new ArrayList<Resource>();
		try {
			for (Resource resource : resources) {
				if (ResourceType
						.determineResourceType(resource.getSourcePath())
						.equals(ResourceType.DRL)) {
					boolean matched = false;
					for (AOP aop : aops) {
						Matcher matcher = aop.getCompiledPattern().matcher(
								resource.getSourcePath());
						if (matcher.find()) {
							logger.debug("Matched resource: {}",
									resource.getSourcePath());
							
							String ruleContent = new String(
									IoUtils.readBytesFromInputStream(resource
											.getInputStream()));
							ruleContent = applyImports( aop, ruleContent );
							ruleContent = applyAfterWhen( aop, ruleContent );
							ByteArrayResource res = new ByteArrayResource(ruleContent.getBytes());
							res.setSourcePath(resource.getSourcePath());
							result.add(res);
							matched = true;
						}
					}
					if( !matched ) {
						result.add(resource);
					}
				} else {
					result.add(resource);
				}
			}
		} catch (IOException ie) {
			logger.error("An error occured", ie);
		}
		return result;
	}

}

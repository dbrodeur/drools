package org.drools.aop.processor.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.drools.aop.model.AOPResource;
import org.drools.aop.processor.ResourceProcessor;
import org.drools.aop.processor.util.ResourceProcessorUtils;
import org.drools.core.io.impl.ByteArrayResource;
import org.drools.core.util.IoUtils;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceProcessorImpl implements ResourceProcessor {

	protected static final transient Logger logger = LoggerFactory
			.getLogger(ResourceProcessorImpl.class);

	private List<AOPResource> aopResources = new ArrayList<AOPResource>();

	private List<Resource> resources;
	
	@Override
	public void loadResources(List<Resource> allResources) {
		List<Resource> filteredResources = new ArrayList<Resource>();
		try {
			for (Resource resource : allResources) {
				if (ResourceType
						.determineResourceType(resource.getSourcePath())
						.equals(ResourceType.AOP)) {
					aopResources.add(ResourceProcessorUtils
							.loadAOPResource(resource));
				} else {
					filteredResources.add(resource);
				}
			}
		} catch (IOException e) {
			logger.error("An error occured while processing AOP resources", e);
		}
		this.resources = filteredResources;
	}
	
	private ByteArrayResource createByteArrayResource(Resource resource, String content) {
		ByteArrayResource res = new ByteArrayResource(content.getBytes());
		res.setSourcePath(resource.getSourcePath());
		return res;
	}

	private String applyDroolsAOP(Resource resource, AOPResource aopResource) throws IOException {
		String ruleContent = new String(
				IoUtils.readBytesFromInputStream(resource
						.getInputStream()));
		if(aopResource.getAfterPackage()!=null) {
			ruleContent = ResourceProcessorUtils.applyAfterPackage(
					aopResource, ruleContent);
		}
		if(aopResource.getAfterRule()!=null) {
			ruleContent = ResourceProcessorUtils.applyAfterRule(
					aopResource, ruleContent);			
		}
		if(aopResource.getAfterThen()!=null) {
			ruleContent = ResourceProcessorUtils.applyAfterThen(
					aopResource, ruleContent);			
		}
		if(aopResource.getAfterWhen()!=null) {
			ruleContent = ResourceProcessorUtils.applyAfterWhen(
					aopResource, ruleContent);			
		}
		if(aopResource.getBeforeThen()!=null) {
			ruleContent = ResourceProcessorUtils.applyBeforeThen(
					aopResource, ruleContent);			
		}		
		if(aopResource.getBeforeEnd()!=null) {
			ruleContent = ResourceProcessorUtils.applyBeforeEnd(
					aopResource, ruleContent);			
		}
		return ruleContent;
	}
	
	@Override
	public List<Resource> processResources() {
		List<Resource> result = new ArrayList<Resource>();
		try {
			for (Resource resource : resources) {
				if (ResourceType
						.determineResourceType(resource.getSourcePath())
						.equals(ResourceType.DRL)) {
					boolean matched = false;
					for (AOPResource aopResource : aopResources) {
						Matcher matcher = aopResource.getCompiledRulePattern()
								.matcher(resource.getSourcePath());
						if (matcher.find()) {
							logger.debug("Matched resource: {}",
									resource.getSourcePath());

							String ruleContent = applyDroolsAOP(resource, aopResource);
							
							logger.debug("Rule Content Changed:" + ruleContent);
							result.add(createByteArrayResource(resource, ruleContent));
							matched = true;
						}
					}
					if (!matched) {
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

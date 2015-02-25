package org.drools.aop.processor;

import java.util.List;

import org.kie.api.io.Resource;

public interface ResourceProcessor {
	void loadResources(List<Resource> resources);
	List<Resource> processResources();
}

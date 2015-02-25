package org.drools.aop.processor;

import java.util.List;

import org.kie.api.io.Resource;

public interface ResourceProcessor {
	List<Resource> processResources(List<Resource> resources);
}

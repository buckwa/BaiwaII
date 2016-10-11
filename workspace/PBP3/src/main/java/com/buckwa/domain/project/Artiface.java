package com.buckwa.domain.project;

import com.buckwa.domain.BaseDomain;

public class Artiface extends BaseDomain{
	
	private Long artifaceId;
	private String artifaceType;
	private String artifaceName;
	public Long getArtifaceId() {
		return artifaceId;
	}
	public void setArtifaceId(Long artifaceId) {
		this.artifaceId = artifaceId;
	}
	public String getArtifaceType() {
		return artifaceType;
	}
	public void setArtifaceType(String artifaceType) {
		this.artifaceType = artifaceType;
	}
	public String getArtifaceName() {
		return artifaceName;
	}
	public void setArtifaceName(String artifaceName) {
		this.artifaceName = artifaceName;
	}
	
	
	

}

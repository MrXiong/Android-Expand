package com.expand.library.internal;

import java.util.List;

public class Model {

	private List<Model> modelChilds;
	private String modelName;
	private String modelImgUrl;

	public List<Model> getModelChilds() {
		return modelChilds;
	}

	public void setModelChilds(List<Model> modelChilds) {
		this.modelChilds = modelChilds;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelImgUrl() {
		return modelImgUrl;
	}

	public void setModelImgUrl(String modelImgUrl) {
		this.modelImgUrl = modelImgUrl;
	}

}

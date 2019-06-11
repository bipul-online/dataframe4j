package com.bipul.datafrome4j;

public class MetaData {
	int index;
	int length;
	Class<?> dataType;
	String name;

	public MetaData(MetaData metaData) {
		this.index = metaData.index;
		this.length = metaData.length;
		this.dataType = metaData.dataType;
		this.name = metaData.name;
	}

	public MetaData() {
	}
}

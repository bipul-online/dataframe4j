package com.bipul.datafrome4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

public class DataFrame {
	private MetaData[] columnMetadata;
	private List<List<String>> records = new ArrayList<>();

	public DataFrame() {
	}

	public DataFrame(String csvFilePath) throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
			String[] headers = csvReader.readNext();
			columnMetadata = new MetaData[headers.length];
			for (int i = 0; i < headers.length; ++i) {
				columnMetadata[i] = new MetaData();
				columnMetadata[i].name = headers[i];
				columnMetadata[i].length = headers[i].length();
			}
			String[] values = null;
			while ((values = csvReader.readNext()) != null) {
				records.add(Arrays.asList(values));
				for (int i = 0; i < values.length; ++i) {
					columnMetadata[i].index = i;
					if (values[i].length() > columnMetadata[i].length) {
						columnMetadata[i].length = values[i].length();
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (MetaData metaData : columnMetadata) {
			stringBuilder.append(String.format("%" + metaData.length + "s | ", metaData.name));
		}
		stringBuilder.append(System.lineSeparator());
		StringBuilder dashBuilder = new StringBuilder();
		for (MetaData metaData : columnMetadata) {
			for (int i = 0; i < metaData.length; ++i) {
				dashBuilder.append('-');
			}
			dashBuilder.append("---");
		}
		stringBuilder.append(dashBuilder);
		stringBuilder.append(System.lineSeparator());
		for (List<String> record : records) {
			for (MetaData metaData : columnMetadata) {
				stringBuilder.append(String.format("%" + metaData.length + "s | ", record.get(metaData.index)));
			}
			stringBuilder.append(System.lineSeparator());
		}
		stringBuilder.append(dashBuilder);
		stringBuilder.append(System.lineSeparator());
		return stringBuilder.toString();
	}

	public DataFrame columns(int startIndex, int endIndex) {
		return columns(startIndex, endIndex, 1);
	}

	private DataFrame columns(int startIndex, int endIndex, int step) {
		DataFrame subDataFrame = new DataFrame();
		int length = endIndex - startIndex;
		subDataFrame.columnMetadata = new MetaData[length];
		for (int i = startIndex, j = 0; i < endIndex; i += step, j++) {
			subDataFrame.columnMetadata[j] = this.columnMetadata[i];
			subDataFrame.columnMetadata[j].index = j;
		}
		for (List<String> record : this.records) {
			List<String> values = new ArrayList<>();
			for (int i = startIndex; i < endIndex; i += step) {
				values.add(record.get(i));
			}
			subDataFrame.records.add(values);
		}
		return subDataFrame;
	}

	public DataFrame rows(int startIndex, int endIndex) {
		return rows(startIndex, endIndex, 1);
	}

	public DataFrame rows(int startIndex, int endIndex, int step) {
		DataFrame subDataFrame = new DataFrame();
		if (endIndex > this.records.size()) {
			throw new IndexOutOfBoundsException(
					"endIndex: " + endIndex + " is larger than number of records in dataframe: " + records.size());
		}
		subDataFrame.columnMetadata = new MetaData[this.columnMetadata.length];
		for (int i = 0; i < subDataFrame.columnMetadata.length; ++i) {
			subDataFrame.columnMetadata[i] = new MetaData(this.columnMetadata[i]);
			subDataFrame.columnMetadata[i].length = this.columnMetadata[i].name.length();
		}
		for (int i = startIndex; i < endIndex; i += step) {
			List<String> values = new ArrayList<>();
			List<String> record = this.records.get(i);
			for (int j = 0; j < record.size(); j++) {
				String value = record.get(j);
				values.add(value);
				if (subDataFrame.columnMetadata[j].length < value.length()) {
					subDataFrame.columnMetadata[j].length = value.length();
				}
			}
			subDataFrame.records.add(values);
		}
		return subDataFrame;
	}
}

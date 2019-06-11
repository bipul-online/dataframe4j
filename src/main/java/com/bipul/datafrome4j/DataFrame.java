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
		for (MetaData metaData : columnMetadata) {
			stringBuilder.append("---");
			for (int i = 0; i < metaData.length; ++i) {
				stringBuilder.append("-");
			}
		}
		stringBuilder.append(System.lineSeparator());
		for (List<String> record : records) {
			for (MetaData metaData : columnMetadata) {
				stringBuilder.append(String.format("%" + metaData.length + "s | ", record.get(metaData.index)));
			}
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
}

package datafrome4j;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.bipul.datafrome4j.DataFrame;

public class DataFrameTest {
	
	@Test
	public void test() throws FileNotFoundException, IOException {
		DataFrame dataFrame = new DataFrame("/Users/bkumar2/git/provider-tools/branding-data-analysis/sample/product-service-mapping.csv");
		System.out.print(dataFrame);
		dataFrame = dataFrame.rows(3, 10);
		System.out.print(dataFrame);
		dataFrame = dataFrame.columns(3, 10);
		System.out.print(dataFrame);
	}

}

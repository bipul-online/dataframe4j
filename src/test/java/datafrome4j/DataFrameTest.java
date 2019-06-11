package datafrome4j;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.bipul.datafrome4j.DataFrame;

public class DataFrameTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		DataFrame dataFrame = new DataFrame(
				"/Users/bkumar2/git/provider-tools/branding-data-analysis/sample/product-service-mapping.csv");
		dataFrame = dataFrame.rows(new int[] { 2 }).columns(new int[] { 2, 4, 6, 8 });
		System.out.print(dataFrame);
	}

}

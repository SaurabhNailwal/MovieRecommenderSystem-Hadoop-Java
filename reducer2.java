/*
 * @author Saurabh Nailwal
 */


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;

public class reducer2 extends MapReduceBase implements Reducer<Text, Text, Text, Text>{

	@Override
	public void reduce(Text key, Iterator<Text> value,
			OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		
		int mulSum=0;
		long sqSum1 = 0,sqSum2 = 0;
		String[] ratings =null;
		
		//calculate the similarity
		while(value.hasNext()){
						
			ratings = value.next().toString().split(",");
			
			mulSum = mulSum + Integer.parseInt(ratings[0]) * Integer.parseInt(ratings[1]);
			
			sqSum1 = sqSum1 + Integer.parseInt(ratings[0])*Integer.parseInt(ratings[0]);
			sqSum2 = sqSum2 + Integer.parseInt(ratings[1])*Integer.parseInt(ratings[1]);

		}
		
		double similarity = 0.0;
	
		//Cosine Similarity calculation
		similarity = mulSum /(Math.sqrt(sqSum1) * Math.sqrt(sqSum2));

		
		//Storing the 1682 values into a matrix		
		output.collect(key, new Text(String.valueOf(similarity)));
		
	}


}

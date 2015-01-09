/*
 * @author Saurabh Nailwal
 */

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class mapper1 extends MapReduceBase implements Mapper<LongWritable,Text,IntWritable,Text>{

	private IntWritable customerId = new IntWritable();
	private Text movieRating = new Text();
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
		// TODO Auto-generated method stub

		StringTokenizer strTokenizer  = new StringTokenizer(value.toString());
		
		while(strTokenizer.hasMoreTokens()){
			
		customerId.set(Integer.parseInt(strTokenizer.nextToken()));
		movieRating.set(strTokenizer.nextToken()+ ","+ strTokenizer.nextToken());
		
		//Skipping TimeStamp value
		strTokenizer.nextToken();
		
		output.collect(customerId, movieRating);
		
		}	
		
		
	}

}

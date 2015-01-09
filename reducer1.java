/*
 * @author Saurabh Nailwal
 */


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;

public class reducer1 extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text>{

	@Override
	public void reduce(IntWritable key, Iterator<Text> values,
			OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
		// TODO Auto-generated method stub
		
		StringBuilder movieRating = new StringBuilder();
		String newValue = "";
		
		while(values.hasNext()){
			newValue = values.next().toString();
			
			movieRating.append(newValue+" ");			
			
		}
	
		output.collect(key, new Text(movieRating.toString().trim()));
	}

	

}

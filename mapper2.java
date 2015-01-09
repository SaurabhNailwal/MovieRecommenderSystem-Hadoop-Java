/*
 * @author Saurabh Nailwal
 */


import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.apache.hadoop.mapred.*;
import org.apache.hadoop.io.*;

public class mapper2 extends MapReduceBase implements Mapper<Text, Text, Text, Text>{

	private Text moviePair = new Text();
	private Text ratingPair = new Text();
	
		
	@Override
	public void map(Text key, Text value, OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException {
		
		HashMap<String,String> movieMap = new HashMap<String,String>();
		
		//creating the movie pair and rating pair
		String[] movRatPairs = value.toString().split(" ");
		String[] split = null;
		
		for(int i=0;i<movRatPairs.length;i++){
			
			//movie and rating key value pairs
			split = movRatPairs[i].split(",");
			movieMap.put(split[0], split[1]);
		}
		
		//forming the movie pairs by iterating through map
		Set<String> keys = movieMap.keySet();
		for(String keyM: keys){

			for(String keyN: keys){
				
				if(Integer.parseInt(keyN) > Integer.parseInt(keyM)){
					
					moviePair.set(keyM+","+keyN);
					ratingPair.set(movieMap.get(keyM)+","+movieMap.get(keyN));

					output.collect(moviePair, ratingPair);
					
				}
			}
			
		}
		
		
	}


}

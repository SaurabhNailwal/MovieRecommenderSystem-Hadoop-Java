/*
 * @author Saurabh Nailwal
 */

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class RecSystemDriver extends Configured implements Tool{

	public int run(String[] args) throws Exception{
		
		//job1 specifications
		JobConf job1 = new JobConf(getConf(),RecSystemDriver.class);
		job1.setJobName("RecSystemJob1");
		
		job1.setOutputKeyClass(IntWritable.class);
		job1.setOutputValueClass(Text.class);
		
		job1.setJarByClass(RecSystemDriver.class);
		
		job1.setMapperClass(mapper1.class);
		job1.setReducerClass(reducer1.class);
		
		       
		//job2 specifications
		JobConf job2 = new JobConf(getConf(), RecSystemDriver.class);
		
		job2.setJobName("RecSystemJob2");
		
		job2.setInputFormat(KeyValueTextInputFormat.class);
		
		job2.setOutputKeyClass(mapper1.class);
		job2.setOutputValueClass(reducer1.class);

	
        
		job2.setJarByClass(RecSystemDriver.class);
		
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		
		job2.setJarByClass(RecSystemDriver.class);
		
		job2.setMapperClass(mapper2.class);
		job2.setReducerClass(reducer2.class);
		
		FileInputFormat.addInputPath(job1, new Path("input")); //new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path("output1")); //new Path(args[1]));
        JobClient.runJob(job1);
		
		FileInputFormat.addInputPath(job2, new Path("output1")); //new Path(args[1]));
		FileOutputFormat.setOutputPath(job2, new Path("output2")); //new Path(args[2]));
		JobClient.runJob(job2);		
        
		return 0;
	}
	
	public static void main(String[] args) throws Exception{
	
		int res = ToolRunner.run(new Configuration(), new RecSystemDriver(), args);		
		System.exit(res);
	}

}

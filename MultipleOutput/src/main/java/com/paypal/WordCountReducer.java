package com.paypal;

import java.io.IOException;

//import java.util.Iterator;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>
{
	protected MultipleOutputs multiple; 
	public void setup(Context context)throws java.io.IOException,InterruptedException
	{
		multiple = new MultipleOutputs(context);
	}
      //Reduce method for just outputting the key from mapper as the value from mapper is just an empty string   
      public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
      {
            int sum = 0;
            /*iterates through all the values available with a key and add them together and give the
            final result as the key and sum of its values*/
            for (IntWritable value : values)
            {
                  sum += value.get();

            }
            multiple.write("output1",key, new IntWritable(sum));
            String output1 = key.toString() + sum;
            context.getCounter("reducer",output1 ).increment(1);
            String newOutput = new String();
            for(int i=0;i<sum;i++)
            {
            	newOutput += key.toString(); 
            }
            multiple.write("output2", key, new Text(newOutput));
            context.getCounter("reducer",newOutput ).increment(1);
       }
}

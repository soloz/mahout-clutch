package mahout;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.VectorWritable;

public class SampleClusterer {

	public void prepareData() throws IOException{
		
		List<NamedVector> vector = new LinkedList<NamedVector>();
	    
		NamedVector v1;
	    
		v1 = new NamedVector(new DenseVector(new double[] {0.1, 0.2, 0.5}), "Item number one");
	    vector.add(v1);

	    Configuration config = new Configuration();
	    FileSystem fs = FileSystem.get(config);

	    Path path = new Path("datasamples/data");

	    //write a SequenceFile form a Vector
	    @SuppressWarnings("deprecation")
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, config, path, Text.class, VectorWritable.class);
	    
	    VectorWritable vec = new VectorWritable();
	    for(NamedVector v:vector){
	        vec.set(v);
	        writer.append(new Text(v.getName()), vec);
	    }
	    writer.close();
		    
	}
	
	public static void main (String args[]) throws IOException{
		new SampleClusterer().prepareData();
	}
}

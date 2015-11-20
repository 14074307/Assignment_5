package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
   private String attributeLine;
   private String dataTypeLine;
   private String tupleLine;
   private String[] tupleLines = new String[100];
   private int numOfTuples = 0;
   private int currentTuple = -1;

	
	public Table(String from) {
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
      try {
         readLines();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
	}
   
   /**
     * It is used to read the lines in the csv file
     */
   
   public void readLines() throws IOException {
		attributeLine = br.readLine();
		dataTypeLine = br.readLine();
      tupleLine = br.readLine();
      while (tupleLine != null) {
         tupleLines[numOfTuples++] = tupleLine;
         tupleLine = br.readLine();
      }
   }

	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next() {
      currentTuple++;
      if (currentTuple >= numOfTuples) return null;
      Tuple next = new Tuple(attributeLine, dataTypeLine, tupleLines[currentTuple]);
      next.setAttributeName();
      next.setAttributeType();
      next.setAttributeValue();
      return next;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}
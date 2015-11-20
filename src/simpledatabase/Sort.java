package simpledatabase;
import java.util.ArrayList;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
   private int currentTuple = -1;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
      sortTuples();
		
	}
   
   /**
     * It is used to sort the tuples and store them in tuplesResult
     */
   
   public void sortTuples() {
      Tuple t;
      ArrayList<Tuple> tuples = new ArrayList<>();
      ArrayList<Attribute> attributes = new ArrayList<>();
      int attributeIndex = 0;
      while ((t = child.next()) != null) {
         tuples.add(t);
         for (int i=0; i < t.getAttributeList().size(); i++) {
            if (t.getAttributeName(i).equals(orderPredicate))
               attributeIndex = i;
         }
      }
      for (int j=0; j < tuples.size(); j++) {
         int small = j;
         for (int k=j+1; k < tuples.size(); k++)
            if (tuples.get(k).getAttributeValue(attributeIndex).toString().compareTo(tuples.get(small).getAttributeValue(attributeIndex).toString()) < 0)
               small = k;
         Tuple temp = tuples.get(j);
         tuples.set(j, tuples.get(small));
         tuples.set(small, temp);
      }
      tuplesResult = tuples;
   }
      
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		currentTuple++;
		if (currentTuple >= tuplesResult.size()) return null;
      return tuplesResult.get(currentTuple);
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}
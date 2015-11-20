package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;
   Tuple currentTuple;
   ArrayList<Tuple> tuples = new ArrayList<>();
   private int numOfTuples = 0;
   private int currTupleInd = -1;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();
      collectTuples();
	}
   
   /**
     * It is used to collect the tuples in the target table
     */
	
   public void collectTuples() {
      Tuple t = child.next();
      boolean hasAttribute = false;
      int attributeIndex = 0;
      for (int i=0; i < t.getAttributeList().size(); i++) {
         if (t.getAttributeName(i).equals(whereAttributePredicate)) {
            hasAttribute = true;
            attributeIndex = i;
            break;
         }
      }
      if (hasAttribute) {
         while (t != null) {
            if (t.getAttributeValue(attributeIndex).toString().equals(whereValuePredicate)) {
               tuples.add(t);
               numOfTuples++;
            }
            t = child.next();
         }
      }
      else {
         while (t != null) {
            tuples.add(t);
            numOfTuples++;
            t = child.next();
         }
      }
   }
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
      currTupleInd++;
      if (currTupleInd >= numOfTuples) return null;
      return tuples.get(currTupleInd);
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}
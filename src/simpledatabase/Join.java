package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
   ArrayList<Tuple> leftTuples = new ArrayList<>();
   ArrayList<Tuple> rightTuples = new ArrayList<>();
   int currentIndex = -1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		collectTuples();
      joinTuples();
	}
   
   /**
     * It is used to collect the tuples in the two tables
     */
   
   public void collectTuples() {
      Tuple leftTuple;
      while ((leftTuple = leftChild.next()) != null) 
         leftTuples.add(leftTuple);
      Tuple rightTuple;
      while ((rightTuple = rightChild.next()) != null)
         rightTuples.add(rightTuple);
   }
   
   /**
     * It is used to join the two table by matching criteria
     */
   
   public void joinTuples() {
      for (int i=0; i < rightTuples.size(); i++) {
         Tuple rightTuple = rightTuples.get(i);
         for (int j=0; j < leftTuples.size(); j++) {
            ArrayList<Attribute> matchedAttributes = new ArrayList<>();
            ArrayList<Attribute> allAttributes = new ArrayList<>();
            boolean notMatched = false;
            Tuple leftTuple = leftTuples.get(j);
            for (int k=0; k < rightTuple.getAttributeList().size(); k++) {
               String rightAttributeName = rightTuple.getAttributeName(k);
               Object rightAttributeVal = rightTuple.getAttributeValue(k);
               for (int x=0; x < leftTuple.getAttributeList().size(); x++) {
                  String leftAttributeName = leftTuple.getAttributeName(x);
                  Object leftAttributeVal = leftTuple.getAttributeValue(x);
                  if (leftAttributeName.equals(rightAttributeName)) {
                     if (!leftAttributeVal.toString().equals(rightAttributeVal.toString()))
                        notMatched = true;
                     else 
                        matchedAttributes.add(rightTuple.getAttributeList().get(k));
                  }
                  if (notMatched) break;
               }
               if (notMatched) break;
            }
            if ((!notMatched) && (!matchedAttributes.isEmpty())) {
               for (int y=0; y < matchedAttributes.size(); y++)
                  allAttributes.add(matchedAttributes.get(y));
               for (int q=0; q < rightTuple.getAttributeList().size(); q++) {
                  boolean alreadyAdded = false;
                  for (int u=0; u < matchedAttributes.size(); u++)
                     if (rightTuple.getAttributeName(q).equals(matchedAttributes.get(u).getAttributeName()))
                        alreadyAdded = true;
                  if (!alreadyAdded)
                     allAttributes.add(rightTuple.getAttributeList().get(q));
               }
               for (int o=0; o < leftTuple.getAttributeList().size(); o++) {
                  boolean alreadyAdded = false;
                  for (int t=0; t < matchedAttributes.size(); t++)
                     if (leftTuple.getAttributeName(o).equals(matchedAttributes.get(t).getAttributeName()))
                        alreadyAdded = true;
                  if (!alreadyAdded)
                     allAttributes.add(leftTuple.getAttributeList().get(o));
               }
               Tuple joinedTuple = new Tuple(allAttributes);
               tuples1.add(joinedTuple);
            }
         }
      }
   }
            
                  
	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next() {
      currentIndex++;
      if (currentIndex >= tuples1.size()) 
         return null;
      return tuples1.get(currentIndex);
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}
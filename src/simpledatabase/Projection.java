package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;
   private int numOfAttributes = 0;
   private int currentAttribute = -1;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
      collectAttributes();
		
	}
   
   /**
     * It is used to collect the attributes in the target tuples
     */
   
   public void collectAttributes() {
      Tuple t;
      while ((t = child.next()) != null) {
         ArrayList<Attribute> nextAttributes = t.getAttributeList();
         for (int i=0; i < nextAttributes.size(); i++)
            if (nextAttributes.get(i).getAttributeName().equals(attributePredicate)) {
               newAttributeList.add(nextAttributes.get(i));
               numOfAttributes++;
            }
      }
   }
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		currentAttribute++;
      if (currentAttribute >= numOfAttributes) return null;
      ArrayList<Attribute> next = new ArrayList(1);
      next.add(newAttributeList.get(currentAttribute));
      Tuple nextT = new Tuple(next);
      return nextT;
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}
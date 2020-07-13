package Driver;


/**
 * Structure to  Variables 
 * 
 * @author
 *
 */
public class AttributesStructure{
    private String attributeName;
    private String attributeType;
    private String attributeVisibility;
    private String direction;
    private Object value;
	
    
	public AttributesStructure(String name, String type, String visibility,
			Object value) {
		super();
		attributeName = name;
		attributeType = type;
		attributeVisibility = visibility;
		this.value = value;
	}
   
	public AttributesStructure() {
		attributeName = "";
		attributeType = "";
		attributeVisibility = "";
	}
	public AttributesStructure(String variableName, String variableType, String variableVisibility) {
		this.attributeName = variableName;
		this.attributeType = variableType;
		this.attributeVisibility = variableVisibility;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeVisibility() {
		return attributeVisibility;
	}

	public void setAttributeVisibility(String attributeVisibility) {
		this.attributeVisibility = attributeVisibility;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String printAttribute(){
		String variable = "";	
		variable+=this.attributeType+" "+this.attributeName;
		return variable;
		
	}
	public String printCompleteVariable(){
		String variable = "";
		variable+=this.attributeVisibility+" "+this.attributeType+" "+this.attributeName;
		return variable;
	}

}


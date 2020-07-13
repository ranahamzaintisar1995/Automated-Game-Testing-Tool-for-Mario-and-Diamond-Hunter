package Driver;


import java.util.ArrayList;
/**
 * 
 * @author Asad Hussain Chachar
 * @description this class represents class structure and constituition
 */

public class ClassStructure {
	
	
	private String className;
	private String classVisibility;  
	private String classType;
	private boolean isAbstract;
	private boolean isFinal;
	private ArrayList<AttributesStructure>  classAttributes;
	private ArrayList<MethodsStructure>     classMethods;
	private ArrayList<RelationStructure> 	classRelationships;
	

	public ClassStructure() {
		super();
		classAttributes = new ArrayList<AttributesStructure>();
		classMethods = new ArrayList<MethodsStructure>();
		classRelationships=new ArrayList<RelationStructure>();
	}

	public ClassStructure(String className, String classVisibility,
			ArrayList<AttributesStructure> classAttributes,
			ArrayList<MethodsStructure> classMethods) {
		this.className = className;
		this.classVisibility = classVisibility;
		this.classAttributes = (classAttributes == null) ? (new ArrayList<AttributesStructure>()) : classAttributes;
		this.classMethods =    (classMethods    == null) ? (new ArrayList<MethodsStructure>())   : classMethods;
	}

/**
 * 	
 * @param String methodName
 * @param String body
 * @desc append a conditional expression in method body
 */
	public void appendBody(String methodName, String body){
		for(MethodsStructure ms: this.classMethods){
			if(ms.getmethodName().equals(methodName)){
				ms.setMethodBody(ms.getMethodBody()+ "\n" +body);
				break;
			}
		}
	}
/**
 * 
 * @param attrStruct
 * @description add Attributes to classes
 */
	public void addAttributes(AttributesStructure attrStruct){
		this.classAttributes.add(attrStruct);
	}
/**
 * 
 * @param paramList
 * @param methodBody
 * @see add Constructor to class
 */
	public void addConstructor(ArrayList<AttributesStructure> paramList,String methodBody){
		this.addMethods(new MethodsStructure(this.className, "", "public", paramList, methodBody));
	}
	public void addMethods(MethodsStructure methodStruct){
				this.classMethods.add(methodStruct);
	}
	
	public String printParentClass() {
		String classStructure= "";

		classStructure +=(this.classVisibility + " class " + this.className + "{\n");
		for(AttributesStructure var:this.classAttributes)
			classStructure += var.printAttribute()+";\n";
		for(MethodsStructure method:this.classMethods)
			classStructure += (method.getmethodName().equalsIgnoreCase(getClassName()) ?  method.printMethod() : method.printMethod())+"\n";
		classStructure += "}\n";

		return classStructure;

	}
	public String printBaseClass(){
		String classStructure= "";

		classStructure +=(this.classVisibility+" class "+this.className +"{\n");
		for(AttributesStructure var:this.classAttributes)
			classStructure += var.printAttribute()+";\n";
		for(MethodsStructure method:this.classMethods)
			classStructure += (method.getmethodName().equalsIgnoreCase(getClassName()) ?  method.printMethod() : method.printMethodEmpty())+"\n";
		classStructure += "}\n";

		return classStructure;
	}
	public String printDerivedClass(String baseClassName){
		String classStructure= "";

		classStructure +=(this.classVisibility+" class "+this.className +" extends "+baseClassName+" {\n");
		for(AttributesStructure attr:this.classAttributes)
			classStructure += attr.printAttribute()+";\n";
		for(MethodsStructure method:this.classMethods)
			classStructure += method.printMethod()+"\n";
		classStructure += "}\n";

		return classStructure;
	}

	public String printMethodEmpty(String methodName){
		String methodComplete = "";
		for(MethodsStructure method:this.classMethods)
			methodComplete += method.printMethodEmpty();//(method.getmethodName().equalsIgnoreCase(getClassName()) ?  method.printMethod() : method.printMethodEmpty())+"\n";
		return methodComplete;
	}
	public String printMethod(String methodName){
		String methodComplete = "";
		for(MethodsStructure method:this.classMethods)
			if(method.getmethodName().equalsIgnoreCase(methodName))
				methodComplete += method.printMethod();//(method.getmethodName().equalsIgnoreCase(getClassName()) ?  method.printMethod() : method.printMethod())+"\n";
		return methodComplete;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassVisibility() {
		return this.classVisibility;
	}

	public void setClassAccessibility(String classVisibility) {
		this.classVisibility = classVisibility;
	}

	public ArrayList<AttributesStructure> getClassAttributes() {
		return this.classAttributes;
	}

	public void setClassAttributes(ArrayList<AttributesStructure> classAttributes) {
		this.classAttributes = classAttributes;
	}

	public ArrayList<MethodsStructure> getClassMethods() {
		return classMethods;
	}

	public void setClassMethods(ArrayList<MethodsStructure> classMethods) {
		this.classMethods = classMethods;
	}

	public String getType() {
		return classType;
	}


	public void setType(String type) {
		classType = type;
	}

	

	public String getVisibility() {
		return classVisibility;
	}


	public void setVisibility(String visibility) {
		classVisibility = visibility;
	}


	public boolean isAbstract() {
		return isAbstract;
	}


	public void setAbstract(boolean abstract1) {
		isAbstract = abstract1;
	}


	public boolean isFinal() {
		return isFinal;
	}


	public void setFinal(boolean final1) {
		isFinal = final1;
	}

	public ArrayList<RelationStructure> getRelationships() {
		return classRelationships;
	}


	public void setRelationships(ArrayList<RelationStructure> relationships) {
		classRelationships = relationships;
	}

}

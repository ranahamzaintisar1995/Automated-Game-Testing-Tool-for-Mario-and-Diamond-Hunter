package Driver;


import java.util.ArrayList;

/***
 * 
 * @author Asad Hussain Chachar
 * @description It describes complete hierarchy of classes
 *
 * * @ClassesStructure is as:
 *                         1. ParentClass  (Board)
 *                         2. BaseLass     (BoardState)
 *                         3. DerivedClass (All Classes extended to BaseClass)
 *
 */
public class HierarchicalStructure {
	private ClassStructure parentClass;
	private ClassStructure baseClass;
	private ArrayList<ClassStructure> derivedClasses;
	
	public HierarchicalStructure(){
		this.parentClass = new ClassStructure();
		this.baseClass = new ClassStructure();
		this.derivedClasses = new ArrayList<ClassStructure>();
	}
	public HierarchicalStructure(ClassStructure baseClass,
			ArrayList<ClassStructure> derivedClasses,ClassStructure parentClass) {
		this.parentClass = parentClass;
		this.baseClass = baseClass;
		this.derivedClasses = derivedClasses;
	}	
	
	public ClassStructure getParentClass() {
		return parentClass;
	}



	
	public void addMethodToBaseClass(MethodsStructure methodStructure){
		this.baseClass.addMethods(methodStructure);
	}
	public void addMethodToParentClass(MethodsStructure methodStructure){
		this.parentClass.addMethods(methodStructure);
	}
	// else part of if in a guard statement
	public void appendBody(String derClassName, String methodName, String body){
		for(ClassStructure cs: this.derivedClasses)
			if(cs.getClassName().equals(derClassName)){
				cs.appendBody(methodName, body);
				break;
			}
	}

	//*********************************************
	public AttributesStructure addAttributeToBase(){
		
		//attribute for base class
		AttributesStructure baseAttribute = new AttributesStructure(parentClass.getClassName().toLowerCase(), parentClass.getClassName(), "private");
		
		// add Attribute in base class
		this.baseClass.addAttributes(baseAttribute);
		return baseAttribute;

	}
	public void addConstructorToBaseClass(AttributesStructure baseAttribute){
		ArrayList<AttributesStructure> baseAttributeList = new ArrayList<AttributesStructure>(); 		
		baseAttributeList.add(baseAttribute);
		
		String baseObjectName = baseAttribute.getAttributeName();
		
		String constructorBody = "this."+baseObjectName+" = "+baseObjectName+";";
		// add constructors in base class
		this.baseClass.addConstructor(baseAttributeList, constructorBody);
		// add constructors in derived classes
		for(ClassStructure cs: this.getDerivedClasses()){
			constructorBody = "super("+baseObjectName+");";
			cs.addConstructor(baseAttributeList, constructorBody);
		}
	}
	//**************xxxxxx*******************************
	public AttributesStructure addAttributeToParent(){
		
		//attribute for parent class
		AttributesStructure parentAttribute = new AttributesStructure(baseClass.getClassName().toLowerCase(), baseClass.getClassName(), "private");
		
		// add Attribute in parent class
		this.parentClass.addAttributes(parentAttribute);
		return parentAttribute;

	}
	public void addConstructorToParentClass(AttributesStructure parentAttribute){
		//******* 
		ArrayList<AttributesStructure> parentAttributeList = new ArrayList<AttributesStructure>(); 		
		parentAttributeList.add(parentAttribute);
		
		String parentObjectName = parentAttribute.getAttributeName();
		
		String constructorBody = "this."+parentObjectName+" = "+parentObjectName;
		// add constructors in base class
		this.parentClass.addConstructor(parentAttributeList, constructorBody);
		// add constructors in derived classes
		for(ClassStructure cs: this.getDerivedClasses()){
			constructorBody = "super("+parentObjectName+");";
			cs.addConstructor(parentAttributeList, constructorBody);
		}
	}
	//****************//*****************//****************
	public ClassStructure getBaseClass() {
		return baseClass;
	}
	public void setBaseClass(ClassStructure baseClass) {
		this.baseClass = baseClass;
	}
	public void setParentClass(ClassStructure parentClass) {
		this.parentClass = parentClass;

	}
	public void setDerivedClasses(ArrayList<ClassStructure> extendingClasses) {
		this.derivedClasses = extendingClasses;
	}
	public void addDerivedClass(ClassStructure derivedClass){
		this.derivedClasses.add(derivedClass);
	}	

	public ArrayList<ClassStructure> getDerivedClasses() {
		return derivedClasses;
	}
	public String printBaseClass(){
		return this.baseClass.printBaseClass()+"\n";
	}
	public String printDerivedClass(){
		String classHierarchy="";
		for(ClassStructure cs:this.derivedClasses)
			classHierarchy += cs.printDerivedClass(this.baseClass.getClassName())+"\n";

		return classHierarchy + "\n";
	}
	public String printParentClass() {
		return this.parentClass.printParentClass();
	}
	

}

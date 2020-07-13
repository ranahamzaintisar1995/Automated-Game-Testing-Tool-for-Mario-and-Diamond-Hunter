package Driver;



import java.util.ArrayList;

/**
 * Structure to Operations 
 * 
 * @autho
 * @description This class represent constituent of a method of class
 *
 */
public class MethodsStructure{
	String methodName; 
	String methodReturnType = "void"; 
	String methodVisibility;
	ArrayList<AttributesStructure> methodParameters;
	String methodBody;
	
	String oNonConditionBody = "";
	String condition = "";
	String conditionTrue_Body = "";
	String conditionFalse_Body = "";
	
	public MethodsStructure() {
		methodName = "";
		methodReturnType = "";
		methodVisibility = "";
		methodBody = "";		
		methodParameters = new ArrayList<AttributesStructure>();

	}
	public MethodsStructure(String methodName, String methodReturnType,
			String methodAccessibility, ArrayList<AttributesStructure> methodParameters,String methodBody) {
		this.methodName = methodName;
		this.methodReturnType = methodReturnType;
		this.methodVisibility = methodAccessibility;
		this.methodBody = methodBody == null ? "" : methodBody;
		this.methodParameters = methodParameters == null ? new ArrayList<AttributesStructure>() : methodParameters;
	}
	

	public String printMethodEmpty(){
		String method = "";
		method+=(this.methodVisibility+" "+this.methodReturnType+" "+ this.methodName+"(");
		for(AttributesStructure param : this.methodParameters){
			method += ( param.printAttribute()+" " );
		}
		method += "){ \n }";
		return method;
	}
	
	public String printMethod(){
		String method = "";
		method+=(this.methodVisibility+" "+this.methodReturnType+" "+ this.methodName+"(");
		for(AttributesStructure param : this.methodParameters){
			method += ( param.printAttribute()+" " );
		}
		method += "){\n"+ this.methodBody+"\n}";
		return method;
	}
	public String getMethodBody() {
		return methodBody;
	}
	public void setMethodBody(String methodBody) {
		this.methodBody = methodBody;
	}
	public void addParameter(AttributesStructure var){
		this.methodParameters.add(var);
	}
	/**************************************************************************/
	

	/**************************************************************************/
	public String getmethodName() {
		return this.methodName;
	}

	public void setmethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getmethodReturnType() {
		return this.methodReturnType;
	}

	public void setmethodReturnType(String methodReturnType) {
		this.methodReturnType = methodReturnType;
	}

	public String getmethodVisibility() {
		return this.methodVisibility;
	}

	public void setmethodAccessibility(String methodVisibility) {
		this.methodVisibility = methodVisibility;
	}

	public ArrayList<AttributesStructure> getmethodParameters() {
		return methodParameters;
	}

	public void setmethodParameters(ArrayList<AttributesStructure> methodParameters) {
		this.methodParameters = methodParameters;
	}


	public String getoNonConditionBody() {
		return oNonConditionBody;
	}



	public void setoNonConditionBody(String oNonConditionBody) {
		this.oNonConditionBody = oNonConditionBody;
	}



	public String getCondition() {
		return condition;
	}



	public void setCondition(String condition) {
		this.condition = condition;
	}



	public String getConditionTrue_Body() {
		return conditionTrue_Body;
	}



	public void setConditionTrue_Body(String conditionTrue_Body) {
		this.conditionTrue_Body = conditionTrue_Body;
	}



	public String getConditionFalse_Body() {
		return conditionFalse_Body;
	}



	public void setConditionFalse_Body(String conditionFalse_Body) {
		this.conditionFalse_Body = conditionFalse_Body;
	}
	
	
	
}


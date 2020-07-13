/*This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package Driver1;



import java.util.ArrayList;

/**
 * Structure to save code Details from information Received
 * from StateStructure
 * 
 * @author 
 * @description It contains complete list of hierarchy each class that is part of it
 * 
 * * @ClassesStructure is as:
 *                         1. ParentClass  (Board)
 *                         2. BaseLass     (BoardState)
 *                         3. DerivedClass (All Classes extended to BaseClass)
 */
public class JavaCodeStructure {

	private ArrayList<HierarchicalStructure> hierarchicalStructure;

	public JavaCodeStructure(){
		this.hierarchicalStructure = new ArrayList<HierarchicalStructure>();
	}

	public JavaCodeStructure(
			ArrayList<HierarchicalStructure> hierarchicalStructure) {
		this.hierarchicalStructure = hierarchicalStructure;
	}

	public void addBaseClass(ClassStructure baseClass){
		this.hierarchicalStructure.add(new HierarchicalStructure(baseClass, new ArrayList<ClassStructure>(),new ClassStructure()));//get(this.hierarchicalStructure.size()).setBaseClass(baseClass);
	}

	public void addClassStructures(ArrayList<ClassStructure> derivedClass,ClassStructure baseClass,ClassStructure parentClass){    	
		this.hierarchicalStructure.add(new HierarchicalStructure(baseClass,derivedClass,parentClass));
	}

	public void addHierarchicalClass(HierarchicalStructure hierarchicalStructure){
		this.hierarchicalStructure.add(hierarchicalStructure);
	}

	public ArrayList<HierarchicalStructure> getHierarchicalStructure() {
		return hierarchicalStructure;
	}

	public void setHierarchicalStructure(
			ArrayList<HierarchicalStructure> hierarchicalStructure) {
		this.hierarchicalStructure = hierarchicalStructure;
	}

	public String printClasses(){
		String allClassStructure = "";
		for(HierarchicalStructure hs: this.hierarchicalStructure)
			allClassStructure += hs.printParentClass() + "\n" + hs.printBaseClass() + "\n" + hs.printDerivedClass();
		return allClassStructure;
	}
	
	public void appendBody(String derClassName, String methodName, String body){
		this.hierarchicalStructure.get(0).appendBody(derClassName, methodName, body);
	}
	public void addConstructorsToDerivedClass(String baseClass){
		for(HierarchicalStructure hs : this.hierarchicalStructure)
			if(hs.getParentClass().getClassName().equalsIgnoreCase(baseClass)){
				hs.addConstructorToBaseClass(hs.addAttributeToBase());
				break;
			}
	}
	public void addAttributeToParentClass(String parentClass){
		for(HierarchicalStructure hs : this.hierarchicalStructure)
			if(hs.getParentClass().getClassName().equalsIgnoreCase(parentClass)){
				hs.addAttributeToParent();
				break;
			}
	}
	public void addParentClass(String baseClass,ClassStructure parentClass){
		for(HierarchicalStructure hs : this.hierarchicalStructure)
			if(hs.getBaseClass().getClassName().equalsIgnoreCase(baseClass)){
				hs.setParentClass(parentClass);
				break;
			}
	}
}


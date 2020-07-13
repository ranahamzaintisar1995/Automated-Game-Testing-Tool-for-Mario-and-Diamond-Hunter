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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Read data objects and Prints the data objects and write into java files
 * 
 * @author
 * @version 1.0
 * @since 2014-08-08
 */

public class CodeFileWriter {

	/**
	 * @param List of Class object and state machine code Structure
	 */
	public void printModel(ArrayList<ClassStructure> Classes,
			JavaCodeStructure codeStructure) {

		for (ClassStructure structure : Classes) {

			System.out.println("\n\n#####################"
					+ structure.getClassName() + " ####################\n\n");

			String code = "";

			/*
			 * CLASS NAME
			 */

			code += structure.getVisibility()
					+ (structure.isFinal() ? " final" : "")
					+ (structure.isAbstract() ? " abstract" : "") + " class "
					+ structure.getClassName() + " { \n";

			/*
			 * ATTRIBUTES
			 */

			for (AttributesStructure attribute : structure.getClassAttributes()) {

				code += attribute.getAttributeVisibility() + " "
						+ attribute.getAttributeType() + " "
						+ attribute.getAttributeName() + "; \n";

			}

			/*
			 * RELATIONS
			 */

			for (RelationStructure relationStructure : structure
					.getRelationships()) {

				/*
				 * IF CLASS LEFT NAME EQUAL TO RELATION CLASS CHANGE SIDE
				 */

				if (relationStructure.getClass_1().equals(
						structure.getClassAttributes())
						&& relationStructure.isNavigable_1()) {

					/*
					 * IF Multipcity NOT (*) SIMPLE ATTRIBUTE
					 */

					if (relationStructure.getMultipcity_Uper_2() != -1) {
						code += relationStructure.getVisibility() + " "
								+ relationStructure.getClass_2() + " "
								+ relationStructure.getRole_Name_1() + "; \n ";

					} else {

						/*
						 * IF Multipcity IS (*) ARRAYLIST
						 */

						code += relationStructure.getVisibility() + " "
								+ "java.util.ArrayList<"
								+ relationStructure.getClass_2() + "> "
								+ relationStructure.getRole_Name_1() + "; \n ";

					}
				}
				/*
				 * IF CLASS NAME NOT EQUAL TO RELATION CLASS
				 */
				else if (relationStructure.getClass_2().equals(
						structure.getClassName())
						&& !relationStructure.isNavigable_2()) {

					/*
					 * IF Multipcity NOT (*) SIMPLE ATTRIBUTE
					 */

					if (relationStructure.getMultipcity_Uper_1() != -1) {
						code += relationStructure.getVisibility() + " "
								+ relationStructure.getClass_1() + " "
								+ relationStructure.getRole_Name_1() + "; \n ";

					} else {

						/*
						 * IF Multipcity IS (*) ARRAYLIST
						 */

						code += relationStructure.getVisibility() + " "
								+ "java.util.ArrayList<"
								+ relationStructure.getClass_1() + "> "
								+ relationStructure.getRole_Name_1() + "; \n ";

					}

				} else if (relationStructure.isNavigable_1()
						&& relationStructure.isNavigable_2()) {

					/*
					 * IF CLASS LEFT NAME EQUAL TO RELATION CLASS CHANGE SIDE
					 */

					if (relationStructure.getClass_1().equals(
							structure.getClassName())) {

						/*
						 * IF Multipcity NOT (*) SIMPLE ATTRIBUTE
						 */

						if (relationStructure.getMultipcity_Uper_2() != -1) {
							code += relationStructure.getVisibility() + " "
									+ relationStructure.getClass_2() + " "
									+ relationStructure.getRole_Name_2()
									+ "; \n ";

						} else {

							/*
							 * IF Multipcity IS (*) ARRAYLIST
							 */

							code += relationStructure.getVisibility() + " "
									+ "java.util.ArrayList<"
									+ relationStructure.getClass_2() + "> "
									+ relationStructure.getRole_Name_2()
									+ "; \n ";

						}
					}
					/*
					 * IF CLASS NAME NOT EQUAL TO RELATION CLASS
					 */
					else if (relationStructure.getClass_2().equals(
							structure.getClassName())) {

						/*
						 * IF Multipcity NOT (*) SIMPLE ATTRIBUTE
						 */

						if (relationStructure.getMultipcity_Uper_1() != -1) {
							code += relationStructure.getVisibility() + " "
									+ relationStructure.getClass_1() + " "
									+ relationStructure.getRole_Name_1()
									+ "; \n ";

						} else {

							/*
							 * IF Multipcity IS (*) ARRAYLIST
							 */

							code += relationStructure.getVisibility() + " "
									+ "java.util.ArrayList<"
									+ relationStructure.getClass_1() + "> "
									+ relationStructure.getRole_Name_1()
									+ "; \n ";

						}

					}

					/*
					 * if (relationStructure.getMultipcity_Uper_2() != -1) {
					 * code += relationStructure.getVisibility() + " " +
					 * relationStructure.getClass_2() + " " +
					 * relationStructure.getRole_Name_1() + "; \n ";
					 * 
					 * } else { code += relationStructure.getVisibility() + " "
					 * + "java.util.ArrayList<" + relationStructure.getClass_2()
					 * + "> " + relationStructure.getRole_Name_1() + "; \n ";
					 * 
					 * }
					 */
				}

			}
			/*
			 * OPERATIONS
			 */

			for (MethodsStructure opperation : structure.getClassMethods()) {

				code += opperation.getmethodVisibility() + " "
						+ opperation.getmethodReturnType() + " "
						+ opperation.getmethodName() + " (";

				int i = opperation.getmethodParameters().size();
				for (AttributesStructure attribute : opperation
						.getmethodParameters()) {
					i--;

					code += attribute.getAttributeType() + " "
							+ attribute.getAttributeName();
					if (i > 0)
						code += " , ";
				}

				code += " ){ ";
				
		
		for(HierarchicalStructure cs: codeStructure.getHierarchicalStructure()){
			for(MethodsStructure methodsStructure : cs.getParentClass().getClassMethods())
			{
				if(methodsStructure.getmethodName().equals(opperation.getmethodName())&&cs.getParentClass().getClassName().equals(structure.getClassName())){
					code += " \n  "+methodsStructure.getMethodBody() +" \n ";
				}
			}
		}
						
		code +=	"\n } \n";

			}

			code += " } \n";

			PrintWriter writer;
			createFolder("GeneratedCode");
			  try { writer = new PrintWriter("GeneratedCode/" + structure.getClassName()+ ".java", "UTF-8");
			  writer.println(code);
			  writer.close();
			  
			  } catch (FileNotFoundException | UnsupportedEncodingException e)
			  { e.printStackTrace(); }
			 

			System.out.print(code);
		}

	}
	
	public void createFolder(String folderName){
		try{
			File newFolder = new File(folderName);
			if (!newFolder.exists()) {
				boolean created = newFolder.mkdir();
				System.out.println(created);
			}
		}catch(Exception e){}
	}
	
	public void printStateClasses(JavaCodeStructure javaCodeStructure){
		PrintWriter writer=null;
		
		for(HierarchicalStructure hs:javaCodeStructure.getHierarchicalStructure()){	
			createFolder("GeneratedCode\\"+hs.getParentClass().getClassName()); //folderName
			 
			try { writer = new PrintWriter("GeneratedCode/"+ hs.getParentClass().getClassName() +"/"+ hs.getBaseClass().getClassName()+ ".java", "UTF-8");
			  writer.println(hs.getBaseClass().printBaseClass());
			  writer.close();
			  } catch (FileNotFoundException | UnsupportedEncodingException e)
			  { e.printStackTrace(); }
			
			for(ClassStructure cs : hs.getDerivedClasses()){
				try { writer = new PrintWriter("GeneratedCode/"+ hs.getParentClass().getClassName() +"/"+ cs.getClassName()+ ".java", "UTF-8");
				  writer.println(cs.printDerivedClass(hs.getBaseClass().getClassName()));
				  writer.close();
				  } catch (FileNotFoundException | UnsupportedEncodingException e)
				  { e.printStackTrace(); }	
			}			
//		}		
	}
	}

}

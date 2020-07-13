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

/**
 * 
 * @author  
 *
 */
public class RelationStructure {
	
	private String Class_1;
	private String Class_2;
	private String Role_Name_1;
	private String Role_Name_2;
	private String Visibility; 
	private int multipcity_Lower_1; 
	private int multipcity_Lower_2; 
	private int multipcity_Uper_1; 
	private int multipcity_Uper_2; 
	private boolean navigable_1; 
	private boolean Navigable_2;
	
	public String getClass_1() {
		return Class_1;
	}
	public void setClass_1(String class_1) {
		Class_1 = class_1;
	}
	public String getClass_2() {
		return Class_2;
	}
	public void setClass_2(String class_2) {
		Class_2 = class_2;
	}
	
	public String getRole_Name_1() {
		return Role_Name_1;
	}
	public void setRole_Name_1(String role_Name_1) {
		Role_Name_1 = role_Name_1;
	}
	public String getRole_Name_2() {
		return Role_Name_2;
	}
	public void setRole_Name_2(String role_Name_2) {
		Role_Name_2 = role_Name_2;
	}
	public boolean isNavigable_1() {
		return navigable_1;
	}
	public void setNavigable_1(boolean navigable_1) {
		this.navigable_1 = navigable_1;
	}
	public boolean isNavigable_2() {
		return Navigable_2;
	}
	public void setNavigable_2(boolean navigable_2) {
		Navigable_2 = navigable_2;
	}
	public int getMultipcity_Lower_1() {
		return multipcity_Lower_1;
	}
	public void setMultipcity_Lower_1(int multipcity_Lower_1) {
		this.multipcity_Lower_1 = multipcity_Lower_1;
	}
	public int getMultipcity_Lower_2() {
		return multipcity_Lower_2;
	}
	public void setMultipcity_Lower_2(int multipcity_Lower_2) {
		this.multipcity_Lower_2 = multipcity_Lower_2;
	}
	public int getMultipcity_Uper_1() {
		return multipcity_Uper_1;
	}
	public void setMultipcity_Uper_1(int multipcity_Uper_1) {
		this.multipcity_Uper_1 = multipcity_Uper_1;
	}
	public int getMultipcity_Uper_2() {
		return multipcity_Uper_2;
	}
	public void setMultipcity_Uper_2(int multipcity_Uper_2) {
		this.multipcity_Uper_2 = multipcity_Uper_2;
	}
	public String getVisibility() {
		return Visibility;
	}
	public void setVisibility(String visibility) {
		Visibility = visibility;
	}
	


}

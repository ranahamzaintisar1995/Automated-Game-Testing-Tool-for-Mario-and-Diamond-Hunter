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

public class StateMachineState {

	private String name;
	private String parentStateName;
	
	public StateMachineState(String name, String parentStateName) {
		this.name = name;
		this.parentStateName=parentStateName;
	}

	public void print() {
		System.out.println("StateName: " + name+ " Parent: "+ parentStateName);
	}
	
	public String getStateName() {
		return this.name;
	}
	
	public String getParentStateName() {
		return this.parentStateName;
	}
}

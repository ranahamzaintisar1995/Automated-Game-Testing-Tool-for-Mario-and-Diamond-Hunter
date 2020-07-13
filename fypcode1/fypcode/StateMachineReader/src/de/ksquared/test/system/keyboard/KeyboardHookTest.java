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
package de.ksquared.test.system.keyboard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;

public class KeyboardHookTest {
	Boolean checkthis = true;
	public ArrayList<String> ConvertKeys() throws NumberFormatException, IOException
	{
		ArrayList<String> keys =new ArrayList<>();
		int c=0;
		String p;
		FileReader file = null;
		try {
			file = new FileReader("sample.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader reader= new BufferedReader(file);
		String line = null;
		String o;
		int count=0;

		while ((line = reader.readLine()) != null)
		{
			String parsed = null;
		    String [] parts = line.split(" ");

		    c=Integer.parseInt(parts[0].toString());
		   
		    if(parts.length > 1)
		    {
		    String [] array1= parts[1].split(",");
		    c=Integer.parseInt(parts[0].toString());
		    if(c== 32 )
			   {  parsed = "space";   }
		    else if(c== 8)
		       {  parsed = "backspace";  }
		    else if(c== 37 )
			   {  parsed = "Left";   }
		    else if(c== 38)
		       {  parsed = "Up";  }
		    else if(c== 39 )
			   {  parsed = "Right";   }
		    else if(c== 40)
		       {  parsed = "Down";  }
		    else if(array1.length <= 1)
		    {
		    	 p = Character.toString ((char) c);
		    	 parsed = p;
		    }
		    else if(array1.length <= 2)
		    { 
		    	o=array1[1].substring(0,array1[1].length()-1);
		    	parsed = o;
		    }
		    else
		    {
		    	o=array1[1].substring(0,array1[1].length());
		    	parsed =o;
		    }
		    if(!(keys.contains(parsed)))
		    {
		    	keys.add(parsed);
		    }
		}
		}
		return keys;
	}
	
	public void start()
	{
		checkthis =true;
	}
	public void stop()
	{
		checkthis =false;
	}
	public void main() throws FileNotFoundException {

	    if(checkthis)
	    {
	    	FileOutputStream file = new FileOutputStream("sample.txt");
		    PrintStream tee = new PrintStream(file);
		    System.setOut(tee);
		new GlobalKeyListener().addKeyListener(new KeyAdapter() {
			@Override public void keyPressed(KeyEvent event) { System.out.println(event); }
			
			/*@Override public void keyReleased(KeyEvent event) {
				System.out.println(event);
				if(event.getVirtualKeyCode()==KeyEvent.VK_ADD
				&& event.isCtrlPressed())
					System.out.println("CTRL+ADD was just released (CTRL is still pressed)");
			}*/
		});
		while(checkthis)
			try { Thread.sleep(100); }
			catch(InterruptedException e) { e.printStackTrace(); }
		
	}
	}
}
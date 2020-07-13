package Driver;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;

import de.ksquared.system.keyboard.KeyEvent;

public class KeyMaps {

	Robot run = null;
	public KeyMaps()
	{
		try {
			this.run = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void runTransition(String transition)
	{
		int key = KeyEvent.VK_SPACE;
		if(transition.equals("RunForward"))
		{
			
			key = KeyEvent.VK_RIGHT;
			run.keyPress(key);
			run.setAutoDelay(2000);				
			run.keyRelease(key);
			System.out.println("RunForward");
		}
		else if(transition.equals("RunBackward") )
		{
			key = KeyEvent.VK_LEFT;
			run.keyPress(key);
			run.setAutoDelay(2000);				
			run.keyRelease(key);
			System.out.println("RunBackward");
		}
		else if(transition.equals("EnemeyVisible") || transition.equals("EnemyVisible"))
		{
			Random rn = new Random();
			int i = rn.nextInt(2);
			if(i == 0)
			{
				int key1 = KeyEvent.VK_LEFT;
				key = KeyEvent.VK_SPACE;
				run.keyPress(key1);
				run.setAutoDelay(2000);
				run.keyPress(key);
								
				run.setAutoDelay(500);				
				run.keyRelease(key);
				run.keyRelease(key1);
			}
			else
			{
				int key1 = KeyEvent.VK_RIGHT;
				key = KeyEvent.VK_SPACE;
				run.keyPress(key1);
				run.setAutoDelay(2000);
				run.keyPress(key);
								
				run.setAutoDelay(500);				
				run.keyRelease(key);
				run.keyRelease(key1);
			}
			System.out.println("EnemeyVisible");
		}
		else if(transition.equals("Enemey") || transition.equals("JumpOnEnemey"))
		{
			key = KeyEvent.VK_SPACE;
			run.keyPress(key);
							
			run.setAutoDelay(500);				
			run.keyRelease(key);
			System.out.println("Enemey");
		}
		else if(transition.equals("EnemeyDead") || transition.equals("CoinsVisible"))
		{
			Random rn = new Random();
			int i = rn.nextInt(2);
			if(i == 0)
			{
				key = KeyEvent.VK_RIGHT;
				run.keyPress(key);
				run.setAutoDelay(2000);				
				run.keyRelease(key);
			}
			else
			{
				key = KeyEvent.VK_LEFT;
				run.keyPress(key);
				run.setAutoDelay(2000);				
				run.keyRelease(key);
			}
			System.out.println("Enemey dead/Coins visible");
		}
				
	}
}

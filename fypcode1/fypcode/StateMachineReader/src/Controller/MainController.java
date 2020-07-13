package Controller;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.xmi.UnresolvedReferenceException;
import Reports.OpenFlashChartReport;
import com.lowagie.text.List;

import Controller1.AGTT;
import Driver.EA;
import Driver.GameAutoStartStop;
import Driver.HillClimbing;
import Driver.JavaCodeStructure;
import Driver.Order;
import Driver.RecievingThread1;
import Driver.RecievingThread2;
import Driver.RunTestPath;
import Driver.SSGA;
import Driver.StateMachineFlattening;
import Driver.StateMachineReader;
import Driver.StateMachineState;
import Driver.StateMachineTransition;
import Driver.TestCase;
import Driver.TestPath;
import Driver.UMLReader;
import Driver1.RunTestcaseThread;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class MainController extends AnchorPane implements Initializable {
	static Timer timer;
	static int interval;
	int p = 1;
	@FXML
	private ProgressBar bar1 = new ProgressBar();
	@FXML
	private ProgressBar bar2 = new ProgressBar();
	@FXML
	private ProgressBar bar3 = new ProgressBar();
	@FXML
	private TextArea textAreaLiveFeed = new TextArea();
	@FXML
	private TextArea textAreaTestCases = new TextArea();
	@FXML
	private Button PlayGameWRTtime = new Button();
	@FXML
	private ProgressBar progressbar1 = new ProgressBar();
	@FXML
	private TextField textfieldAlgo = new TextField();
//	@FXML
//	private RadioButton radio1 = new RadioButton();
//	@FXML
//	private RadioButton radio2 = new RadioButton();
//	@FXML
//	private RadioButton radio3 = new RadioButton();
	@FXML
	private TextField CurrentState = new TextField();
	@FXML
	private TextField GoalsAchieved = new TextField();
	@FXML
	private Button Exit = new Button();
	@FXML
	private TextField GoalsField = new TextField();
	@FXML
	private TextField DiamondsField = new TextField();
	@FXML
	private TextField HealthField = new TextField();
	//@FXML
	//private Button MarioButton=new Button();
	@FXML
	private TextField CurrentTransitionText = new TextField();
	@FXML
	private TextField NextTransitionText = new TextField();
	@FXML
	private TextField PreviousTransitionText = new TextField();
	@FXML
	private RadioButton MarioButton=new RadioButton();
	@FXML
	private RadioButton DiamondButton=new RadioButton();
	@FXML
	private Button OptionButton=new Button();
	@FXML
	private TextField OptionTextArea=new TextField();
	@FXML
	private Button SelectStateMachine=new Button();
	@FXML
	private TextField SelectStateMachineField=new TextField();
	@FXML
	private Button RunButton=new Button();
	@FXML
	private Button ClearButton=new Button();
	@FXML
	private Button ExitGame=new Button();
	GameAutoStartStop gassDiamond=new GameAutoStartStop();
	Driver1.GameAutoStartStop gassMario=new Driver1.GameAutoStartStop();
	
	ArrayList<ArrayList<String>>values = new ArrayList<ArrayList<String>>();
	private int lastNumberInt = 0;
	private int lastNumberInt1 = 0;
	private int lastNumberInt2 = 3;
	private int count=1;
	public String path; 
	public String jarPath;
	static AGTT obj = null;

	 
	GameAutoStartStop gass = new GameAutoStartStop();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//**********************DIAMOND HUNTER GAME**************************
		GoalsField.setText("0/2");
		DiamondsField.setText("0/40");
		HealthField.setText("3/3");
		bar3.setProgress(1);
		//***********************************************************
		
		//**********************MARIO GAME**************************
		obj=new AGTT();
		obj.generateTestCases(); 
		ArrayList <String> testCases =obj.getAllTestCases();
		obj.SetTestCases(testCases);
		//***********************************************************

		textAreaLiveFeed.setEditable(false);
		textAreaLiveFeed.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		/*textAreaTestCases.setEditable(false);
		textAreaTestCases.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });*/
		GoalsAchieved.setEditable(false);
		GoalsAchieved.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		GoalsField.setEditable(false);
		GoalsField.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		DiamondsField.setEditable(false);
		DiamondsField.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		HealthField.setEditable(false);
		HealthField.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		OptionTextArea.setEditable(false);
		OptionTextArea.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		SelectStateMachineField.setEditable(false);
		SelectStateMachineField.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		CurrentState.setEditable(false);
		CurrentState.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		CurrentTransitionText.setEditable(false);
		CurrentTransitionText.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		NextTransitionText.setEditable(false);
		NextTransitionText.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
		PreviousTransitionText.setEditable(false);
		PreviousTransitionText.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent t) { t.consume(); }
	    });
	
		
	}

	
	private static final int setInterval() {
		if (interval == 31)
			timer.cancel();

		interval += 1;
		return interval;
	}
	
	
	public void ExitGUI() {

		OpenFlashChartReport o=new OpenFlashChartReport(values);
		System.out.println(values); 
		
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForDiamond.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error on 249");
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForTestPaths.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e)    {
			// TODO Auto-generated catch block
			System.out.println("error on 261");
			e.printStackTrace();
		}

 		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForGoals.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error on 274");
			e.printStackTrace();
		}
 		
 		try {
			FileWriter fwOb;
			fwOb = new FileWriter("GameTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error on 287");
			e.printStackTrace();
		}

		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForHealth.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error on 300");
			e.printStackTrace();
		}

		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentState.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 312");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForNextTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 324");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 336");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForPreviousTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error on 349");
			e.printStackTrace();
		}
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("GameCordinates.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error on 362");
			e.printStackTrace();
		}
		
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentTransitionMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error on 376");
			e.printStackTrace();
		}
		
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForPreviousTransitionMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 389");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentStateMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 402");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForNextTransitionMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 414");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	
	
	
	public void Options(ActionEvent event) throws IOException
	{ 
		FileChooser fileChooser = new FileChooser();
		
		File selectedFile = fileChooser.showOpenDialog(null);
		
		if (selectedFile != null) 
		{
				OptionTextArea.setText(selectedFile.getAbsolutePath());
				jarPath=selectedFile.getAbsolutePath();
		}	

		}
	
	
	
	public void SelectMachine(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		
		if (selectedFile != null) 
		{
			SelectStateMachineField.setText(selectedFile.getAbsolutePath());
			path=selectedFile.getAbsolutePath();
		}

	}
	
	public void clearingFiles(ActionEvent event)
	{
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForDiamond.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 464");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForTestPaths.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e)    {
			System.out.println("error on 476");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForGoals.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 489");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		try {
			FileWriter fwOb;
			fwOb = new FileWriter("GameTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 502");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForHealth.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 515");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentState.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 528");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForNextTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 540");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 552");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForPreviousTransition.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 564");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("GameCordinates.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 576");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentTransitionMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 589");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForPreviousTransitionMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 603");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForCurrentStateMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 615");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForNextTransitionMario.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			System.out.println("error on 627");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void ExitGameAction(ActionEvent event){


		if(OptionTextArea.getText().contains("mario")&&SelectStateMachineField.getText().contains("Mario"))
		{
			gassMario.StopGame();
		}
		
		else if(OptionTextArea.getText().contains("diamond")&&SelectStateMachineField.getText().contains("DiamondHunter"))
		{
			gassDiamond.StopGame();
		}
	}
	
	
	public void RunButtonAction(ActionEvent event) throws IOException
	{
	
		if(OptionTextArea.getText().contains("mario")&&SelectStateMachineField.getText().contains("Mario"))
		{
			RunMarioGame();
		}
		
		else if(OptionTextArea.getText().contains("diamond")&&SelectStateMachineField.getText().contains("DiamondHunter"))
		{
		
			RunDiamondHunterGame();
		}
		
		else{
			RunDiamondHunterGame();
		}
		
	}
	
	
	
		//+++++++++++++++++++Code for Diamond Hunter+++++++++++++++++++++++++++++++++
	
		public void RunDiamondHunterGame() throws IOException
		{
			StateMachineReader smReader = new StateMachineReader();
			UMLReader reader = new UMLReader();
			JavaCodeStructure javaCodeStructure = new JavaCodeStructure();

			Object objModel = reader.loadModel((new File(path)).toURI().toString());

			try {
				javaCodeStructure = smReader.processTheModel(objModel);
			} catch (UnresolvedReferenceException e) {
				System.out.println("error on 684");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ArrayList<StateMachineState> states;
			ArrayList<StateMachineTransition> transitions = null;

			states = smReader.getStates();
			PrintWriter writer = null;
			try {
				writer = new PrintWriter("newStates.txt", "UTF-8");
			} catch (FileNotFoundException e1) {
				System.out.println("error on 697");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (StateMachineState stateMachineState : states) {
				// System.out.println(stateMachineState.getStateName());
				try {

					writer.println(stateMachineState.getStateName());

				} catch (Exception e) {
					// do something
				}
			}
			writer.close();
			
			transitions = smReader.getTransitions();
			PrintWriter writer2 = null;
			try {
				writer2 = new PrintWriter("newTransitions.txt", "UTF-8");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (StateMachineTransition stateMachineTransition : transitions) {
				try {

					writer2.println(stateMachineTransition.getTransitionName());
					// System.out.println(stateMachineTransition.getTransitionName());

				} catch (Exception e) {
					// do something
				}
			}
			writer2.close();

			// ========================================
			StateMachineFlattening stateMachineFlattening = new StateMachineFlattening();
			ArrayList<StateMachineTransition> modifiedTransitions = stateMachineFlattening
					.getPathReadyTransitions(transitions);

			final TestCase testCases = new TestCase();
			testCases.generateTestPaths(modifiedTransitions, null, null, new TestPath());

			// testCases.printPathsOnConsole();
			// ========================================
			// ======================New
			// Code==============================================
	/*		ArrayList<TestPath> sample = testCases.getTestPaths();
			for (int i = 0; i < sample.size(); i++) {
				System.out.println(i);
				sample.get(i).consolePrint();
			}
	*/
			// Order o=new Order(testCases);
			// StateMachineTransition t1=transitions.get(17);
			// StateMachineTransition t2=transitions.get(0);
			//RecievingThread rt = new RecievingThread();
			RecievingThread1 rt1 = new RecievingThread1();
			RecievingThread2 rt2 = new RecievingThread2();
			
			
		/*	
			// *****************************SSGA*******************************************

			if (radio2.isSelected()) {

				gass.StartGame();
				SSGA steadySearch = new SSGA();

				rt.start();
				rt1.start();
				steadySearch.Solution(testCases);

				interval = 0;
				timer = new Timer();
				long delay = 1000;
				long period = 1000;

				// ************************************
				// for showing in test cases textarea
				ArrayList<TestPath> sample1 = testCases.getTestPaths();
				for (int i = 0; i < 100; i++) {
					// System.out.println(sample1.get(i).consolePrint1());
					textAreaTestCases.appendText("Test Case: " + (i + 1) + "\n");
				}
				// ************************************

				timer.scheduleAtFixedRate(new TimerTask() {

					public void run() {

						ArrayList<TestPath> sample = testCases.getTestPaths();
						int x = setInterval();
						String s = Integer.toString(x);

						textAreaLiveFeed.setText("test case:" + Integer.toString(x) + "\n");
						textAreaLiveFeed.appendText(sample.get(x - 1).consolePrint1() + "\n");
					}
				}, delay, period);

			}

			// *******************************Hill
			// climbing***************************
			else if (radio1.isSelected()) {

				new Thread() {
					public void run() {
						gass.StartGame();
					}
				}.start();
				HillClimbing h = new HillClimbing(states);

				rt.start();
				rt1.start();
				h.evaluate(testCases);



			}


		*/
			// ****************************1+1EA***********************************
		//	 if (radio3.isSelected()) {
				new Thread() {
					public void run() {
						gass.StartGame(jarPath);
					}
				}.start();

			//	rt.start();
				rt1.start();
				rt2.start();
				EA evolutionary = new EA();

				 evolutionary.EA_Run(testCases);
				// evolutionary.setCheck(rt1.getCheck());

				// ************************************
				// for showing in test cases textarea
				// ArrayList<TestPath> sample1=testCases.getTestPaths();
			/*	for (int i = 0; i < 100; i++) {
					// System.out.println(sample1.get(i).consolePrint1());
					textAreaTestCases.appendText("Test Case: " + (i + 1) + "\n");
				}
				// ************************************

				*/
				
				//*********************** GUI Diamonds Display*****************************
				double size = 40.0;
				new Thread() {
					public void run() {
						String sCurrentLine;

						BufferedReader br;
						try {
							br = new BufferedReader(new FileReader("ForDiamond.txt"));

							while (true) {
								String lastLine = "";

								try {
									while ((sCurrentLine = br.readLine()) != null) {
										lastLine = sCurrentLine;
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								final Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");
								// String input = "...";
								Matcher matcher = lastIntPattern.matcher(lastLine);

								if (matcher.find()) {
									String someNumberStr = matcher.group(1);
									lastNumberInt = Integer.parseInt(someNumberStr);
									Platform.runLater(() -> bar2.setProgress((lastNumberInt / size) + 0.025));
									DiamondsField.setText(lastNumberInt + "/40");
								}

								try {
									Thread.sleep(1000);
								} catch (InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				//***************************************************************************
				
				
				//*********************** GUI Goals Display*****************************
				double size1 = 2.0;
				new Thread() {
					public void run() {
						String sCurrentLine1;

						BufferedReader br1;
						try {
							br1 = new BufferedReader(new FileReader("ForGoals.txt"));

							while (true) {
								String lastLine1 = "";

								try {
									while ((sCurrentLine1 = br1.readLine()) != null) {
										lastLine1 = sCurrentLine1;
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								final Pattern lastIntPattern1 = Pattern.compile("[^0-9]+([0-9]+)$");
								Matcher matcher1 = lastIntPattern1.matcher(lastLine1);

								if (matcher1.find()) {
									String someNumberStr = matcher1.group(1);
									lastNumberInt1 = Integer.parseInt(someNumberStr);
									Platform.runLater(() -> bar1.setProgress((lastNumberInt1 / size1)));
									GoalsField.setText(lastNumberInt1 + "/2");
								}

								try {
									Thread.sleep(1000);
								} catch (InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				//***************************************************************************
				
				
				
				//*********************** GUI Health Display*****************************
				double size2 = 3.0;
				new Thread() {
					public void run() {
						String sCurrentLine2;

						BufferedReader br2;
						try {
							br2 = new BufferedReader(new FileReader("ForHealth.txt"));

							while (true) {
								String lastLine2 = "";

								try {
									while ((sCurrentLine2 = br2.readLine()) != null) {
										lastLine2 = sCurrentLine2;
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								final Pattern lastIntPattern2 = Pattern.compile("[^0-9]+([0-9]+)$");
								Matcher matcher2 = lastIntPattern2.matcher(lastLine2);

								if (matcher2.find()) {
									String someNumberStr = matcher2.group(1);
									lastNumberInt2 = Integer.parseInt(someNumberStr);
									Platform.runLater(() -> bar3.setProgress((lastNumberInt2 / size2)));
									HealthField.setText(lastNumberInt2 + "/3");
								}

								try {
									Thread.sleep(1000);
								} catch (InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				//***************************************************************************
				
				
				
				//*********************** GUI Current Transition Display*****************************
			

				new Thread() {
					public void run() {
						
						
						String sCurrentLine9;

						BufferedReader br9;
						try {
							br9 = new BufferedReader(new FileReader("ForCurrentTransition.txt"));

							while (true) {
								String lastLine9 = "";

								try {
									while ((sCurrentLine9 = br9.readLine()) != null) {
										//System.out.println(sCurrentLine3);
										lastLine9= sCurrentLine9;
									}
								} catch (IOException e) {
									// TODO Auto-generated catch bloc 
									System.out.println("error in thread");
									e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch bloc 
									System.out.println("ERRROORRRR");
									e.printStackTrace();
								
								}
								
								
								  CurrentTransitionText.setText(lastLine9);
								 
								
								//OpenFlashChartReport o=new OpenFlashChartReport();
								if(lastLine9.contains("Run")){
									ArrayList<String> temp=new ArrayList<String>();
									temp.add(lastLine9);
									temp.add("User Generated");
									temp.add("Pass");
									values.add(temp);
								}
									
								else{
									ArrayList<String> temp=new ArrayList<String>();
									temp.add(lastLine9);
									temp.add("System Generated");
									temp.add("----");
									values.add(temp);
								}
								
								
								
								
								
								
								//CurrentTransitionText.setText(lastLine3 );
								/*
								if(lastLine3.contains("RunForward")||lastLine3.contains("RunBackward")||lastLine3.contains("RunDown")||lastLine3.contains("RunUp")){
									CurrentTransitionText.setText(lastLine3 );
									CurrentTransitionText.setStyle("-fx-text-fill: #00ff00;");
								}
								else{
									CurrentTransitionText.setText(lastLine3 );
									CurrentTransitionText.setStyle("-fx-text-fill: #ff4500;");
								}
								*/ 
								
								try {
									Thread.sleep(1000);
								} catch (InterruptedException ex) {
									System.out.println("error on 1033");
									Thread.currentThread().interrupt();
								} catch (Exception e) {
									// TODO Auto-generated catch bloc 
									System.out.println("error in thread 1071");
									e.printStackTrace();
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							System.out.println("error on 1037");
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch bloc 
							System.out.println("error in thread 1080");
							e.printStackTrace();
						}
					}
				}.start();

				//***************************************************************************
				
				
				//*********************** GUI Next Transition Display*****************************
				new Thread() {
					public void run() {
						String sCurrentLine4;

						BufferedReader br4;
						try {
							br4 = new BufferedReader(new FileReader("ForNextTransition.txt"));

							while (true) {
								String lastLine4 = "";

								try {
									while ((sCurrentLine4 = br4.readLine()) != null) {
										lastLine4 = sCurrentLine4;
									}
								} catch (Exception e) {
									System.out.println("error on 1066");
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								NextTransitionText.setText(lastLine4 );
								try {
									Thread.sleep(1000);
								} catch (Exception ex) {
									System.out.println("error on 1074");
									Thread.currentThread().interrupt();
								}
							}
						} catch (Exception e) {
							System.out.println("error on 1079");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				//***************************************************************************

				
				
				
				//*********************** GUI Previous Transition Display*****************************
				new Thread() {
	   				public void run() {
    						String sCurrentLine5;

 	 	 	   			BufferedReader br5;
	     	 				try {
							br5 = new BufferedReader(new FileReader("ForPreviousTransition.txt"));

							while (true) {
								String lastLine5 = "";

								try {
									while ((sCurrentLine5 = br5.readLine()) != null) {
										lastLine5 = sCurrentLine5;
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									System.out.println("error on 1108");
									e.printStackTrace();
								}
								PreviousTransitionText.setText(lastLine5 );
								try {
									Thread.sleep(1000);
								} catch (Exception ex) {
									System.out.println("error on 1115");
									Thread.currentThread().interrupt();
								}
							}
						} catch (Exception e) {
							System.out.println("error on 1120");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				//***************************************************************************
				
				
				
				//*********************** GUI Current State Display*****************************
				new Thread() {
					public void run() {
						String sCurrentLine6;

						BufferedReader br6;
						try {
							br6 = new BufferedReader(new FileReader("ForCurrentState.txt"));

  							while (true) {
  								String lastLine6 = "";
 
								try {
									while ((sCurrentLine6 = br6.readLine()) != null) {
		 								lastLine6 = sCurrentLine6;
									}
								} catch (Exception e) {
									System.out.println("error on 1147");
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
 	 							CurrentState.setText(lastLine6 );
								try {
									Thread.sleep(1000);
								} catch (Exception ex) {
									System.out.println("error on 1155");
									Thread.currentThread().interrupt();
								}
 							}
						} catch (Exception e) {
							System.out.println("error on 1160");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
 				//***************************************************************************
				
				
				
				//*********************** GUI Live Feed Display*****************************
				new Thread() {
					public void run() {
 						String sCurrentLine6;

						BufferedReader br6;
						try {
							br6 = new BufferedReader(new FileReader("ForTestPaths.txt"));

							while (true) {
								String lastLine6 = "";
 
								try {
									while ((sCurrentLine6 = br6.readLine()) != null) {
										lastLine6 = sCurrentLine6;
									} 
								} catch  (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								textAreaLiveFeed.setText("TestCase: "+count +"\n");
								textAreaTestCases.appendText("Test Case: " + count + "\n");
								
								String[] tokens = lastLine6.split(",");
								for (String t : tokens)
								textAreaLiveFeed.appendText(t+"\n" );
								
								
							/*	final ColorPicker colorPicker=new ColorPicker();
								colorPicker.valueProperty().addListener((observable,oldColor,newColor)->
								textAreaLiveFeed.setStyle("-fx-text-fill: "+toRedString(newColor)+";"
										)
										);
								*/
								
								
								try {
									Thread.sleep(1000);
								} catch (InterruptedException ex) {
									System.out.println("error on 1209");
									Thread.currentThread().interrupt();
								}
								count++;
							}
						} catch (FileNotFoundException e) {
							System.out.println("error on 1215");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}.start();
				//***************************************************************************

			}

			
		

		
	
	
	
	
	
	
		//+++++++++++++++++++++++++++Code for MARIO+++++++++++++++++++++++++++++++++++++
		public void RunMarioGame()
		{
			int min=5;
			obj.StartGame();
			min = min*60000;
			obj.prepareTestcases(min);

			
			new Thread() {
				public void run() {
					String sCurrentLine9;

					BufferedReader br9;
					try {
						br9 = new BufferedReader(new FileReader("TestCases1.txt"));

						while (true) {
							String lastLine9 = "";
								int counter=1;
							try {
								while ((sCurrentLine9 = br9.readLine()) != null) {
								//	System.out.println(sCurrentLine6);
									lastLine9 = sCurrentLine9;
									//System.out.println(lastLine9);
									//textAreaLiveFeed.setText(lastLine9 );
									textAreaLiveFeed.setText("Test Case: "+Integer.toString(counter)+"\n");
									textAreaTestCases.appendText("Test Case"+Integer.toString(counter)+"\n");
									String[] tokens = lastLine9.split(",");
									for (String t : tokens)
									textAreaLiveFeed.appendText(t+"\n" );
									 counter++;
									try {
										Thread.sleep(15000);
									} catch (InterruptedException ex) {
										Thread.currentThread().interrupt();
									} 
																	}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				
							
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			
		
			/*
			//*********************** GUI Current Transition MARIO Display*****************************
			new Thread() {
				public void run() {
					String sCurrentLine6;

					BufferedReader br6;
					try {
						br6 = new BufferedReader(new FileReader("ForCurrentTransitionMario.txt"));

						while (true) {
							String lastLine6 = "";

							try {
								while ((sCurrentLine6 = br6.readLine()) != null) {
									
									lastLine6 = sCurrentLine6;
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							CurrentTransitionText.setText(lastLine6 );
							try {
								Thread.sleep(3000);
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			//***************************************************************************

			
			
			//*********************** GUI Previous Transition MARIO Display*****************************
			new Thread() {
				public void run() {
					String sCurrentLine6;

					BufferedReader br6;
					try {
						br6 = new BufferedReader(new FileReader("ForPreviousTransitionMario.txt"));

						while (true) {
							String lastLine6 = "";

							try {
								while ((sCurrentLine6 = br6.readLine()) != null) {
									
									lastLine6 = sCurrentLine6;
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							PreviousTransitionText.setText(lastLine6 );
							try {
								Thread.sleep(3000);
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			//***************************************************************************

			
			
			//*********************** GUI Current State MARIO Display*****************************
			new Thread() {
				public void run() {
					String sCurrentLine6;

					BufferedReader br6;
					try {
						br6 = new BufferedReader(new FileReader("ForCurrentStateMario.txt"));

						while (true) {
							String lastLine6 = "";

							try {
								while ((sCurrentLine6 = br6.readLine()) != null) {
									
									lastLine6 = sCurrentLine6;
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							CurrentTransitionText.setText(lastLine6 );
							try {
								Thread.sleep(3000);
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			//***************************************************************************

			
			
			
			//*********************** GUI Next Transition MARIO Display*****************************
			new Thread() {
				public void run() {
					String sCurrentLine6;

					BufferedReader br6;
					try {
						br6 = new BufferedReader(new FileReader("ForNextTransitionMario.txt"));

						while (true) {
							String lastLine6 = "";

							try {
								while ((sCurrentLine6 = br6.readLine()) != null) {
									
									lastLine6 = sCurrentLine6;
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							CurrentTransitionText.setText(lastLine6 );
							try {
								Thread.sleep(3000);
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			//********************** * ****************************************************
   
 			
			
		*/	
 
		}

		
}






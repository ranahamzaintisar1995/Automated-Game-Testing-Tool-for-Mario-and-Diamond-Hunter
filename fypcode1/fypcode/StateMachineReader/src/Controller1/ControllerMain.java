package Controller1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerMain  extends AnchorPane implements Initializable{

	 static AGTT obj = null;
	@FXML private Button myButton =new Button();
	@FXML private ListView<String> myList=new ListView<String>();
	@FXML private TextField textField1=new TextField();
	@FXML private TextArea textArea=new TextArea();
	@FXML private Button button2 =new Button();
	@FXML private TextField textField2=new TextField();
	private int count=0;
	private String msg;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
  //********************Code for adding testcases to the ListView*********************
		obj=new AGTT();
		obj.generateTestCases(); 
		ArrayList <String> testCases =obj.getAllTestCases();
		obj.SetTestCases(testCases);
		textArea.setEditable(false);
		String [] data=new String [testCases.size()];
		for(int i=0;i<testCases.size();i++)
		{
			data[i] = "Testcase "+(i+1);				
		}
		
		ObservableList<String>items=FXCollections.<String>observableArrayList();
		for(int i=0;i<testCases.size();i++)
		{
			items.add(data[i]);
			
		}
		
		textArea.setText(msg);
		System.out.println(msg);
		myList.setItems(items);
		Console console = new Console(textArea);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
	}

	//***********************************************************************************
	
	@FXML
	public void ButtonClickedAction(ActionEvent event)
	{
		String x=textField1.getText();
		int min=Integer.parseInt(x);
		obj.StartGame();
		min = min*60000;
		obj.prepareTestcases(min);
		
	}
	@FXML
	public void ButtonClickedAction2(ActionEvent event)
	{
		
		String x=textField2.getText();
		String[] lineVector = x.split(",");
		
		int counter=x.length()-(x.length()/2);
		//System.out.println(counter);
		int [] TestNo=new int[counter];
		for (int i=0;i<counter;i++){
			TestNo[i]=Integer.parseInt(lineVector[i]);
			
		}
		
		obj.StartGame();
		obj.prepareTestcases(TestNo);
        //parsing the values to Integer
       // a=Integer.parseInt(lineVector[0]);
        //b=Integer.parseInt(lineVector[1]);
        //c=Integer.parseInt(lineVector[2]);
		
		
	}
	public void displayTestCase(ArrayList<String> testCase){
		StringBuilder sb = new StringBuilder();
		for( String text: testCase) {

		    if( sb.length() > 0) {
		        sb.append("\n");
		    }

		    sb.append(text);
		}
		//System.out.println("hee");
		//System.out.println(testCase+"in display test case");
		textArea.setText("hi");
		msg=sb.toString();
		//System.out.println(msg);
		textArea.setText("hello");
		
	}
	
	public void ListAction(MouseEvent event)
	{
		ArrayList<String> Cases=obj.getAllTestCases();
		String testnum=myList.getSelectionModel().getSelectedItem();
		int number= Integer.parseInt(testnum.replaceAll("[^0-9]", ""));
		//System.out.println("clicked on " + number);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(Cases.get(number-1));

		alert.showAndWait();
			/*obj=new AGTT();
			obj.generateTestCases(); 
			ArrayList <String> testCases =obj.getAllTestCases();
			obj.SetTestCases(testCases);
			
			String [] data=new String [testCases.size()];
			for(int i=0;i<testCases.size();i++)
			{
				data[i] = "Testcase "+(i+1);				
			}
			
			ObservableList<String>items=FXCollections.<String>observableArrayList();
			
			for(int i=0;i<testCases.size();i++)
			{
				items.add(data[i]);
			}
		
			myList.setItems(items);
			
			String[]columns=new String[]{"State(parent)","Transition(Child)","Key Stroke"};
		
			
			String a=testCases.get(count);
			String [] tokens=a.split(",");

	
			Object[][] data1=new Object[tokens.length][3];
			int index=0;
			
			for(String s:tokens)
			{
				if(index%2==0)
				{
					data1[index][0]=s;
					data1[index][1]="";
					data1[index][2]="";
				}
				
				else
				{
					data1[index][0]="";
					data1[index][1]=s;
					
					if(s.equals(" runforward")) {	data1[index][2] = "Right";	}
         		    else if(s.equals(" runbackward")) {	data1[index][2] = "Left";	}
         		    else if(s.equals(" Jump") || s.equals(" jump")) {	data1[index][2] = "Space";	}
         		    else  { 	data1[index][2] = "null";	}
				}
				index++;
			}
			
			
			  final Class[] columnClass = new Class[] {
            		  String.class, String.class, String.class
              };
			
			
			count++;
					 */
		}
	
	public static class Console extends OutputStream {

        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException {
            output.appendText(String.valueOf((char) i));
        }
    }
}

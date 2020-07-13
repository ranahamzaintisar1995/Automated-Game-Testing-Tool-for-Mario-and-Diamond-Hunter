package Interface;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private AnchorPane mainLayout;
	FXMLLoader loader = new FXMLLoader();

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Automated Game Testing Tool");
		showMainView();

	}

	private void showMainView() throws IOException {
		Task loadView = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {
	           
	            Platform.runLater(() -> {                   
	            	  
	         		loader.setLocation(Main.class.getResource("Main1.fxml"));
	         		try {
						mainLayout = loader.load();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	         		Scene scene = new Scene(mainLayout);
	         		
	         		
	         		primaryStage.setScene(scene);
	         		primaryStage.show();
	            }); 
	            return null;
	        }            
	    };
	    new Thread(loadView).start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

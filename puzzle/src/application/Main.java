package application;
	
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			stage.initStyle(StageStyle.UNDECORATED);
			InputStream is = getClass().getClassLoader().getResourceAsStream("application/Scene.fxml");
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(is);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("拼图游戏");
			stage.show();
			Controller controller = (Controller)loader.getController();
			controller.setApp(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeApp() {
		stage.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

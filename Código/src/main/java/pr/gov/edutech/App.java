package pr.gov.edutech;

import java.io.IOException;

import classes.Aluno;
import classes.Professor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    
	private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 1280, 720);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    static void setRootArg(String fxml, Aluno a) throws IOException{
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    	Parent root = (Parent)fxmlLoader.load();          
    	EditarAlunoController controller = fxmlLoader.<EditarAlunoController>getController();
    	controller.setAluno(a);
    	scene.setRoot(root);
    }
    
    static void setRootArgTurma(String fxml, String turma) throws IOException{
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    	Parent root = (Parent)fxmlLoader.load();          
    	Presenca2Controller controller = fxmlLoader.<Presenca2Controller>getController();
    	controller.setTurma(turma);
    	scene.setRoot(root);
    }

    public static void main(String[] args) {
        launch();
    }

}
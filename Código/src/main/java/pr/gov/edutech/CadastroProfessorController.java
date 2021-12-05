package pr.gov.edutech;

import java.io.IOException;

import classes.Professor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import pr.gov.edutech.daos.ProfessorDao;

public class CadastroProfessorController {

	@FXML
	private TextField nomeField;
	@FXML
	private TextField turmaField;
	@FXML
	private ComboBox<String> turnoField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField senhaField;
	@FXML
	private Label statusLabel;
	@FXML
	private Button botaoEnviar;
	
	@FXML
	public void initialize() {
		turmaField.setTooltip(
			    new Tooltip("Coloque as turmas separadas por vírgula."
			    		+ "Ex: Turma1, Turma2, Turma3")
			);
		emailField.setTooltip(
				new Tooltip("O endereço de e-mail precisa ser do domínio @escola.pr.gov.br")
				);
		turnoField.getItems().add("Manhã");
		turnoField.getItems().add("Tarde");
		turnoField.getItems().add("Noite");
	}
	
	@FXML
	public void voltar() throws IOException {
		App.setRoot("login");
	}
	
	@FXML
	public void enviarCadastro() {
		String nome = nomeField.getText();
		String turma = turmaField.getText();
		String turno = turnoField.getValue();
		String email = emailField.getText();
		String senha = senhaField.getText();
		
		try {
			if(turnoField.getValue() == null) {
				throw new Exception("Selecione um turno.");
			}
			Professor p = new Professor(nome, email, turma, turno, senha);
			ProfessorDao pd = new ProfessorDao();
			pd.turmaTemProfessor(p);
			pd.salvar(p);
			botaoEnviar.setDisable(true);
			botaoEnviar.setCursor(Cursor.DEFAULT);
			statusLabel.setText("Cadastro Adicionado!");
	        Task<Void> sleeper = new Task<Void>() {
	            @Override
	            protected Void call() throws Exception {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                }
	                return null;
	            }
	        };
	        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent event) {
	            	try {
						App.setRoot("login");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
	        new Thread(sleeper).start();

			
		} catch (Exception e) {
			statusLabel.setText(e.getMessage());
		}
	}
	
	
}

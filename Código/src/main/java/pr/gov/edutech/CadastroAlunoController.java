package pr.gov.edutech;

import java.io.IOException;

import classes.Aluno;
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
import pr.gov.edutech.daos.AlunoDao;
import pr.gov.edutech.exceptions.TurmaException;

public class CadastroAlunoController {
	
	@FXML
	private TextField nomeField;
	@FXML
	private TextField emailField;
	@FXML
	private ComboBox<String> turnoField;
	@FXML
	private TextField turmaField;
	@FXML
	private TextField statusField;
	@FXML
	private TextField cgmField;
	@FXML
	private Label statusLabel;
	@FXML
	private Button botaoEnviar;
	
	private AlunoDao ad;
	
	@FXML
	public void initialize() {
		emailField.setTooltip(
				new Tooltip("O endereço de e-mail precisa ser do domínio @escola.pr.gov.br")
				);
		statusField.setTooltip(
				new Tooltip("O status precisa ser ATIVO ou INATIVO.")
				);
		cgmField.setTooltip(
				new Tooltip("O CGM precisa ter 8 (oito) dígitos.")
				);
		turnoField.getItems().add("Manhã");
		turnoField.getItems().add("Tarde");
		turnoField.getItems().add("Noite");
		turnoField.setDisable(true);
	}
	
	@FXML
	public void voltar() throws IOException {
		App.setRoot("consulta");
	}
	
	@FXML
	public void enviarCadastro() {
		ad = new AlunoDao();
		String nome = nomeField.getText();
		String turma = turmaField.getText();
		String turno = turnoField.getValue();
		String email = emailField.getText();
		String status = statusField.getText();
		String cgm = cgmField.getText();
		
		try {
			ad.cgmExistente(cgm);
			ad.emailExistente(email);
			String turnoServidor = ad.checarTurnoDaTurma(turma);
			if(turnoServidor != null){
				turno = turnoServidor;
			}
			if(turnoServidor == null && turno == null) {
				throw new TurmaException("Turma não existente, insira o turno.");
			}
			
			Aluno p = new Aluno(nome, email, turma, turno, cgm, status, 0);
			ad.salvar(p);
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
						App.setRoot("consulta");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
	        new Thread(sleeper).start();

			
		}catch(TurmaException e) {
			turnoField.setDisable(false);
			statusLabel.setText(e.getMessage());
			
		}
		catch (Exception ex) {
			     statusLabel.setText(ex.getMessage());
		}
	
}
}

package pr.gov.edutech;

import java.io.IOException;

import classes.Professor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import pr.gov.edutech.daos.ProfessorConectado;
import pr.gov.edutech.daos.ProfessorDao;

public class LoginController {

		@FXML
		private TextField userField;
		@FXML
		private PasswordField senhaField;
		@FXML
		private Label statusLabel;
	
		@FXML
		private void initialize() {
			userField.setTooltip(
					new Tooltip("Insira o E-mail cadastrado.")
					);
		}
		
		@FXML
		public void chamarCadastro() throws IOException {
			App.setRoot("cadastroProf");
		}
		
		@FXML
		public void entrar() throws IOException {
			Professor entrar = null;
			try {
				entrar = new ProfessorDao().buscar(userField.getText(), senhaField.getText());
				new ProfessorConectado(entrar);
				App.setRoot("consulta");
			} catch (Exception e) {
				statusLabel.setText(e.getMessage());
			}
		}
		
}

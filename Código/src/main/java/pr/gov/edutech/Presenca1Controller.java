package pr.gov.edutech;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import pr.gov.edutech.daos.ProfessorConectado;

public class Presenca1Controller {

		@FXML
		private ComboBox<String> turmaComboBox;
	
		@FXML
		public void initialize() throws Exception {
			List<String> turmas = ProfessorConectado.getP().getTurmasUtil();
			System.out.println(turmas.toString());
			turmas.forEach((turma) -> turmaComboBox.getItems().add(turma));
		}
		
		@FXML
		public void voltar() throws IOException {
			App.setRoot("consulta");
		}
		
		@FXML
		public void avancar() {
			try {
				System.out.println(turmaComboBox.getValue().toString());
				App.setRootArgTurma("registrarPresencas2", turmaComboBox.getValue().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}

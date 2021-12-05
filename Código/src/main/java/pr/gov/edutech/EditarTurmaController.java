package pr.gov.edutech;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import pr.gov.edutech.daos.AlunoDao;
import pr.gov.edutech.daos.ProfessorConectado;

public class EditarTurmaController {

		@FXML
		private ComboBox<String> turmaComboBox;
		@FXML
		private ComboBox<String> turnoComboBox;
		
		@FXML
		public void initialize() throws Exception {
			List<String> turmas = new AlunoDao().listarTodasTurmas();
			System.out.println(turmas.toString());
			turmas.forEach((turma) -> turmaComboBox.getItems().add(turma));
			turnoComboBox.getItems().add("Manhã");
			turnoComboBox.getItems().add("Tarde");
			turnoComboBox.getItems().add("Noite");
		}
		
		@FXML
		public void voltar() throws IOException {
			App.setRoot("consulta");
		}
		
		@FXML
		public void salvar() {
			new AlunoDao().editarTurmas(turmaComboBox.getValue().toString().trim(), turnoComboBox.getValue().toString().trim());
			try {
				App.setRoot("consulta");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}

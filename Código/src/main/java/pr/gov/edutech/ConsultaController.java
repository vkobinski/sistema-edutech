package pr.gov.edutech;

import java.io.IOException;
import java.util.ArrayList;

import classes.Aluno;
import classes.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pr.gov.edutech.daos.AlunoDao;
import pr.gov.edutech.daos.ProfessorConectado;
import pr.gov.edutech.daos.ProfessorDao;

public class ConsultaController {

		@FXML
		private TableView<Aluno> tabela;
		
		@FXML
		private TableColumn<Aluno, String> cgmTabela;
		@FXML
		private TableColumn<Aluno, String> nomeTabela;
		@FXML
		private TableColumn<Aluno, String> emailTabela;
		@FXML
		private TableColumn<Aluno, String> turnoTabela;
		@FXML
		private TableColumn<Aluno, String> turmaTabela;
		@FXML
		private TableColumn<Aluno, String> statusTabela;
		@FXML
		private TableColumn<Aluno, String> presencasTabela;
		@FXML
		private TextField pesquisarField;
		@FXML
		private Label statusLabel;
		@FXML
		private ComboBox<String> comboBoxProf;
		
		private ObservableList<Aluno> listaCompleta;
		
		private ArrayList<Professor> professores;
		
		private AlunoDao ad;
		private ProfessorDao pd;

		@FXML
		public void initialize() throws Exception {
			
			cgmTabela.setCellValueFactory(new PropertyValueFactory<>("cgm"));
			nomeTabela.setCellValueFactory(new PropertyValueFactory<>("nome"));
			emailTabela.setCellValueFactory(new PropertyValueFactory<>("email"));
			turnoTabela.setCellValueFactory(new PropertyValueFactory<>("turno"));
			turmaTabela.setCellValueFactory(new PropertyValueFactory<>("turma"));
			statusTabela.setCellValueFactory(new PropertyValueFactory<>("status"));
			presencasTabela.setCellValueFactory(new PropertyValueFactory<>("presencas"));
			
			ad = new AlunoDao();
			pd = new ProfessorDao();
			professores = pd.buscarProfessores();
			ArrayList<Aluno> alunosList = (ArrayList<Aluno>) ad.listar();
			ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunosList);
			listaCompleta = alunos;
			comboBoxProf.getItems().add("Todos");
			for(Professor pf : professores) {
				comboBoxProf.getItems().add(pf.getNome());
			}
			tabela.setItems(alunos);
		}
		
		@FXML
		public void trocarProf() {
			Professor profSelec = null;
			String profBox = comboBoxProf.getValue().toString();
			if(profBox.equals("Todos")) {
				tabela.setItems(listaCompleta);
				tabela.refresh();
				return;
			}
			for(Professor p : professores) {
				if(p.getNome() == profBox) {
					profSelec = p;
				}
			}
			try {
				System.out.println(profSelec);
				ArrayList<Aluno> alunosProf = pd.buscarTurmas(profSelec);
				ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunosProf);
				tabela.setItems(alunos);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				statusLabel.setText(e.getMessage());
			}
		}
		
		@FXML
		public void editarTurma() throws IOException {
			App.setRoot("editarTurma");
		}
		
		@FXML
		public void cadastrar() throws IOException {
			App.setRoot("cadastroAluno");
		}
		
		@FXML
		public void meusAlunos() {
			Professor logado = ProfessorConectado.getP();
			System.out.println(logado);
			ArrayList<Aluno> alunosList = null;
			try {
				alunosList = pd.buscarTurmas(logado);
				System.out.println(alunosList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				statusLabel.setText("Não há alunos matriculados");
			}
			ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunosList);

			tabela.setItems(alunos);
			tabela.refresh();
		}
		
		@FXML
		public void estatisticas() throws IOException {
			App.setRoot("estatisticas");
		}
		
		@FXML
		public void pesquisar() {
			String cgm = pesquisarField.getText();
			Aluno procurado = null;
			try {
				procurado = ad.procurarPorCgm(cgm);
			} catch (Exception e) {
				e.printStackTrace();
				statusLabel.setText(e.getMessage());
			}
			ArrayList<Aluno> alunosList = new ArrayList<>();
			alunosList.add(procurado);
			ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunosList);

			tabela.setItems(alunos);
			tabela.refresh();
		}
		
		@FXML
		public void limparBusca() {
			tabela.setItems(listaCompleta);
			tabela.refresh();
		}
		
		@FXML
		public void editar() {

			try {
				App.setRootArg("editarAluno", tabela.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@FXML
		public void registrarPresencas() throws IOException {
			App.setRoot("registrarPresencas1");
		}
	
}

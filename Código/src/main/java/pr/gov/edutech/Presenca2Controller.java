package pr.gov.edutech;

import java.io.IOException;
import java.util.ArrayList;

import classes.Aluno;
import classes.Professor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import pr.gov.edutech.daos.AlunoDao;
import pr.gov.edutech.daos.ProfessorConectado;
import pr.gov.edutech.daos.ProfessorDao;

public class Presenca2Controller {

	@FXML
	private TableView<Aluno> tabela;

	@FXML
	private TableColumn<Aluno, String> cgmTabela;
	@FXML
	private TableColumn<Aluno, String> nomeTabela;
	@FXML
	private TableColumn<Aluno, Boolean> presenteTabela;
	@FXML
	private TextField pesquisarField;
	@FXML
	private Label statusLabel;

	private ObservableList<Aluno> listaCompleta;

	private AlunoDao ad;
	private ProfessorDao pd;

	private String turma;

	@FXML
	public void initialize() throws Exception {

		Platform.runLater(() -> {

			presenteTabela.setCellValueFactory(new PropertyValueFactory<>("selected"));
			cgmTabela.setCellValueFactory(new PropertyValueFactory<>("cgm"));
			nomeTabela.setCellValueFactory(new PropertyValueFactory<>("nome"));

			ad = new AlunoDao();
			pd = new ProfessorDao();
			System.out.println(turma);
			ArrayList<Aluno> alunosList = null;
			try {
				alunosList = (ArrayList<Aluno>) ad.listarTurmaAtiva(this.turma);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			presenteTabela.setCellFactory(CheckBoxTableCell.forTableColumn(presenteTabela));

			ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunosList);
			listaCompleta = alunos;

			tabela.setItems(alunos);
		});
	}

	public void setTurma(String turma) {
		this.turma = turma.trim();
	}
	
	@FXML
	public void voltar() throws IOException {
		App.setRoot("consulta");
	}

	@FXML
	public void registrar() {
		ArrayList<Aluno> alunosPresentes = new ArrayList<>();
		for (Aluno a : listaCompleta) {
			if (a.isSelected()) {
				alunosPresentes.add(a);
			}
		}
		System.out.println(alunosPresentes.toString());
		try {
			ad.atualizarPresencas(alunosPresentes);
			statusLabel.setText("Presenças Adicionadas!");
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

		} catch (Exception e) {
			statusLabel.setText(e.getMessage());
		}
	}
}

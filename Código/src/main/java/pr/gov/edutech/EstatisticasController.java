package pr.gov.edutech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import classes.Aluno;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import pr.gov.edutech.daos.AlunoDao;
import pr.gov.edutech.daos.ProfessorConectado;
import pr.gov.edutech.daos.ProfessorDao;

public class EstatisticasController {

		@FXML
		private PieChart graficoPizza;
		@FXML
		private Label statusLabel;
		
		
		private AlunoDao ad;
		private ProfessorDao pd;
		
		private ArrayList<Aluno> alunosList;
				
		@FXML
		public void initialize() throws Exception {
			Platform.runLater(() ->{
				this.ad = new AlunoDao();
				this.pd = new ProfessorDao();
				ArrayList<Integer> alunos = contarAtivos();
				ObservableList<PieChart.Data> pieChartData =
		                FXCollections.observableArrayList(
		                new PieChart.Data("Ativos", alunos.get(0)),
		                new PieChart.Data("Inativos", alunos.get(1))
		                );
				graficoPizza.setTitle("Alunos Ativos/Inativos");
				graficoPizza.setData(pieChartData);
				
				
			});
		}
		
		@FXML
		public void voltar() throws IOException {
			App.setRoot("consulta");
		}
		
		public ArrayList<Integer> contarAtivos(){
			Integer ativos = 0;
			Integer inativos = 0;
			Integer total = null;
			try {
				alunosList = (ArrayList<Aluno>) pd.buscarTurmas(ProfessorConectado.getP());
				total = alunosList.size();
				for(int i = 0; i < alunosList.size(); i++) {
					if(alunosList.get(i).getStatus().toLowerCase().equals("ativo")) {
						ativos++;
					}else {
						inativos++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<Integer> valores = new ArrayList<>();
			valores.add(ativos*100/total);
			valores.add(inativos*100/total);
			return valores;
			
		}
}

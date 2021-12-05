package pr.gov.edutech.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import classes.Aluno;
import classes.Professor;
import pr.gov.edutech.factory.ConnectionFactory;

public class ProfessorDao {

	private Connection connection;

	public ProfessorDao() {
		try {
			connection = new ConnectionFactory().recuperarConexao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection criarConexao() {
		try {
			connection = new ConnectionFactory().recuperarConexao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	

	public Professor buscar(String email, String senha) throws Exception {
		String sql = "SELECT * FROM `professor` WHERE (email = ? AND senha = ?);";
		this.connection = criarConexao();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
			Professor p = null;
			try (PreparedStatement pst = connection.prepareStatement(sql)) {
				pst.setString(1, email);
				pst.setString(2, senha);
				pst.execute();
				ResultSet rs = pst.getResultSet();
				while (rs.next()) {
					p = new Professor(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(3), rs.getString(5));
				}
				if(p == null) {
					throw new Exception("E-mail ou senha incorretos");
				}
				return p;

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	public ArrayList<Professor> buscarProfessores() throws Exception {
		String sql = "SELECT * FROM `professor`;";

		this.connection = criarConexao();
		ArrayList<Professor> professores = new ArrayList<>();
		Professor p = null;
			try (PreparedStatement pst = connection.prepareStatement(sql)) {
				pst.execute();
				ResultSet rs = pst.getResultSet();
				while (rs.next()) {
					professores.add(new Professor(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(3), rs.getString(5)));
				}
				return professores;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	public boolean turmaTemProfessor(Professor p) throws Exception {
		ArrayList<String> turmasJaExistentes = new ArrayList<>();

		this.connection = criarConexao();
		try {
			ArrayList<Professor> professores = buscarProfessores();
			for(Professor pa : professores) {
				turmasJaExistentes.addAll(pa.getTurmasUtil());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(turmasJaExistentes.toString());
		for(String turma : p.getTurmasUtil()) {
			if(turmasJaExistentes.contains(turma.trim())) {
				throw new Exception("Turma já tem outro professor.");
			}
		}
		return false;
	}

	
	public ArrayList<Aluno> buscarTurmas(Professor p) throws Exception {
		
		List<String> turmas = ProfessorConectado.getP().getTurmasUtil();

		this.connection = criarConexao();
		System.out.println(turmas.get(0));
		String sql = "SELECT * FROM aluno WHERE (turma = ?)";
		ArrayList<Aluno> alunos = new ArrayList<>();
		if(turmas.size() == 0) {
			throw new Exception("Nenhum aluno matriculado em suas turmas");
		}
		if(turmas.size() == 1) {
			sql = sql + ";";
		}else {
			for(int i = 1; i < turmas.size(); i++) {
				sql = sql + "OR (turma = ?)";				
			}
			sql = sql + ";";
		}
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try (PreparedStatement pst = this.connection.prepareStatement(sql)) {

			for(int i = 0; i < turmas.size(); i++) {
				pst.setString(i+1, turmas.get(i));
			}
			pst.execute();
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				try {
					alunos.add(new Aluno(rs.getString(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(6),rs.getString(5), rs.getInt(7)));
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("Nenhum aluno matriculado!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		return alunos;
	}
	
	public boolean emailExistente(String cgm) throws Exception {
		List<Professor> professores = new ArrayList<Professor>();

		this.connection = criarConexao();
		String sql = "SELECT * FROM professor where email = ?";
		try(PreparedStatement pst = connection.prepareStatement(sql)){
			pst.setString(1, cgm);
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				professores.add( new Professor(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(3), rs.getString(5)));
				}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if(professores.size() != 0) {
			throw new Exception("E-mail já existente.");
	}
		return false;
	}
	
	public void salvar(Professor p) {
		String sql = "INSERT INTO professor (nome_completo, email, turno, turmas, senha) VALUES (?,?,?,?,?);";

		this.connection = criarConexao();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try (PreparedStatement pst = this.connection.prepareStatement(sql)) {

			pst.setString(1, p.getNome());
			pst.setString(2, p.getEmail());
			pst.setString(3, p.getTurno());
			pst.setString(4, p.getTurmas());
			pst.setString(5, p.getSenha());

			pst.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}

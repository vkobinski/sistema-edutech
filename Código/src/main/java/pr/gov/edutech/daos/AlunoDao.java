package pr.gov.edutech.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.Aluno;
import classes.Professor;
import pr.gov.edutech.exceptions.TurmaException;
import pr.gov.edutech.exceptions.TurnoException;
import pr.gov.edutech.factory.ConnectionFactory;

public class AlunoDao {

	private Connection connection;
	
	public Connection criarConexao() {
		try {
			connection = new ConnectionFactory().recuperarConexao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public boolean cgmExistente(String cgm) throws Exception {
		List<Aluno> alunos = new ArrayList<Aluno>();
		this.connection = criarConexao();
		String sql = "SELECT * FROM aluno where cgm = ?";
		try(PreparedStatement pst = connection.prepareStatement(sql)){
			pst.setString(1, cgm);
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				alunos.add(new Aluno(rs.getString(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(6),rs.getString(5), rs.getInt(7)));
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if(alunos.size() != 0) {
			throw new Exception("CGM já existente.");
	}
		return false;
	}
	
	public boolean emailExistente(String cgm) throws Exception {
		List<Aluno> alunos = new ArrayList<Aluno>();
		this.connection = criarConexao();
		String sql = "SELECT * FROM aluno where email = ?";
		try(PreparedStatement pst = connection.prepareStatement(sql)){
			pst.setString(1, cgm);
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				alunos.add(new Aluno(rs.getString(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(6),rs.getString(5), rs.getInt(7)));
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if(alunos.size() != 0) {
			throw new Exception("E-mail já existente.");
	}
		return false;
	}
	
	public void atualizarPresencas(ArrayList<Aluno> presentes) throws Exception {
		this.connection = criarConexao();
		String sql = "UPDATE aluno SET presencas = presencas + 1 WHERE (cgm = ?)";
		if(presentes.size() == 0) {
			throw new Exception("Nenhum aluno presente.");
		}
		if(presentes.size() == 1) {
			sql = sql + ";";
		}else {
			for(int i = 1; i < presentes.size(); i++) {
				sql = sql + " OR (cgm = ?)";				
			}
			sql = sql + ";";
		}
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try (PreparedStatement pst = this.connection.prepareStatement(sql)) {

			for(int i = 0; i < presentes.size(); i++) {
				pst.setString(i+1, presentes.get(i).getCGM().toString());
				System.out.println(presentes.get(i).getCGM().toString());
			}
			System.out.println(pst.toString());
			pst.execute();
			connection.commit();
			} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			}
		}		
	
	public String checarTurnoDaTurma(String turma) throws Exception {
		List<Aluno> alunos = new ArrayList<Aluno>();
		this.connection = criarConexao();
		String sql = "SELECT * FROM aluno where turma = ?";
		try(PreparedStatement pst = connection.prepareStatement(sql)){
			pst.setString(1, turma);
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				alunos.add(new Aluno(rs.getString(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(6),rs.getString(5), rs.getInt(7)));
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if(alunos.size() != 0) {
			return alunos.get(0).getTurno();
	}
	List<Professor> professores = new ArrayList<Professor>();
	this.connection = criarConexao();
	sql = "SELECT * FROM professor;";
	try(PreparedStatement pst = connection.prepareStatement(sql)){
		pst.execute();
		
		ResultSet rs = pst.getResultSet();
		while(rs.next()) {
			professores.add( new Professor(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(3), rs.getString(5)));
		}
		
	}catch (SQLException e) {
		throw new RuntimeException(e);
	}
	for(Professor p : professores) {
		if(p.getTurmasUtil().contains(turma)) {
			return p.getTurno();
		}
	}
		return null;
	}
	
	public List<Aluno> listar() throws Exception{
		List<Aluno> alunos = new ArrayList<Aluno>();
		this.connection = criarConexao();
		String sql = "SELECT * FROM aluno";
		try(PreparedStatement pst = connection.prepareStatement(sql)){
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				alunos.add(new Aluno(rs.getString(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(6),rs.getString(5), rs.getInt(7)));
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return alunos;
	}
	
	public List<Aluno> listarTurmaAtiva(String turma) throws Exception{
		List<Aluno> alunos = new ArrayList<Aluno>();
		this.connection = criarConexao();
		String sql = "SELECT * FROM aluno WHERE turma = ? AND status = 'ATIVO';";
		try(PreparedStatement pst = connection.prepareStatement(sql)){
			pst.setString(1, turma);
			pst.execute();
			
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				alunos.add(new Aluno(rs.getString(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(6),rs.getString(5), rs.getInt(7)));
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return alunos;
	}
	
	public ArrayList<String> listarTodasTurmas() {
		List<Aluno> alunos = null ;

		this.connection = criarConexao();
		try {
			alunos = this.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> turmas = new ArrayList<>();
		for(Aluno a : alunos) {
			if(turmas.contains(a.getTurma()) == false) {
				turmas.add(a.getTurma());
			}
		}
		return turmas;
 	}
	
	public void editarAluno(Aluno a) {
		String sql = "UPDATE aluno SET turma = ?, turno = ? , status = ? WHERE (nome_completo = ? AND email = ? AND cgm = ?);";

		this.connection = criarConexao();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try (PreparedStatement pst = this.connection.prepareStatement(sql)) {

			pst.setString(4, a.getNome());
			pst.setString(5, a.getEmail());
			pst.setString(2, a.getTurno());
			pst.setString(1, a.getTurma());
			pst.setString(3, a.getStatus());
			pst.setString(6, a.getCGM());

			pst.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public Aluno procurarPorCgm(String nome) throws Exception {
		String sql = "SELECT * FROM aluno WHERE cgm = ?";
		this.connection = criarConexao();
		Aluno p = null;
		try(PreparedStatement pst = connection.prepareStatement(sql)){
			pst.setString(1, nome);
			pst.execute();
			ResultSet rs = pst.getResultSet();
			while(rs.next()) {
				try {
					p = new Aluno(rs.getString(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(6),rs.getString(5), rs.getInt(7));
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("Aluno não encontrado!");
				}
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return p;
	}

	public void salvar(Aluno p) {
		this.connection = criarConexao();
		String sql = "INSERT INTO aluno (nome_completo, email, turno, turma, status, cgm) VALUES (?,?,?,?,?,?);";
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try (PreparedStatement pst = this.connection.prepareStatement(sql)) {

			pst.setString(1, p.getNome());
			pst.setString(2, p.getEmail());
			pst.setString(3, p.getTurno());
			pst.setString(4, p.getTurma());
			pst.setString(5, p.getStatus());
			pst.setString(6, p.getCGM());

			pst.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public void editarTurmas(String turma, String novoTurno) {
		String sql = "UPDATE aluno SET turno = ? WHERE turma = ?;";
		this.connection = criarConexao();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try (PreparedStatement pst = this.connection.prepareStatement(sql)) {

			pst.setString(2, turma);
			pst.setString(1, novoTurno);
			
			pst.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}

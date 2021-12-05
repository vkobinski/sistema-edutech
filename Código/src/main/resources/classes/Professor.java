package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Professor {

	private String nome;
	private String email;
	private ArrayList<String> turmas;
	private String turno;
	private String senha;
	
	public Professor(String nome, String email, String turmas, String turno, String senha) throws Exception {
		setNome(nome);
		setEmail(email);
		setTurmas(turmas);
		setTurno(turno);
		setSenha(senha);
	}
	
	public String getSenha() {
		return this.senha;
	}
	
	public void setSenha(String senha) throws Exception {
		if(senha.length() < 10) {
			throw new Exception("Senha precisa ter no mínimo 10 dígitos.");
		}
		else if(senha.isBlank() || senha.isEmpty()) {
			throw new Exception("Senha vazia.");
		}
		else {
			this.senha = senha.trim();
		}
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) throws Exception {
		int spaceCount = 0;
		for (char c : nome.toCharArray()) {
		    if (c == ' ') {
		         spaceCount++;
		    }
		}
		if(nome.isBlank()) {
			throw new Exception("Nome em branco!");
		}
		else if(nome.isEmpty()) {
			throw new Exception("Nome vazio!");
		}
		else if(spaceCount == 0) {
			throw new Exception("Precisa ter mais de um nome.");
		}
		
		else {
			this.nome = StringUtils.capitalize(nome).trim();
		}
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws Exception {
		int spaceCount = 0;
		for (char c : email.trim().toCharArray()) {
		    if (c == ' ') {
		         spaceCount++;
		    }
		}
		if(email.isEmpty() || email.isBlank()) {
			throw new Exception("Email vazio!");
		}
		else if(spaceCount != 0) {
			throw new Exception("O nome não pode ter espaços.");
		}
		else if(!email.toLowerCase().contains("@escola.pr.gov.br")) {
			throw new Exception("O email precisa ser do domínio: @escola.pr.gov.br");
		}
		else {
			this.email = email.toLowerCase().trim();
		}
	}
	public String getTurmas() {
		String turmasString = "";
		for(String turma : this.turmas) {
			turmasString = turmasString + turma;
			turmasString = turmasString + ".";
		}
		return turmasString;
	}
	
	public List<String>getTurmasUtil() {
		ArrayList<String> turmasNova = new ArrayList<>();
		for(String turma : this.turmas) {
			turmasNova.add(turma.trim().replaceAll("[.]", ""));
		}
		System.out.println(turmasNova);
		return turmasNova;
	}
	
	public void setTurmas(String turmas) throws Exception {
		if(turmas.isBlank() || turmas.isEmpty()) {
			throw new Exception("Insira as turmas separadas por vírgulas, Ex: 'Turma1, Turma2'.");
		}else {
		ArrayList<String> listTurmas = new ArrayList<String>();
	    Collections.addAll(listTurmas, turmas.split(","));
	    this.turmas = listTurmas;
		}
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) throws Exception {
		if(turno.isBlank()) {
			throw new Exception("Turno em branco!");
		}
		else if(turno.isEmpty()) {
			throw new Exception("Turno vazio!");
		}
		else if(turno.toLowerCase().equals("manhã") || turno.toLowerCase().equals("manha") || turno.toLowerCase().equals("tarde") || turno.toLowerCase().equals("noite")) {
			if(turno.toLowerCase().equals("manha")) {
				this.turno = "Manhã";
			}
			this.turno = (StringUtils.capitalize(turno));
		}
		else {
			throw new Exception("O turno precisa ser: Manhã, Tarde ou Noite");	
		}
	
	}
	@Override
	public String toString() {
		return this.getNome() + "," + this.getEmail()+ "," +this.getSenha()+ "," +this.getTurno()+ "," +this.getTurmas();
	}
}

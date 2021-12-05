
package classes;

import org.apache.commons.lang3.StringUtils;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Aluno {
	
    private final SimpleBooleanProperty selected;
	public final SimpleStringProperty nome;
    public final SimpleStringProperty email;
	public final SimpleStringProperty turma;
    public final SimpleStringProperty turno;
	public final SimpleStringProperty cgm;
    public final SimpleStringProperty status;
    public final SimpleIntegerProperty presencas;
		
	public Aluno(String nome, String email, String turma, String turno, String cGM, String status, int presencas) throws Exception {
		this.selected = new SimpleBooleanProperty(false);
		this.nome = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.turma = new SimpleStringProperty();
		this.turno = new SimpleStringProperty();
		this.cgm = new SimpleStringProperty();
		this.status = new SimpleStringProperty();
		this.presencas = new SimpleIntegerProperty();
		setNome(nome);
		setEmail(email);
		setTurma(turma);
		setTurno(turno);
		setCGM(cGM);
		setStatus(status);
		setPresencas(presencas);
	}
	
	public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
	
	public SimpleStringProperty nomeProperty() {
        return this.nome;
    }
	
	public SimpleIntegerProperty presencaProperty() {
		return this.presencas;
	}
	
	public SimpleStringProperty emailProperty() {
        return this.email;
    }
	public SimpleStringProperty turmaProperty() {
        return this.turma;
    }
	public SimpleStringProperty turnoProperty() {
        return this.turno;
    }
	public SimpleStringProperty cgmProperty() {
        return this.cgm;
    }
	public SimpleStringProperty statusProperty() {
        return this.status;
    }
	
	public int getPresencas() {
		return this.presencas.get();
	}
	
	public void setPresencas(int presencas) {
		this.presencas.set(presencas);
	}
	
	public String getNome() {
		return this.nome.get();
	}
	public void setNome(String nome) throws Exception {
		if(nome.isBlank()) {
			throw new Exception("Nome em branco!");
		}
		else if(nome.isEmpty()) {
			throw new Exception("Nome vazio!");
		}
		else {
			this.nome.set(StringUtils.capitalize(nome));
		}
		
	}
	public String getEmail() {
		return this.email.get();
	}
	public void setEmail(String email) throws Exception {
		if(email.isEmpty() || email.isBlank()) {
			throw new Exception("Email vazio!");
		}
		else if(!email.toLowerCase().contains("@escola.pr.gov.br")) {
			throw new Exception("O email precisa ser do domínio: @escola.pr.gov.br");
		}
		else {
			this.email.set(email.toLowerCase());
		}
	}
	public String getTurma() {
		return this.turma.get();
	}
	public void setTurma(String turma) throws Exception {
		if(turma.isBlank()) {
			throw new Exception("Turma em branco!");
		}
		else if(turma.isEmpty()) {
			throw new Exception("Turma vazio!");
		}
		else {
			this.turma.set(StringUtils.capitalize(turma));
		}
		
	}
	public String getTurno() {
		return this.turno.get();
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
				this.turno.set("Manhã");
			}
			this.turno.set(StringUtils.capitalize(turno));
		}
		else {
			throw new Exception("O turno precisa ser: Manhã, Tarde ou Noite");	
		}
		
	}
	public String getCGM() {
		return this.cgm.get();
	}
	public void setCGM(String cGM) throws Exception {
		if(cGM.length() != 8) {
			throw new Exception("CGM precisa ser de 8 (oito) dígitos.");
		}
		if (cGM.matches("[0-9]+")) {
			this.cgm.set(cGM);
		}
		else {
			throw new Exception("CGM precisa ser só números.");
		}
	}
	public String getStatus() {
		return this.status.get();
	}
	public void setStatus(String status) throws Exception {
		if(status.isBlank() || status.isEmpty()) {
			throw new Exception("Status Vazio!");
		}
		if(status.toUpperCase().equals("ATIVO") || status.toUpperCase().equals("INATIVO")) {
			this.status.set(status.toUpperCase());
		}
		
		else {
			throw new Exception("O status precisa ser: ATIVO ou INATIVO.");			
		}
	}
	
	@Override
	public String toString() {
		
		return "Nome: " + getNome() + "\n" + "CGM: " + getCGM() + "\n" + "Email: " + getEmail() + "\n" + "Status: " + status;
	}
	 
	 
}

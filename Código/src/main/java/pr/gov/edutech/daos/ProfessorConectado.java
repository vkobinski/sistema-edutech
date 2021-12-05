package pr.gov.edutech.daos;

import classes.Professor;

public class ProfessorConectado {

	private static Professor p;
	
	public ProfessorConectado(Professor p) {
		ProfessorConectado.setP(p);
	}
	
	public Professor getProfessor() {
		return getP();
	}

	public static Professor getP() {
		return p;
	}

	public static void setP(Professor p) {
		ProfessorConectado.p = p;
	}
	
}

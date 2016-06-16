package rest;

import entities.act.Akt;
import entities.amendment.Amandman;

public class ActAmendment {

	private Akt akt;
	private Amandman amandman;
	public ActAmendment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActAmendment(Akt akt, Amandman amandman) {
		super();
		this.akt = akt;
		this.amandman = amandman;
	}
	public Akt getAkt() {
		return akt;
	}
	public void setAkt(Akt akt) {
		this.akt = akt;
	}
	public Amandman getAmandman() {
		return amandman;
	}
	public void setAmandman(Amandman amandman) {
		this.amandman = amandman;
	}
	
	
	
}

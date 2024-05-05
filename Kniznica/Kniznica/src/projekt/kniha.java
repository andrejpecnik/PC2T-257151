package projekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class kniha {
	private String nazov;
	private List<String> autori;
	private int rokVydania;
	private boolean dostupnost;
	private ZanreRomanu zaner;

	public kniha(String nazov, List<String> autori, int rokVydania, ZanreRomanu zaner) {
		this.nazov = nazov;
		this.autori = autori;
		this.rokVydania = rokVydania;
		this.dostupnost = true;
		this.zaner = zaner;
	}

	public String getNazov() {
		return nazov;
	}

	public List<String> getAutori() {
		return autori;
	}

	public int getRokVydania() {
		return rokVydania;
	}
	
	public void setRokVydania(int rokVydania) {
		this.rokVydania = rokVydania;
	}

	public boolean isJeDostupna() {
		return dostupnost;
	}

	public void setJeDostupna(boolean dostupnost) {
		this.dostupnost = dostupnost;
	}
	
	public ZanreRomanu getZaner() {
		return zaner;
	}
	
	public String getInfo() {
		return "Nazov: " + nazov + "\nAutori: " + autori + "\nRok vydania: " + rokVydania + "\nStav dostupnosti: " + (dostupnost ? "K dispozicii" : "Vypozicane") + "\nZaner: " + zaner;
	}
	
}
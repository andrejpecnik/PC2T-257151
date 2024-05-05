package projekt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

public class spravcaKniznice {
	private List<kniha> knihy;
	
	
	public spravcaKniznice() {
		this.knihy = new ArrayList<>();
	}
		
	public void pridatNovuKnihu(kniha kniha) {
        knihy.add(kniha);
    }
	
	public void upravitKnihu(String nazov, String novyAutor, int novyRokVydania, boolean novaDostupnost) {
		for (kniha kniha : knihy) {
			if (kniha.getNazov().equals(nazov)) {
			kniha.getAutori().clear();
			kniha.getAutori().add(novyAutor);
			kniha.setRokVydania(novyRokVydania);
			kniha.setJeDostupna(novaDostupnost);
			System.out.println("Kniha bola uspesne upravena.");
			return;
			}
		} 
		System.out.println("Kniha s tymto nazvom nebola najdena.");
	}
	
	public void vymazatKnihu(String nazov) {
		for (int i = 0; i < knihy.size(); i++) {
			kniha kniha = knihy.get(i);
			if (kniha.getNazov().equals(nazov)) {
				knihy.remove(i);
				System.out.println("Kniha bola uspesne zmazana.");
				return;
			}
		}
		System.out.println("Kniha s tymto nazvom neexistuje.");
	}
	
	public void oznacitKnihuAkoVypozicanu(String nazov) {
		for (kniha kniha : knihy) {
			if (kniha.getNazov().equals(nazov)) {
				kniha.setJeDostupna(false);
				System.out.println("Kniha bola oznacena ako vypozicana.");
				return;
			}
		}
		System.out.println("Kniha s tymto nazvom neexistuje.");
	}
	
	public void oznacitKnihuAkoVratenu(String nazov) {
		for (kniha kniha : knihy) {
			if (kniha.getNazov().equals(nazov)) {
				kniha.setJeDostupna(true);
				System.out.println("Kniha bola oznacena ako vratena.");
				return;
			}
		}
		System.out.println("Kniha s tymto nazvom neexistuje.");
	}
	
	public void vypisatVsetkyKnihy() {
		Collections.sort(knihy, (kniha1, kniha2) -> kniha1.getNazov().compareToIgnoreCase(kniha2.getNazov()));
		for (kniha kniha : knihy) {
			System.out.println(kniha.getInfo());
		}
	}
	
	public void vyhladatKnihu(String nazov) {
		for (kniha kniha : knihy) {
			if (kniha.getNazov().equals(nazov)) {
				System.out.println(kniha.getInfo());
				return;
			}
		}
		System.out.println("Kniha s tymto nazvom neexistuje.");
	}
	
	public void vypisatKnihyPodlaAutora(String autor) {
		Collections.sort(knihy, Comparator.comparingInt(kniha -> kniha.getRokVydania()));
		boolean najdena = false;
		for (kniha kniha : knihy) {
			if (kniha.getAutori().contains(autor)) {
				System.out.println(kniha.getInfo());
				najdena = true;
			}
		}
		if (!najdena) {
			System.out.println("Kniha tohoto autora nebola najdena.");
		}
	}
	
	public void vypisatKnihyPodlaZanru(ZanreRomanu zaner) {
	    boolean najdena = false;
	    List<kniha> zoznamKnihPodlaZanru = new ArrayList<>();
	    for (kniha kniha : knihy) {
	        if (kniha.getZaner().equals(zaner)) {
	            zoznamKnihPodlaZanru.add(kniha);
	            najdena = true;
	        }
	    }
	    if (najdena) {
	        Collections.sort(zoznamKnihPodlaZanru, Comparator.comparing(kniha -> kniha.getNazov()));
	        for (kniha kniha : zoznamKnihPodlaZanru) {
	            System.out.println(kniha.getInfo());
	        }
	    } else {
	        System.out.println("Kniha tohoto zanru nebola najdena.");
	    }
	}
	
	public void vypisatVypozicaneKnihyPodlaTypu() {
		for (kniha kniha : knihy) {
			if (!kniha.isJeDostupna()) {
				System.out.println(kniha.getInfo() + (kniha instanceof ucebnica ? " (Ucebnica)" : " (Roman)"));
			}
		}
	}
	public boolean ulozDoSuboru(String nazovSouboru, String nazovKnihy) {
	    try {
	        FileWriter fw = new FileWriter(nazovSouboru);
	        BufferedWriter out = new BufferedWriter(fw);
	        boolean knihaNajdena = false;
	        for (kniha kniha : knihy) {
	            if (kniha.getNazov().equals(nazovKnihy)) {
	                out.write(kniha.getNazov() + "\n");
	                out.write(String.join(", ", kniha.getAutori()) + "\n");
	                out.write(kniha.getRokVydania() + "\n");
	                out.write(kniha.isJeDostupna() + "\n");
	                //out.write(kniha.getZaner());
	                out.write(kniha.getZaner().toString());
	                knihaNajdena = true;
	                break;
	            }
	        }
	        if (!knihaNajdena) {
	            System.out.println("Kniha s tymto nazvom nebola najdena.");
	            out.close();
	            fw.close();
	            return false;
	        }
	        out.close();
	        fw.close();
	    } catch (IOException e) {
	        System.out.println("Subor sa nepodarilo vytvorit.");
	        return false;
	    }
	    return true;
	}
	
	public boolean pridatNovuKnihuZoSuboru(String nazovSuboru1) {
	    try (FileReader fr = new FileReader(nazovSuboru1);
	         BufferedReader in = new BufferedReader(fr)) {
	        String nazov = in.readLine();
	        if (nazov == null) {
	            System.out.println("Chyba: Subor neobsahuje nazov knihy.");
	            return false;
	        }

	        String autoriString = in.readLine();
	        if (autoriString == null) {
	            System.out.println("Chyba: Subor neobsahuje zoznam autorov knihy.");
	            return false;
	        }
	        List<String> autori = Arrays.asList(autoriString.split(", "));

	        String rokVydaniaString = in.readLine();
	        if (rokVydaniaString == null) {
	            System.out.println("Chyba: Subor neobsahuje rok vydania knihy.");
	            return false;
	        }
	        int rokVydania = Integer.parseInt(rokVydaniaString);

	        String dostupnostString = in.readLine();
	        if (dostupnostString == null) {
	            System.out.println("Chyba: Subor neobsahuje informaciu o dostupnosti knihy.");
	            return false;
	        }
	        boolean jeDostupna = Boolean.parseBoolean(dostupnostString);

	        String zanerString = in.readLine();
	        if (zanerString == null) {
	            System.out.println("Chyba: Subor neobsahuje zaner knihy.");
	            return false;
	        }
	        ZanreRomanu zaner = ZanreRomanu.valueOf(zanerString);
	        

	        kniha novaKniha = new kniha(nazov, autori, rokVydania, zaner);
	        novaKniha.setJeDostupna(jeDostupna);
	        knihy.add(novaKniha);

	    } catch (IOException e) {
	        System.out.println("Chyba pri citani zo suboru.");
	        return false;
	    } catch (NumberFormatException e) {
	        System.out.println("Chyba pri prevode ciselných hodnot.");
	        return false;
	    } catch (IllegalArgumentException e) {
	        System.out.println("Chyba: Neplatný žáner knihy v súbore.");
	        return false;
	    }

	    return true;
	}

}

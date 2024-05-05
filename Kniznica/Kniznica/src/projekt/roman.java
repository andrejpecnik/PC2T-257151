package projekt;

import java.util.List;

public class roman extends kniha{
	private String zaner;
	
	public roman(String nazov, List<String> autori, int rokVydania, ZanreRomanu zaner) {
		super(nazov, autori, rokVydania, zaner);
	}
}
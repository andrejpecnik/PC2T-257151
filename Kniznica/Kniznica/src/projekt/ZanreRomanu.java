package projekt;

public enum ZanreRomanu {
	DETEKTIVNY,
    HISTORICKY,
    PSYCHOLOGICKY,
    ROMANTICKY,
    SCI_FI;
	
    public String toString() {
        return this.name();
    }
}
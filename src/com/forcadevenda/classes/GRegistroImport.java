package com.forcadevenda.classes;

@SuppressWarnings("serial")
public abstract class GRegistroImport extends GRegistro{

    protected String codigoImport;
    
    public static final String colCodigoImport = "CODIGOIMPORT";

    public String getCodigoImport() {
        return codigoImport;
    }

    public void setCodigoImport(String codigoImport) {
        this.codigoImport = codigoImport;
    }

	public String getColcodigoimport() {
		return colCodigoImport;
	}
    
}

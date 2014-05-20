package com.forcadevenda.classes;




@SuppressWarnings("serial")
public class Empresa extends GRegistroImport {
	
    

	private String rasoemp;
    private String ftpdemp;
    private String ftpuser;
    private String ftppass;
    private String codiven;

    public static final String colRasoemp = "RASOEMP";
    public static final String colFtpdemp = "FTPDEMP";
    public static final String colFtpuser = "FTPUSER";
    public static final String colFtppass = "FTPPASS";
    public static final String colCodiven = "CODIVEN";
    
    
    public static final String[] colunas = new String[]{
        colId,
        colRasoemp,
        colFtpdemp,
        colFtpuser,
        colFtppass,
        colCodiven,
        colCodigoImport
    };
    
    public String getRasoemp() {
		return rasoemp;
	}

	public void setRasoemp(String rasoemp) {
		this.rasoemp = rasoemp;
	}

	public String getFtpdemp() {
		return ftpdemp;
	}

	public void setFtpdemp(String ftpdemp) {
		this.ftpdemp = ftpdemp;
	}

	public String getFtpuser() {
		return ftpuser;
	}

	public void setFtpuser(String ftpuser) {
		this.ftpuser = ftpuser;
	}

	public String getFtppass() {
		return ftppass;
	}

	public void setFtppass(String ftppass) {
		this.ftppass = ftppass;
	}



    public String getCodiven() {
		return codiven;
	}

	public void setCodiven(String codiven) {
		this.codiven = codiven;
	}


	
	public Empresa(String rasoemp, String ftpdemp, String ftpuser,
			String ftppass, String codiven) {
		super();
		this.rasoemp = rasoemp;
		this.ftpdemp = ftpdemp;
		this.ftpuser = ftpuser;
		this.ftppass = ftppass;
		this.codiven = codiven;
	}

	public Empresa() {

	}

	public boolean isVazia(){
		if ((this.ftpdemp.length()==0)||((this.ftpuser.length()==0))||((this.ftppass.length()==0))||((this.codiven.length()==0)))
      		return true;
		return false;
    }
    
}

package com.forcadevenda.uteis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import com.forcadevenda.activity.*;
import com.forcadevenda.bd.TbEmpresas;
import com.forcadevenda.classes.Empresa;

import android.net.ConnectivityManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.EditText;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class G {

	public static final String BDNOME = "bdfdv";
	public static final String TAGLOG = "stardroid";

	public static final String DIRBASE = "integrad";
	public static final String DIRXML = "xml";
	public static final String DIRBKP = "bkp";
	public static final String DIRTESTES = "testes";
	
	public static final String DIRFTPSALVAR = (G.getPathSdCard() + G.strEntreChars(G.DIRBASE, "/")+ G.strEntreChars(G.DIRXML, "/"));
	
	public static final int INTNULL = -1;

	public static final int TRUE = 1;
	public static final int FALSE = 0;

	public static final int STEXECUSAO = 1;
	public static final int STPAUSADO = 2;

	public static final int IDINCLUIR = 9001;
	public static final int IDEDITAR = 9002;
	public static final int IDDELETAR = 9003;
	public static final int IDVER = 9004;
	public static final int IDSELECIONAR = 9005;
	public static final int IDFILTRAR = 9006;
	public static final int IDORDENAR = 9007;
	public static final int IDSALVAR = 9008;
	public static final int IDCANCELAR = 9009;
	public static final int IDLIMPAR = 9010;
	public static final int IDTODOS = 9011;
	public static final int IDNENHUM = 9012;
	public static final int IDOK = 9013;

	public static final int IDDATADIALOG = 9500;

	public static final int IDGETCODBARRAS = 9998;
	public static final int IDGETVOZ = 9999;

	public static final String EXTRASTRFILTRO = "exStrFiltro";
	public static final String EXTRACTVFILTRO = "exCtvFiltro";
	public static final String EXTRAID = "exId";
	public static final String EXTRASELECAO = "exSelecao";
	public static final String EXTRAEDITAR = "exEditar";

	public static final String MASCDOUBLE2 = "0.00";
	public static final String MASCDATAHORA = "dd/MM/yyyy kk:mm:ss";
	public static final String MASCDATAHORADB = "yyyy-MM-dd kk:mm:ss";
	public static final String MASCDATA = "dd/MM/yyyy";
	public static final String MASCDATADB = "yyyy-MM-dd";

	public static final String ORDERDESC = " DESC";

	public static final String USERADMIN = "ADMIN";
	public static final String SENHAADMIN = "321";

	public static String getString(Context ctx, int s) {
		return ctx.getString(s);
	}

	public static int getColor(Activity act, int cor) {
		return act.getResources().getColor(cor);
	}

	public static int getColor(Context ctx, int cor) {
		return ctx.getResources().getColor(cor);
	}

	// Preferencias ------------------------------------------------------------

	public static String prefCodVend = "V001";
	public static String prefSenhaVend = "123";
	public static String prefEmailExport = "";

	public static void setPreferencias(Context ctx) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(ctx);
        
		TbEmpresas tbemp = new TbEmpresas(ctx);
        String codiven = tbemp.get(1).getCodiven();
		
        prefCodVend = pref.getString("prefCodVend", codiven.length()==0 ? prefCodVend : codiven );
		prefSenhaVend = pref.getString("prefCodVend", prefCodVend);
		prefEmailExport = pref.getString("prefEmailExport", prefCodVend);
	}

	// Mensagens ---------------------------------------------------------------

	public static OnClickListener listenerdimiss = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			dialog.dismiss();
		}
	};

	public static void pergunta(final Context ctx, final String msg,
			OnClickListener listenersim, OnClickListener listenernao) {
		try {
			AlertDialog alerta = new AlertDialog.Builder(ctx).create();

			alerta.setTitle(G.getString(ctx, R.string.titmsgpergunta));
			alerta.setMessage(msg);

			alerta.setButton(AlertDialog.BUTTON_POSITIVE, ctx.getResources()
					.getString(R.string.sim), listenersim);
			alerta.setButton(AlertDialog.BUTTON_NEGATIVE,
					ctx.getString(R.string.nao), listenernao);

			alerta.show();
		} catch (Exception e) {
			addLogErro(e.getMessage());
		}
	}

	
	public static void mensagem(final Context ctx, final String titulo,
			final String msg) {
		try {
			AlertDialog alerta = new AlertDialog.Builder(ctx).create();

			alerta.setTitle(titulo);
			alerta.setMessage(msg);

			alerta.setButton(ctx.getString(R.string.ok), listenerdimiss);

			alerta.show();
		} catch (Exception e) {
			addLogErro(e.getMessage());
		}
	}

	public static void msgInformacao(Context ctx, String msg) {
		mensagem(ctx, G.getString(ctx, R.string.titmsginformacao), msg);
	}

	public static void msgAlerta(Context ctx, String msg) {
		mensagem(ctx, G.getString(ctx, R.string.titmsgalerta), msg);
	}

	public static void msgErro(Context ctx, String msg) {
		addLogErro(msg);
		mensagem(ctx, G.getString(ctx, R.string.titmsgerro), msg);
	}

	public static void msgErro(Context ctx, String titulo, String msg) {
		msgErro(ctx, G.strAdd(titulo, strEntreAspas(msg), ": "));
	}

	public static void alertar(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	}

	public static void addLogErro(String msg) {
		Log.e(TAGLOG, "ERRO! " + msg);
	}

	public static void msgLista(Context ctx, String msg, String[] itens,
			DialogInterface.OnClickListener listener) {
		new AlertDialog.Builder(ctx).setTitle(msg).setItems(itens, listener)
				.show();
	}

	public static void msgLista(Context ctx, String[] itens,
			DialogInterface.OnClickListener listener) {
		msgLista(ctx, getString(ctx, R.string.titmsgopcoes), itens, listener);
	}

	// Arquivos ----------------------------------------------------------------

	public static String getPathSdCard() {
		return Environment.getExternalStorageDirectory().toString();
	}

	public static boolean salvarArquivo(Context ctx, File arq, String conteudo) {
		try {
			FileOutputStream fos;

			fos = new FileOutputStream(arq);

			fos.write(conteudo.getBytes());

			fos.flush();
			fos.close();

			return true;
		} catch (Exception e) {
			G.msgErro(ctx, G.getString(ctx, R.string.errogerararq) + " "
					+ strEntreAspas(arq.getName()), e.getMessage());
			return false;
		}
	}

	public static boolean salvarArquivo(Context ctx, String diretorio,
			String fileName, String conteudo) {
		try {
			String path = getPathSdCard() + strEntreChars(DIRBASE, "/")
					+ diretorio;

			File dir = new File(path);
			dir.mkdirs();

			File arq = new File(path, fileName);

			return salvarArquivo(ctx, arq, conteudo);
		} catch (Exception e) {
			G.msgErro(ctx, G.getString(ctx, R.string.errogerararq),
					e.getMessage());
			return false;
		}
	}

	public static File abrirArquivo(Context ctx, String diretorio,
			String fileName) {
		try {
			String path = getPathSdCard() + strEntreChars(DIRBASE, "/")
					+ diretorio;

			File arq = new File(path, fileName);
			if (arq.exists())
				return arq;
			else
				return null;
		} catch (Exception e) {
			G.msgErro(ctx, G.getString(ctx, R.string.errorecuperararq) + " "
					+ strEntreAspas(fileName), e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("resource")
	public static boolean copiarArquivo(Context ctx, String de, String para) {
		String pathde = getPathSdCard() + strEntreChars(DIRBASE, "/") + de;
		String pathpara = getPathSdCard() + strEntreChars(DIRBASE, "/") + para;

		FileInputStream origem;
		FileOutputStream destino;
		try {
			origem = new FileInputStream(pathde);
			destino = new FileOutputStream(pathpara);

			FileChannel fcOrigem = origem.getChannel();
			FileChannel fcDestino = destino.getChannel();

			fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);

			return true;
		} catch (Exception e) {
			G.msgErro(ctx, G.getString(ctx, R.string.errocopiararq) + " "
					+ strEntreAspas(de), e.getMessage());
			return false;
		}
	}

	public static boolean moveToBkp(Context ctx, String diretorio,
			String fileName) {
		try {
			String path = getPathSdCard() + strEntreChars(DIRBASE, "/")
					+ diretorio;

			File arq = new File(path, fileName);

			if (!arq.exists())
				return true;

			String pathBkp = path + strEntreChars(DIRBKP, "/");

			File dir = new File(pathBkp);
			dir.mkdirs();

			String extensao = fileName.substring(fileName.lastIndexOf('.'));
			String fileNameBkp = fileName.substring(0, fileName.indexOf("."))
					+ G.dateToStr(G.data()).replaceAll("/", ".")
							.replaceAll(":", ".") + extensao;

			return arq.renameTo(new File(pathBkp, fileNameBkp));
		} catch (Exception e) {
			G.msgErro(ctx, G.getString(ctx, R.string.errogerararq),
					e.getMessage());
			return false;
		}
	}

	// Strings -----------------------------------------------------------------

	public static String strEntreChars(String s, String sI, String sF) {
		return (sI + s + sF);
	}

	public static String strEntreChars(String s, String sIF) {
		return (strEntreChars(s, sIF, sIF));
	}

	public static String strEntreParenteses(String s) {
		return (strEntreChars(s, "(", ")"));
	}

	public static String strEntreAspas(String s) {
		return (strEntreChars(s, "'"));
	}

	public static String strEntrePorcentagem(String s) {
		return (strEntreChars(s, "%"));
	}

	public static String strEntreEspacos(String s) {
		return (strEntreChars(s, " "));
	}

	public static String strAdd(String s1, String s2, String separador) {
		String retorno = s1;

		if (G.strIsEmpty(s2))
			return retorno;

		if (!G.strIsEmpty(retorno))
			retorno += separador;

		retorno += s2;

		return retorno;
	}

	public static boolean strIsEmpty(String s) {
		return ((s == null) || (s.equals("")));
	}

	public static String strNotNull(String s) {
		if (s == null)
			return "";
		else
			return s;
	}

	// Datas -------------------------------------------------------------------

	@SuppressLint("SimpleDateFormat")
	public static Date strToDate(String format, String data) {
		SimpleDateFormat formatador = new SimpleDateFormat(format);
		try {
			return formatador.parse(data);
		} catch (Exception e) {
			return (new Date());
		}
	}

	public static Date strToDate(String data) {
		return strToDate(G.MASCDATAHORA, data);
	}

	public static String dateToStr(String format, Date data) {
		try {
			SimpleDateFormat formatador = new SimpleDateFormat(format);
			return formatador.format(data);
		} catch (Exception e) {
			return "";
		}
	}

	public static String dateToStr(Date data) {
		return dateToStr(G.MASCDATAHORA, data);
	}

	public static Date data() {
		return new Date(System.currentTimeMillis());
	}

	// NUMEROS -----------------------------------------------------------------

	public static boolean intToBool(int valor) {
		return (valor == G.TRUE);
	}

	public static int boolToInt(boolean valor) {
		if (valor)
			return G.TRUE;
		else
			return G.FALSE;
	}

	public static String formatDouble(String format, double valor) {
		NumberFormat f = new DecimalFormat(format);
		return (f.format(valor));
	}

	public static String formatDouble(double valor) {
		return (G.formatDouble(G.MASCDOUBLE2, valor));
	}

	public static Double getDoubleForEditText(EditText edit) {
		String str = edit.getText().toString();

		if (G.strIsEmpty(str))
			return 0.0;

		try {
			return (Double.parseDouble(str));
		} catch (Exception e) {
			return 0.0;
		}
	}

	public static void CadastraEmpresa(Context ctx) {

		Empresa empresaCadastrada = new TbEmpresas(ctx).get(1);

		if (empresaCadastrada == null)
			empresaCadastrada = new Empresa();

		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.empresasform);// carregando o layout
														// do dialog do xml
		dialog.setTitle("Star Droid - Transmissão");// título do
													// dialog

		Button ok1 = (Button) dialog.findViewById(R.id.btConfirmaFtp);
		Button can = (Button) dialog.findViewById(R.id.btCancelaFtp);

		final EditText endereftp = (EditText) dialog.findViewById(R.id.endeftp);
		final EditText usuariftp = (EditText) dialog.findViewById(R.id.usuaftp);
		final EditText sennhaftp = (EditText) dialog.findViewById(R.id.senhftp);
		final EditText ocodiven = (EditText) dialog.findViewById(R.id.urlr);

		endereftp.setText(empresaCadastrada.getFtpdemp());
		usuariftp.setText(empresaCadastrada.getFtpuser());
		sennhaftp.setText(empresaCadastrada.getFtppass());
		ocodiven.setText(empresaCadastrada.getCodiven());

		ok1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Empresa novaEmpresa = new Empresa("", endereftp.getText().toString(), usuariftp.getText().toString(), sennhaftp.getText().toString(), ocodiven.getText().toString());
				novaEmpresa.setId(1);
				new TbEmpresas(v.getContext()).gravar(novaEmpresa);
				dialog.dismiss();// encerra o dialog
			}
		});
		can.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();// encerra o dialog

			}
		});

		dialog.show();// mostra o dialog

	}
	public static  boolean verificaConexao(Context c) {  
	    boolean conectado;  
	    ConnectivityManager conectivtyManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if (conectivtyManager.getActiveNetworkInfo() != null  
	            && conectivtyManager.getActiveNetworkInfo().isAvailable()  
	            && conectivtyManager.getActiveNetworkInfo().isConnected()) {  
	        conectado = true;  
	    } else {  
	        conectado = false;  
	    }  
	    return conectado;  
	}  
}

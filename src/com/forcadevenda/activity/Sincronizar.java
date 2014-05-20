package com.forcadevenda.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Random;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import com.forcadevenda.bd.TbEmpresas;
import com.forcadevenda.classes.Empresa;
import com.forcadevenda.sinc.SincXML;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GApi;
import com.thoughtworks.xstream.converters.ConversionException;

public class Sincronizar extends Activity {

	private CheckBox cbProdutos;
	private CheckBox cbClientes;
	private CheckBox cbCondPagto;
	private CheckBox cbPedidos;

	private Button btProcessar;

	private ProgressDialog dialog;
	@SuppressWarnings("unused")
	private Handler handler = new Handler();

	public static void call(Activity act) {
		try {
			Intent i = new Intent(act, Sincronizar.class);

			act.startActivity(i);
		} catch (Exception e) {
			G.msgErro(act, G.getString(act, R.string.erroformexibir),
					e.getMessage());
		}
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.sincronizar);
		inicializar();

	}

	private void inicializar() {

		G.setPreferencias(this);
		cbProdutos = (CheckBox) findViewById(R.id.cbProdutos);
		cbClientes = (CheckBox) findViewById(R.id.cbClientes);
		cbCondPagto = (CheckBox) findViewById(R.id.cbCondPagto);
		cbPedidos = (CheckBox) findViewById(R.id.cbPedidos);

		marcarTodos(true);

		btProcessar = (Button) findViewById(R.id.btProcessar);

		btProcessar.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				dialog = ProgressDialog.show(Sincronizar.this,
						"Sincronizar Dados", "Aguarde...", false, true);
				new Thread() {
					public void run() {
						try {
							processar();
						} catch (Exception e) {
							G.addLogErro("Exception :" + e.getMessage());
						}
					}
				}.start();

			}
		});

	}

	private void processar() {
		
		atualizaTela("Verificando Dados 1/3...");
		SincXML sync = new SincXML(Sincronizar.this);
		boolean erros = false;
		if ((!cbProdutos.isChecked()) && (!cbClientes.isChecked())
				&& (!cbCondPagto.isChecked()) && (!cbPedidos.isChecked())) {
			atualizaTela(erros,G.getString(Sincronizar.this, R.string.nadaselecionado));
			return;
		}
		
		Empresa empresa = new TbEmpresas(Sincronizar.this).get(1);

		Log.i("empresa.isVazia()", empresa.isVazia() ? "True" : "False");

		if (empresa.isVazia()) {
			//mensagem("Não é possivel continuar com os dados de transmissão incompletos!");
			atualizaTela(erros, "Não é possivel continuar com os dados de transmissão incompletos!");
			return;
		}
		
		if (!G.verificaConexao(Sincronizar.this)){
			//mensagem("Não é possivel continuar com sincronização ,você não tem conexão com Internet!");
			atualizaTela(erros, "Não é possivel continuar com sincronização você não tem conexão com Internet!");
			return;
		}
		
		atualizaTela("Verificando Dados 2/3...");
		String host = empresa.getFtpdemp();
		String login = empresa.getFtpuser();
		String senha = empresa.getFtppass();
		String diretorio = host.substring(host.indexOf("/"),
				host.lastIndexOf("/"));
		host = empresa.getFtpdemp().substring(0,
				empresa.getFtpdemp().indexOf("/"));

		Log.i("host", host);
		Log.i("login", login);
		Log.i("senha", senha);
		Log.i("diretorio", diretorio);
		atualizaTela("Verificando Dados 3/3...");
		if (cbClientes.isChecked() || cbProdutos.isChecked()
				|| cbCondPagto.isChecked())
			try {
				File dirpadraoxml = new File(G.DIRFTPSALVAR);
				dirpadraoxml.mkdirs();

				FileOutputStream fclientes;
				FileOutputStream fprodutos;
				FileOutputStream fcondpagt;

				FTPClient ftp = new FTPClient();
				atualizaTela("Conectando Ftp...");
				ftp.connect(host, 21);
				ftp.login(login, senha);

				ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

				ftp.changeWorkingDirectory("/" + login + diretorio);

				if (cbClientes.isChecked()) {
					atualizaTela("Download Clientes 1/2...");
					fclientes = new FileOutputStream(G.DIRFTPSALVAR
							+ SincXML.arqXmlImpClientes);
					ftp.retrieveFile(SincXML.arqXmlImpClientes, fclientes);
					fclientes.flush();
					fclientes.close();
					atualizaTela("Download Clientes 2/2...");
					erros = (erros || !sync.impClientes());

				}

				if (cbProdutos.isChecked()) {
					atualizaTela("Download Produto 1/2...");
					fprodutos = new FileOutputStream(G.DIRFTPSALVAR
							+ SincXML.arqXmlImpProdutos);
					ftp.retrieveFile(SincXML.arqXmlImpProdutos, fprodutos);
					fprodutos.flush();
					fprodutos.close();
					atualizaTela("Download Produto 2/2...");
					erros = (erros || !sync.impProdutos());
				}

				if (cbCondPagto.isChecked()) {
					atualizaTela("Download Cond.Pagamento 1/2...");
					fcondpagt = new FileOutputStream(G.DIRFTPSALVAR
							+ SincXML.arqXmlImpCondPagtos);
					ftp.retrieveFile(SincXML.arqXmlImpCondPagtos, fcondpagt);
					fcondpagt.flush();
					fcondpagt.close();
					atualizaTela("Download Cond.Pagamento 2/2...");
					erros = (erros || !sync.impCondPagtos());
				}

				ftp.logout();
				ftp.disconnect();

			} catch (SocketException e1) {
				G.addLogErro("SocketException " + e1.getMessage());
				atualizaTela(erros,"");
			} catch (IOException e1) {
				G.addLogErro("IOException " + e1.getMessage());
				atualizaTela(erros,"");
			}

		if (cbPedidos.isChecked()) {
			if (!(erros = (erros || !sync.expOrcamentos()))) {
				FileInputStream forcamentos;
				try {
					atualizaTela("Enviando Pedidos 1/3...");
					String arqEnviar = SincXML.arqXmlExpOrcamentos;
					int nr = new Random().nextInt(9000);
					forcamentos = new FileInputStream(G.DIRFTPSALVAR
							+ arqEnviar);
					arqEnviar = arqEnviar.replaceAll(".xml",
							String.format("%05d", nr) + ".xml");

					FTPClient ftp2 = new FTPClient();
					ftp2.connect(host, 21);
					ftp2.login(login, senha);
					atualizaTela("Enviando Pedidos 2/3...");

					ftp2.setFileTransferMode(FTP.BINARY_FILE_TYPE);
					ftp2.setFileType(FTPClient.BINARY_FILE_TYPE);

					ftp2.changeWorkingDirectory("/" + login + diretorio);
					ftp2.storeFile(arqEnviar, forcamentos);

					ftp2.logout();
					ftp2.disconnect();
					atualizaTela("Enviando Pedidos 3/3...");
					Log.i("ArquivoEnviado", arqEnviar);

				} catch (IOException e) {
		
					G.msgErro(Sincronizar.this, "IOException", e.getMessage());
				}

			}
		}
		GApi.vibrar(getApplicationContext(), 500);
		atualizaTela(!erros,"");
	}

	private void atualizaTela(final boolean ok,final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
				if (ok)
					G.alertar(Sincronizar.this,(msg.length()==0? G.getString(Sincronizar.this,   R.string.sucesso ): msg));
				else
					G.alertar(Sincronizar.this,(msg.length()==0? G.getString(Sincronizar.this,   R.string.sucessocomerro ): msg));
			}
		});
	}
	private void atualizaTela(final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				dialog.setMessage(msg);
			}
		});
	}
	private void mensagem(final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				G.msgInformacao(Sincronizar.this, msg);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, G.IDTODOS, 0, R.string.menutodos).setIcon(R.drawable.todos);
		menu.add(0, G.IDNENHUM, 0, R.string.menunenhum).setIcon(
				R.drawable.nenhum);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case G.IDTODOS:
			marcarTodos(true);
			break;
		case G.IDNENHUM:
			marcarTodos(false);
			break;
		}
		return true;
	}

	private void marcarTodos(boolean valor) {
		cbProdutos.setChecked(valor);
		cbClientes.setChecked(valor);
		cbCondPagto.setChecked(valor);

		cbPedidos.setChecked(valor);
	}

}

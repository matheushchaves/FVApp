package com.forcadevenda.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.forcadevenda.bd.GConexao;
import com.forcadevenda.uteis.G;

public class Uteis extends Activity {

	private LinearLayout layout;
	private Button btExec;

	public static void call(Activity act) {
		try {
			Intent i = new Intent(act, Uteis.class);

			act.startActivity(i);
		} catch (Exception e) {
			G.msgErro(act,
					G.getString(act, R.string.erroformexibir) + e.getMessage());
		}
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.uteis);

		inicializar();
	}

	@SuppressWarnings("deprecation")
	public void setBotaoCopiarXML(String texto, final String arqde,
			final String arqpara) {
		Button bt = new Button(this);
		bt.setText(texto);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				G.copiarArquivo(Uteis.this, G.DIRTESTES + "/" + arqde, G.DIRXML
						+ "/" + arqpara);
			}
		});
		bt.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		layout.addView(bt);
	}

	@SuppressWarnings("deprecation")
	public void setButtonExcluir(final String tb, final boolean apaga) {
		Button bt = new Button(this);
		if (apaga)
			bt.setText("Excluir " + tb);
		else
			bt.setText("Remover " + tb);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (apaga)
					GConexao.getConexao(Uteis.this)
							.execSQL("delete from " + tb);
				else
					GConexao.getConexao(Uteis.this).execSQL("drop table " + tb);
			}
		});
		bt.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		layout.addView(bt);
	}

	@SuppressWarnings("deprecation")
	private void inicializar() {
		layout = (LinearLayout) findViewById(R.id.llAvancado);

		Button btEmp = new Button(this);
		btEmp.setText("Dados de Transmissão");
		btEmp.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				G.CadastraEmpresa(Uteis.this);
			}
		});
		btEmp.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		layout.addView(btEmp);

		btExec = (Button) findViewById(R.id.btExec);

		btExec.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				final EditText etSQLMsg = new EditText(Uteis.this);
				etSQLMsg.setLayoutParams(new LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));

				new AlertDialog.Builder(Uteis.this)
						.setTitle("SQL")
						.setView(etSQLMsg)
						.setPositiveButton("Executar",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										try {
											GConexao.getConexao(Uteis.this)
													.execSQL(
															etSQLMsg.getText()
																	.toString());
										} catch (Exception e) {
											G.msgErro(Uteis.this,
													"Erro ao executar sql: "
															+ e.getMessage());
										}
									}
								})
						.setNegativeButton("Cancelar",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.dismiss();
									}
								}).create().show();
			}
		});

		setButtonExcluir("EMPRESAS", true);
        setButtonExcluir("EMPRESAS",false);

		// setButtonExcluir("CLIENTES");
		// setButtonExcluir("CONDPAGTOS");
		// setButtonExcluir("GASTOS");
		// setButtonExcluir("GRUPOS");
		// setButtonExcluir("ORCAMENTOS");
		// setButtonExcluir("ORCAMENTOSITENS");
		// setButtonExcluir("PRODUTOS");
		// setButtonExcluir("REGIOES");

		// setBotaoCopiarXML("Testar XML CONDPAGTOS", "impcondpagtos.xml",
		// "impcondpagtos.xml");
		// setBotaoCopiarXML("Testar XML CLIENTES", "impclientes.xml",
		// "impclientes.xml");
		// setBotaoCopiarXML("Testar XML CLIENTES (longo)",
		// "impclientes (longo).xml", "impclientes.xml");
		// setBotaoCopiarXML("Testar XML PRODUTOS", "impprodutos.xml",
		// "impprodutos.xml");
		// setBotaoCopiarXML("Testar XML PRODUTOS (atu)",
		// "impprodutos (atualizar).xml", "impprodutos.xml");

	}

}

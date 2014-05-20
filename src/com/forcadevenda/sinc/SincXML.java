package com.forcadevenda.sinc;

import java.io.File;
import java.util.List;

import android.content.Context;

import com.forcadevenda.activity.R;
import com.forcadevenda.bd.GTabela;
import com.forcadevenda.bd.TbClientes;
import com.forcadevenda.bd.TbCondPagtos;
import com.forcadevenda.bd.TbEmpresas;
import com.forcadevenda.bd.TbGastos;
import com.forcadevenda.bd.TbOrcamentos;
import com.forcadevenda.bd.TbProdutos;
import com.forcadevenda.classes.Cliente;
import com.forcadevenda.classes.CondPagto;
import com.forcadevenda.classes.Empresa;
import com.forcadevenda.classes.GRegistro;
import com.forcadevenda.classes.GRegistroImport;
import com.forcadevenda.classes.Gasto;
import com.forcadevenda.classes.Grupo;
import com.forcadevenda.classes.Orcamento;
import com.forcadevenda.classes.OrcamentoItem;
import com.forcadevenda.classes.Produto;
import com.forcadevenda.classes.Regiao;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GSQL;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SincXML {

	private Context ctx;
	private XStream xstream;

	public static final String arqXmlImpClientes = "impclientes.xml";
	public static final String arqXmlImpCondPagtos = "impcondpagtos.xml";
	public static final String arqXmlImpProdutos = "impprodutos.xml";
	public static final String arqXmlImpEmpresas = "impempresas.xml";

	public static final String arqXmlExpClientes = "expclientes.xml";
	public static final String arqXmlExpGastos = "expgastos.xml";
	public static final String arqXmlExpOrcamentos = "exporcamentos.xml";

	public SincXML(Context ctx) {
		this.ctx = ctx;
		xstream = new XStream(new DomDriver("UTF-8"));

		xstream.alias("fdv", TbXml.class);
		xstream.alias("lista", List.class);
		xstream.alias("cliente", Cliente.class);
		xstream.alias("condpagto", CondPagto.class);
		xstream.alias("gasto", Gasto.class);
		xstream.alias("grupo", Grupo.class);
		xstream.alias("orcamento", Orcamento.class);
		xstream.alias("orcamentoitem", OrcamentoItem.class);
		xstream.alias("produto", Produto.class);
		xstream.alias("regiao", Regiao.class);
		xstream.alias("empresa", Empresa.class);
	}

	public <T extends GRegistro> boolean exportar(List<T> lista, String fileName) {
		try {
			TbXml<T> xml = new TbXml<T>(lista);
			G.salvarArquivo(ctx, G.DIRXML, fileName, xstream.toXML(xml));

			return true;
		} catch (Exception e) {
			G.msgErro(ctx, G.getString(ctx, R.string.errogerararq),
					e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends GRegistroImport> boolean importar(TbXml<T> xml,
			String fileName, GTabela<T> tabela) {
		try {

			File arq = G.abrirArquivo(ctx, G.DIRXML, fileName);

			if (arq == null)
				return true;

			xml = (TbXml<T>) xstream.fromXML(arq);

			for (T objeto : (List<T>) xml.getValores()) {

				if (!tabela.gravarBy(objeto.getColcodigoimport(),
						objeto.getCodigoImport(), objeto)) {
					tabela.gravar(objeto);
				}

			}
			G.moveToBkp(ctx, G.DIRXML, fileName);

			return true;
		} catch (Exception e) {
			G.msgErro(ctx, G.getString(ctx, R.string.errorecuperararq),
					e.getMessage());
			return false;
		}
	}

	public boolean expClientes() {
		return exportar(new TbClientes(ctx).listar(), arqXmlExpClientes);
	}

	public boolean expGastos() {
		return exportar(new TbGastos(ctx).listar(), arqXmlExpGastos);
	}

	public boolean expOrcamentos() {
		return exportar(new TbOrcamentos(ctx).listar(GSQL.filtro(
				Orcamento.colStatus, "=", Orcamento.STCONCLUIDO)),
				arqXmlExpOrcamentos);
	}

	public boolean impClientes() {
		return importar(new TbXml<Cliente>(), arqXmlImpClientes,
				new TbClientes(ctx));
	}

	public boolean impCondPagtos() {
		return importar(new TbXml<CondPagto>(), arqXmlImpCondPagtos,
				new TbCondPagtos(ctx));
	}

	public boolean impProdutos() {
		return importar(new TbXml<Produto>(), arqXmlImpProdutos,
				new TbProdutos(ctx));
	}

	public boolean impEmpresas() {
		return importar(new TbXml<Empresa>(), arqXmlImpEmpresas,
				new TbEmpresas(ctx));
	}

}
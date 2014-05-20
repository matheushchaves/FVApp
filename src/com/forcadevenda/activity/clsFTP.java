package com.forcadevenda.activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class clsFTP {
	FTPClient mFtp;
	private String TAG = "clsFTP";

	// Listar os arquivos e diret�rios de um determinado caminho
	public FTPFile[] Dir(String pDiretorio) {
		try {
			FTPFile[] ftpFiles = mFtp.listFiles(pDiretorio);

			return ftpFiles;
		} catch (Exception e) {
			Log.e(TAG,
					"Erro: n�o foi poss�vel listar os arquivos e pastas do diret�rio "
							+ pDiretorio + ". " + e.getMessage());
		}
		return null;
	}

	// Pegar o nome do diret�rio corrente
	public String DiretorioCorrente() {
		try {
			String workingDir = mFtp.printWorkingDirectory();
			return workingDir;
		} catch (Exception e) {
			Log.e(TAG,
					"Erro: n�o foi poss�vel obter o diret�rio atual. "
							+ e.getMessage());
		}
		return null;
	}

	// Mudar de diret�rio
	public boolean MudarDiretorio(String pDiretorio) {
		try {
			 mFtp.changeWorkingDirectory(pDiretorio);
		} catch (Exception e) {
			Log.e(TAG, "Erro: n�o foi poss�vel mudar o diret�rio para "
					+ pDiretorio);
		}
		return false;
	}

	// Criar um diret�rio
	public boolean CriarDiretorio(String pDiretorio) {
		try {
			boolean status = mFtp.makeDirectory(pDiretorio);
			return status;
		} catch (Exception e) {
			Log.e(TAG, "Erro: n�o foi poss�vel criar o diret�rio " + pDiretorio);
		}
		return false;
	}

	// Excluir um diret�rio
	public boolean RemoveDiretorio(String pDiretorio) {
		try {
			boolean status = mFtp.removeDirectory(pDiretorio);
			return status;
		} catch (Exception e) {
			Log.e(TAG, "Erro: n�o foi poss�vel remover o diret�rio "
					+ pDiretorio);
		}
		return false;
	}

	// Deletar arquivo
	public boolean Delete(String pArquivo) {
		try {
			boolean status = mFtp.deleteFile(pArquivo);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Renomear arquivo
	public boolean Rename(String from, String to) {
		try {
			boolean status = mFtp.rename(from, to);
			return status;
		} catch (Exception e) {
			Log.e(TAG, "Erro: N�o pode renomear o arquivo: " + from + " para: "
					+ to);
		}
		return false;
	}

	// Baixar um arquivo do servidor FTP
	public boolean Download(String pDiretorioOrigem, String pArqOrigem,
			String pArqDestino) {
		boolean status = false;
		try {
			MudarDiretorio(pDiretorioOrigem);

			// Cria o outputStream para ser passado como parametro
			FileOutputStream desFileStream = new FileOutputStream(pArqDestino);
			

			// Tipo de arquivo
			mFtp.setFileType(FTP.BINARY_FILE_TYPE);

			// http://commons.apache.org/net/apidocs/org/apache/commons/net/ftp/FTPClient.html#enterLocalActiveMode()
			mFtp.enterLocalPassiveMode();

			// Faz o download do arquivo
			status = mFtp.retrieveFile(pArqOrigem, desFileStream);
			Log.i("Origem", pArqOrigem);
			Log.i("Destino", pArqDestino);

			// Fecho o output
			desFileStream.close();

			return status;
		} catch (Exception e) {
			Log.e(TAG, "Erro: Falha ao efetuar download. " + e.getMessage());
		}
		return status;
	}

	// Enviar arquivo para o servidor FTP
	public boolean Upload(String pArqOrigem, String pArqDestino,
			String pDiretorioDestino) {
		boolean status = false;
		try {
			FileInputStream srcFileStream = new FileInputStream(pArqOrigem);

			// muda o diret�rio para o destino espec�fico
			if (MudarDiretorio(pDiretorioDestino)) {
				status = mFtp.storeFile(pArqDestino, srcFileStream);
			}

			srcFileStream.close();
			return status;
		} catch (Exception e) {
			Log.e(TAG, "Erro: Falha ao efetuar Upload. " + e.getMessage());
		}
		return status;
	}

	// Encerrar a conex�o com o servidor FTP
	public boolean Desconectar() {
		try {
			// mFtp.logout();
			mFtp.disconnect();
			mFtp = null;
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Erro: ao desconectar. " + e.getMessage());
		}
		return false;
	}

	// Efetuar conex�o com o servidor FTP
	public boolean Conectar(String pHost, String pUsuario, String pSenha,
			int pPorta) {
		try {
			mFtp = new FTPClient();

			// conectando no host
			mFtp.connect(pHost, pPorta);

			// verifica se a conex�o est� ok
			if (FTPReply.isPositiveCompletion(mFtp.getReplyCode())) {

				// efetua login
				boolean status = mFtp.login(pUsuario, pSenha);

				mFtp.setFileType(FTP.BINARY_FILE_TYPE);
				mFtp.enterLocalPassiveMode();

				return status;
			}
		} catch (Exception e) {
			Log.e(TAG, "Erro: n�o foi poss�vel conectar ao host " + pHost);
		}
		return false;
	}
}

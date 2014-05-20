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

	// Listar os arquivos e diretórios de um determinado caminho
	public FTPFile[] Dir(String pDiretorio) {
		try {
			FTPFile[] ftpFiles = mFtp.listFiles(pDiretorio);

			return ftpFiles;
		} catch (Exception e) {
			Log.e(TAG,
					"Erro: não foi possível listar os arquivos e pastas do diretório "
							+ pDiretorio + ". " + e.getMessage());
		}
		return null;
	}

	// Pegar o nome do diretório corrente
	public String DiretorioCorrente() {
		try {
			String workingDir = mFtp.printWorkingDirectory();
			return workingDir;
		} catch (Exception e) {
			Log.e(TAG,
					"Erro: não foi possível obter o diretório atual. "
							+ e.getMessage());
		}
		return null;
	}

	// Mudar de diretório
	public boolean MudarDiretorio(String pDiretorio) {
		try {
			 mFtp.changeWorkingDirectory(pDiretorio);
		} catch (Exception e) {
			Log.e(TAG, "Erro: não foi possível mudar o diretório para "
					+ pDiretorio);
		}
		return false;
	}

	// Criar um diretório
	public boolean CriarDiretorio(String pDiretorio) {
		try {
			boolean status = mFtp.makeDirectory(pDiretorio);
			return status;
		} catch (Exception e) {
			Log.e(TAG, "Erro: não foi possível criar o diretório " + pDiretorio);
		}
		return false;
	}

	// Excluir um diretório
	public boolean RemoveDiretorio(String pDiretorio) {
		try {
			boolean status = mFtp.removeDirectory(pDiretorio);
			return status;
		} catch (Exception e) {
			Log.e(TAG, "Erro: não foi possível remover o diretório "
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
			Log.e(TAG, "Erro: Não pode renomear o arquivo: " + from + " para: "
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

			// muda o diretório para o destino específico
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

	// Encerrar a conexão com o servidor FTP
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

	// Efetuar conexão com o servidor FTP
	public boolean Conectar(String pHost, String pUsuario, String pSenha,
			int pPorta) {
		try {
			mFtp = new FTPClient();

			// conectando no host
			mFtp.connect(pHost, pPorta);

			// verifica se a conexão está ok
			if (FTPReply.isPositiveCompletion(mFtp.getReplyCode())) {

				// efetua login
				boolean status = mFtp.login(pUsuario, pSenha);

				mFtp.setFileType(FTP.BINARY_FILE_TYPE);
				mFtp.enterLocalPassiveMode();

				return status;
			}
		} catch (Exception e) {
			Log.e(TAG, "Erro: não foi possível conectar ao host " + pHost);
		}
		return false;
	}
}

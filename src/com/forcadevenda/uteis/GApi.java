package com.forcadevenda.uteis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;

import com.forcadevenda.activity.R;

public class GApi {
    
    public static final String PAGINAPRINCIPAL = "http://www.google.com.br";
    public static final int MAPAZOOM = 18;
    
    public static void vibrar(Context ctx, long tempo){
        Vibrator rr = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);        
        rr.vibrate(tempo);
    }
    
    public static void vibrar(Context ctx){
        vibrar(ctx, 30);
    }    

    public static void navegar(Activity act, String url){
        if (G.strIsEmpty(url))
            url = PAGINAPRINCIPAL;
                
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        act.startActivity(browserIntent);        
    }    
    
    public static void startFala(Activity act){
        try{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 6);                
            act.startActivityForResult(intent, G.IDGETVOZ);        
        } catch (ActivityNotFoundException e){
            G.msgErro(act, G.getString(act, R.string.aparelhosemsuporte));
        } catch (Exception e){
            G.msgErro(act, G.getString(act, R.string.errofala), e.getMessage());            
        }
    }        
    
    public static void setBtFala(final Activity act, ImageButton btFala) {
        btFala.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                startFala(act);
            }
        });
    }
    
    public static String[] getResultFala(Intent data){
        ArrayList<String> resultados = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);                        
        return resultados.toArray(new String[resultados.size()]);
    }    
    
    public static void startCodBarras(Activity act) {
        try{
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);        
            act.startActivityForResult(intent, G.IDGETCODBARRAS);
        } catch (ActivityNotFoundException e){
            G.msgErro(act, G.getString(act, R.string.aparelhosemsuporte));
        } catch (Exception e){
            G.msgErro(act, G.getString(act, R.string.errocodbarras), e.getMessage());            
        }
    }    

    public static void setBtCodBarras(final Activity act, ImageButton btCodBarras) {
        btCodBarras.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View arg0) {
                startCodBarras(act);
            }
        });
    }
    
    public static String getResultCodBarras(Intent data){
        String conteudo = data.getStringExtra("SCAN_RESULT");
        
        if (G.strIsEmpty(conteudo))
            conteudo = "";
        
        return conteudo;
    }    
    
    public static void enviarEmail(Activity act, String[] para, String assunto, String mensagem, String[] arquivos) {
        try {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
            
            emailIntent.setType("text/plain");
 
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, para); 
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, assunto);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mensagem);
 
            ArrayList<Uri> uris = new ArrayList<Uri>() ;
            
            for (String arquivo : arquivos) {
                File file = new File(arquivo);
                if (!file.exists())
                    continue;
                Uri u = Uri.fromFile(file);
                uris.add(u);
            }
            
            emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);                                    
 
            act.startActivity(emailIntent);
 
        } catch (ActivityNotFoundException e){
            G.msgErro(act, G.getString(act, R.string.aparelhosemsuporte));        
        } catch (Exception e) {            
            G.msgErro(act, G.getString(act, R.string.erroenviaremail), e.getMessage());
        }
    }    
    
    public static void enviarEmail(Activity act, String[] para, String assunto, String mensagem){
        enviarEmail(act, para, assunto, mensagem, new String[]{});
    }
    
    public static void enviarEmail(Activity act, String[] para){
        enviarEmail(act, para, "", "");
    }
    
    public static void ligar(Activity act, String numero){
        try{
            numero.replaceAll("[^0-9]*", "");
            if (numero.length() == 10)
                numero = "0" + numero;            
            
            numero = "tel:" + numero.trim();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(numero)); 
            act.startActivity(callIntent);         
        } catch (ActivityNotFoundException e){
            G.msgErro(act, G.getString(act, R.string.aparelhosemsuporte));            
        } catch (Exception e) {
            G.msgErro(act, G.getString(act, R.string.erroligar), e.getMessage());
        }        
    }
    
    public static void enviarSMS(Activity act, String[] para, String mensagem){
        try{
            SmsManager sms = SmsManager.getDefault();
            PendingIntent intent = PendingIntent.getBroadcast(act, 0, new Intent(), 0);

            for (String strpara: para)
                sms.sendTextMessage(strpara, null, mensagem, intent, null);
            
            G.alertar(act, G.getString(act, R.string.sucesso));
        } catch (ActivityNotFoundException e){
            G.msgErro(act, G.getString(act, R.string.aparelhosemsuporte));            
        } catch (Exception e) {
            G.msgErro(act, G.getString(act, R.string.errosms), e.getMessage());
        }        
    }
    
    @SuppressWarnings("deprecation")
	public static void enviarSMS(final Activity act, final String[] para){
        final EditText etMsg = new EditText(act);
        etMsg.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));                
                
        new AlertDialog.Builder(act).setTitle(G.getString(act, R.string.mensagem))
            .setView(etMsg)
            .setPositiveButton(G.getString(act, R.string.enviar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {    
                        GApi.enviarSMS(act, para, etMsg.getText().toString());
                    }
                })
                .setNegativeButton(G.getString(act, R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();                                
                    }
                }).create().show();            
    }
    
    public static void mapa(Activity act, String endereco, String cep){
        try {
            Geocoder geocoder = new Geocoder(act, Locale.getDefault());            
            
            List<Address> enderecos;
                    
            enderecos = geocoder.getFromLocationName(endereco, 1);
            
            if ((enderecos.isEmpty()) && (!G.strIsEmpty(cep)))
                enderecos = geocoder.getFromLocationName(cep, 1);            
            
            if (enderecos.isEmpty()){
                G.msgAlerta(act, G.getString(act, R.string.endereconaoencontrado));
                return;
            }
            
            String latitude = Double.toString(enderecos.get(0).getLatitude()).replace(",", ".");
            String longitude = Double.toString(enderecos.get(0).getLongitude()).replace(",", ".");
            
            //endereco = "geo:0,0?q="+endereco.replace(" ", "+");            
            endereco = String.format("geo:%s,%s?z=%s", latitude, longitude, GApi.MAPAZOOM);
            
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(endereco));            
            act.startActivity(intent);        
        } catch (ActivityNotFoundException e){
            G.msgErro(act, G.getString(act, R.string.aparelhosemsuporte));            
        } catch (Exception e) {
            G.msgErro(act, G.getString(act, R.string.erromapa), e.getMessage());
        }        
    }
    
    public static void mapa(Activity act, String endereco){
        mapa(act, endereco, "");
    }
 }

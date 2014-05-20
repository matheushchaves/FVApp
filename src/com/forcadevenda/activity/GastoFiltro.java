package com.forcadevenda.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import com.forcadevenda.bd.TbGastos;
import com.forcadevenda.classes.Gasto;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GApi;
import com.forcadevenda.uteis.GSQL;
import android.widget.DatePicker;
import java.util.Calendar;

public class GastoFiltro extends GFiltroActivity {
    
    private AutoCompleteTextView etDescricao;  
    private EditText etData;  
    
    private ImageButton btData;
    
    private int mYear;
    private int mMonth;
    private int mDay;    
    
    public static void call(Activity act, int idChamada, ContentValues filtro){
        try{    
            Intent i = new Intent(act, GastoFiltro.class);            
            
            i.putExtra(G.EXTRACTVFILTRO, filtro);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errofiltroexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle, R.layout.gastofiltro);
    }
    
    @Override
    protected void inicializar(){
        GApi.setBtFala(this, (ImageButton) findViewById(R.id.btFala));        
        
        etDescricao = (AutoCompleteTextView) findViewById(R.id.etDescricao);        
        etData = (EditText) findViewById(R.id.etData);        
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, (new TbGastos(this).listarDescricoes()));
        etDescricao.setAdapter(adapter);        
        
        btData = (ImageButton) findViewById(R.id.btData);
        
        btData.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
			public void onClick(View v) {
                showDialog(G.IDDATADIALOG);
            }
        });
        
        etDescricao.requestFocus();
        
        super.inicializar();            
    }
    
    @Override
    protected void popularTela(){
        etDescricao.setText(filtro.getAsString(Gasto.colDescricao));
        etData.setText(filtro.getAsString(Gasto.colData));
    }
    
    @Override
    protected void popularObjeto(){
        filtro.put(Gasto.colDescricao, etDescricao.getText().toString());
        filtro.put(Gasto.colData, etData.getText().toString());
    }
    
    @Override
    protected void limpar() {
        etDescricao.setText("");
        etData.setText("");
	filtrar();
    }

    @Override
    public String toString() {
        String f = "";
        f = GSQL.filtroAnd(f, GSQL.filtroLike(Gasto.colDescricao, filtro.getAsString(Gasto.colDescricao)));
        f = GSQL.filtroAnd(f, GSQL.filtroData(Gasto.colData, " = ", filtro.getAsString(Gasto.colData)));        
        
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == G.IDGETVOZ){
                final String[] itens = GApi.getResultFala(data);
                G.msgLista(this, itens, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            etDescricao.setText(itens[which]);
                            openOptionsMenu();
                        }
                    });                
            }
        }
    }   
    
    @Override
    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        if (!G.strIsEmpty(etData.getText().toString()))
            c.setTime(G.strToDate(G.MASCDATA, etData.getText().toString()));
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);         
        
        if (id == G.IDDATADIALOG)
            return new DatePickerDialog(this,
                                        mDateSetListener,
                                        mYear, mMonth, mDay);
        else
            return null;
    }    
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        if (id == G.IDDATADIALOG)
            ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);        
    }      
    
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    
                    etData.setText(new StringBuilder()
                        .append(mDay).append("/")
                        .append(mMonth + 1).append("/")
                        .append(mYear));
                    
                    openOptionsMenu();
                }
                
            };    
    
}

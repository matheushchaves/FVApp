package com.forcadevenda.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.forcadevenda.uteis.G;
import com.forcadevenda.uteis.GSQL;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import com.forcadevenda.bd.TbClientes;
import java.util.Calendar;
import com.forcadevenda.classes.Cliente;
import com.forcadevenda.classes.Orcamento;

public class OrcamentoFiltro extends GFiltroActivity {
    
    private EditText etCliente;  
    private EditText etData;  
    
    private RadioGroup rgStatus;
    
    private ImageButton btData;
    private ImageButton btProcCliente;
    
    private Cliente cliente;
    
    private final int IDACHACLIENTE = 1;
    
    private int mYear;
    private int mMonth;
    private int mDay;    
    
    public static void call(Activity act, int idChamada, ContentValues filtro){
        try{    
            Intent i = new Intent(act, OrcamentoFiltro.class);            
            
            i.putExtra(G.EXTRACTVFILTRO, filtro);            
            
            act.startActivityForResult(i, idChamada);                       
        } catch(Exception e){
            G.msgErro(act, G.getString(act, R.string.errofiltroexibir), e.getMessage());
        }
    }    
    
    @Override
    public void onCreate(Bundle icicle) {
        cliente = new Cliente("");
        
        super.onCreate(icicle, R.layout.orcamentofiltro);
    }
    
    @Override
    protected void inicializar(){
        etCliente = (EditText) findViewById(R.id.etCliente);        
        etData = (EditText) findViewById(R.id.etData);        
        rgStatus = (RadioGroup) findViewById(R.id.rgStatus);
        
        btData = (ImageButton) findViewById(R.id.btData);
        
        btData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(G.IDDATADIALOG);
            }
        });
        
        btProcCliente = (ImageButton) findViewById(R.id.btProcCliente);     
        
        btProcCliente.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Clientes.call(OrcamentoFiltro.this, IDACHACLIENTE, true);
            }
        });        
        
        etCliente.requestFocus();
        
        super.inicializar();            
    }
    
    @Override
    protected void popularTela(){
        etData.setText(filtro.getAsString(Orcamento.colData));
        
        if (filtro.getAsInteger(Orcamento.colStatus) != null)
            rgStatus.check(filtro.getAsInteger(Orcamento.colStatus));        
        
        long idcliente = 0;
        try{
            idcliente = filtro.getAsLong(Orcamento.colCliente);
        } catch (Exception e){
            idcliente = 0;
        }
        
        setCliente(idcliente);         
    }
    
    @Override
    protected void popularObjeto(){
        filtro.put(Orcamento.colStatus, rgStatus.getCheckedRadioButtonId());
        filtro.put(Orcamento.colCliente, cliente.getId());
        filtro.put(Orcamento.colData, etData.getText().toString());                
    }
    
    @Override
    protected void limpar() {
        etData.setText("");
        rgStatus.check(R.id.rbAmbos);
        setCliente(new Cliente(""));
	filtrar();
    }
    
    private void setCliente(long id){
        setCliente((new TbClientes(this)).get(id));
    }
    
    private void setCliente(Cliente cliente) {
        if (cliente == null)
            cliente = new Cliente("");
        
        this.cliente = cliente;
        etCliente.setText(cliente.getNome());
    }    

    @Override
    public String toString() {
        String f = "";
        
        if (cliente.getId() != 0)
            f = GSQL.filtroAnd(f, GSQL.filtro(Orcamento.colCliente, " = ", filtro.getAsLong(Orcamento.colCliente)));  
        
        f = GSQL.filtroAnd(f, GSQL.filtroData(Orcamento.colData, " = ", filtro.getAsString(Orcamento.colData)));        
        
        switch (rgStatus.getCheckedRadioButtonId()) {
            case R.id.rbAberto: f = GSQL.filtroAnd(f, GSQL.filtro(Orcamento.colStatus, " = ", Orcamento.STABERTO)); break;
            case R.id.rbConcluido: f = GSQL.filtroAnd(f, GSQL.filtro(Orcamento.colStatus, " = ", Orcamento.STCONCLUIDO)); break;
        }
        
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if ((requestCode == IDACHACLIENTE)){
                setCliente((Cliente)data.getSerializableExtra(G.EXTRASELECAO));
                openOptionsMenu();
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

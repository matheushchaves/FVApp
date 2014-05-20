package com.forcadevenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.forcadevenda.uteis.G;

public class Configuracoes extends PreferenceActivity {
    
    public static void call(Activity act) {
        try {
            Intent i = new Intent(act, Configuracoes.class);

            act.startActivity(i);
        } catch (Exception e) {
            G.msgErro(act, G.getString(act, R.string.erroformexibir), e.getMessage());
        }
    }     

	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.layout.configuracoes);
    }

}

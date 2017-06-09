package com.manu.projeto.boaviagemfinal.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manu.projeto.boaviagemfinal.R;
import com.manu.projeto.boaviagemfinal.Sobre;
import com.manu.projeto.boaviagemfinal.login.LoginActivity;
import com.manu.projeto.boaviagemfinal.registro.CadastroUsuario;


/**
 * Created by emanu on 07/06/2017.
 */

@SuppressLint("Registered")
public class MainDashBoard extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    TextView txtSejaBemVindo;
    String nome;
    String currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            nome = user.getEmail();
            currentUser = String.valueOf(FirebaseAuth.getInstance().getCurrentUser());
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
        txtSejaBemVindo = (TextView) findViewById(R.id.txtSejaBemVindo);

        Bundle bundle = getIntent().getExtras();
//        final String nomePessoa = (String) bundle.get("nome");
        txtSejaBemVindo.setText("Seja bem vind@  " + currentUser);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                adicionarViagem();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void adicionarViagem() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info_usuario:
                cadastroUsuario();
                return true;
            case R.id.sobre_app:
                sobreActivity();
                return true;
            case R.id.sair:
                sairActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cadastroUsuario(){
        Intent intent = new Intent(this, CadastroUsuario.class);
        startActivity(intent);
    }
    public void sobreActivity() {
        Intent intent = new Intent(this, Sobre.class);
        startActivity(intent);
    }

    public void sairActivity() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}

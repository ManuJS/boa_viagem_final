package com.manu.projeto.boaviagemfinal.registro;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manu.projeto.boaviagemfinal.R;
import com.manu.projeto.boaviagemfinal.modelo.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emanu on 08/06/2017.
 */

public class CadastroUsuario extends AppCompatActivity {

    Button btnSalvarDadosUsuario;

    EditText edtNomeUsuario;
    EditText edtNumeroTelUsuario;

    private static final String TAG = "NovoUsuario";
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_usuario);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSalvarDadosUsuario = (Button) findViewById(R.id.botao_atualizar_dados_usuario);

        btnSalvarDadosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarNovoUsuario();
            }
        });

        edtNomeUsuario = (EditText) findViewById(R.id.nome_usuario);
        edtNumeroTelUsuario = (EditText) findViewById(R.id.telefone_usuario);



    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    private void criarNovoUsuario() {
        final String nome = edtNomeUsuario.getText().toString().trim();
        final String telefone = edtNumeroTelUsuario.getText().toString().trim();
        final String userId = getUid();


            mDatabase.child("usuario").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Usuario user = dataSnapshot.getValue(Usuario.class);
                    if (user == null) {
                        Toast.makeText(CadastroUsuario.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                    } else {
                        novoUsuario(userId, nome, telefone);
                    }

                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(CadastroUsuario.this, "onCancelled: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

    }


    private void novoUsuario(String userId, String nome, String telefone) {
        // Create new post at /user-posts/$userid/$postid
        // and at /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Usuario usuario = new Usuario(userId, nome, telefone);
        Map<String, Object> postValues = usuario.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/usuario/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }


}

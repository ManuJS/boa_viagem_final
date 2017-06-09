package com.manu.projeto.boaviagemfinal.registro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.manu.projeto.boaviagemfinal.R;
import com.manu.projeto.boaviagemfinal.dashboard.MainDashBoard;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener  {

    private FirebaseAuth firebaseAuth;
    EditText editLogin;
    EditText editPassword;
    Button btnLoginRegister;
    ProgressDialog progressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        editLogin = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        btnLoginRegister = (Button) findViewById(R.id.email_sign_in_button);
        btnLoginRegister.setText(R.string.action_register);
        progressLogin = new ProgressDialog(this);
        btnLoginRegister.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editLogin.getText().toString().trim();
        String password  = editPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, R.string.enter_your_email,Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.enter_your_password,Toast.LENGTH_LONG).show();
            return;
        }
        progressLogin.setMessage(getString(R.string.registering_user));
        progressLogin.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, R.string.successfully_registred,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainDashBoard.class);
                            intent.putExtra("nome", editLogin.getText().toString().trim());
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegistrationActivity.this, R.string.regristration_error,Toast.LENGTH_LONG).show();
                        }
                        progressLogin.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }
}

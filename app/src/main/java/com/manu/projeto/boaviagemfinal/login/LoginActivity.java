package com.manu.projeto.boaviagemfinal.login;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.manu.projeto.boaviagemfinal.registro.RegistrationActivity;


/**
 * Created by emanu on 11/02/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    EditText editLogin;
    EditText editPassword;
    Button btnLogin;
    Button btnRegister;
    ProgressDialog progressLogin;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), MainDashBoard.class));
        }

        editLogin = (EditText) findViewById(R.id.email);
        String message = editLogin.getText().toString();
        editPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnLogin.setText(R.string.action_sign_in);
        progressLogin = new ProgressDialog(this);
        btnLogin.setOnClickListener(this);


        btnRegister = (Button) findViewById(R.id.btn_registro);
        btnRegister.setText("Novo " + getString(R.string.action_register));
        btnRegister.setOnClickListener(this);
    }


    public void userLogin(){
        final String email = editLogin.getText().toString().trim();
        String password  = editPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, R.string.enter_your_email,Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.enter_your_password,Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressLogin.setMessage(getString(R.string.make_login));
        progressLogin.show();



        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressLogin.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainDashBoard.class);
                            intent.putExtra("nome", editLogin.getText().toString().trim());
                            startActivity(intent);


                        }
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, R.string.Invalid_user,Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin){
            userLogin();
        }

        if(view == btnRegister){
            finish();
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }
}

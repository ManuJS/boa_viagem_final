package com.manu.projeto.boaviagemfinal.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.manu.projeto.boaviagemfinal.R;
import com.manu.projeto.boaviagemfinal.dashboard.MainDashBoard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Pesquisador04 on 31/03/2017.
 */
public class ExampleFragment extends Fragment {

    @BindView(R.id.email)
    EditText toastEditUsuario;
    @BindView(R.id.password)
    EditText toastEditSenha;

    @BindView(R.id.btn_registro)
    Button buttonRegistro;

    @BindView(R.id.email_sign_in_button)
    Button buttonLogin;

    private FirebaseAuth firebaseAuth;

    //    @BindViews(R.id.login_progress)
    ProgressDialog progressLogin;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Add this line in order for this fragment to handle menu events.
//        setHasOptionsMenu(true);
//
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_login, container, false);
        ButterKnife.bind(this, view);
        buttonLogin.setText("Login");
        buttonRegistro.setText("Registro");

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

//        // Initializes Butter Knife and Bind the Views to our Activity
//        ButterKnife.bind(this);
//        buttonLogin.setText("Login");
//        buttonRegistro.setText("Registro");

    }

    @OnClick(R.id.email_sign_in_button)
    public void loginUsuario(Button buttonLogin) {

        firebaseAuth = FirebaseAuth.getInstance();
        progressLogin = new ProgressDialog(getActivity());
        progressLogin.setMessage(getString(R.string.make_login));
        progressLogin.show();


        final String usuario = toastEditUsuario.getText().toString();
        final String senha = toastEditSenha.getText().toString();


        if (toastEditUsuario.getText().toString().isEmpty()) {

            Toast.makeText(getActivity().getApplicationContext(), "Usuario vazio", Toast.LENGTH_SHORT).show();

        } else {
            firebaseAuth.signInWithEmailAndPassword(usuario, senha)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //if the task is successfull
                            progressLogin.dismiss();
                            if (task.isSuccessful()) {
                                //start the profile activity
                                getActivity().finish();
                                Intent intent = new Intent(getActivity().getApplicationContext(), MainDashBoard.class);
                                intent.putExtra("nome", usuario);
                                startActivity(intent);
                            }
                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.Invalid_user, Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                            }
                        }
                    });


        }
    }

    @OnClick(R.id.btn_registro)
    public void cadastrarUsuario() {
        Toast.makeText(getActivity().getApplicationContext(), "Hello " + toastEditSenha.getText().toString(), Toast.LENGTH_SHORT).show();
    }


}
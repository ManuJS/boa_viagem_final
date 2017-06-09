package com.manu.projeto.boaviagemfinal.login;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.manu.projeto.boaviagemfinal.R;
import com.manu.projeto.boaviagemfinal.dashboard.MainDashBoard;

/**
 * Created by Pesquisador04 on 31/03/2017.
 */

public class PrimeiraActivity extends AppCompatActivity {

    private final String LOG_TAG = MainDashBoard.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ExampleFragment())
                    .commit();
        }
    }

}

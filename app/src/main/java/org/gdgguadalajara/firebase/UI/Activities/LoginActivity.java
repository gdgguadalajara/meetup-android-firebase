package org.gdgguadalajara.firebase.UI.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.gdgguadalajara.firebase.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private String mUsername;
    TextView  txtEmail, txtPassword;
    Button btnLogin;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        initializeView();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initializeView(){
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        txtEmail = (TextView)findViewById(R.id.edit_txt_mail);
        txtPassword  =(TextView)findViewById(R.id.edit_txt_pass);
    }


    @Override
    public void onClick(View view) {
        Toast.makeText(context,"Test mail:"+txtEmail.getText(),Toast.LENGTH_LONG).show();
    }
}

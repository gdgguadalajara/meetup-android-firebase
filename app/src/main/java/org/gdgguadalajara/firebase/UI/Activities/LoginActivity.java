package org.gdgguadalajara.firebase.UI.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.gdgguadalajara.firebase.R;
import org.gdgguadalajara.firebase.Utils.Settings;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private String mUsername;
    private TextView  txtEmail, txtPassword;
    private Button btnLogin;
    private Context context;
    private Firebase mFirebaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        Settings.getFirebaseInitialize(context);
        mFirebaseReference = Settings.getFirebaseReference();
        initializeView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAuthStateListener();
    }


    private void initializeView(){
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        txtEmail = (TextView)findViewById(R.id.edit_txt_mail);
        txtPassword  =(TextView)findViewById(R.id.edit_txt_pass);
    }


    private void getAuthStateListener(){

        mFirebaseReference.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    mUsername = ((String) authData.getProviderData().get(Settings.getFirebaseMail()));
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Settings.USER_MAIL, mUsername);
                    startActivity(intent);
                    Settings.setMail(context, mUsername);
                    Toast.makeText(context, getString(R.string.welcome) + " " + mUsername, Toast.LENGTH_SHORT).show();

                } else {
                    mUsername = null;

                }
            }
        });
    }

    private void firebaseLogin (final String email, final String password){
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, null,getString(R.string.login_progress_dialog), true);

        mFirebaseReference.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                mFirebaseReference.authWithPassword(email, password, null);
                progressDialog.dismiss();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                if(firebaseError.getCode() == FirebaseError.EMAIL_TAKEN){
                    mFirebaseReference.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {

                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            progressDialog.dismiss();
                            Toast.makeText(context, getString(R.string.review_credentials), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        firebaseLogin(txtEmail.getText().toString(), txtPassword.getText().toString());
        //Toast.makeText(context,"Test mail:"+txtEmail.getText(),Toast.LENGTH_LONG).show();
    }


}

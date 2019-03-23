package a.sarvesh.articjobs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signup extends AppCompatActivity implements View.OnClickListener{

    EditText Etmail, EtPassword, EtMobile;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Etmail = findViewById(R.id.etmail);
        EtPassword = findViewById(R.id.password);
        EtMobile = findViewById(R.id.phone);
        progressBar = findViewById(R.id.progressbar);
        findViewById(R.id.btnsignup2).setOnClickListener(this);
        findViewById(R.id.login2).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(){
        String username = Etmail.getText().toString().trim();
        String password = EtPassword.getText().toString().trim();
        String mobile = EtMobile.getText().toString().trim();

        if (username.isEmpty()){
            Etmail.setError("Enter Email id");
            Etmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            Etmail.setError("Enter a valid Email id");
            Etmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            EtPassword.setError("Enter Password");
            EtPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            EtPassword.setError("Minimum length of password is 6");
            EtPassword.requestFocus();
            return;
        }

        if (mobile.isEmpty()){
            EtMobile.setError("Enter Mobile Number");
            EtMobile.requestFocus();
            return;
        }

        if (mobile.length()<10){
            EtMobile.setError("Enter valid Mobile Number");
            EtMobile.requestFocus();
            return;
        }

        if (mobile.length()>10){
            EtMobile.setError("Enter valid Mobile Number");
            EtMobile.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Registeration Sucessful!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, Login.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Registration Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnsignup2:
                registerUser();
                break;

            case R.id.login2:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}

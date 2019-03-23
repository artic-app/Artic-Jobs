package a.sarvesh.articjobs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText Etmail, EtPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        Etmail = findViewById(R.id.lmail);
        EtPassword = findViewById(R.id.lpassword);

        findViewById(R.id.btnsignup).setOnClickListener(this);
        findViewById(R.id.btnlogin).setOnClickListener(this);

    }

    private void userlogin(){
        String username = Etmail.getText().toString().trim();
        String password = EtPassword.getText().toString().trim();

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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(Login.this, NavigationBar.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
                }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnsignup:
                startActivity(new Intent(this, signup.class));
                break;

            case R.id.btnlogin:
                userlogin();
                break;
        }


    }
}

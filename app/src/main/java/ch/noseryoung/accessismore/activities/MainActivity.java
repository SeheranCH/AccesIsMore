package ch.noseryoung.accessismore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.noseryoung.accessismore.R;
import ch.noseryoung.accessismore.domainModell.User;
import ch.noseryoung.accessismore.persistence.AppDatabase;
import ch.noseryoung.accessismore.persistence.UserDAO;
import ch.noseryoung.accessismore.security.PasswordEncoder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private UserDAO mUserDAO;

    private PasswordEncoder passwordEncoder;

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonToGetSignedIn;
    private Button mButtonToCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonToGetSignedIn = findViewById(R.id.signInButton);
        mButtonToGetSignedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextEmail = findViewById(R.id.emailFieldText);
                mEditTextPassword = findViewById(R.id.passwordFieldText);
                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();
                try {
                    password = passwordEncoder.encrypt(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkSignInData(email, password);
            }
        });
        mButtonToCreateAccount = findViewById(R.id.createNewAccountButton);
        mButtonToCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccountActivity();
            }
        });

        mUserDAO = AppDatabase.getAppDb(getApplicationContext()).getUserDAO();
    }

    private void checkSignInData(String email, String password) {
        User user = mUserDAO.checkSignInData(email, password);
        Log.d(TAG, "\n" + email + "\n" + password);
        if (user!=null) {
            Log.d(TAG, getString(R.string.message_signed_in_success));
            Toast.makeText(this, getString(R.string.toast_signed_in_success), Toast.LENGTH_LONG).show();
        } else {
            // throw new Exception
        }
        // TO DO: go to Welcome-Activity
    }

    private void openCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        Log.d(TAG, "Open new activity 'CreateAccount'");
        startActivity(intent);
    };


}

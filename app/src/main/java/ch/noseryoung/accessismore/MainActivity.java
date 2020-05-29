package ch.noseryoung.accessismore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.noseryoung.accessismore.domainModell.User;
import ch.noseryoung.accessismore.persistence.AppDatabase;
import ch.noseryoung.accessismore.persistence.UserDAO;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private UserDAO mUserDAO;

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonToGetSignedIn;
    private Button mButtonToCreateAccount;

    private void checkSignInData(String email, String password) {
        User user = mUserDAO.checkSignInData(email, password);
        if (user!=null) {
            Log.d(TAG, "you have been signed in succesfully");
            Toast.makeText(this, "Sie haben sich erfolgreich eingeloggt", Toast.LENGTH_LONG).show();
        } else {
            // throw new Exception
        }
        // TO DO: go to Welcome-Activity
    }

    private void openCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
        Log.d(TAG, "open new activity 'CreateAccount'");
    };

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
}

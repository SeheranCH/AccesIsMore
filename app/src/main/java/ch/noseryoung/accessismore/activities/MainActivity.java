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
import ch.noseryoung.accessismore.exception.InvalidDataException;
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
        // OnClickHandler for button 'Anmelden'
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
                try {
                    checkSignInData(email, password);
                } catch (InvalidDataException e) {
                    e.printStackTrace();
                }
            }
        });
        mButtonToCreateAccount = findViewById(R.id.createNewAccountButton);
        // OnClickHandler for button 'Neues Konto erstellen'
        mButtonToCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccountActivity();
            }
        });

        // DB connection
        mUserDAO = AppDatabase.getAppDb(getApplicationContext()).getUserDAO();
    }

    private void checkSignInData(String email, String password) throws InvalidDataException {
        User user = mUserDAO.getSignInData(email, password);
        if (user != null) {
            Log.d(TAG, "You have been signed in successfully");
            Toast.makeText(this, getString(R.string.toast_signed_in_success), Toast.LENGTH_LONG).show();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String pathPicture = user.getPathPicture();
            openWelcomeScreenActivity(firstName, lastName, pathPicture);
        } else {
            Toast.makeText(this, getString(R.string.toast_signed_in_failure), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Sign in data are false");
            throw new InvalidDataException("Sign in data are false");
        }
    }

    private void openCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        Log.d(TAG, "Open new activity 'CreateAccount'");
        startActivity(intent);
    }

    private void openWelcomeScreenActivity(String firstName, String lastName, String picturePath) {
        Intent intent = new Intent(this, WelcomeScreenActivity.class);
        // Add user info for WelcomeScreenActivity
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("pathPicture", picturePath);
        Log.d(TAG, "Open new activity 'WelcomeScreen'");
        startActivity(intent);
    }
}

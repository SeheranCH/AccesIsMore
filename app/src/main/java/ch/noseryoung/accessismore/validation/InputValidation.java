package ch.noseryoung.accessismore.validation;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final String NAME_PATTERN = "^[A-Za-zäàæÄÀÆéèÉÈöÖüÜ ]+$";

    public boolean isNotEmpty (String text) {
        return !text.isEmpty();
    }

    public boolean onlyLetters (String text) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public boolean isValidEmail (String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean matchPasswords (String password1, String password2) {
        return password1.equals(password2);
    }

    public boolean hasMaxLength50 (String text) {
        return text.length() <= 50;
    }

    public boolean hasMaxLength100 (String text) {
        return text.length() <= 100;
    }


}

package ch.noseryoung.accessismore.messages;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.accessismore.R;

public class ToastHandler extends AppCompatActivity {

    Context context;
    private static ToastHandler ourInstance;

    public ToastHandler (Context context) {
        this.context = context;
    }

    public static ToastHandler getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new ToastHandler(context);
        return ourInstance;
    }

    public void callToast(String message,int type){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastLayout = (View) inflater.inflate(R.layout.layout_toast,null);
        TextView toastShowMessage = (TextView) toastLayout.findViewById(R.id.toastTextView);

        switch (type){
            case 0:
                //fail toast method
                callFailToast(toastLayout,toastShowMessage,message);
                break;
            case 1:
                //success toast method
                callSuccessToast(toastLayout,toastShowMessage,message);
                break;

            case 2:
                //warning toast method
                callWarningToast( toastLayout, toastShowMessage, message);
                break;
        }
    }

    //Failure toast message method
    private final void callFailToast(View toastLayout,TextView toastMessage,String message){
        toastLayout.setBackgroundColor(context.getResources().getColor(R.color.colorDanger));
        toastMessage.setText(message);
        toastMessage.setTextColor(context.getResources().getColor(R.color.colorWhite));
        showToast(context,toastLayout);
    }

    //warning toast message method
    private final void callWarningToast( View toastLayout, TextView toastMessage, String message) {
        toastLayout.setBackgroundColor(context.getResources().getColor(R.color.colorWarning));
        toastMessage.setText(message);
        toastMessage.setTextColor(context.getResources().getColor(R.color.colorWhite));
        showToast(context, toastLayout);
    }

    //success toast message method
    private final void callSuccessToast(View toastLayout,TextView toastMessage,String message){
        toastLayout.setBackgroundColor(context.getResources().getColor(R.color.colorSuccess));

        toastMessage.setText(message);
        toastMessage.setTextColor(context.getResources().getColor(R.color.colorWhite));
        showToast(context,toastLayout);
    }

    private void showToast(Context context, View view){
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}

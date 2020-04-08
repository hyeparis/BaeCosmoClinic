package com.example.baecosmoclinic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class ManageProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText displayName, email, password, confirmPassword;
    private ImageView img;
    private Button saveChanges, deleteProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        displayName = findViewById(R.id.displayNameProfile);
        email = findViewById(R.id.emailFromProfile);
        password = findViewById(R.id.passwordFromProfile);
        confirmPassword = findViewById(R.id.confirmPasswordFromProfile);
        saveChanges = findViewById(R.id.btnSaveProfile);
        deleteProfile = findViewById(R.id.btnDeleteProfile);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        displayName.setText(user.getDisplayName());
        email.setText(user.getEmail());
        password.setText("123456");
        confirmPassword.setText("123456");

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    user.delete();
                                    Toast.makeText(ManageProfile.this,"Deleted!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ManageProfile.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(ManageProfile.this);
                    builder.setMessage("Are you sure you want to permanently delete your account?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                    //return;
                }




                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.
//                AuthCredential credential = EmailAuthProvider
//                        .getCredential("user@example.com", "password1234");
//
//                // Prompt the user to re-provide their sign-in credentials
//                user.reauthenticate(credential)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                user.delete()
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                    Toast.makeText(ManageProfile.this,"Deleted!", Toast.LENGTH_SHORT).show();
//                                                    mAuth = FirebaseAuth.getInstance();
//                                                    mAuth.signOut();
//                                                    Intent intent = new Intent(ManageProfile.this,MainActivity.class);
//                                                    startActivity(intent);
//                                                    finish();
//                                                    //Log.d(TAG, "User account deleted.");
//                                                }
//                                                else {
//                                                    Toast.makeText(ManageProfile.this,"Not Deleted!", Toast.LENGTH_SHORT).show();
//                                                    System.out.println("==================="+task.getException());
//
//                                                }
//                                            }
//                                        });
//
//                            }
//                        });
            }
        });


    }
}

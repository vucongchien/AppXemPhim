package com.example.appxemphim.Utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUtils {
    private static FirebaseAuth mAuth;
    private static FirebaseUser user;

    public static FirebaseAuth getAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static FirebaseUser getUser(){
        if (user == null) {
            user = mAuth.getCurrentUser();
        }
        return user;
    }
}

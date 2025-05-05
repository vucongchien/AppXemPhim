package com.example.appxemphim.Utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    private static FirebaseAuth mAuth;
    private static FirebaseUser user;
    private  static DatabaseReference db;

    public  static DatabaseReference  getDb(){
        if(db == null){
            db = FirebaseDatabase.getInstance().getReference();
        }
        return db;
    }

    public static FirebaseAuth getAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static FirebaseUser getUser(){
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        if (user == null) {
            user = mAuth.getCurrentUser();
        }
        return user;
    }
}

package com.agpitcodeclub.app.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agpitcodeclub.app.Models.CommunityModel;
import com.agpitcodeclub.app.R;
import com.agpitcodeclub.app.utils.FirebasePath;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Profile extends Fragment {

    private FirebaseUser user;
    private String UserID;

    public Profile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initUit(view);
        return view;
    }

    private void initUit(View view) {
        initiateUser();
    }

    private DatabaseReference reference;

    private DatabaseReference databaseReference;
    private String token_des = "";

    private void initiateUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference(FirebasePath.MEMBER_USERTOKENS);

        databaseReference.child(UserID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NotNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {

                    Toast.makeText(getContext(), "ud : " + task.getResult().getValue().toString(), Toast.LENGTH_SHORT)
                            .show();
                    token_des = task.getResult().getValue().toString();
                    reference = FirebaseDatabase.getInstance().getReference(FirebasePath.COMMUNITY);
                    reference.child(token_des).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            CommunityModel user = snapshot.getValue(CommunityModel.class);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        /*
         * databaseReference.addValueEventListener(new ValueEventListener() {
         * 
         * @SuppressLint("NotifyDataSetChanged")
         * 
         * @Override
         * public void onDataChange(@NotNull DataSnapshot snapshot) {
         * for (DataSnapshot dataSnapshot : snapshot.getChildren()){
         * 
         * System.out.println("Userid......... :"+dataSnapshot.getKey()+"\n");
         * 
         * CommunityModel user = dataSnapshot.getValue(CommunityModel.class);
         * 
         *//*
            * user.setDesignation(dataSnapshot.getKey().substring(2));
            * System.out.println("Node1 : "+dataSnapshot.getKey().toString()+"\n"+
            * dataSnapshot.getKey().equals(FirebasePath.DEVELOPER));
            * if
            * (dataSnapshot.getKey().equals(FirebasePath.DEVELOPER)||dataSnapshot.getKey().
            * equals(FirebasePath.MEMBER)){
            * DatabaseReference innerChild =
            * FirebaseDatabase.getInstance().getReference(FirebasePath.COMMUNITY).child(
            * dataSnapshot.getKey());
            * innerChild.addValueEventListener(new ValueEventListener() {
            * 
            * @Override
            * public void onDataChange(DataSnapshot snapshot1) {
            * 
            * for (DataSnapshot dataSnapshot1 : snapshot1.getChildren()){
            * CommunityModel user1 = dataSnapshot1.getValue(CommunityModel.class);
            * user1.setDesignation(dataSnapshot.getKey().substring(2));
            * // System.out.println("Name : "+user1.getName());
            * // System.out.println("des2 : "+user1.getDesignation());
            * 
            * 
            * }
            * 
            * }
            * 
            * @Override
            * public void onCancelled(DatabaseError error) {
            * 
            * }
            * });
            * 
            * 
            * }else {
            *//**//*
                   * list.add(user);
                   * community_prg.setVisibility(View.GONE);
                   *//**//*
                          * 
                          * }
                          *//*
                             * 
                             * }
                             * }
                             * 
                             * @Override
                             * public void onCancelled( DatabaseError error) {
                             * }
                             * });
                             */

    }
}
package com.example.satvik;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddcardInfo_Fragment extends Fragment {

    private EditText eduserName, edName, edAddress, edCategory;
    private Button UploadInfoButton;

    private MaterialCardView SelectPhoto;
    private ImageView ProfileimgView;
    private Uri ImageUri;
    private Bitmap bitmap;

    private FirebaseStorage storage;
    private FirebaseFirestore firestore;
    private StorageReference mStoragref;
    private String PhotoUrl;
    private FirebaseAuth firebaseAuth;

    private String CurrentUserID;
    public AddcardInfo_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.supplier_register, container, false);

        eduserName = view.findViewById(R.id.username);
        edName = view.findViewById(R.id.fullname);
        edAddress = view.findViewById(R.id.address);
        edCategory = view.findViewById(R.id.category);
        UploadInfoButton= view.findViewById(R.id.supplier_button);

        SelectPhoto = view.findViewById(R.id.select_photo);
        ProfileimgView = view.findViewById(R.id.profile_image);

        // Initialize Firebase
        FirebaseApp.initializeApp(getContext());

        // Initialize Firebase services
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mStoragref = storage.getReference();
        firebaseAuth=FirebaseAuth.getInstance();

        CurrentUserID=firebaseAuth.getCurrentUser().getUid();

        SelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckStoragePermission();
                UploadImage();
            }
        });
        UploadInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void CheckStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                PickImageFromGallery();
            }
        } else {
            PickImageFromGallery();
        }
    }

    private void PickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    private ActivityResultLauncher<Intent> launcher
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        ImageUri = data.getData();

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                    getActivity().getContentResolver(),
                                    ImageUri
                            );
                            ProfileimgView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PickImageFromGallery();
            } else {
                Toast.makeText(getActivity(), "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void UploadImage() {
        if (ImageUri != null) {
            // Create Storage Instances
            final StorageReference myRef = mStoragref.child("photo/" + ImageUri.getLastPathSegment());
            myRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // here we need to getdownloadURL to store in String
                    myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null) {
                                PhotoUrl = uri.toString();
                                UploadInfo();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    //now we need to Uload All other Information

    private void UploadInfo() {
        // we need to get text from Edit Text
        String username = eduserName.getText().toString().trim();
        String fullname = edName.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String category = edCategory.getText().toString().trim();

        // for now i am save location into database it will in next video

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty((fullname)) && TextUtils.isEmpty(address)) {
            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            DocumentReference documentReference = firestore.collection("Info").document();

            //here we need to set all data into fullnamme class
            //so we need to create fullname class

            fullname fullname1 = new fullname(username,fullname,address,category,PhotoUrl);
            documentReference.set(fullname, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            if(task.isSuccessful()){
                                //we need to get doc id
                                DocId=documentReference.getId();
                                fullname1.setProfile_Img(DocId);
                                documentReference.set(fullname1,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                          if(task.isSuccessful()){
                                              Toast.makeText(getContext(),"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                          }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}


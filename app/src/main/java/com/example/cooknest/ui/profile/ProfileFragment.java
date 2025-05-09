package com.example.cooknest.ui.profile;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.ui.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private TextView tvUserName, tvEmail;
    private CircleImageView ivProfile;
    private MaterialButton btnLogout;
    private static final int REAUTH_REQUEST = 123;

    private void setupClickListeners(MaterialButton btnLogout, TextView... textViews) {
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());

        textViews[0].setOnClickListener(v -> showPasswordChangeDialog());
        textViews[1].setOnClickListener(v -> showEmailChangeDialog());
        textViews[2].setOnClickListener(v -> showDeleteAccountDialog());
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.logout)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.logout, (dialog, which) -> performLogout())
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void performLogout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        requireActivity().finish();
    }

    private void showPasswordChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.change_password, null);
        TextInputEditText etCurrentPassword = view.findViewById(R.id.etCurrentPassword);
        TextInputEditText etNewPassword = view.findViewById(R.id.etNewPassword);

        builder.setView(view)
                .setTitle("Change Password")
                .setPositiveButton("Change", (dialog, which) -> {
                    String currentPass = etCurrentPassword.getText().toString().trim();
                    String newPass = etNewPassword.getText().toString().trim();
                    if (!currentPass.isEmpty() && !newPass.isEmpty()) {
                        reauthenticateAndChangePassword(currentPass, newPass);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void reauthenticateAndChangePassword(String currentPassword, String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                    if (updateTask.isSuccessful()) {
                        Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEmailChangeDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.change_email, null);
        TextInputEditText etNewEmail = view.findViewById(R.id.etNewEmail);
        TextInputEditText etCurrentPassword = view.findViewById(R.id.etCurrentPassword);

        builder.setView(view)
                .setTitle("Change Email")
                .setPositiveButton("Change", (dialog, which) -> {
                    String newEmail = etNewEmail.getText().toString().trim();
                    String currentPass = etCurrentPassword.getText().toString().trim();
                    if (!newEmail.isEmpty() && !currentPass.isEmpty()) {
                        reauthenticateAndChangeEmail(currentPass, newEmail);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void reauthenticateAndChangeEmail(String currentPassword, String newEmail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.updateEmail(newEmail).addOnCompleteListener(updateTask -> {
                    if (updateTask.isSuccessful()) {
                        Toast.makeText(getContext(), "Email updated successfully", Toast.LENGTH_SHORT).show();
                        tvEmail.setText(newEmail);
                    } else {
                        Toast.makeText(getContext(), "Error: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        tvUserName = view.findViewById(R.id.tvUserName);
        tvEmail = view.findViewById(R.id.tvEmail);
        ivProfile = view.findViewById(R.id.ivProfile);
        btnLogout = view.findViewById(R.id.btnLogout);
        TextView tvChangePassword = view.findViewById(R.id.tvChangePassword);
        TextView tvChangeEmail = view.findViewById(R.id.tvChangeEmail);
        TextView tvDeleteAccount = view.findViewById(R.id.tvDeleteAccount);

        loadUserProfile();
        setupClickListeners(btnLogout, tvChangePassword, tvChangeEmail, tvDeleteAccount);

        return view;
    }

    private void loadUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            tvUserName.setText(user.getDisplayName() != null ? user.getDisplayName() : "User");
            tvEmail.setText(user.getEmail());

            if(user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .placeholder(R.drawable.profile)
                        .into(ivProfile);
            }
        }
    }
    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Account")
                .setMessage("Are you sure? This cannot be undone!")
                .setPositiveButton("Delete", (dialog, which) -> deleteAccount())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.delete_account, null);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);

        builder.setView(view)
                .setTitle("Delete Account")
                .setMessage("This action is permanent! Enter your password to confirm:")
                .setPositiveButton("Delete", (dialog, which) -> {
                    String password = etPassword.getText().toString().trim();
                    if (!password.isEmpty()) {
                        reauthenticateAndDeleteAccount(password);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void reauthenticateAndDeleteAccount(String password) {
        FirebaseUser user = mAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);

        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.delete().addOnCompleteListener(deleteTask -> {
                    if (deleteTask.isSuccessful()) {
                        Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        requireActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "Error: " + deleteTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
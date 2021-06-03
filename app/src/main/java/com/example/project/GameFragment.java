package com.example.project;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.project.databinding.GameFragmentBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GameFragment extends Fragment {
    GameViewModel viewModel;
    GameFragmentBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameFragmentBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        viewModel.getStatus().observe(getViewLifecycleOwner(), (GameStatus status) -> {
            if (status == GameStatus.END) {
                showFinalDialog();
            }
        });

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    public void showFinalDialog() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Congratulations!")
                .setMessage(String.format("You know %d celebrities", viewModel.getCountRight()))
                .setCancelable(false)
                .setNegativeButton("exit", (i, v) -> exitGame())
                .setPositiveButton("play again", (i, v) -> restartGame())
                .show();
    }

    private void exitGame() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigateUp();
    }

    private void restartGame() {
        viewModel.start();
    }
}



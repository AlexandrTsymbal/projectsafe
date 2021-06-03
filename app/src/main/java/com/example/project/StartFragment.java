package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project.databinding.FragmentStartBinding;

public class StartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentStartBinding binding = FragmentStartBinding.inflate(inflater);
        binding.setStartFragment(this);

        return binding.getRoot();
    }

    public void goTo(int toId) {
        NavController navController = NavHostFragment.findNavController(this);

        switch (toId) {
            case 0: {
                navController.navigate(R.id.action_startFragment_to_gameFragment);
            }
        }
    }
}

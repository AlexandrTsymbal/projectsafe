package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.navHost);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        RadioGroup radioGroup = findViewById(R.id.radioGroup);
//        RadioButton popular = findViewById(R.id.popular);
//        popular.setOnClickListener(radioButtonClickListener);
//        RadioButton star = findViewById(R.id.starwars);
//        star.setOnClickListener(radioButtonClickListener);
//        RadioButton monarch = findViewById(R.id.monarch);
//        monarch.setOnClickListener(radioButtonClickListener);
//        RadioButton show = findViewById(R.id.show);
//        show.setOnClickListener(radioButtonClickListener);
//    }
//        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RadioButton rb = (RadioButton)v;
//                switch (rb.getId()) {
//                    case R.id.popular: setContentView(R.layout.activity_main);
//                        break;
//                    case R.id.starwars:;
//                        break;
//                    case R.id.monarch: ;
//                        break;
//                    case R.id.show: ;
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        };


    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
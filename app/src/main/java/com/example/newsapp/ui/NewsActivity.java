package com.example.newsapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.newsapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.news_nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
    }
}

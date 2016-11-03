package edu.wallawalla.dailytodolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Dictionary;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Dictionary<Integer, String> d = new Hashtable<Integer, String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

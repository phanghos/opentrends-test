package org.taitasciore.android.opentrendstest.characters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.taitasciore.android.opentrendstest.R;

/**
 * Created by roberto on 27/07/17.
 */

public class CharactersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Buscar Súper-Héroe");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new CharactersFragment()).commit();
    }
}

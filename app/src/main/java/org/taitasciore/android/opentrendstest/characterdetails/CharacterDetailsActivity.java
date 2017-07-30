package org.taitasciore.android.opentrendstest.characterdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.taitasciore.android.model.Character;
import org.taitasciore.android.opentrendstest.R;

/**
 * Created by roberto on 28/07/17.
 */

public class CharacterDetailsActivity extends AppCompatActivity {

    private Character character;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        character = (Character) getIntent().getSerializableExtra("character");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, CharacterDetailsFragment.newInstance(character))
                .commit();
    }
}

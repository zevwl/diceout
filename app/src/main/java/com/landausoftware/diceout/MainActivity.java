package com.landausoftware.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView rollResult;
    Button rollButton;
    int score;
    TextView scoreText;

    Random random;

    int die1;
    int die2;
    int die3;

    ArrayList<Integer> dice;
    ArrayList<ImageView> diceImageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        // Set initial score
        score = 0;

        // Link instances to widgets in the activity view
        rollResult = (TextView) findViewById(R.id.rollResult);
        rollButton = (Button) findViewById(R.id.rollButton);
        scoreText = (TextView) findViewById(R.id.scoreText);

        // Initialize the random number generator
        random = new Random();

        // Initialize ArrayList container for dice values
        dice = new ArrayList<>();

        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageList = new ArrayList<>();
        diceImageList.add(die1Image);
        diceImageList.add(die2Image);
        diceImageList.add(die3Image);

        // Create greeting
        Toast.makeText(getApplicationContext(), getString(R.string.welcome), Toast.LENGTH_SHORT).show();
    }

    public void rollDice(View view) {

        // Roll dice
        die1 = random.nextInt(6) + 1;
        die2 = random.nextInt(6) + 1;
        die3 = random.nextInt(6) + 1;

        // Set dice values into an ArrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for (int i = 0; i < 3; i++) {
            String imageName = "die_" + dice.get(i) + ".png";

            try {
                InputStream stream = getAssets().open(imageName);
                Drawable drawable = Drawable.createFromStream(stream, null);
                diceImageList.get(i).setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String message;

        if (die1 == die2 && die2 == die3) {
            // Triples
            int scoreDelta = die1 * 100;
            message = getString(R.string.triples_message, die1, scoreDelta);
            score += scoreDelta;
        } else if (die1 == die2 || die1 == die3 || die2 == die3) {
            // Doubles
            message = getString(R.string.doubles_message);
            score += 50;
        } else {
            message = getString(R.string.no_match_massage);
        }

        rollResult.setText(message);
        setScoreText(score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_reset_score) {
            resetScore();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetScore() {
        updateScore(0);
    }

    private void updateScore(int score) {
        this.score = score;
        setScoreText(score);
    }

    private void setScoreText(int score) {
        scoreText.setText(getString(R.string.score_text, score));
    }
}

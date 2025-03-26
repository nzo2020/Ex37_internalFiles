package com.example.ex37_internalfiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * MainActivity manages internal file storage operations.
 * It allows the user to write text to a file, read from it, reset the file, and exit while saving.
 * The file is stored in the internal storage of the application.
 *
 * @author Noa Zohar <nz2020@bs.amalnet.k12.il>
 * @version 1.0
 * @since 17/03/2025
 */
public class MainActivity extends AppCompatActivity {

    /**
     * EditText for user input.
     */
    private EditText etInput;

    /**
     * TextView to display the content of the file.
     */
    private TextView tvShow;

    /**
     * The name of the internal storage file.
     */
    private final String FILENAME = "inttest.txt";

    /**
     * Initializes the activity, sets up UI components, and loads the saved file content.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        tvShow = findViewById(R.id.tvShow);

        tvShow.setText(readText());
    }

    /**
     * Appends the user input to the internal storage file and updates the displayed content.
     *
     * @param view The button view that triggered the event.
     */
    public void save_click(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_APPEND);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write(etInput.getText().toString());
            bW.close();
            oSW.close();
            fOS.close();

            tvShow.setText(readText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the file by clearing its content and updating the UI.
     *
     * @param view The button view that triggered the event.
     */
    public void reset_click(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write("");
            bW.close();
            oSW.close();
            fOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        etInput.setText("");
        tvShow.setText("");
    }

    /**
     * Saves the user input and exits the application.
     *
     * @param view The button view that triggered the event.
     */
    public void exit_click(View view) {
        save_click(view);
        finish();
    }

    /**
     * Reads and returns the content of the internal storage file.
     *
     * @return The content of the file as a String.
     */
    public String readText() {
        String text = "";
        try {
            FileInputStream fIS = openFileInput(FILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();

            while (line != null) {
                sB.append(line).append('\n');
                line = bR.readLine();
            }

            text = sB.toString();

            bR.close();
            iSR.close();
            fIS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    /**
     * Inflates the menu and adds the option for credits.
     *
     * @param menu The menu to inflate.
     * @return True if the menu was successfully inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creditsmain, menu);
        return true;
    }

    /**
     * Handles menu item selections, navigating to the credits screen if selected.
     *
     * @param item The selected menu item.
     * @return True if the selection was handled.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuCredits) {
            Intent intent = new Intent(this, mainCredits.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

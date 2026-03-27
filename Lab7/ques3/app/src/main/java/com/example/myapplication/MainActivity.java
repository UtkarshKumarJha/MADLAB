package com.example.myapplication;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvContent;
    List<String> sentences;
    String currentKeyword = ""; // Stores the last searched keyword for relevance sorting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Digital Transformation");
        }

        tvContent = findViewById(R.id.tvContent);

        // Initializing the content as a list of sentences for sorting purposes
        sentences = new ArrayList<>(Arrays.asList(
                "Digital transformation is the integration of digital technology into all areas of a business.",
                "It fundamentally changes how you operate and deliver value to customers.",
                "It is also a cultural change that requires organizations to continually challenge the status quo.",
                "Companies adopt digital transformation to improve efficiency and business agility.",
                "Innovation and adaptation are the key drivers of this massive technological shift.",
                "Artificial intelligence and cloud computing play a massive role in this transformation."
        ));

        displayContent();
    }

    // Joins the list of sentences and displays them in the TextView
    private void displayContent() {
        StringBuilder builder = new StringBuilder();
        for (String sentence : sentences) {
            builder.append(sentence).append("\n\n");
        }
        tvContent.setText(builder.toString().trim());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            showInputDialog("Search Keyword", "search");
            return true;
        } else if (id == R.id.action_highlight) {
            showInputDialog("Highlight Keyword", "highlight");
            return true;
        } else if (id == R.id.action_sort_alpha) {
            sortAlphabetically();
            return true;
        } else if (id == R.id.action_sort_relevance) {
            if (currentKeyword.isEmpty()) {
                Toast.makeText(this, "Please 'Search' for a keyword first to define relevance.", Toast.LENGTH_LONG).show();
            } else {
                sortByRelevance(currentKeyword);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Generic Alert Dialog to get user input for Searching or Highlighting
    private void showInputDialog(String title, String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        input.setHint("Enter word or phrase");
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String word = input.getText().toString().trim();
            if (!word.isEmpty()) {
                if (action.equals("search")) {
                    currentKeyword = word; // Save for relevance sorting
                    searchKeyword(word);
                } else if (action.equals("highlight")) {
                    highlightText(word);
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // A. Search Action
    private void searchKeyword(String keyword) {
        String fullText = tvContent.getText().toString().toLowerCase();
        String searchWord = keyword.toLowerCase();

        int count = 0;
        int index = 0;
        while ((index = fullText.indexOf(searchWord, index)) != -1) {
            count++;
            index += searchWord.length();
        }

        if (count > 0) {
            Toast.makeText(this, "Found '" + keyword + "' " + count + " times.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Keyword not found.", Toast.LENGTH_SHORT).show();
        }
    }

    // B. Highlight Action
    private void highlightText(String keyword) {
        // Reset to default text first to remove old highlights
        displayContent();

        String fullText = tvContent.getText().toString();
        SpannableString spannableString = new SpannableString(fullText);

        String textLower = fullText.toLowerCase();
        String keywordLower = keyword.toLowerCase();

        int index = textLower.indexOf(keywordLower);
        while (index >= 0) {
            // Apply Yellow Background to matched words
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), index, index + keyword.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            index = textLower.indexOf(keywordLower, index + keyword.length());
        }

        tvContent.setText(spannableString);
        Toast.makeText(this, "Highlighted: " + keyword, Toast.LENGTH_SHORT).show();
    }

    // C1. Sort Alphabetically
    private void sortAlphabetically() {
        Collections.sort(sentences); // Standard A-Z sort
        displayContent();
        Toast.makeText(this, "Sorted Alphabetically", Toast.LENGTH_SHORT).show();
    }

    // C2. Sort by Relevance (Sentences with keyword go to top)
    private void sortByRelevance(String keyword) {
        Collections.sort(sentences, (s1, s2) -> {
            boolean contains1 = s1.toLowerCase().contains(keyword.toLowerCase());
            boolean contains2 = s2.toLowerCase().contains(keyword.toLowerCase());

            // If s1 has the keyword and s2 doesn't, s1 comes first (-1)
            if (contains1 && !contains2) return -1;
            // If s2 has the keyword and s1 doesn't, s2 comes first (1)
            if (!contains1 && contains2) return 1;

            // Otherwise, sort them alphabetically
            return s1.compareToIgnoreCase(s2);
        });

        displayContent();

        // Highlight the relevant keyword so it's easy to see why it was sorted
        highlightText(keyword);
        Toast.makeText(this, "Sorted by relevance to: " + keyword, Toast.LENGTH_SHORT).show();
    }
}
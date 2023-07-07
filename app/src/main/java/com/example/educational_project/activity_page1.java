package com.example.educational_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.util.Log;
import android.widget.SearchView;


public class activity_page1 extends AppCompatActivity {

    private List<VocabularyItem> vocabularyList;
    private VocabularyAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        Button backButton = findViewById(R.id.back_button_word);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity();
            }
        });

        vocabularyList = new ArrayList<>();

        try {
            JSONArray jsonArray = loadJsonArrayFromAsset("vocabulary.json");
            if (jsonArray != null) {
                Log.i("JSON", "JSON data loaded successfully: " + jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String englishWord = jsonObject.optString("englishWord", "");
                    String bengaliTranslation = jsonObject.optString("bengaliTranslation", "");
                    String wordMeaning = jsonObject.optString("wordMeaning", "");
                    vocabularyList.add(new VocabularyItem(englishWord, bengaliTranslation, wordMeaning));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(vocabularyList, new Comparator<VocabularyItem>() {
            @Override
            public int compare(VocabularyItem item1, VocabularyItem item2) {
                return item1.getEnglishWord().compareTo(item2.getEnglishWord());
            }
        });

        adapter = new VocabularyAdapter(vocabularyList);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterVocabulary(newText);
                return true;
            }
        });
    }

    private void filterVocabulary(String query) {
        List<VocabularyItem> filteredList = new ArrayList<>();
        for (VocabularyItem item : vocabularyList) {
            if (item.getEnglishWord().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private JSONArray loadJsonArrayFromAsset(String filename) {
        try {
            InputStream inputStream = getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            return new JSONArray(jsonString);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("JSON", "Error loading JSON file: " + e.getMessage());
        }
        return null;
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static class VocabularyItem {
        private String englishWord;
        private String bengaliTranslation;
        private String wordMeaning;

        public VocabularyItem(String englishWord, String bengaliTranslation, String wordMeaning) {
            this.englishWord = englishWord;
            this.bengaliTranslation = bengaliTranslation;
            this.wordMeaning = wordMeaning;
        }

        public String getEnglishWord() {
            return englishWord;
        }

        public String getBengaliTranslation() {
            return bengaliTranslation;
        }

        public String getWordMeaning() {
            return wordMeaning;
        }
    }

    public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {

        private List<VocabularyItem> vocabularyList;
        private List<VocabularyItem> filteredList;

        public VocabularyAdapter(List<VocabularyItem> vocabularyList) {
            this.vocabularyList = vocabularyList;
            this.filteredList = new ArrayList<>(vocabularyList);
        }

        public void filterList(List<VocabularyItem> filteredList) {
            this.filteredList = filteredList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_vocabulary, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            VocabularyItem item = filteredList.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return filteredList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView englishWordTextView;
            private TextView bengaliTranslationTextView;
            private TextView wordMeaningTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                englishWordTextView = itemView.findViewById(R.id.englishWordTextView);
                bengaliTranslationTextView = itemView.findViewById(R.id.bengaliTranslationTextView);
                wordMeaningTextView = itemView.findViewById(R.id.wordMeaningTextView);
            }

            public void bind(VocabularyItem item) {
                englishWordTextView.setText(item.getEnglishWord());
                bengaliTranslationTextView.setText(item.getBengaliTranslation());
                wordMeaningTextView.setText(item.getWordMeaning());
            }
        }
    }
}

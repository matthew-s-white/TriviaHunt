package com.example.triviahunt;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataParser {

    public ArrayList<TriviaCard> triviaCards = new ArrayList<>();

    public DataParser(MainActivity mainActivity) throws IOException {
        InputStream is = mainActivity.getAssets().open("TriviaData.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = br.readLine();
        int counter = 0;

        String question = "";
        ArrayList<String> answers = new ArrayList<String>();
        String correct = "";

        while (line != null) {

            if (line.indexOf("question") == 1 && counter == 0) {
                int lastQuotation = line.lastIndexOf("\"");
                line = line.substring(13, lastQuotation);
                question = line;
                counter++;
            } else if (line.indexOf("A") == 1 && counter == 1) {
                int lastQuotation = line.lastIndexOf("\"");
                line = line.substring(6, lastQuotation);
                answers.add(line);
                counter++;
            } else if (line.indexOf("B") == 1 && counter == 2) {
                int lastQuotation = line.lastIndexOf("\"");
                line = line.substring(6, lastQuotation);
                answers.add(line);
                counter++;
            } else if (line.indexOf("C") == 1 && counter == 3) {
                int lastQuotation = line.lastIndexOf("\"");
                line = line.substring(6, lastQuotation);
                answers.add(line);
                counter++;
            } else if (line.indexOf("D") == 1 && counter == 4) {
                int lastQuotation = line.lastIndexOf("\"");
                line = line.substring(6, lastQuotation);
                answers.add(line);
                counter++;
            } else if (line.indexOf("answer") == 1 && counter == 5) {
                int lastQuotation = line.lastIndexOf("\"");
                line = line.substring(11, lastQuotation);

                if (line.equals("A")) {
                    correct = answers.get(0);
                    answers.remove(0);
                } else if (line.equals("B")) {
                    correct = answers.get(1);
                    answers.remove(1);
                } else if (line.equals("C")) {
                    correct = answers.get(2);
                    answers.remove(2);
                } else if (line.equals("D")) {
                    correct = answers.get(3);
                    answers.remove(3);
                }

                TriviaCard card = new TriviaCard(question, correct, (ArrayList<String>) answers.clone());
                triviaCards.add(card);


                question = "";
                correct = "";
                answers.clear();
                counter = 0;
            }

            line = br.readLine();
        }

        br.close();
    }
}
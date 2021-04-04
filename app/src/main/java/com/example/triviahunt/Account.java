package com.example.triviahunt;

import java.io.*;
import java.util.ArrayList;

public class Account {
    private String username;
    private String name;
    private String password;
    private int coins;
    private ArrayList<String> badges;

    private BufferedReader bfr;
    private PrintWriter bou;

    public Account() {
        username = "";
        name = "";
        password = "";
        coins = 0;
        badges = new ArrayList<>();
    }

    public Account(String username, String name, String password, int coins, ArrayList<String> badges) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.coins = coins;
        if (badges != null) {
            this.badges = badges;
        } else {
            this.badges = new ArrayList<>();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public ArrayList<String> getBadges() {
        return badges;
    }

    public void setBadges(ArrayList<String> badges) {
        this.badges = badges;
    }

    public void addCoins(int addedCoins) {
        this.coins += addedCoins;
    }

    public void incrementCoins() {
        this.coins++;
    }

    public void addBadge(String badge) {
        this.badges.add(badge);
    }

    public void writeToFile(String filename, CreateAccount createAccount) throws FileNotFoundException {
        String outputLine1 = username + "~" + name + "~" + password + "~" + Integer.toString(coins);
        String outputLine2 = "";
        if (badges.size() != 0) {
            for (String s : badges) {
                outputLine2 += s + "~";
            }
        }

        File myAccountDetails = new File(createAccount.getFilesDir(), "myAccount.txt");
        try {
            myAccountDetails.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bou = new PrintWriter(myAccountDetails);
        bou.println(outputLine1);
        bou.println(outputLine2);
        // System.out.println(outputLine1);
        bou.close();
    }

    public void readFromFile(String filename) throws IOException {
        bfr = new BufferedReader(new FileReader(filename));
        String line1 = bfr.readLine();
        String[] arr = line1.split("~");
        setUsername(arr[0]);
        setName(arr[1]);
        setPassword(arr[2]);
        setCoins(Integer.parseInt(arr[3]));

        String line2 = bfr.readLine();
        ArrayList<String> ls = new ArrayList<String>();
        if (line2 != null) {
            String[] badgeList = line2.split("~");
            for (String s : badgeList) {
                ls.add(s);
            }
            setBadges(ls);
        }

    }

    // FOR TESTING

    @Override
    public String toString() {
        // System.out.println("Username: " + username);
        String output = username + " " + name + " " + password + " " + coins + "\nBadges:";
        for (String s : badges) {
            output += " " + s;
        }
        return output;
    }
}
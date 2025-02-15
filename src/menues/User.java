package menues;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static java.util.ArrayList<User> scoreboard;

    private String username;
    private String password;
    private int score;
    private int winsCount;
    private int loosesCount;
    private int drawsCount;

    @Override
    public String toString() {
        return username + " " + score + " " + winsCount + " " + drawsCount + " " + loosesCount;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        allUsers.add(this);
        this.score = 0;
        this.drawsCount = 0;
        this.loosesCount = 0;
        this.winsCount = 0;
    }

    public static ArrayList<User> getScoreboard() {
        return scoreboard;
    }

    public static User getUserByName(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public boolean isPasswordWrong(String inputPassword) {
        return !this.password.equals(inputPassword);
    }

    public static boolean isUserPassInvalid(String inputUsername, String inputPassword) {
        Pattern userPassPattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher userPassMatcher = userPassPattern.matcher(inputUsername);
        if (!userPassMatcher.find()) {
            System.out.println("username format is invalid");
            return true;
        }
        userPassMatcher = userPassPattern.matcher(inputPassword);
        if (!userPassMatcher.find()) {
            System.out.println("password format is invalid");
            return true;
        }
        return false;
    }

    public static boolean userExists(String username) {
        return getUserByName(username) != null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public boolean hasHigherRankThan(User otherUser) {
        if (this.score != otherUser.score)
            return this.score > otherUser.score;
        if (this.winsCount != otherUser.winsCount)
            return this.winsCount > otherUser.winsCount;
        if (this.drawsCount != otherUser.drawsCount)
            return this.drawsCount > otherUser.drawsCount;
        if (this.loosesCount != otherUser.loosesCount)
            return this.loosesCount < otherUser.loosesCount;
        return this.username.compareTo(otherUser.username) < 0;
    }

    public static void sortScoreBoard() {
        ArrayList<User> listCopy = new ArrayList<>(allUsers);
        ArrayList<User> sortedList = new ArrayList<>();
        User tempMaxRank;
        for (int i = 0; i < allUsers.size(); i++) {
            tempMaxRank = null;
            for (User user : listCopy) {
                if (tempMaxRank == null || user.hasHigherRankThan(tempMaxRank))
                    tempMaxRank = user;
            }
            listCopy.remove(tempMaxRank);
            sortedList.add(tempMaxRank);
        }
        scoreboard = sortedList;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void addWinsCount() {
        this.winsCount++;
    }

    public void addLoosesCount() {
        this.loosesCount++;
    }

    public void addDrawsCount() {
        this.drawsCount++;
    }

    private static void sortUsersList() {
        ArrayList<User> listCopy = new ArrayList<>(allUsers);
        ArrayList<User> sortedList = new ArrayList<>();
        User tempMaxRank;
        for (int i = 0; i < allUsers.size(); i++) {
            tempMaxRank = null;
            for (User user : listCopy) {
                if (tempMaxRank == null || user.username.compareTo(tempMaxRank.username) < 0)
                    tempMaxRank = user;
            }
            listCopy.remove(tempMaxRank);
            sortedList.add(tempMaxRank);
        }
        allUsers = sortedList;
    }

    public static void printUsersList() {
        sortUsersList();
        for (User user : allUsers) {
            System.out.println(user.username);
        }
    }
}

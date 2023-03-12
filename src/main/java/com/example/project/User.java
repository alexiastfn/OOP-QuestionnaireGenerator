package com.example.project;

import java.io.*;

public class User {
    private String username;
    private String password;
    private static int ID = -1;

    User(String u, String p) {
        this.setUsername(u);
        this.setPassword(p);
        setID(getID() + 1);

    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        User.ID = ID;
    }

    public void createUser(String u, String p) {
        this.setUsername(u);
        this.setPassword(p);

        if (u != null && p != null) {
            boolean search = this.searchFileForUser(u);
            if (search) {
                // user already exists
                System.out.println("{ 'status' : 'error', 'message' : 'User already exists'}");
            } else {
                // user doesn't exist => I append to the file
                this.appendToFile(u, p);
                System.out.println("{ 'status' : 'ok', 'message' : 'User created successfully'}");
            }
        }
    }

    boolean successfulLogin(String u, String p) {
        boolean value = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                for (int i = 0; i < elements.length; i = i + 2) {
                    if (elements[i].equals(u)) {
                        // found the username, now verify the password:
                        if (elements[i + 1].equals(p)) {
                            value = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    boolean searchFileForUser(String u) {
        boolean value = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                for (int i = 0; i < elements.length; i = i + 2) {
                    if (elements[i].equals(u)) {
                        value = true; // user already exists
                        break;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    boolean searchThePassword(String u, String p) {
        boolean value = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                for (int i = 0; i < elements.length; i = i + 2) {
                    if (elements[i].equals(u))
                        if (elements[i + 1].equals(p)) {
                            value = true;  // the password exists
                            break;
                        }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    void appendToFile(String u, String p) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true));
            writer.write(u + "," + p);
            writer.write("\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void userCreatesQuestion(String u, String p, String question, String type, String answers) {
        if (successfulLogin(u, p)) {
            Question this_question = new Question(u, question, type, answers);
            this_question.addQuestionInSystem(u, question, type, answers);
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
}

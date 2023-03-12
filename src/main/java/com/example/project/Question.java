package com.example.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Question {

    private String user;

    private String text;

    private String id;

    static int INCREMENT_ID = 0;

    static int answersID = 0;

    private ArrayList<Answer> listAnswers = new ArrayList<>();

    private String type = "single";

    private String answers;

    Question(String user, String text, String type, String answers){

        this.setText(text);
        this.setType(type);
        this.setAnswers(answers);
        this.setUser(user);
        INCREMENT_ID = INCREMENT_ID + 1;

    }
    Question(String id, String text, String type, ArrayList<Answer> answers){
        this.setText(text);
        this.setType(type);
        this.setId(id);
        this.setListAnswers(answers);
    }
    Question(String username){
        this.setUser(username);
        INCREMENT_ID = INCREMENT_ID + 1;
    }
    void addQuestionInSystem(String user,String text, String type, String answers){
        this.setText(text);
        if(text != null) {
            boolean search = this.searchFileForQuestion(text);
            if(search){
                System.out.println("{ 'status' : 'error', 'message' : 'Question already exists'}");
            } else {
                this.appendToFile(user, text, type, answers);
                System.out.println("{ 'status' : 'ok', 'message' : 'Question added successfully'}");
            }
        }
    }
    boolean searchFileForQuestion(String t){
        boolean value = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("questions.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                for(int i = 2; i < elements.length; i=i+5){
                    if (elements[i].equals(t)) {
                        value = true;  // text-question found
                        break;
                    }
                }
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }
    boolean searchFileForQuestionAfterId(String id){
        boolean value = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("questions.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                for(int i = 0; i < elements.length; i=i+5){
                    if (elements[i].equals(id)) {
                        value = true;   // id found
                        break;
                    }
                }
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return value;

    }
    String getAllQuestionsUserPosted(String username) {

        String id;
        String text;
        String[] result = new String[10];
        String total;
        int j = 0;

        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("questions.csv"));
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");

                for (int i = 1; i < elements.length; i = i + 5) {
                    if (elements[i].equals(username)) {
                        id = elements[i-1];
                        text = elements[i+1];
                        result[j] = "{\"question_id\" : \"" + id + "\", \"question_name\" : \"" + text + "\"}";
                        j++;

                    }
                }

            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        total = result[0];
        for(int k = 1; k < j; k++){
            total = total + ", " + result[k];
        }
        return total;
    }
    static Question getQuestionByID(String id) {
        Question value = null;

        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("questions.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                if (elements[0].equals(id)){
                    String[] answers = elements[4].split(" ");
                    ArrayList<Answer> listAnswers = new ArrayList<>();
                    for (int i = 0; i < answers.length; i = i + 3) {
                        listAnswers.add(new Answer(answers[i], answers[i + 1], answers[i + 2]));
                    }
                    value = new Question(id, elements[2], elements[3], listAnswers);
                }
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }
    ArrayList<String> getCorrectAnswersId() {
        ArrayList<String> result = new ArrayList<>();
        for (Answer answer : getListAnswers()) {
            if(answer.isCorrect()) {
                result.add(answer.getId());
            }
        }
        return result;
    }
    ArrayList<String> getIncorrectAnswersId() {
        ArrayList<String> result = new ArrayList<>();
        for (Answer answer : getListAnswers()) {
            if(!answer.isCorrect()) {
                result.add(answer.getId());
            }
        }
        return result;
    }
    String getIDofQuestion(String text){
        String value = null;
        if(this.searchFileForQuestion(text)){

            String line;
            try {
                BufferedReader reader = new BufferedReader(new FileReader("questions.csv"));
                while((line = reader.readLine()) != null){
                    String[] elements = line.split(",");
                    for(int i = 2; i < elements.length; i=i+5){
                        if(elements[i].equals(text)){
                            value = elements[i - 2];
                        }
                    }
                }
                reader.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return value;
    }
    void appendToFile(String u, String t, String ty, String a) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("questions.csv", true));
            String[] elements = a.trim().replace("'", "").split(" ");
            String finalAnswer = "";
            for (int i = 0; i < elements.length; i=i+4) {
                finalAnswer = finalAnswer + " " + (++answersID) + " " + elements[i + 1] + " " + elements[i + 3];
            }
            writer.write(INCREMENT_ID + "," + u + "," + t + "," + ty + "," + finalAnswer.trim() + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("answers.csv", true));
            writer.write(INCREMENT_ID + "," + ty + "," + a + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "{\"question-name\":\"" + this.getText() + "\", \"question_index\":\"" + this.getId() + "\", \"question_type\":\"" + this.getType() +
                "\", \"answers\":\"" + this.getListAnswers() + "\"}";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Answer> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(ArrayList<Answer> listAnswers) {
        this.listAnswers = listAnswers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}

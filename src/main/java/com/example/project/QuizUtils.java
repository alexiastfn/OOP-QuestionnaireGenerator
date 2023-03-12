package com.example.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class QuizUtils {

    private QuizUtils() {}

    public static boolean quizAlreadySubmitted(String username, String quiz_id) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("submittedQuiz.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                // search quiz by username and id
                if (elements[0].equals(username) && elements[1].equals(quiz_id)) {
                    return true;
                }
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addSubmittedQuiz(String username, String quiz_id, String result) {
        try{
            BufferedWriter writer =  new BufferedWriter(new FileWriter("submittedQuiz.csv", true));
            writer.write(username + "," + quiz_id + "," + result);
            writer.write("\n");
            writer.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteQuiz(String id) {
        String line;
        String finalFile = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                if (!elements[0].equals(id)) {
                    finalFile = finalFile + line + "\n";
                }
            }
            reader.close();
            BufferedWriter writer =  new BufferedWriter(new FileWriter("quizz.csv"));
            writer.write(finalFile);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMysolutions(String username) {
        String result = "";
        String line;
        int i = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("submittedQuiz.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                if (elements[0].equals(username)) {
                    if (!result.isEmpty()) {
                        result = result + ",";
                    }
                    result = result + "{\"quiz-id\" : \"" + elements[1] + "\", \"quiz-name\" : \"Chestionarul " + elements[1] + "\", \"score\" : \"" + elements[2] +"\", \"index_in_list\" : \"" + i++ + "\"}";
                }
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static Quiz getQuizById(String id) {
        Quiz quiz = null;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                if (elements[0].equals(id)) {
                    quiz = new Quiz(id, elements[1], elements[2], elements[3]);
                    break;
                }
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return quiz;
    }

    static void addQuizzInSystem(String user, String name, String questionAndAnswers){
        if(name != null){
            if(searchFileForQuizz(name)){
                System.out.println("{ 'status' : 'error', 'message' : 'Quizz name already exists'}");
            } else {
                appendQuizzToFile(user, name, questionAndAnswers);
                System.out.println("{ 'status' : 'ok', 'message' : 'Quizz added succesfully'}");
            }
        }
    }

    static void appendQuizzToFile(String username, String name, String questions){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("quizz.csv", true));
            writer.write(++Quiz.ID + "," + username + "," + name + "," + questions + "\n");
            writer.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    static boolean searchFileForQuizz(String name){
        boolean value = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            while((line = reader.readLine()) != null){
                String[] elements = line.split(",");
                for(int i = 2; i < elements.length; i=i+4){
                    if(elements[i].equals(name)){
                        value = true;
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
}

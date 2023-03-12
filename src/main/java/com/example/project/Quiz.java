package com.example.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Quiz {
    static int ID = 0;
    private String user;
    private String name;
    private String id;
    private ArrayList<Question> questions = new ArrayList<>();
    private boolean is_completed = false;

    Quiz(String username) {
        this.setUser(username);
        ID = ID + 1;
        setName("Chestionarul " + ID);
    }

    Quiz(String username, String name) {
        this.setUser(username);
        this.setName(name);
        String id = name.replace("Chestionarul ", "");
        int nr = Integer.parseInt(id);
        this.ID = nr;
    }

    Quiz(String id, String username, String name, String questions) {
        this(username, name);
        this.setId(id);
        String[] ids = questions.replace("'", "").split(" ");
        for (int i = 1; i < ids.length; i = i + 2) {
            this.getQuestions().add(Question.getQuestionByID(ids[i]));
        }
    }

    public ArrayList<String> getALlAnswersId() {
        ArrayList<String> allAnswers = new ArrayList<>();
        for (Question question : this.getQuestions()) {
            allAnswers.addAll(question.getCorrectAnswersId());
            allAnswers.addAll(question.getIncorrectAnswersId());
        }
        return allAnswers;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    String[] searchFileAfterUser1(String username) {
        // return the ids:
        String line;
        int counter = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                for (int i = 1; i < elements.length; i = i + 4) {
                    if (elements[i].equals(username)) {
                        counter++;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] ids = new String[counter];
        int index = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            while (index < counter) {
                while ((line = reader.readLine()) != null) {
                    String[] elements = line.split(",");
                    for (int i = 1; i < elements.length; i = i + 4) {
                        if (elements[i].equals(username)) {
                            ids[index] = elements[i - 1];
                        }
                    }
                    index++;
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;

    }

    String[] searchFileAfterUser2(String username) {
        // return the names
        String line;
        int counter = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                for (int i = 1; i < elements.length; i = i + 4) {
                    if (elements[i].equals(username)) {
                        counter++;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] names = new String[counter];
        int index = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            //
            while (index < counter) {
                while ((line = reader.readLine()) != null) {
                    String[] elements = line.split(",");
                    for (int i = 1; i < elements.length; i = i + 4) {
                        if (elements[i].equals(username)) {
                            names[index] = elements[i + 1];
                        }
                    }
                    index++;
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    void returnQuestionsFromQuizbyId(String idQuiz) {

        int nrOfQuestions;
        String theQuestions = null;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("quizz.csv"));
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                for (int i = 0; i < elements.length; i = i + 4) {
                    if (elements[i].equals(idQuiz)) {
                        theQuestions = elements[i + 3];
                        break;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // nr of questions:
        nrOfQuestions = (theQuestions.split("-question-").length) - 1;
        String replace = "";

        for (int k = 1; k <= nrOfQuestions; k++) {
            String target = "-question-" + k;
            replace = theQuestions.replace(target, "");
            theQuestions = replace;
        }

        String indexes = theQuestions.replace("'", "");
        ArrayList<Question> questions = new ArrayList<>();

        for (String index : indexes.trim().split(" ")) {
            Question q = Question.getQuestionByID(index);
            if (q != null) {
                questions.add(q);
            }
        }

        System.out.println("{'status':'ok','message':'" + questions + "'}");
    }

    public String getUsername() {
        return getUser();
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }
}

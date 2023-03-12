package com.example.project;

import java.io.*;
import java.util.ArrayList;


public class Tema1 {

    static File myUsersFile = new File("users.csv");
    static File myQuestionsFile = new File("questions.csv");
    static File myQuizFile = new File("quizz.csv");
    static File myAnswers = new File("answers.csv");
    static File submittedQuiz = new File("submittedQuiz.csv");

    public static void creatingFiles() {
        try {
            myUsersFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myQuestionsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myQuizFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myAnswers.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            submittedQuiz.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        if (args == null) {
            System.out.print("Hello world!");
        } else {
            creatingFiles();
            for (int i = 0; i < args.length; i++) {

                if (args[i].equals("-create-user")) {

                    if (args.length == 1) {
                        System.out.println("{ 'status' : 'error', 'message' : 'Please provide username'}");
                        return;
                    } else {

                        String username = returnUsername(args[i + 1]);

                        if (args.length == 2) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Please provide password'}");
                            return;
                        } else {

                            String password = returnPassword(args[i + 2]);
                            User user = new User(username, password);
                            user.createUser(username, password);
                        }
                    }
                }

                if (args[i].equals("-cleanup-all")) {

                    Question.INCREMENT_ID = 0;
                    Quiz.ID = 0;
                    Answer.idAnswer = 0;
                    Question.answersID = 0;
                    if (myUsersFile.delete() && myQuestionsFile.delete() && myQuizFile.delete() && myAnswers.delete() && submittedQuiz.delete()) {
                        System.out.println("{ 'status' : 'ok', 'message' : 'Cleanup finished successfully'}");
                        return;
                    }
                }

                if (args[i].equals("-create-question")) {

                    if (args.length == 1) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                        return;
                    } else {
                        if (args.length == 2) {
                            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                            return;
                        } else {
                            if (args.length == 3) {

                                User user = new User(returnUsername(args[i + 1]), returnPassword(args[i + 2]));
                                if (!user.searchFileForUser(returnUsername(args[i + 1]))) {
                                    System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                                    return;
                                } else {
                                    if (!user.searchThePassword(returnUsername(args[i + 1]), returnPassword(args[i + 2]))) {
                                        System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                                        return;
                                    }
                                }
                                System.out.println("{ 'status' : 'error', 'message' : 'No question text provided'}");
                                return;

                            } else {
                                if (!args[i + 3].contains("-text")) {
                                    System.out.println("{ 'status' : 'error', 'message' : 'No question text provided'}");
                                    return;
                                }
                                if (args.length == 5) {
                                    System.out.println("{ 'status' : 'error', 'message' : 'No answer provided'}");
                                    return;
                                }
                                if (args.length == 7) {
                                    System.out.println("{ 'status' : 'error', 'message' : 'Only one answer provided'}");
                                    return;
                                }
                                if (args[i + 4].contains("single")) {
                                    int countYesAnswers = 0;

                                    if (args.length > 6 && args[i + 6].contains("'1'")) {
                                        countYesAnswers++;
                                    }
                                    if (args.length > 8 && args[i + 8].contains("'1'")) {

                                        countYesAnswers++;
                                    }
                                    if (args.length > 10 && args[i + 10].contains("'1'")) {
                                        countYesAnswers++;
                                    }
                                    if (args.length > 12 && args[i + 12].contains("'1'")) {
                                        countYesAnswers++;
                                    }
                                    if (args.length > 14 && args[i + 14].contains("'1'")) {
                                        countYesAnswers++;
                                    }
                                    if (countYesAnswers > 1) {
                                        System.out.println("{ 'status' : 'error', 'message' : 'Single correct answer question has more than one correct answer'}");
                                        return;
                                    }
                                }
                                if (args[i + 4].contains("single")) {

                                    if (args.length > 8) {
                                        if ((args[i + 5].contains("Yes") && args[i + 7].contains("Yes")) || (args[i + 5].contains("No") && args[i + 7].contains("No"))) {
                                            System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
                                            return;
                                        }
                                    }
                                    if (args.length > 10) {
                                        boolean yes = args[i + 5].contains("Yes") && args[i + 7].contains("Yes") || args[i + 5].contains("Yes") && args[i + 9].contains("Yes") || args[i + 7].contains("Yes") && args[i + 9].contains("Yes");
                                        boolean no = args[i + 5].contains("No") && args[i + 7].contains("No") || args[i + 5].contains("No") && args[i + 9].contains("No") || args[i + 7].contains("No") && args[i + 9].contains("No");
                                        if (yes || no) {
                                            System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
                                            return;
                                        }
                                    }
                                    if (args.length > 12) {
                                        boolean yes1 = args[i + 5].contains("Yes") && args[i + 7].contains("Yes") || args[i + 5].contains("Yes") && args[i + 9].contains("Yes") || args[i + 7].contains("Yes") && args[i + 9].contains("Yes");
                                        boolean yes2 = args[i + 11].contains("Yes") && args[i + 7].contains("Yes") || args[i + 5].contains("Yes") && args[i + 11].contains("Yes") || args[i + 11].contains("Yes") && args[i + 9].contains("Yes");
                                        boolean yes = yes1 || yes2;
                                        boolean no1 = args[i + 5].contains("No") && args[i + 7].contains("No") || args[i + 5].contains("No") && args[i + 9].contains("No") || args[i + 7].contains("No") && args[i + 9].contains("No");
                                        boolean no2 = args[i + 11].contains("No") && args[i + 7].contains("No") || args[i + 5].contains("No") && args[i + 11].contains("No") || args[i + 11].contains("No") && args[i + 9].contains("No");
                                        boolean no = no1 || no2;
                                        if (yes || no) {
                                            System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
                                            return;
                                        }
                                    }
                                    if (args.length > 14) {
                                        boolean yes1 = args[i + 5].contains("Yes") && args[i + 7].contains("Yes") || args[i + 5].contains("Yes") && args[i + 9].contains("Yes") || args[i + 7].contains("Yes") && args[i + 9].contains("Yes");
                                        boolean yes2 = args[i + 11].contains("Yes") && args[i + 7].contains("Yes") || args[i + 5].contains("Yes") && args[i + 11].contains("Yes") || args[i + 11].contains("Yes") && args[i + 9].contains("Yes");
                                        boolean yes3 = args[i + 5].contains("Yes") && args[i + 13].contains("Yes") || args[i + 13].contains("Yes") && args[i + 9].contains("Yes") || args[i + 7].contains("Yes") && args[i + 13].contains("Yes");
                                        boolean yes = yes1 || yes2 || yes3 || args[i + 13].contains("Yes") && args[i + 11].contains("Yes");
                                        boolean no1 = args[i + 5].contains("No") && args[i + 7].contains("No") || args[i + 5].contains("No") && args[i + 9].contains("No") || args[i + 7].contains("No") && args[i + 9].contains("No");
                                        boolean no2 = args[i + 11].contains("No") && args[i + 7].contains("No") || args[i + 5].contains("No") && args[i + 11].contains("No") || args[i + 11].contains("No") && args[i + 9].contains("No");
                                        boolean no3 = args[i + 5].contains("Yes") && args[i + 13].contains("No") || args[i + 13].contains("No") && args[i + 9].contains("No") || args[i + 7].contains("No") && args[i + 13].contains("No");
                                        boolean no = no1 || no2 || no3 || args[i + 13].contains("No") && args[i + 11].contains("No");
                                        if (yes || no) {
                                            System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
                                            return;
                                        }


                                    }

                                }
                                // answer has no description
                                if (args[i + 5].contains("answer")) {

                                    if (args.length > 15) {
                                        System.out.println("{ 'status' : 'error', 'message' : 'More than 5 answers were submitted'}");
                                        return;
                                    }

                                    int nrOfAnswers = args.length - 6 + 1;
                                    int counter;
                                    int counter2;
                                    int index = 0;
                                    int[] good1 = new int[nrOfAnswers];
                                    int[] good2 = new int[nrOfAnswers];

                                    if (nrOfAnswers % 2 != 0) {
                                        for (int j = 5; j < args.length; j++) {
                                            counter = 1;
                                            counter2 = 1;
                                            index++;

                                            // answer description:
                                            if (args[j].contains("-answer-" + counter + " ")) {
                                                good1[counter - 1] = 1;
                                            }
                                            if (args[j].contains("-answer-" + counter + "-is-correct")) {
                                                System.out.println("{ 'status' : 'error', 'message' : 'Answer " + index + " has no answer description'}");
                                                return;
                                            }
                                            // answer flag:
                                            if (args[j].contains("-answer-" + counter2 + "-is-correct")) {
                                                good2[counter2 - 1] = 1;
                                            }
                                            if (args[j].contains("-answer-" + counter2 + " ")) {
                                                System.out.println("{ 'status' : 'error', 'message' : 'Answer " + index + " has no answer correct flag'}");
                                                return;
                                            }

                                            counter++;
                                            counter2++;
                                            if (counter > 2) {
                                                if (!(good1[0] == 1 && good1[1] == 1)) {
                                                    System.out.println("{ 'status' : 'error', 'message' : 'Answer " + index + " has no answer description'}");
                                                    return;

                                                }
                                            }
                                            if (counter2 > 2) {
                                                if (!(good2[0] == 1)) {
                                                    System.out.println("{ 'status' : 'error', 'message' : 'Answer " + index + " has no answer correct flag'}");
                                                    return;
                                                }
                                            }

                                        }

                                    }

                                }


                            }
                        }

                    }

                    String answers = "";
                    for (int j = 5; j < args.length; j++) {
                        answers = answers + args[j] + " ";
                    }
                    User user = new User(returnUsername(args[i + 1]), returnPassword(args[i + 2]));
                    user.userCreatesQuestion(returnUsername(args[i + 1]), returnPassword(args[i + 2]), returnText(args[i + 3]), returnType(args[i + 4]), answers);
                }

                if (args[i].equals("-get-question-id-by-text")) {

                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                        return;
                    } else {

                        User user = new User(returnUsername(args[i + 1]), returnPassword(args[i + 2]));
                        if (!user.searchFileForUser(returnUsername(args[i + 1]))) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        }
                        if (!user.successfulLogin(returnUsername(args[i + 1]), returnPassword(args[i + 2]))) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        }
                    }

                    Question question = new Question(returnUsername(args[i + 1]), returnText(args[i + 3]), "", "");
                    if (!question.searchFileForQuestion(returnText(args[i + 3]))) {
                        System.out.println("{ 'status' : 'error', 'message' : 'Question does not exist'}");
                        return;
                    } else {
                        String index = question.getIDofQuestion(returnText(args[i + 3]));
                        if (index != null) {
                            System.out.println("{ 'status' : 'ok', 'message' : '" + index + "'}");
                            return;
                        }
                    }

                }

                if (args[i].equals("-get-all-questions")) {

                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                        return;
                    } else {
                        String username = returnUsername(args[i + 1]);
                        String password = returnPassword(args[i + 2]);
                        User user = new User(username, password);

                        if (!user.searchFileForUser(username)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        } else {
                            if (!user.successfulLogin(username, password)) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                                return;
                            } else {

                                Question question = new Question(username);
                                if (question.getAllQuestionsUserPosted(username) != null) {
                                    String result = "{ 'status' : 'ok', 'message' : '[" + question.getAllQuestionsUserPosted(username) + "]'}";
                                    System.out.println(result);
                                }
                            }
                        }
                    }
                }

                if (args[i].equals("-create-quizz")) {

                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                        return;
                    }
                    if (args.length == 3) {

                        User user = new User(returnUsername(args[i + 1]), returnPassword(args[i + 2]));
                        if (!user.searchFileForUser(returnUsername(args[i + 1]))) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        } else {
                            if (!user.searchThePassword(returnUsername(args[i + 1]), returnPassword(args[i + 2]))) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                                return;
                            }
                        }
                    }
                    if (args.length > 14) {
                        System.out.println("{ 'status' : 'error', 'message' : 'Quizz has more than 10 questions'}");
                        return;
                    }
                    String username = returnUsername(args[i + 1]);
                    String name = returnName(args[i + 3]);
                    int argQuestions = args.length - 4;

                    for (int j = 1; j <= argQuestions; j++) {
                        String questionId = returnQuestionId(args[3 + j], j);
                        Question question = new Question(username);
                        if (!question.searchFileForQuestionAfterId(questionId)) {
                            System.out.println("{'status':'error','message':'Question ID for question " + j + " does not exist'}");
                            return;
                        }
                    }
                    String questions = args[3 + 1];
                    for (int j = 2; j <= argQuestions; j++) {
                        questions = questions + " " + args[3 + j];
                    }
                    QuizUtils.addQuizzInSystem(username, name, questions);
                }

                if (args[i].equals("-get-quizz-by-name")) {

                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                        return;
                    }
                    String username = returnUsername(args[i + 1]);
                    String password = returnPassword(args[i + 2]);

                    if (args.length == 3) {
                        User user = new User(username, password);
                        if (!user.searchFileForUser(username)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        } else {
                            if (!user.searchThePassword(username, password)) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                                return;
                            }
                        }

                    }
                    String name = returnName(args[i + 3]);
                    String id = name.replace("Chestionarul ", "");
                    if (!QuizUtils.searchFileForQuizz(name)) {
                        System.out.println("{ 'status' : 'error', 'message' : 'Quizz does not exist'}");
                        return;
                    } else {
                        System.out.println("{ 'status' : 'ok', 'message' : '" + id + "'}");
                        return;
                    }
                }

                if (args[i].equals("-get-all-quizzes")) {

                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                        return;
                    }

                    String username = returnUsername(args[i + 1]);
                    String password = returnPassword(args[i + 2]);
                    User user = new User(username, password);
                    if (!user.searchFileForUser(username)) {
                        System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                        return;
                    } else {
                        if (!user.searchThePassword(username, password)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        } else {

                            Quiz quiz = new Quiz(username);
                            String[] ids = quiz.searchFileAfterUser1(username);
                            String[] names = quiz.searchFileAfterUser2(username);
                            String[] completed = new String[names.length];

                            for (int k = 0; k < ids.length; k++) {
                                Quiz quiz1 = new Quiz(username, names[k]);
                                if (Boolean.toString(quiz1.isIs_completed()).equals("false")) {
                                    completed[k] = "False";
                                } else {
                                    completed[k] = "True";
                                }
                            }

                            if (completed != null) {
                                String tempResult = "{\"quizz_id\" : \"" + ids[0] + "\", \"quizz_name\" : \"" + names[0] + "\", \"is_completed\" : \"" + completed[0] + "\"}";
                                String result;
                                for (int k = 1; k < ids.length; k++) {
                                    tempResult = tempResult + ", " + "{\"quizz_id\" : \"" + ids[k] + "\", \"quizz_name\" : \"" + names[k] + "\", \"is_completed\" : \"" + completed[k] + "\"}";
                                }
                                result = "{ 'status' : 'ok', 'message' : '[" + tempResult + "]'}";
                                if (result != null) {
                                    System.out.println(result);
                                }

                            }

                        }
                    }

                }

                if (args[i].equals("-get-quizz-details-by-id")) {

                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                    }
                    if (args.length == 3) {

                        String username = returnUsername(args[i + 1]);
                        String password = returnPassword(args[i + 2]);
                        User user = new User(username, password);

                        if (!user.searchFileForUser(username)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");        // username doesnt't exist
                            return;
                        } else {
                            if (!user.searchThePassword(username, password)) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");     // login credentials are wrong
                                return;
                            }
                        }
                    }
                    if (args.length == 4) {
                        String username = returnUsername(args[i + 1]);
                        String name = "Chestionarul " + returnId(args[i + 3]);
                        ;
                        Quiz quiz = new Quiz(username, name);
                        quiz.returnQuestionsFromQuizbyId(returnId(args[i + 3]));
                    }
                }

                if (args[i].equals("-submit-quizz")) {
                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                    }

                    if (args.length == 3) {

                        String username = returnUsername(args[i + 1]);
                        String password = returnPassword(args[i + 2]);
                        User user = new User(username, password);
                        if (!user.searchFileForUser(username)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");        // username doesnt't exist
                            return;
                        } else {
                            if (!user.searchThePassword(username, password)) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");     // login credentials are wrong
                                return;

                            }
                        }

                        System.out.println("{ 'status' : 'error', 'message' : 'No quizz identifier was provided'}");
                        return;
                    }
                    if (args.length > 4) {

                        String username = returnUsername(args[i + 1]);
                        Quiz quiz = QuizUtils.getQuizById(returnQuizId(args[i + 3]));
                        if (quiz == null) {
                            System.out.println("{ 'status' : 'error', 'message' : 'No quiz was found'}");
                            return;
                        }
                        // lista cu user-ului:
                        ArrayList<String> answersList = new ArrayList<>();
                        ArrayList<String> allAnswersId = quiz.getALlAnswersId();
                        for (int j = 4; j < args.length; j++) {
                            String answ = args[j].split(" ")[1].replace("'", "");
                            if (!allAnswersId.contains(answ)) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Answer ID for answer " + (j - 3) + " does not exist'}");
                                return;
                            }
                            answersList.add(answ);
                        }
                        if (QuizUtils.quizAlreadySubmitted(username, quiz.getId())) {
                            System.out.println("{ 'status' : 'error', 'message' : 'You already submitted this quizz'}");
                            return;
                        }
                        if (quiz.getUsername().equals(username)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'You cannot answer your own quizz'}");
                            return;
                        }
                        double finalResult = 0;

                        for (Question q : quiz.getQuestions()) {
                            int questionRes = 0;
                            if (answersList.containsAll(q.getCorrectAnswersId())) {
                                questionRes = 1;
                            } else {
                                for (String correctId : q.getCorrectAnswersId()) {
                                    if (answersList.contains(correctId)) {
                                        questionRes = questionRes + 1 / q.getCorrectAnswersId().size();
                                    }
                                }
                            }
                            for (String incorrectAnswerId : q.getIncorrectAnswersId()) {
                                if (answersList.contains(incorrectAnswerId)) {
                                    questionRes = questionRes - 1 / q.getIncorrectAnswersId().size();
                                }
                            }
                            finalResult += (double) questionRes * 100 / quiz.getQuestions().size();
                        }

                        if (finalResult < 0) {
                            finalResult = 0;
                        }
                        long finalRes = Math.round(finalResult);
                        System.out.println("{ 'status' : 'ok', 'message' : '" + finalRes + " points'}");
                        QuizUtils.addSubmittedQuiz(username, returnQuizId(args[i + 3]), String.valueOf(finalRes));
                    }
                }

                if (args[i].equals("-delete-quizz-by-id")) {

                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                        return;
                    }

                    if (args.length == 3) {

                        String username = returnUsername(args[i + 1]);
                        String password = returnPassword(args[i + 2]);
                        User user = new User(username, password);
                        if (!user.searchFileForUser(username)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        } else {
                            if (!user.searchThePassword(username, password)) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                                return;
                            }
                        }

                        System.out.println("{ 'status' : 'error', 'message' : 'No quizz identifier was provided'}");
                        return;
                    }
                    if (args.length == 4) {

                        Quiz quiz = QuizUtils.getQuizById(returnId(args[i + 3]));
                        if (quiz == null) {
                            System.out.println("{ 'status' : 'error', 'message' : 'No quiz was found'}");
                            return;
                        }
                        QuizUtils.deleteQuiz(returnId(args[i + 3]));
                        System.out.println("{ 'status' : 'ok', 'message' : 'Quizz deleted successfully'}");
                    }
                }

                if (args[i].equals("-get-my-solutions")) {
                    if (args.length == 1 || args.length == 2) {
                        System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                    }

                    if (args.length == 3) {

                        String username = returnUsername(args[i + 1]);
                        String password = returnPassword(args[i + 2]);
                        User user = new User(username, password);
                        if (!user.searchFileForUser(username)) {
                            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                            return;
                        } else {
                            if (!user.searchThePassword(username, password)) {
                                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                                return;
                            }
                        }
                        System.out.println("{ 'status' : 'ok', 'message' : '[" + QuizUtils.getMysolutions(username) + "]'}");
                    }

                }
            }

        }

    }

    public static String returnUsername(String args) {
        return args.replace("-u ", "").replace("'", "");
    }

    public static String returnPassword(String args) {
        return args.replace("-p ", "").replace("'", "");
    }

    public static String returnText(String args) {
        return args.replace("-text ", "").replace("'", "");
    }

    public static String returnType(String args) {
        return args.replace("-type ", "").replace("'", "");
    }

    public static String returnName(String args) {
        return args.replace("-name ", "").replace("'", "");
    }

    public static String returnQuestionId(String args, int j) {
        return args.replace("-question-" + j + " ", "").replace("'", "");
    }

    public static String returnId(String args) {
        return args.replace("-id ", "").replace("'", "");
    }

    public static String returnQuizId(String args) {
        return args.replace("-quiz-id ", "").replace("'", "");
    }
}




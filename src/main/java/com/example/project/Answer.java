package com.example.project;

public class Answer {

    private String id;
    private String text;
    private boolean correct;

    static int idAnswer = 0;

    Answer(String id, String text, String correct) {
        this.setId(id);
        this.setText(text);
        if (correct.contains("1")) {
            this.setCorrect(true);
        } else {
            this.setCorrect(false);
        }
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "{\"answer_name\":\"" + this.getText() + "\", \"answer_id\":\"" + this.getId() + "\"}";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}

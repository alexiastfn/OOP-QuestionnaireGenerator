# tema-1-alexiastfn
tema-1-alexiastfn created by GitHub Classroom

* Stefan Alexia 
* 322 CB 

> Pentru realizarea temei m-am folosit de 5 clase diferite: User, Question, Quiz, Answer si QuizUtils.

## 1. User
- In aceasta clasa am implementate metodele ce se ocupa cu adugarea in fisierul "users.csv", cu 
cautarea in acesta dupa diferite criterii si verificarea diferitelor elemente din el.
- metoda "ppendToFile": adauga in fiser pe cate o linie username-ul si parola.
- metoda "searchThePassword" verifica existenta parolei.
- metoda "searchFileForUser" verifica existenta unui username in fisier.
- metoda "successfulLogin" verifica existenta si corespondenta corecta intre credentialele username-parola.
- metoda "createUser" se foloseste de metodele "searchFileForUser" si "appendToFile" pentru a returna
mesajele corespunzatoare cand se incearca crearea unui username.

## 2. Question
- In acesta clasa am implementat metodele care se ocupa cu adaugarea in fisierul "questions.csv", cu 
cautarea in acesta dupa anumite criterii, cu verificarea anumitor elemente care tin de intrebarea in sine, dar care tin si de raspunsurile asignate intrebarii (getCorrectAnswersId si getIncorrectAnswersId)
- metoda "appendToFile" adauga in "questions.csv" detaliile unui question (id, textul, type si un string
de raspunsuri), dar adauga si in "answers.csv id-ul intrebarii, type-ul raspunsului si raspunsurile propriu-zise.
- metoda "getIdOfQuestion" intoarce id-ul unei intrebari cu un anumit text sub forma de string.
- metoda "getQuestionById" intoarce intrebarea dupa id si adauga intr-un ArrayList de tip Answer 
raspunsurile, despartite dupa " ".
- metoda "getAllQuestionsUserPosted" este folositoare pentru task-ul 4 (-get-all-questions) si intoarce
toate (question_id-urile, question_name-urile).
- metoda "searchFileForQuestionAfterId" verifica existenta unei intrebari in fisier cu un anumit id
- metoda "searchFileForQuestion" verifica existenta unei intrebari dupa textul ei.
- metoda "addQuestionInSystem" se foloseste de metodele "searchFileForQuestion" si de "appendToFile" si
returneaza corespunzatoare cand se incearca crearea unei intrebari.

## 3. Quiz
- In acesta clasa am implementat metodele care se ocupa cu adaugarea in fisierul "quizz.csv" si care 
sunt ajutatoare task-urilor unde se cer lucruri din acest fisier.
- metoda "returnQuestionsFromQuizbyId"  intoarce intrebarile unui quiz dupa id si am folosit-o pentru 
task-ul 8.
- metodele "searchFileAfterUser1" si "searchFileAfterUser2" intorc id-urile si numele quiz-urilor
dupa username-ul care le-a creat (ajutatorare pentru task-ul 7).
- metoda "getAllAnswersId" este ajutoare pentru task-ul 9, cel de submit, deoarece intoare intr-o lista
toate id-urile raspunsurilor din quiz.

## 4. Answer
- In acesta clasa nu am metode propriu-zise in afara de constructori si de metoda toString.

## 5. QuizUtils
- Acesta este o clasa doar cu metode statice referitoare la entitatea de "Quiz".
- Metodele continute aici sunt "getQuizById" (intoarce quiz-ul cu anumit id), "addQuizzInSystem" (
se foloseste de metodele "searchFileForQuiz" si "appendQuizzToFile" pentru a returna
mesajele corespunzatoare cand se incearca crearea unui quiz) si "searchFileForQuizz".
- De asemenea, aceasta clasa are metode ce ma ajuta pentru task-ul de submitted.


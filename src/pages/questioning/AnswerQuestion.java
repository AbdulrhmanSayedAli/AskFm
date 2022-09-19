package pages.questioning;

import managers.ProfileManager;
import engine.Engine;
import engine.Page;
import models.Question;
import managers.QuestionManager;

public class AnswerQuestion extends Page {
      
    @Override
    public String getPageName() {
        return "answer a question";
    }

    @Override
    public String getDefaultContent() {
        return "<html><body><h2>answer a question</h2><p>enter the question id</p></body></html>";
    }

    @Override
    public void onStart() {
        Engine.openTextField((String id) -> {
            try {
                Question question = QuestionManager.getQuestion(Integer.parseInt(id));
                if (question == null) {
                    throw new Exception();
                }

                if (question.getReciverID() != ProfileManager.getCurrentUser().getId()) {
                    changeContent("<html><body><h2>answer a question</h2><p>you can only answer questions to you</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                    setGoBackEvents();
                    return;
                }

                Engine.changeText("<html><body><h2>answer a question</h2><p>enter the answer</p></body></html>");
                Engine.openTextField((String answer) -> {
                    QuestionManager.answerQuestion(Integer.parseInt(id), answer);
                    Engine.restart();
                });

            } catch (Exception e) {
                changeContent("<html><body><h2>answer a question</h2><p>question not found</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                setGoBackEvents();
            }

        });
    }    
}

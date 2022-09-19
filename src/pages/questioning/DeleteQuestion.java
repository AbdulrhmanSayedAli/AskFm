
package pages.questioning;

import managers.QuestionManager;
import managers.ProfileManager;
import engine.Engine;
import engine.Page;
import models.Question;


public class DeleteQuestion extends Page {
    
    @Override
    public String getPageName() {
       return "delete a question";
    }

    @Override
    public String getDefaultContent() {
        return "<html><body><h2>delete a question</h2><p>enter the question id</p></body></html>";
    }

    @Override
    public void onStart() {
        Engine.openTextField((String id) -> {
            try {
                Question question = QuestionManager.getQuestion(Integer.parseInt(id));
                if (question == null) {
                    throw new Exception();
                }

                if (question.getSendeID() != ProfileManager.getCurrentUser().getId() && question.getReciverID() != ProfileManager.getCurrentUser().getId()) {
                    changeContent("<html><body><h2>answer a question</h2><p>you can only delete questions to you or from you</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                    setGoBackEvents();
                    return;
                }

                QuestionManager.deleteQuestion(Integer.parseInt(id));
                Engine.restart();

            } catch (Exception e) {
                changeContent("<html><body><h2>answer a question</h2><p>question not found</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                setGoBackEvents();
            }

        });
    }

}

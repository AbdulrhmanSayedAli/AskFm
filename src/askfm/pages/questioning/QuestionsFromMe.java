
package askfm.pages.questioning;

import askfm.managers.QuestionManager;
import askfm.engine.Page;
import askfm.models.Question;


public class QuestionsFromMe extends Page {
    
    @Override
    public String getPageName() {
        return "questions from me";
    }
    
    
    private String getThread(int id){
        String content = "<ul>";
        int k = 0;
        for(Question q : QuestionManager.getQuestionThread(id)){
            content += QuestionParse(q, false);
            k++;
        }
        content+="</ul>";
        
        return k==0 ? "" : content;
    }
    
    private String QuestionParse(Question q,boolean thread){
        String content="";
        content+="<li> <span style=\"color:blue\">("+q.getId()+")</span> ---- to : "+q.getReciverName()+"<br/>";
        content+="question: "+q.getQuestion()+"<br/>";
        content+="answer: "+(q.getAnswer().trim().isEmpty()?"<span style=\"color:red\">not answered</span>":q.getAnswer())+"<br/>";
        if(thread)content+=getThread(q.getId());
        content+="</li>";
        
        return content;
    }

    @Override
    public String getDefaultContent() {
        String content = "<html><body><h2>questions from me</h2><ol>";

        for (Question q : QuestionManager.getQuestionsFromMe()) {
            content += QuestionParse(q, true);
        }

        content += "</ol><p style=\"color:blue\">click 3 to exit.</p></body></html>";
        return content;
        
    }

    @Override
    public void onStart() {
       setGoBackEvents();
    }

}

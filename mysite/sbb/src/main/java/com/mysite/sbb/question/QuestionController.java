package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*의존성 주입은 필요한 객체를 직접 생성하는 것이 아닌 외부로부터 객체를 받아 사용하는 것입니다.*/
@RequestMapping("/question")// URL 항상 /question으로 시작해야댐
@RequiredArgsConstructor //final이 붙은 속성을 포함하는 생성자를 자동으로 생성하는 역할 -> 객체 자동으로주입
@Controller
public class QuestionController {

    private final QuestionService questionService;
    @GetMapping("/list")
    public String list (Model model, @RequestParam(value = "page",defaultValue = "0")int page){
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging",paging);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")//id  매개변수 id이름이 동일해야한다
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question",question);//question객체를 템플릿에 전달하는 코드
        return "question_detail";
    }

    //양식 데이터를 처리하고 결과를 반환하는 데 사용
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list";
    }

    //"질문 생성" 페이지를 렌더링하는 데 사용
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }


}
//구조 : 컨트롤러 -> 서비스 -> 레포지토리
//컨트롤러는 래더링을 모두 관리
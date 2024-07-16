package com.yum.yumyums.reference.controller;

import com.yum.yumyums.reference.dto.BoardDTO;
import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.reference.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    /*
    글 목록 - GET : /board
    새 글 등록 페이지 - GET : /board/save
    새 글 등록  - POST : /board
    1글 보기 - GET : /board/1
    1번 글 수정 페이지 - GET : /board/1/update
    1번 글 수정등록 - PUT : /board/1
    1번 글 삭제 - DELETE : /board/1
    */


    /*
    글 목록 진입
    * */
    @GetMapping("")
    public String boardList(Model model, TemplateData templateData){
        templateData.setViewPath("board/list");
        List<BoardDTO> boardDTOList = boardService.findAll();

        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("templateData",templateData);

        return "template";
    }


    /*
    새 글 등록 페이지 진입
    * */
    @GetMapping("save")
    public String boardSaveForm(Model model, TemplateData templateData){

        templateData.setViewPath("board/save");

        return "template";
    }


    /*
    새 글 등록 페이지에서 등록하기
    * */
    @PostMapping("")
    public String boardSaveSubmit(Model model, BoardDTO boardDTO, TemplateData templateData){

        templateData.setViewPath("board/list");

        boardService.save(boardDTO);
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "template";
    }


    /*
    {id}번 글 상세보기
    * */
    @GetMapping("{id}")
    public String boardDetail(Model model,TemplateData templateData, @PathVariable Long id){

        templateData.setViewPath("board/detail");

        BoardDTO boardDTO = boardService.findById(id);
        boardService.updateHits(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("templateData",templateData);

        return "template";
    }


    /*
    {id}번 글 수정 페이지 진입
    * */
    @GetMapping("{id}/update")
    public String boardUpdate(Model model,TemplateData templateData, @PathVariable Long id){

        templateData.setViewPath("board/update");

        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        model.addAttribute("templateData",templateData);
        return "template";
    }


    /*
    {id}번 글 수정 페이지에서 수정등록
    * */
    @PutMapping("{id}")
    public ResponseEntity<Void> boardUpdateSubmit(@RequestBody BoardDTO boardDTO, @PathVariable Long id){

        /*
        * 비교적 간단한 작업인 DELETE 와는 다르게 PUT 요청에서는
        * RESTful API의 설계 원칙을 따르기 위해 Location 헤더를 사용하는 것이 일반적입니다.
        * PUT 요청은 리소스를 업데이트하는 작업이기 때문에 업데이트된 리소스의 상세 페이지로 리디렉션하려면
        * 서버 측에서 해당 리소스의 URL을 클라이언트에게 알려주는 것이 좋습니다.
        * 클라이언트는 응답의 Location 헤더를 확인하여 해당 URL로 리디렉션합니다.
        * */


        try {
            boardService.update(boardDTO);
            URI location = new URI("/board/" + id); // 수정 후 상세 페이지로 리디렉션할 URL
            return ResponseEntity.status(HttpStatus.OK).location(location).build();
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    /*
    {id}번 글 삭제
    * */
    /*
    @DeleteMapping("{id}")
    public void boardDelete(@PathVariable Long id){
        void를 반환하는 메소드.
        이는 HTTP 응답의 본문을 포함하지 않으며, HTTP 상태 코드도 명시적으로 설정하지 않습니다.
        따라서 클라이언트는 이 요청의 결과를 확실히 알 수 없습니다.
        기본적으로 성공 시 HTTP 200 상태 코드가 반환되지만,
        클라이언트가 이를 적절히 처리하지 않는다면 리디렉션을 수행할 수 없습니다.

        boardService.delete(id);
    }
    */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> boardDelete(@PathVariable Long id){
        /*
        ResponseEntity를 사용하여 HTTP 응답을 명시적으로 반환하는 메소드.
        성공 시 ResponseEntity.noContent().build()를 통해 HTTP 204 상태 코드를 반환합니다.
        이는 클라이언트에게 요청이 성공적으로 처리되었음을 명확히 알릴 수 있습니다.
        실패 시에는 HTTP 500 상태 코드를 반환하여 오류 상황을 클라이언트가 인지할 수 있습니다.
        따라서 성공시 클라이언트 측에서 리디렉션을 수행할 수 있습니다.
        * */
        try {
            boardService.delete(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 Internal Server Error 반환
        }
        
    }


}

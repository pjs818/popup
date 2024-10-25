package dev.zeronelab.mybatis.controller;

import dev.zeronelab.mybatis.dao.BoardMapper;
import dev.zeronelab.mybatis.dao.MemberMapper;
import dev.zeronelab.mybatis.dao.ReplyMapper;
import dev.zeronelab.mybatis.dto.BoardDTO;
import dev.zeronelab.mybatis.vo.BoardEntity;
import dev.zeronelab.mybatis.vo.ReplyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//테스트용 주석2
@RestController
@Log4j2
@RequestMapping("api/board/")
public class BoardController {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @GetMapping("boardList")
    public @ResponseBody Map<String, Object> boardList(){
        Map<String, Object> map = new HashMap<>();

        List<BoardEntity> list = boardMapper.listBoard();

        map.put("boardList", list);

        return map;
    }

    @GetMapping("boardPage/{bno}")
    public Map<String, Object> boardPage(@PathVariable Long bno){

        boardMapper.boardCnt(bno);

        Map<String, Object> map = new HashMap<>();

        List<BoardEntity> list = new ArrayList<>();

        list.add(boardMapper.boardPage(bno));

        map.put("boardPage", list);

        return map;
    }
    @Transactional
    @PostMapping("boardRegist")
    public String boardRegist(@RequestBody BoardEntity entity) throws Exception {
        Long mno = memberMapper.findMno(entity.getBwriter());
        entity.setMno(mno);
        log.info("Entity = "+entity);
        boardMapper.boardRegist(entity);

        String[] files = entity.getFiles();

        if(files == null){
            return "succ";
        }else{
            for(String fullName : files){
                boardMapper.boardAddAttach(fullName);
            }
        }

        return "succ";
    }

    @PostMapping("boardModify")
    public String boardModify(@RequestBody BoardDTO dto) throws Exception{
        log.info("BoardDTO = "+dto);
        boardMapper.boardModify(dto);
        return "succ";
    }

    @Transactional
    @PostMapping("boardDelete")
    public String boardDelete(@RequestBody Map<String, Long> request){
        //리액트에서 bno를 json형태로 전달하고 있어서 Map형식으로 받고 거기에 bno를 get으로 얻는다.
        Long bno = request.get("bno");
        log.info("삭제합니다.");
        replyMapper.replyAllDelete(bno);
        boardMapper.boardDelete(bno);
        return "succ";
    }
}

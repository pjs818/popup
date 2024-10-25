package dev.zeronelab.mybatis.dao;

import dev.zeronelab.mybatis.dto.BoardDTO;
import dev.zeronelab.mybatis.vo.BoardEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    public List<BoardEntity> listBoard();

    public BoardEntity boardPage(Long bno);

    public void boardRegist(BoardEntity entity);

    public void boardCnt(Long bno);

    public void boardBccnt(Long bno);

    public void boardBccntm(Long bno);

    public Long boardFindBno(Long cno);

    public void boardModify(BoardDTO dto);

    public void boardDelete(Long bno);

    public void boardAddAttach(String fullName);

}

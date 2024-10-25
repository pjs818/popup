package dev.zeronelab.mybatis.controller;

import dev.zeronelab.mybatis.dao.PopupMapper;
import dev.zeronelab.mybatis.vo.PopupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PopupController {

    @Autowired
    private PopupMapper popupMapper;
    //테스트용 컨트롤러
    @RequestMapping("/home")
	public Map<String, String> home() {
		System.out.println("/*** home running2...");

		Map<String, String> rtnObj = new HashMap<>();
		rtnObj.put("home", "This is a home page.");
		return rtnObj;
	}

    @RequestMapping("/api/popup/popupList")
    public @ResponseBody Map<String, Object> popup(){
        Map<String, Object> popMap = new HashMap<>();

        List<PopupEntity> popupList = popupMapper.listPopup();

        popMap.put("popupList",popupList);

        return popMap;
    }

    @GetMapping("/api/popup/popupRead/{sno}")
    public Map<String, Object> read(@PathVariable Long sno){
        Map<String, Object> map = new HashMap<>();

        List<PopupEntity> list = new ArrayList<>();
        list.add(popupMapper.read(sno));

        map.put("popupRead",list);

        return map;

    }

}

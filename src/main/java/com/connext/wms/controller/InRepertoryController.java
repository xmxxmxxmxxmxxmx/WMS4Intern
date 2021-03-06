package com.connext.wms.controller;

import com.connext.wms.api.dto.InRepertoryDetailDTO;
import com.connext.wms.api.dto.InputSth;
import com.connext.wms.api.util.EntityAndDto;
import com.connext.wms.entity.InRepertory;
import com.connext.wms.service.InRepertoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * @Author: Marcus
 * @Date: 2019/1/2 11:23
 * @Version 1.0
 */
@Controller
@RequestMapping("/inRepertory")
public class InRepertoryController {
    private final InRepertoryService inRepertoryService;
    private final ObjectMapper objectMapper;
    private final EntityAndDto entityAndDto;

    @Autowired
    public InRepertoryController(InRepertoryService inRepertoryService, ObjectMapper objectMapper, EntityAndDto entityAndDto) {
        this.inRepertoryService = inRepertoryService;
        this.objectMapper = objectMapper;
        this.entityAndDto = entityAndDto;
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("one", inRepertoryService.findOne(id));
        return "detail";
    }

    @GetMapping("page/{page}")
    public String list(@PathVariable Integer page, Model model) {
        model.addAttribute("list", inRepertoryService.findPage(page, 10));
        return "warehouse-in-list.html";
    }
    @GetMapping("page/{page}/{status}")
    public String listBy(@PathVariable Integer page,@PathVariable String status, Model model) {
        model.addAttribute("list", inRepertoryService.findPageBy(status,page,10));
        return "warehouse-in-list.html";
    }

    @GetMapping("/search")
    public String search(@RequestParam String like, Model model) {
        String likeSth = "%" + like + "%";
        model.addAttribute("list", inRepertoryService.findAllLike(likeSth));
        return "warehouse-in-list.html";
    }

    @PostMapping("/")
    public String finish(@RequestParam Integer id, @RequestParam String status, @RequestParam String list) throws IOException {
        List<InRepertoryDetailDTO> inRepertoryDetailDTOS = objectMapper.readValue(list, new TypeReference<List<InRepertoryDetailDTO>>() {});
        InRepertory inRepertory= inRepertoryService.findOne(id);
        inRepertory.setRepertoryDetails(entityAndDto.toEntity(String.valueOf(id),inRepertoryDetailDTOS));
        inRepertoryService.changeInRepertoryStatus(id, status);
        inRepertoryService.pushInRepertoryState(inRepertory);
        return "";
    }
}

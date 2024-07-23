package com.example.Lee.controller;

import com.example.Lee.model.NoticeModel;
import com.example.Lee.service.NoticeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noticeList")
public class ListController {

    private final NoticeListService noticeListService;

    @Autowired
    public ListController(NoticeListService noticeListService) {
        this.noticeListService = noticeListService;
    }

    @GetMapping
    public List<NoticeModel> getAllNotices() {
        return noticeListService.getAllNotices();
    }

    @GetMapping("/{membId}")
    public ResponseEntity<NoticeModel> getNoticeById(@PathVariable String membId) {
        NoticeModel notice = noticeListService.getNoticeById(membId);
        return ResponseEntity.ok(notice);
    }

    @PostMapping
    public NoticeModel createNotice(@RequestBody NoticeModel notice) {
        return noticeListService.createNotice(notice);
    }

    @PutMapping("/{membId}")
    public ResponseEntity<NoticeModel> updateNotice(@PathVariable String membId, @RequestBody NoticeModel notice) {
        NoticeModel updatedNotice = noticeListService.updateNotice(membId, notice);
        return ResponseEntity.ok(updatedNotice);
    }

    @DeleteMapping("/{membId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable String membId) {
        noticeListService.deleteNotice(membId);
        return ResponseEntity.noContent().build();
    }
}

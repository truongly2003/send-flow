package com.example.sendflow.controller;

import com.example.sendflow.entity.SendLog;
import com.example.sendflow.enums.EventStatus;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.repository.SendLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/tracking")
@RequiredArgsConstructor
public class TrackingController {
    private final SendLogRepository sendLogRepository;

    @GetMapping("/open/{logId}")
    public ResponseEntity<byte[]> trackOpen(@PathVariable Long logId) {
        SendLog sendLog = sendLogRepository.findById(logId).orElseThrow(() ->
                new ResourceNotFoundException("Send log not found"));
        if (sendLog != null && sendLog.getOpenedAt() == null) {
            sendLog.setOpenedAt(LocalDateTime.now());
            sendLog.setStatus(EventStatus.OPENED);
            sendLogRepository.save(sendLog);
        }
        byte[] pixel = new byte[]{};
        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(pixel);
    }
    @GetMapping("/click/{logId}")
    public ResponseEntity<Void> trackClick(@PathVariable Long logId) {
        SendLog sendLog = sendLogRepository.findById(logId).orElseThrow(() ->
                new ResourceNotFoundException("Send log not found"));
        if (sendLog != null && sendLog.getClickedAt() == null) {
            sendLog.setClickedAt(LocalDateTime.now());
            sendLog.setStatus(EventStatus.CLICKED);
            sendLogRepository.save(sendLog);
        }
        return ResponseEntity.status(302)
                .header("Location", "https://google.com")
                .build();
    }
}

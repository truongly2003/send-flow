package com.example.sendflow.controller;

import com.example.sendflow.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {
    private final IContactService contactService;
}

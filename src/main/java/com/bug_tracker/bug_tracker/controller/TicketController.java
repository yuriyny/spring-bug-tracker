package com.bug_tracker.bug_tracker.controller;


import com.bug_tracker.bug_tracker.dto.TicketDto;
import com.bug_tracker.bug_tracker.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public TicketDto create(@RequestBody @Valid TicketDto ticketDto){
        return ticketService.save(ticketDto);
    }
}

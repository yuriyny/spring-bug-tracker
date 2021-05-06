package com.bug_tracker.bug_tracker.controller;


import com.bug_tracker.bug_tracker.dto.CommentDto;
import com.bug_tracker.bug_tracker.dto.TicketDto;
import com.bug_tracker.bug_tracker.dto.TicketHistoryDto;
import com.bug_tracker.bug_tracker.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public TicketDto create(@RequestBody @Valid TicketDto ticketDto) {
        return ticketService.save(ticketDto);
    }

    @PostMapping("/update")
    public TicketHistoryDto update(@RequestBody @Valid TicketHistoryDto ticketHistoryDto) {
        return ticketService.update(ticketHistoryDto);
    }

    @PostMapping("/add-comment")
    public CommentDto addComment(@RequestBody @Valid CommentDto commentDto) {
        return ticketService.addComment(commentDto);
    }

    @GetMapping("/by-projectname/{projectname}")
    public ResponseEntity<List<TicketDto>> getAllTicketsByProjectName(@PathVariable("projectname") String projectname) {
        return status(OK)
                .body(ticketService.getTicketsByProjectName(projectname));
    }

    @GetMapping("/by-projectid/{projectid}")
    public ResponseEntity<List<TicketDto>> getAllTicketsByProjectId(@PathVariable("projectid") Long projectid) {
        return status(OK)
                .body(ticketService.getTicketsByProjectId(projectid));
    }

    @GetMapping("/comments/by-ticketid/{ticketid}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByTicketId(@PathVariable("ticketid") Long ticketid) {
        return status(OK)
                .body(ticketService.getCommentsByTicketId(ticketid));
    }

    @GetMapping("/by-id/{ticketId}")
    public TicketDto getTicketById(@PathVariable("ticketId") Long ticketId) {
        return this.ticketService.getTicketById(ticketId);
    }

    @GetMapping("/ticket-history/{ticketId}")
    public ResponseEntity<List<TicketHistoryDto>> getTicketHistoryByTicketId(@PathVariable("ticketId") Long ticketId) {
        return status(OK).body(ticketService.getTicketHistoryByTicketId(ticketId));
    }

}

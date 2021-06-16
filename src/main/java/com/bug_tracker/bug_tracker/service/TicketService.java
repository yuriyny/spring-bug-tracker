package com.bug_tracker.bug_tracker.service;

import com.bug_tracker.bug_tracker.dto.CommentDto;
import com.bug_tracker.bug_tracker.dto.TicketDto;
import com.bug_tracker.bug_tracker.dto.TicketHistoryDto;
import com.bug_tracker.bug_tracker.exceptions.ProjectNotFoundException;
import com.bug_tracker.bug_tracker.mapper.CommentMapper;
import com.bug_tracker.bug_tracker.mapper.TicketHistoryMapper;
import com.bug_tracker.bug_tracker.mapper.TicketMapper;
import com.bug_tracker.bug_tracker.model.*;
import com.bug_tracker.bug_tracker.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ProjectRepository projectRepository;
    private final AuthService authService;
    private final TicketMapper ticketMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ParticipantRepository participantRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketHistoryMapper ticketHistoryMapper;

    //creating
    public TicketDto save(TicketDto ticketDto) {
        //System.out.println("==================");
        System.out.println(ticketDto);
        Ticket t = ticketMapper.mapDtoToTicket(ticketDto);
        Project p = projectRepository.getOne(ticketDto.getProjectId());
        //Project p = projectRepository.findByProjectName(ticketDto.getProjectName())
        //       .orElseThrow(() -> new ProjectNotFoundException(ticketDto.getProjectName()));
        t.setProject(p);
        Participant assignedParticipant = participantRepository.getOne(ticketDto.getAssignedParticipant());
        User u = authService.getCurrentUser();
        Participant participant = participantRepository.findParticipantByUserAndProject(u, p);
        t.setCreator(participant);
        t.setAssignedParticipant(assignedParticipant);
        t.setStatus(Status.OPEN);
        ticketRepository.save(t);
        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setTicket(t);
        ticketHistory.setTicketName(t.getTicketName());
        ticketHistory.setDescription(t.getDescription());
        ticketHistory.setPriority(t.getPriority());
        ticketHistory.setUpdatedDate(t.getUpdatedDate());
        ticketHistory.setUpdateParticipant(participant);
        Participant ap = participantRepository.getOne(ticketDto.getAssignedParticipant());
        ticketHistory.setAssignedParticipant(ap);
        ticketHistoryRepository.save(ticketHistory);
        ticketDto.setTicketId(t.getTicketId());
        return ticketMapper.mapTicketToDto(t);
    }

    public TicketHistoryDto update(TicketHistoryDto ticketHistoryDto) {
        Ticket t = ticketRepository.getOne(ticketHistoryDto.getTicket());
        t.setTicketName(ticketHistoryDto.getTicketName());
        t.setDescription(ticketHistoryDto.getDescription());
        t.setPriority(ticketHistoryDto.getPriority());
        User u = authService.getCurrentUser();
        Participant updateParticipant = this.participantRepository.findParticipantByUserAndProject(u, t.getProject());
        Participant ap = this.participantRepository.getOne(ticketHistoryDto.getAssignedParticipant());
        t.setAssignedParticipant(ap);
        t.setUpdatedDate(Instant.now());
        t.setStatus(ticketHistoryDto.getStatus());
        ticketRepository.save(t);
        TicketHistory ticketHistory = ticketHistoryMapper.mapDtoToTicketHistory(ticketHistoryDto);
        ticketHistory.setTicket(t);
        ticketHistory.setUpdatedDate(t.getUpdatedDate());
        ticketHistory.setAssignedParticipant(ap);
        ticketHistory.setUpdateParticipant(updateParticipant);
        ticketHistoryRepository.save(ticketHistory);
        ticketHistoryDto.setTicketHistoryId(ticketHistory.getTicketHistoryId());
        ticketHistoryDto.setUpdatedDate(ticketHistory.getUpdatedDate());
        ticketHistoryDto.setUpdateParticipant(updateParticipant.getParticipantId());
        return ticketHistoryDto;
    }

    public List<TicketDto> getTicketsByProjectName(String projectname) {
        Project p = projectRepository.findByProjectName(projectname)
                .orElseThrow(() -> new ProjectNotFoundException(projectname));

        return ticketRepository.findByProject(p)
                .stream()
                .map(ticket -> ticketMapper.mapTicketToDto(ticket))
                .collect(toList());

    }

    public List<TicketDto> getTicketsByProjectId(Long projectId) {
        Project p = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("some string projectId"));

        return ticketRepository.findByProject(p)
                .stream()
                .map(ticket -> ticketMapper.mapTicketToDto(ticket))
                .collect(toList());

    }

    public CommentDto addComment(CommentDto commentDto) {
        User u = authService.getCurrentUser();
        Ticket ticket = ticketRepository.getOne(commentDto.getTicketId());
        Comment comment = new Comment();
        comment.setTicket(ticket);
        comment.setAuthor(u);
        comment.setCreatedDate(java.time.Instant.now());
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return commentMapper.mapCommentToDto(comment);
    }

    public List<CommentDto> getCommentsByTicketId(Long ticketId) {
        Ticket ticket = ticketRepository.getOne(ticketId);
        return commentRepository.findByTicket(ticket)
                .stream()
                .map(comment ->
                        commentMapper.mapCommentToDto(comment))
                .collect(toList());
    }

    public TicketDto getTicketById(Long ticketId) {
        return this.ticketMapper.mapTicketToDto(ticketRepository.getOne(ticketId));
    }

    public List<TicketHistoryDto> getTicketHistoryByTicketId(Long ticketId) {
        Ticket t = ticketRepository.getOne(ticketId);
        TicketDto td = ticketMapper.mapTicketToDto(t);
        return ticketHistoryRepository.findByTicket(t).stream().map(th ->
                ticketHistoryMapper.mapTicketHistoryToDto(th))
                .collect(toList());
    }

    public List<TicketDto> getTicketForCurrentUser() {
        User u = authService.getCurrentUser();
        return ticketRepository.findTicketsByUser(u).stream().map(ticket ->
                ticketMapper.mapTicketToDto(ticket))
                .collect(toList());
    }

    public List<TicketDto> getAssignedTicketForCurrentUser() {
        User u = authService.getCurrentUser();
        return ticketRepository.findAssignedTicketsByUser(u).stream().map(ticket ->
                ticketMapper.mapTicketToDto(ticket))
                .collect(toList());
    }

    public List<TicketDto> getTicketCreatedByUser() {
        User u = authService.getCurrentUser();
        return ticketRepository.findCreatedTicketsByUser(u).stream().map(ticket ->
                ticketMapper.mapTicketToDto(ticket))
                .collect(toList());
    }

    public List<TicketDto> getOpenTicketForCurrentUser() {
        User u = authService.getCurrentUser();
        return ticketRepository.findTicketsByUserAndStatus(u, Status.OPEN).stream().map(ticket ->
                ticketMapper.mapTicketToDto(ticket))
                .collect(toList());
    }
    public List<TicketDto> getClosedTicketForCurrentUser() {
        User u = authService.getCurrentUser();
        return ticketRepository.findTicketsByUserAndStatus(u, Status.CLOSED).stream().map(ticket ->
                ticketMapper.mapTicketToDto(ticket))
                .collect(toList());
    }

}

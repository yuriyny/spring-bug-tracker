package com.bug_tracker.bug_tracker.service;

import com.bug_tracker.bug_tracker.dto.TicketDto;
import com.bug_tracker.bug_tracker.execptions.ProjectNotFoundException;
import com.bug_tracker.bug_tracker.mapper.TicketMapper;
import com.bug_tracker.bug_tracker.model.*;
import com.bug_tracker.bug_tracker.repository.ParticipantRepository;
import com.bug_tracker.bug_tracker.repository.ProjectRepository;
import com.bug_tracker.bug_tracker.repository.TicketRepository;
import com.bug_tracker.bug_tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
//@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ProjectRepository projectRepository;
    private final AuthService authService;
    private final TicketMapper ticketMapper;
    private final ParticipantRepository participantRepository;

    public TicketDto save(TicketDto ticketDto) {
        Ticket t = ticketMapper.mapDtoToTicket(ticketDto);
        Project p = projectRepository.findByProjectName(ticketDto.getProjectName())
                .orElseThrow(()-> new ProjectNotFoundException(ticketDto.getProjectName()));
        t.setProject(p);
        Participant participant = new Participant();
        User u = authService.getCurrentUser();
        participant.setUser(u);
        participant.setRole(Role.ADMIN);
        participant.setProject(p);
        participantRepository.save(participant);
        t.setCreator(participant);
        ticketRepository.save(t);
        ticketDto.setTicketId(t.getTicketId());
        return ticketDto;
    }
}

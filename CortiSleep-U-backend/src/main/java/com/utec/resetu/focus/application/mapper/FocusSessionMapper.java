package com.utec.resetu.focus.application.mapper;

import com.utec.resetu.focus.application.dto.FocusSessionRequest;
import com.utec.resetu.focus.application.dto.FocusSessionResponse;
import com.utec.resetu.focus.domain.model.FocusSession;
import org.springframework.stereotype.Component;

@Component
public class FocusSessionMapper {

    public FocusSession toEntity(FocusSessionRequest request) {
        if (request == null) return null;
        FocusSession fs = new FocusSession();
        fs.setDurationMinutes(request.getDurationMinutes());
        fs.setSessionType(request.getSessionType());
        fs.setTaskDescription(request.getTaskDescription());
        return fs;
    }

    public FocusSessionResponse toDto(FocusSession session) {
        if (session == null) return null;
        FocusSessionResponse r = new FocusSessionResponse();
        r.setId(session.getId());
        r.setUserId(session.getUser() != null ? session.getUser().getId() : null);
        r.setDurationMinutes(session.getDurationMinutes());
        r.setCompleted(session.getCompleted());
        r.setStartedAt(session.getStartedAt());
        r.setEndedAt(session.getEndedAt());
        r.setSessionType(session.getSessionType());
        r.setTaskDescription(session.getTaskDescription());
        r.setCreatedAt(session.getCreatedAt() != null ? session.getCreatedAt().toString() : null);
        return r;
    }
}

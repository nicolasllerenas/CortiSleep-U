package com.utec.resetu.focus.application.mapper;

import com.utec.resetu.focus.application.dto.FocusSessionRequest;
import com.utec.resetu.focus.application.dto.FocusSessionResponse;
import com.utec.resetu.focus.domain.model.FocusSession;
import org.springframework.stereotype.Component;

@Component
public class FocusSessionMapper {

    public FocusSession toEntity(FocusSessionRequest request) {
        if (request == null) return null;
        return FocusSession.builder()
                .durationMinutes(request.getDurationMinutes())
                .sessionType(request.getSessionType())
                .taskDescription(request.getTaskDescription())
                .build();
    }

    public FocusSessionResponse toDto(FocusSession session) {
        if (session == null) return null;
        return FocusSessionResponse.builder()
                .id(session.getId())
                .userId(session.getUser() != null ? session.getUser().getId() : null)
                .durationMinutes(session.getDurationMinutes())
                .completed(session.getCompleted())
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt())
                .sessionType(session.getSessionType())
                .taskDescription(session.getTaskDescription())
                .createdAt(session.getCreatedAt() != null ? session.getCreatedAt().toString() : null)
                .build();
    }
}

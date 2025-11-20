package com.utec.resetu.senses.application.dto;

import com.utec.resetu.senses.domain.model.UserSensoryPreference;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSensoryPreferenceDto {
    private Long id;
    private Long contentId;
    private Boolean favorite;
    private Integer playCount;
    private LocalDateTime lastPlayedAt;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserSensoryPreferenceDto fromEntity(UserSensoryPreference e) {
        return UserSensoryPreferenceDto.builder()
                .id(e.getId())
                .contentId(e.getContent() != null ? e.getContent().getId() : null)
                .favorite(e.getFavorite())
                .playCount(e.getPlayCount())
                .lastPlayedAt(e.getLastPlayedAt())
                .rating(e.getRating())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}

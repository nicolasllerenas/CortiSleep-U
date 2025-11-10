package com.utec.resetu.senses.application.service;

import com.utec.resetu.auth.domain.model.User;
import com.utec.resetu.senses.domain.model.SenseType;
import com.utec.resetu.senses.domain.model.SensoryContent;
import com.utec.resetu.senses.domain.model.UserSensoryPreference;
import com.utec.resetu.senses.domain.repository.SensoryContentRepository;
import com.utec.resetu.senses.domain.repository.UserSensoryPreferenceRepository;
import com.utec.resetu.shared.exception.ResourceNotFoundException;
import com.utec.resetu.shared.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensesService {

    private final SensoryContentRepository contentRepository;
    private final UserSensoryPreferenceRepository preferenceRepository;
    private final CurrentUserService currentUserService;

    public List<SensoryContent> listAll() {
        return contentRepository.findByIsActiveTrueOrderByViewCountDesc();
    }

    public List<SensoryContent> listByType(SenseType type) {
        return contentRepository.findBySenseTypeAndIsActiveTrue(type);
    }

    public void incrementView(Long contentId) {
        SensoryContent content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado"));
        Integer vc = content.getViewCount() == null ? 0 : content.getViewCount();
        content.setViewCount(vc + 1);
        contentRepository.save(content);
    }

    public boolean toggleFavorite(Long contentId) {
        User user = currentUserService.getCurrentUser();
        SensoryContent content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado"));
        UserSensoryPreference pref = preferenceRepository
                .findByUser_IdAndContent_Id(user.getId(), contentId)
                .orElseGet(() -> {
                    UserSensoryPreference p = new UserSensoryPreference();
                    p.setUser(user);
                    p.setContent(content);
                    p.setFavorite(false);
                    p.setPlayCount(0);
                    return p;
                });
        boolean newFav = !Boolean.TRUE.equals(pref.getFavorite());
        pref.setFavorite(newFav);
        preferenceRepository.save(pref);
        return newFav;
    }

    public void registerPlay(Long contentId) {
        User user = currentUserService.getCurrentUser();
        SensoryContent content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado"));
        UserSensoryPreference pref = preferenceRepository
                .findByUser_IdAndContent_Id(user.getId(), contentId)
                .orElseGet(() -> {
                    UserSensoryPreference p = new UserSensoryPreference();
                    p.setUser(user);
                    p.setContent(content);
                    p.setFavorite(false);
                    p.setPlayCount(0);
                    return p;
                });
        int pc = pref.getPlayCount() == null ? 0 : pref.getPlayCount();
        pref.setPlayCount(pc + 1);
        pref.setLastPlayedAt(LocalDateTime.now());
        preferenceRepository.save(pref);
    }

    public List<SensoryContent> recommended(SenseType type, Integer stressLevel) {
        List<SensoryContent> base = (type != null) ? listByType(type) : listAll();
        if (stressLevel != null) {
            base = base.stream().filter(sc -> {
                Integer min = sc.getStressLevelMin();
                Integer max = sc.getStressLevelMax();
                if (min == null && max == null) return true;
                if (min == null) return stressLevel <= max;
                if (max == null) return stressLevel >= min;
                return stressLevel >= min && stressLevel <= max;
            }).collect(Collectors.toList());
        }
        return base.stream()
                .sorted(
                        Comparator.comparing((SensoryContent sc) -> sc.getViewCount(), Comparator.nullsFirst(Comparator.naturalOrder())).reversed()
                                .thenComparing(sc -> sc.getCreatedAt(), Comparator.nullsFirst(Comparator.naturalOrder())).reversed()
                )
                .collect(Collectors.toList());
    }
}

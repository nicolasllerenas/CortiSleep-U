package com.utec.resetu.screentime.application.service;

import com.utec.resetu.auth.domain.model.User;
import com.utec.resetu.screentime.domain.model.AppUsage;
import com.utec.resetu.screentime.domain.model.ScreenTimeEntry;
import com.utec.resetu.screentime.domain.repository.AppUsageRepository;
import com.utec.resetu.screentime.domain.repository.ScreenTimeRepository;
import com.utec.resetu.shared.exception.ResourceNotFoundException;
import com.utec.resetu.shared.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScreenTimeService {

    private final ScreenTimeRepository screenTimeRepository;
    private final AppUsageRepository appUsageRepository;
    private final CurrentUserService currentUserService;

    public ScreenTimeEntry upsertEntry(LocalDate date, Integer totalMinutes, String deviceType) {
        User user = currentUserService.getCurrentUser();
        ScreenTimeEntry entry = screenTimeRepository.findByUser_IdAndDate(user.getId(), date)
                .orElseGet(() -> {
                    ScreenTimeEntry e = new ScreenTimeEntry();
                    e.setUser(user);
                    e.setDate(date);
                    e.setTotalMinutes(0);
                    e.setDeviceType(deviceType);
                    return e;
                });
        entry.setTotalMinutes(totalMinutes);
        if (deviceType != null) entry.setDeviceType(deviceType);
        return screenTimeRepository.save(entry);
    }

    public AppUsage addAppUsage(Long entryId, String appName, String appCategory, Integer usageMinutes) {
        ScreenTimeEntry entry = screenTimeRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Entry no encontrada"));
        AppUsage usage = new AppUsage();
        usage.setScreenTimeEntry(entry);
        usage.setAppName(appName);
        usage.setAppCategory(appCategory);
        usage.setUsageMinutes(usageMinutes);
        return appUsageRepository.save(usage);
    }

    public Map<String, Object> getStats(String range) {
        User user = currentUserService.getCurrentUser();
        LocalDate end = LocalDate.now();
        LocalDate start = "daily".equalsIgnoreCase(range) ? end : end.minusDays(6); // weekly 7 días por defecto
        List<ScreenTimeEntry> entries = screenTimeRepository.findByUser_IdAndDateBetweenOrderByDateDesc(user.getId(), start, end);
        int total = entries.stream().map(ScreenTimeEntry::getTotalMinutes).filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
        double avg = entries.isEmpty() ? 0 : total / (double) entries.size();
        // Top apps por minutos
        Map<String, Integer> appTotals = new HashMap<>();
        for (ScreenTimeEntry e : entries) {
            for (AppUsage u : appUsageRepository.findByScreenTimeEntryId(e.getId())) {
                appTotals.merge(u.getAppName(), Optional.ofNullable(u.getUsageMinutes()).orElse(0), Integer::sum);
            }
        }
        List<Map.Entry<String, Integer>> topApps = new ArrayList<>(appTotals.entrySet());
        topApps.sort((a,b) -> Integer.compare(b.getValue(), a.getValue()));
        if (topApps.size() > 5) topApps = topApps.subList(0,5);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("range", range == null ? "weekly" : range.toLowerCase());
        res.put("start", start);
        res.put("end", end);
        res.put("totalMinutes", total);
        res.put("averageMinutes", Math.round(avg));
        res.put("entries", entries);
        res.put("topApps", topApps);
        return res;
    }
}

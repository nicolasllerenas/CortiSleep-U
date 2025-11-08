
package com.utec.resetu.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
public class PageResponse<T> {
    public PageResponse() {}
    public PageResponse(java.util.List<T> content, int page, int size, long totalElements, int totalPages, boolean first, boolean last) {
        this.content = content; this.page = page; this.size = size; this.totalElements = totalElements; this.totalPages = totalPages; this.first = first; this.last = last;
    }
    private java.util.List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    
    public static <T> PageResponse<T> from(org.springframework.data.domain.Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
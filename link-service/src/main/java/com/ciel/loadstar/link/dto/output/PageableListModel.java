package com.ciel.loadstar.link.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class PageableListModel<T> {
    public PageableListModel() {
        total = 0;
    }

    private long total;

    private List<T> items;
}

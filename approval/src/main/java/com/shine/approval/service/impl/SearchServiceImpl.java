package com.shine.approval.service.impl;

import com.shine.approval.service.ISearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyao
 */
@Service
public class SearchServiceImpl implements ISearchService {
    @Override
    public List<Object> search(String recordId) {
        return new ArrayList<>();
    }
}

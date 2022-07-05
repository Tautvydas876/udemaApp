package com.example.doctorappdemo.service;

import com.example.doctorappdemo.dao.CommentsRepository;
import com.example.doctorappdemo.dao.EntriesRepository;
import com.example.doctorappdemo.entity.Comments;
import com.example.doctorappdemo.entity.Entries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EntriesServiceImpl implements EntriesService {

    @Autowired
    private EntriesRepository entriesRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public List<Entries> findAllEntries() {
        return entriesRepository.findAllEntries();
    }

    @Override
    public Entries findById(int id) {
        return entriesRepository.findById(id);
    }

    @Override
    public Page<Entries> listAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1,
                10,
                Sort.by("data").descending()
        );
        return entriesRepository.findAll(pageable);
    }

    @Override
    public List<Comments> findAllEntriesComments(int id) {
        return entriesRepository.findAllByCommentsByEntriesId(id);
    }

    @Override
    public void saveComment(Comments comment) {
        commentsRepository.save(comment);
    }

    @Override
    public void save(Entries entries) {
        entriesRepository.save(entries);
    }
}

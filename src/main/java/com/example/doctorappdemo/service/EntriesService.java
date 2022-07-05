package com.example.doctorappdemo.service;

import com.example.doctorappdemo.entity.Comments;
import com.example.doctorappdemo.entity.Entries;
import com.example.doctorappdemo.entity.Pictures;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EntriesService {

    List<Entries> findAllEntries();

    void save(Entries entries);

    Entries findById(int id);

    Page<Entries> listAll(int currentPage);

    List<Comments> findAllEntriesComments(int id);

    void saveComment(Comments comment);


}


package com.example.doctorappdemo.dao;

import com.example.doctorappdemo.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentsRepository extends JpaRepository<Comments, Integer> {

}

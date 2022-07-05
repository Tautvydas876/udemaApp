package com.example.doctorappdemo.dao;


import com.example.doctorappdemo.entity.Comments;
import com.example.doctorappdemo.entity.Entries;
import com.example.doctorappdemo.entity.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntriesRepository extends PagingAndSortingRepository<Entries, Long> {

    @Query("SELECT u FROM Entries u left join  u.picture order by u.data desc ")
        //suranda viska ir surikiuoja pagal naujiasia
    List<Entries> findAllEntries();

    @Query("SELECT u from Pictures u")
        //suranda visus url
    List<Pictures> findAllPictures();

    @Query("select u.pictureUrl  from  Pictures u join u.entries  where u.entries.id =:entries_id")
        //suranda atitinkamo Entries visus url
    List<String> findAllPicturesOfEntriesById(@Param("entries_id") int id);

    Entries findById(int id);       // suranda atitinkama entries

    @Query("select u from  Comments u where  u.entries.id =:entries_id order by u.timeStamp desc ")
    List<Comments> findAllByCommentsByEntriesId(@Param("entries_id") int id);




}

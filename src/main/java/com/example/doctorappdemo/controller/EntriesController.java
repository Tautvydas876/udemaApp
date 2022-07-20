package com.example.doctorappdemo.controller;


import com.example.doctorappdemo.entity.Comments;
import com.example.doctorappdemo.entity.Entries;

import com.example.doctorappdemo.entity.Pictures;
import com.example.doctorappdemo.entity.User;
import com.example.doctorappdemo.service.EntriesService;
import com.example.doctorappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Controller
public class EntriesController {

    @Autowired
    UserService userService;

    @Autowired
    private EntriesService entriesService;


    @GetMapping("/blog")
    public String BlogForm(Model model) {

        return listByPage(model, 1);
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Entries> page = entriesService.listAll(currentPage);
        List<Entries> entriesList = page.getContent();


        User user = userService.findUserById(1);


        int totalPages = page.getTotalPages();
        System.out.println(user.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("entries", entriesList);
        model.addAttribute("totalPages", totalPages);

        return "/blog";
    }

    @GetMapping("/page/blog-post")
    public String blogPost(@RequestParam("entriesId") int id, Model model) {
        Entries entries = entriesService.findById(id);
        List<Comments> comments = entriesService.findAllEntriesComments(id);


        model.addAttribute("comments", comments);
        model.addAttribute("entries", entries);
        return "blog-post";
    }

    @GetMapping("/blog-post")
    public String blogPostForum(@RequestParam("entriesId") int id, Model model) {
        Entries entries = entriesService.findById(id);

        List<Comments> comments = entriesService.findAllEntriesComments(id);


        model.addAttribute("comments", comments);
        model.addAttribute("entries", entries);
        return "blog-post";
    }


    @PostMapping("/blog-post/postComment")
    public String blogPostComment(@ModelAttribute("entries") Entries entries, @ModelAttribute("comments") Comments comments, Model model,
                                  @RequestParam("userName") String userName, @RequestParam("comment") String comment) {

        int id = entries.getId();
        Entries entrieFromDB = entriesService.findById(id);

        Comments newComment = new Comments(userName, comment);

        newComment.setEntries(entrieFromDB);
        entriesService.saveComment(newComment);


        model.addAttribute("comments", comments);
        model.addAttribute("entries", entries);

        return "redirect:/page/blog-post?entriesId=" + id;
    }

    @GetMapping("/entries-profile")
    public String entriesProfileForm() {

        return "/admin_section/entries-profile";
    }

    @PostMapping("/entries-profile/create-Entry")
    public String createEntry(@RequestParam("title") String title, @RequestParam("entry") String entry, Model model) {

        Entries entries = new Entries(title, entry);
        entriesService.save(entries);

        model.addAttribute("message", "Entry Created successful!");

        return "/admin_section/entries-profile";
    }
}

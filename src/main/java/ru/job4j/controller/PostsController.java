package ru.job4j.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.PostDto;
import ru.job4j.dto.PostUserMinDto;
import ru.job4j.service.post.PostService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getAll() {
        return postService.findAll().stream().toList();
    }

    @GetMapping("/{userIds}")
    public ResponseEntity<List<PostUserMinDto>> getByUserIds2(@PathVariable("userIds")
                                                              @NotNull
                                                              List<Long> userIds) {
        List<PostUserMinDto> postUserMinDtoList = postService.findPostsByUserIds(userIds).stream().toList();
        return ResponseEntity.ok(postUserMinDtoList);
    }
}
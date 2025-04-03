package ru.job4j.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.PostDto;
import ru.job4j.dto.PostUserMinDto;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.service.post.PostService;

import java.util.List;

@Tag(name = "PostsController", description = "PostsController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;

    @Operation(
            summary = "Retrieve list of all posts",
            description = "Get list of all posts. The response is list of Post-objects.",
            tags = { "Post", "getAll" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PostDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getAll() {
        return postService.findAll().stream().toList();
    }

    @Operation(
            summary = "Retrieve list of all posts",
            description = "Get list of all posts by specifying userIds. The response is list of Post-objects.",
            tags = { "Post", "getByUserIds" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PostUserMinDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })

    @GetMapping("/{userIds}")
    public ResponseEntity<List<PostUserMinDto>> getByUserIds(@PathVariable("userIds")
                                                              @NotNull
                                                              List<Long> userIds) {
        List<PostUserMinDto> postUserMinDtoList = postService.findPostsByUserIds(userIds).stream().toList();
        return ResponseEntity.ok(postUserMinDtoList);
    }
}
package com.zonebug.debugging.controller;

import com.zonebug.debugging.dto.WriteCommentDTO;
import com.zonebug.debugging.dto.WritePostDTO;
import com.zonebug.debugging.dto.response.*;
import com.zonebug.debugging.security.user.CustomUserDetails;
import com.zonebug.debugging.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/")
    public ResponseEntity<MainPostResponseDTO> getMainPosts(
            @AuthenticationPrincipal CustomUserDetails authUser) {
        return ResponseEntity.ok(communityService.getMainPosts(authUser));
    }

    @PostMapping("/post")
    public ResponseEntity<PostIdResponseDTO> writePost(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestBody WritePostDTO writePost) {
        return ResponseEntity.ok(communityService.writePost(authUser, writePost));
    }

    @PutMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<PostIdResponseDTO> updatePost(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @PathVariable(name = "postId") Long postId,
            @RequestBody WritePostDTO writePost
    ) {
        return ResponseEntity.ok(communityService.updatePost(authUser, postId, writePost));
    }

    @DeleteMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<PostIdResponseDTO> deletePost(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @PathVariable(name = "postId") Long postId
    ) {
        return ResponseEntity.ok(communityService.deletePost(authUser, postId));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<SimplePostResponseDTO> getSimplePosts(
            @PathVariable(name = "tag") String tag,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestParam(name = "pageNum") Integer pageNum) {
        return ResponseEntity.ok(communityService.getTagPosts(authUser, tag, pageNum));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> readPost(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(communityService.readPost(authUser, postId));
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<SimplePostResponseDTO> searchPost(
            @PathVariable String keyword,
            @RequestParam(name = "pageNum") Integer pageNum,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        return ResponseEntity.ok(communityService.searchPosts(authUser, keyword, pageNum));
    }


    @PostMapping("/comment")
    public ResponseEntity<CommentIdResponseDTO> writeComment(
            @RequestBody WriteCommentDTO writeCommentDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(communityService.writeComment(customUserDetails.getUser(), writeCommentDTO));
    }


    @PutMapping("/comment/{commentId}")
    @ResponseBody
    public ResponseEntity<CommentIdResponseDTO> updateComment(
            @PathVariable Long commentId,
            @RequestBody WriteCommentDTO writeCommentDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(communityService.updateComment(customUserDetails.getUser(), commentId, writeCommentDTO));
    }


    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<CommentIdResponseDTO> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(communityService.deleteComment(customUserDetails.getUser(), commentId));
    }


}

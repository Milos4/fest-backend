package com.festapp.FestApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.festapp.FestApplication.dto.ReactionRequestDTO;
import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.Reaction;
import com.festapp.FestApplication.models.Reaction.ReactionType;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.service.PostService;
import com.festapp.FestApplication.service.ReactionService;
import com.festapp.FestApplication.service.UserService;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

	private final ReactionService reactionService;
	private final UserService userService;
	private final PostService postService;

	@Autowired
	public ReactionController(ReactionService reactionService, UserService userService, PostService postService) {
		this.reactionService = reactionService;
		this.userService = userService;
		this.postService = postService;

	}

	@PostMapping("/add")
	public Reaction addReaction(@RequestBody ReactionRequestDTO reactionRequest) {
	    User user = userService.getUserById(reactionRequest.getUserId());
	    Post post = postService.getPostById(reactionRequest.getPostId());
	    
	    ReactionType reactionType = ReactionType.valueOf(reactionRequest.getType());

	    return reactionService.addReaction(reactionType, user, post);
	}

	@DeleteMapping("/remove")
	public void removeReaction(@RequestParam Long reactionId) {
		Reaction reaction = reactionService.getReactionById(reactionId);
		reactionService.removeReaction(reaction);
	}
}
package com.festapp.FestApplication.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.festapp.FestApplication.dto.CommentDTO;
import com.festapp.FestApplication.models.Comment;
import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.CommentRepository;
import com.festapp.FestApplication.repository.PostRepository;
import com.festapp.FestApplication.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
			UserRepository userRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<CommentDTO> getCommentsByPostId(Long postId) {
		return commentRepository.findByPostId(postId).stream().map(CommentDTO::new).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Comment createComment(Long userId, Long postId, CommentDTO commentDTO) {
		// Proverite da li korisnik postoji
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// Proverite da li post postoji
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

		// Kreirajte novi komentar
		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		comment.setUser(user); // Povezivanje komentara sa korisnikom
		comment.setPost(post); // Povezivanje komentara sa postom

		// Spasite komentar u bazi
		return commentRepository.save(comment);
	}

	@Override
	@Transactional
	public void deleteComment(Long userId, Long commentId) {
		// Pronalazimo komentar po ID-u
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		// Proveravamo da li korisnik koji pokušava da obriše komentar jeste autor
		if (!comment.getUser().getId().equals(userId)) {
			throw new RuntimeException("You are not the author of this comment!");
		}

		commentRepository.delete(comment);
	}

	@Override
	@Transactional
	public Comment updateComment(Long userId, Long commentId, String newContent) {
		// Pronalazimo komentar po ID-u
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		// Proveravamo da li korisnik koji pokušava da menja komentar jeste autor
		if (!comment.getUser().getId().equals(userId)) {
			throw new RuntimeException("You are not the author of this comment!");
		}

		// Postavljamo novi sadržaj komentara
		comment.setContent(newContent);

		// Sačuvamo izmenjeni komentar
		return commentRepository.save(comment);
	}
}
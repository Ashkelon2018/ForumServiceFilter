package telran.ashkelon2018.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DatePeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;
import telran.ashkelon2018.forum.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController {
	@Autowired
	ForumService service;

	@PostMapping("/post")
	public Post addPost(@RequestBody NewPostDto newPost) {
		return service.addNewPost(newPost);
	}

	@GetMapping("/post/{id}")
	public Post getPost(@PathVariable String id) {
		return service.getPost(id);
	}

	@DeleteMapping("/post/{id}")
	public Post removePost(@PathVariable String id, @RequestHeader("Authorization")
			String token) {
		return service.removePost(id, token);
	}

	@PutMapping("/post")
	public Post updatePost(@RequestBody PostUpdateDto postUpdateDto,
			@RequestHeader("Authorization") String token) {
		return service.updatePost(postUpdateDto, token);
	}

	@PutMapping("/post/{id}/like")
	public boolean addLike(@PathVariable String id) {
		return service.addLike(id);
	}

	@PutMapping("/post/{id}/comment")
	public Post addComment(@PathVariable String id,
			@RequestBody NewCommentDto newCommentDto) {
		return service.addComment(id, newCommentDto);
	}

	@PostMapping("/posts/tags")
	public Iterable<Post> getPostsByTags(@RequestBody List<String> tags) {
		return service.findPostsByTags(tags);
	}

	@GetMapping("/posts/author/{author}")
	public Iterable<Post> getPostsByAuthor(@PathVariable String author) {
		return service.findPostsByAuthor(author);
	}

	@PostMapping("/posts/period")
	public Iterable<Post> getPostsBetweenDate(@RequestBody DatePeriodDto periodDto) {
		return service.findPostsByDates(periodDto);
	}

}

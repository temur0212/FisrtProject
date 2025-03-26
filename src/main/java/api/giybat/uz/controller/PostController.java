package api.giybat.uz.controller;

import api.giybat.uz.dto.FilterResultDTO;
import api.giybat.uz.dto.Post.PostCreateDTO;
import api.giybat.uz.dto.Post.PostDTO;
import api.giybat.uz.dto.Post.PostFilterDTO;
import api.giybat.uz.entity.PostEntity;
import api.giybat.uz.service.PostService;
import api.giybat.uz.util.SpringSecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    private ResponseEntity<PostDTO> createPost(@Valid  @RequestBody PostCreateDTO dto) {
        return ResponseEntity.ok(postService.createPost(dto));
    }

    // Bitta foydalanuvchiga ega barcha postlarni chiqarish uchun
    @GetMapping("/profile")
    private ResponseEntity<List<PostDTO>> getProfilePostList() {
        return ResponseEntity.ok(postService.getProfilePostList());
    }

    // Bitta postni ochish uchun
    @GetMapping("/public/{id}")
    private ResponseEntity<PostDTO> getPost(@PathVariable("id") String id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<PostDTO> updatePost(@PathVariable("id") String id, @Valid @RequestBody PostCreateDTO dto) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    @DeleteMapping("delete/{id}")
    private ResponseEntity<String> deleteMessage(@PathVariable("id") String id){
        return ResponseEntity.ok( postService.deletePost(id));
    }

    @PostMapping("/public/filter")
    private ResponseEntity<Page<PostDTO>> createPost(@Valid  @RequestBody PostFilterDTO dto,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(postService.filter(dto,page-1,size));
    }



    


}

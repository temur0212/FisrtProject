package api.giybat.uz.service;

import api.giybat.uz.dto.FilterResultDTO;
import api.giybat.uz.dto.Post.PostCreateDTO;
import api.giybat.uz.dto.Post.PostDTO;
import api.giybat.uz.dto.Post.PostFilterDTO;
import api.giybat.uz.entity.PostEntity;
import api.giybat.uz.enums.ProfileRoles;
import api.giybat.uz.exps.AppBadExseption;
import api.giybat.uz.repozitory.CustomRepository;
import api.giybat.uz.repozitory.PostRepository;
import api.giybat.uz.util.SpringSecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static api.giybat.uz.util.SpringSecurityUtil.getCurrentUserId;

@Service
public class PostService {
     @Autowired
    private PostRepository postRepository;

     @Autowired
     private AttachService attachService;

     @Autowired
        private CustomRepository customRepository;

     public PostDTO createPost(PostCreateDTO dto) {
         PostEntity postEntity = new PostEntity();
            postEntity.setTitle(dto.getTitle());
            postEntity.setContent(dto.getContent());
            postEntity.setPhotoId(dto.getPhoto().getId());
            postEntity.setVisible(true);
            postEntity.setCreatedDate(LocalDate.now());
            postEntity.setUserId(getCurrentUserId());
            postRepository.save(postEntity);
            return convertToDTO(postEntity);
     }

     public List<PostDTO> getProfilePostList() {
         Long userId = getCurrentUserId();
        List<PostEntity> postEntities = postRepository.findAllByUserIdAndVisibleTrue(userId);
        List<PostDTO> postDTOS = postEntities
                .stream()
                .map(this::toAllToDTO)
                .toList();
        return postDTOS;}

    public PostDTO getPost(String id) {
        PostEntity postEntity = get(id);
        return convertToDTO(postEntity);
    }

    private PostDTO convertToDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContent(postEntity.getContent());
        postDTO.setPhoto(attachService.attachDTO(postEntity.getPhotoId()));
        postDTO.setCreatedDate(postEntity.getCreatedDate());
        return postDTO;
    }

    private PostDTO toAllToDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setPhoto(attachService.attachDTO(postEntity.getPhotoId()));
        postDTO.setCreatedDate(postEntity.getCreatedDate());
        return postDTO;
    }

    public PostDTO updatePost(String id, @Valid PostCreateDTO dto) {
        PostEntity postEntity = get(id);
        Long userId = getCurrentUserId();
        if (!SpringSecurityUtil.hasRole(ProfileRoles.ROLE_ADMIN) || !postEntity.getUserId().equals(userId)) {
                throw new AppBadExseption("You are not allowed to update this post");
        }
        if (postEntity.getVisible().equals(false)){
            throw new AppBadExseption("Post not exist");
        }

        String oldPhotoId = null;
        if (!dto.getPhoto().getId().equals(postEntity.getPhotoId())) {
            oldPhotoId = dto.getPhoto().getId();
        }
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        postEntity.setPhotoId(dto.getPhoto().getId());
        postRepository.save(postEntity);
        if (oldPhotoId != null) {
            attachService.delete(oldPhotoId);
        }
        return convertToDTO(postEntity);
    }

    public Page<PostDTO> filter(PostFilterDTO dto , int page, int size) {
         FilterResultDTO<PostEntity> resultDTO = customRepository.filter(dto, page, size);
         List<PostDTO> postDTOS = resultDTO.getData()
                 .stream()
                 .map(this::toAllToDTO)
                 .toList();
            return new PageImpl<>(postDTOS, PageRequest.of(page, size), resultDTO.getTotalElements());

    }
    public String deletePost(String id) {
        Long userId = getCurrentUserId();
        long dbUser = postRepository.findUserIdByPostId(id);

        if (!SpringSecurityUtil.hasRole(ProfileRoles.ROLE_ADMIN) || !userId.equals(dbUser)) {
            throw new AppBadExseption("You are not allowed to delete this post");
        }
        postRepository.vizibility(id);
        return "Successufuly deleted";
    }

    private PostEntity get(String id) {
        return postRepository.findById(id).orElseThrow(() -> new AppBadExseption("Post not found"));
    }


}

package _Blog.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.models.Entity.Media;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.repository.MediaRepo;

@Service
public class MediaService {
    @Autowired
    private MediaRepo mediaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    public void create(Post post, MultipartFile mediaFile) {
        String mediaType = fileStorageService.getTypeOfFile(mediaFile);
        System.out.println("Detected media type: " + mediaType);
        if (!mediaType.equals("image") && !mediaType.equals("video")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported media type");
        }
        String mediaUrl = fileStorageService.uploadFile(mediaFile, mediaType, post.getId());

        Media mediaEntity = Media.toMediaEntity(post, mediaUrl, mediaType);
        if (mediaEntity == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot create media entity");
        }
        mediaRepo.save(mediaEntity);
    }

    public List<Media> getMediaByPostId(Long postId) {
        return mediaRepo.findAllByPostId(postId);
    }
}

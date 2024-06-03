package bside.meme.image;

import bside.meme.content.Content;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Value("${image.upload.path}")
    private String uploadPath; // application.properties에 설정된 업로드 경로
    @Value(("${meme.project.host}"))
    private String basicURL;
    public Image uploadImage(MultipartFile file) {
        try {

            // 이미지를 저장하고 관련 정보를 설정
            String originalFilename = file.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(originalFilename);
            String newName = UUID.randomUUID() + "." + extension;

            Path filePath = Paths.get(uploadPath, newName);

            String url = basicURL + ":8080/images/" + newName; // 이미지를 저장할 경로
            Files.copy(file.getInputStream(), filePath);

            Image image = new Image();
            image.setOriName(originalFilename);
            image.setNewName(newName);
            image.setExtension(extension);
            image.setUrl(url);

            return imageRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public void assignImageToContent(Long imageId, Content content) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        image.setContent(content);
        imageRepository.save(image);
    }
}

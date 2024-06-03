package bside.meme.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;
    @PostMapping
    public ResponseEntity<Content> createContent(@RequestBody Content content, Long id) {

        return ResponseEntity.ok(contentService.createContent(content, id));
    }

    @GetMapping
    public Page<Content> getAllContents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return contentService.getAllContents(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentById(@PathVariable Long id) {
        return contentService.getContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content contentDetails) {
        return ResponseEntity.ok(contentService.updateContent(id, contentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Content>> searchContents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Content> result;

        if (title != null && !title.isEmpty() && tags != null && !tags.isEmpty()) {
            result = contentService.searchContentsByTitleAndTagsPaginated(title, tags, page, size);
        } else if (title != null && !title.isEmpty()) {
            result = contentService.searchContentsByTitlePaginated(title, page, size);
        } else if (tags != null && !tags.isEmpty()) {
            result = contentService.searchContentsByTagsPaginated(tags, page, size);
        } else if (category != null) {
            result = contentService.searchContentsByCategoryPaginated(category, page, size);
        } else {
            result = contentService.getContentsPaginated(page, size);
        }

        return ResponseEntity.ok(result);
    }

//    @GetMapping("/main")
//    public ResponseEntity<Page<ContentDTO>> getMainContents(@RequestParam(defaultValue = "0") int page,
//                                                            @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<ContentDTO> contentDTOPage = contentService.getPaginatedContents(pageable);
//        return ResponseEntity.ok(contentDTOPage);
//    }
}

package bside.meme.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long>, JpaSpecificationExecutor<Content> {
    Page<Content> findByTitleContaining(String title, Pageable pageable);
    Page<Content> findByTitleContainingAndTagsIn(String title, List<String> tags, Pageable pageable);
    Page<Content> findByCategory(Category category, Pageable pageable);
}
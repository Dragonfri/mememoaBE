package bside.meme.content;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;

public class ContentSpecification {
    public static Specification<Content> hasTags(List<String> tags) {
        return (root, query, criteriaBuilder) -> {
            ListJoin<Content, String> tagJoin = root.joinList("Tags", JoinType.INNER);
            Predicate[] predicates = tags.stream()
                    .map(tag -> criteriaBuilder.equal(tagJoin, tag))
                    .toArray(Predicate[]::new);
            return criteriaBuilder.and(predicates);
        };
    }
}

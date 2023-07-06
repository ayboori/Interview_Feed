package com.tenminute.interview_feed.specification;

import com.tenminute.interview_feed.entity.Post;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostSpecification {
    public Specification<Post> hasNameContainingAll(List<String> names) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String name : names) {
                predicates.add(builder.isMember(name, root.get("name")));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package api.giybat.uz.repozitory;

import api.giybat.uz.dto.FilterResultDTO;
import api.giybat.uz.dto.Post.PostFilterDTO;
import api.giybat.uz.entity.PostEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<PostEntity> filter(PostFilterDTO dto, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder(" where p.visible = true ");
        Map<String, Object> params = new HashMap<>();

        if (dto.getSearch() != null && !dto.getSearch().trim().isEmpty()) {
            queryBuilder.append(" and lower(p.title) like :title ");
            params.put("title", "%" + dto.getSearch().toLowerCase() + "%");
        }

        // ORDER BY faqat selectQuery uchun kerak!
        String selectQueryStr = "Select p from PostEntity p " + queryBuilder + " order by p.createdDate desc";

        // COUNT query uchun ORDER BY olib tashlangan!
        String countQueryStr = "Select count(p) from PostEntity p " + queryBuilder;

        Query selectQuery = entityManager.createQuery(selectQueryStr);
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        List postEntities = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(countQueryStr);
        params.forEach(countQuery::setParameter);
        Long count = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<>(postEntities, count);
    }
}



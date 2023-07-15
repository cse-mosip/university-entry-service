package com.cse19.ue.repository;

import com.cse19.ue.dto.EntryRecord;
import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.model.UniversityEntryLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
public class EntranceLogRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EntranceRecordsResponse filterEntranceLogs(
            String index,
            String faculty,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            int skip,
            int take) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT e FROM UniversityEntryLog e");

        List<String> whereClause = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();


        if (index != null) {
            whereClause.add("e.index = :index");
            parameters.put("index", index);
        }

//        if (faculty != null) {
//            whereClause.add("faculty = :faculty");
//            parameters.put("faculty", faculty);
//        }

        if (fromDate != null) {
            whereClause.add("e.timestamp >= :fromDate");
            parameters.put("fromDate", fromDate);
        }

        if (toDate != null) {
            whereClause.add("e.timestamp <= :toDate");
            parameters.put("toDate", toDate);
        }

        if (whereClause.size() > 0) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", whereClause));
        }

//        queryBuilder.append(" LIMIT :take");
//        queryBuilder.append(" OFFSET :skip");
//        parameters.put("skip", String.valueOf(skip));
//        parameters.put("take", String.valueOf(take));
//
//
//        String dataQuery = queryBuilder.toString();
//        String countQuery = getCountQuery(dataQuery);
//        log.info("Data Query: {}", dataQuery);
//        log.info("Count Query: {}", countQuery);
//
//        TypedQuery<UniversityEntryLog> typedDataQuery = entityManager
//                .createQuery(dataQuery, UniversityEntryLog.class);
//        TypedQuery<UniversityEntryLog> typedCountQuery = entityManager
//                .createQuery(countQuery, UniversityEntryLog.class);
//
        Query typedDataQuery = entityManager.createQuery(queryBuilder.toString());
        Query typedCountQuery = entityManager.createQuery(getCountQuery(queryBuilder.toString()));



        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            typedDataQuery.setParameter(entry.getKey(), entry.getValue());
            typedCountQuery.setParameter(entry.getKey(), entry.getValue());
        }

        typedDataQuery.setFirstResult(skip);
        typedDataQuery.setMaxResults(take);


        List<UniversityEntryLog> filteredData = typedDataQuery.getResultList();
        Long totalRecordsCount = (Long) typedCountQuery.getSingleResult();

        List<EntryRecord> entryRecordList = new ArrayList<>();
        for(UniversityEntryLog log: filteredData)
            entryRecordList.add(new EntryRecord(log));



        return EntranceRecordsResponse
                .builder()
                .records(entryRecordList)
                .totalCount(totalRecordsCount.intValue())
                .skip(skip)
                .take(take)
                .build();
    }


    public String getCountQuery(String query) {
        int fromIndex = query.toLowerCase().indexOf("from");
        return "SELECT COUNT(e.id) " + query.substring(fromIndex);
    }
}

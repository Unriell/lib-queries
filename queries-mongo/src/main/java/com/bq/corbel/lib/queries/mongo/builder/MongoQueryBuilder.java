package com.bq.corbel.lib.queries.mongo.builder;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Query;

import com.bq.corbel.lib.queries.builder.QueryBuilder;
import com.bq.corbel.lib.queries.request.Pagination;
import com.bq.corbel.lib.queries.request.ResourceQuery;

/**
 * @author Alberto J. Rubio
 */
public class MongoQueryBuilder implements QueryBuilder {

    protected final Query query;

    public MongoQueryBuilder() {
        query = new Query();
    }

    @Override
    public QueryBuilder query(ResourceQuery resourceQuery) {
        if (resourceQuery != null) {
            query.addCriteria(CriteriaBuilder.buildFromResourceQuery(resourceQuery));
        }
        return this;
    }

    @Override
    public QueryBuilder query(List<ResourceQuery> resourceQueries) {
        if (resourceQueries != null && !resourceQueries.isEmpty()) {
            query.addCriteria(CriteriaBuilder.buildFromResourceQueries(resourceQueries));
        }
        return this;
    }

    @Override
    public QueryBuilder pagination(Pagination pagination) {
        if (pagination != null) {
            query.with(new PageRequest(pagination.getPage(), pagination.getPageSize()));
        }
        return this;
    }

    @Override
    public QueryBuilder sort(com.bq.corbel.lib.queries.request.Sort sort) {
        if (sort != null) {
            query.with(new Sort(Direction.fromString(sort.getDirection().name()), sort.getField()));
        }
        return this;
    }

    @Override
    public Query build() {
        return query;
    }

}

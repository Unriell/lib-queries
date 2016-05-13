package com.bq.corbel.lib.queries.parser;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.bq.corbel.lib.queries.exception.MalformedJsonQueryException;
import com.bq.corbel.lib.queries.request.Sort;

/**
 * @author Alexander De Leon
 * 
 */
public class JacksonSortParserTest {

    JacksonSortParser parser = new JacksonSortParser(new CustomJsonParser(new ObjectMapper().getFactory()));

    @Test(expected = MalformedJsonQueryException.class)
    public void testInvalidJson() throws MalformedJsonQueryException {
        parser.parse("no a json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotTheExpectedJson() throws MalformedJsonQueryException {
        parser.parse("[1]");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotMoreThanOneField() throws MalformedJsonQueryException {
        parser.parse("{\"a\":\"asc\", \"b\":\"desc\"}");
    }

    @Test
    public void testParseAsc() throws MalformedJsonQueryException {
        Sort sort = parser.parse("{\"a\":\"asc\"}");
        assertThat(sort.getField()).isEqualTo("a");
        assertThat(sort.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    public void testParseDesc() throws MalformedJsonQueryException {
        Sort sort = parser.parse("{\"a\":\"desc\"}");
        assertThat(sort.getField()).isEqualTo("a");
        assertThat(sort.getDirection()).isEqualTo(Sort.Direction.DESC);
    }
}

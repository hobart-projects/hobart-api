package io.github.hobart.api;

import io.github.hobart.api.enums.ResultCodeEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ResultsTests {

    private Logger logger = LoggerFactory.getLogger(ResultsTests.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void success() {
        Result<Void> result = Results.success();
        Assertions.assertEquals(ResultCodeEnum.SUCCESS.getCode(), result.getCode());
        Assertions.assertNull(result.getData());
    }

    @Test
    void testSuccess() {
        PageResult<Object> pageResult = Results.emptyPage();
        logger.info(pageResult.toString());
        Assertions.assertEquals(0, pageResult.getData().getTotalPage());
    }

    @Test
    void testSuccess1() {
    }

    @Test
    void testSuccess2() {
    }

    @Test
    void testSuccess3() {
    }

    @Test
    void failure() {
    }

    @Test
    void testFailure() {
    }

    @Test
    void testFailure1() {
    }

    @Test
    void testFailure2() {
    }

    @Test
    void testFailure3() {
    }

    @Test
    void testFailure4() {
    }

    @Test
    void result() {
    }

    @Test
    void of() {
    }
}
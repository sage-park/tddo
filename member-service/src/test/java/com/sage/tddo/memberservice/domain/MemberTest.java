package com.sage.tddo.memberservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    /**
     * check argument
     */
    @Test
    void checkArg() {

        Exception exception = catchException(() -> new Member(null, "김구"));
        Assertions.assertThat(exception).isInstanceOf(IllegalArgumentException.class);


        Exception exception02 = catchException(() -> new Member("user01", null));
        Assertions.assertThat(exception02).isInstanceOf(IllegalArgumentException.class);

    }

}

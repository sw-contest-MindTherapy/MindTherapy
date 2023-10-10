package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="entity not found")
public class DataNotFoundException extends RuntimeException{
    private static final long serialVersionUID=1L;
    public DataNotFoundException(String message) {
        super(message);
    }
}
/*RuntimeException을 상속하여 만듬. 만약 DataNoutFoundException이 발생하면 404오류가 나타날 것이다.
* */

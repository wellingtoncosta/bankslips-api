package br.com.wellingtoncosta.bankslipsapi.web.json;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Wellington Costa on 17/11/18
 */
@Data @Builder public class ExceptionJson {

    private Date timestamp;
    private int status;
    private String error;
    private String message;

}

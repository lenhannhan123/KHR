package fpt.aptech.KHR.ImpServices;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonServices {


    public static ResponseEntity dd(List list) {

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

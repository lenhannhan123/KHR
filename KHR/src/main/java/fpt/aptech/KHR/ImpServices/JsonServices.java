package fpt.aptech.KHR.ImpServices;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import antlr.collections.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public class JsonServices {


    public static void dd(String value, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(value);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dd(int value, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(value);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dd(boolean value, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(value);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dd(double value, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(value);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String ParseToJson(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(value);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


}

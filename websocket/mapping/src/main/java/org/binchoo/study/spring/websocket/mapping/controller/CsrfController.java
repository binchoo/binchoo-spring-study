package org.binchoo.study.spring.websocket.mapping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * packageName : org.binchoo.study.spring.websocket.mapping.controller
 * fileName : CsrfController
 * author : wnwoq
 * date : 2021-12-21
 * description : CSRF 토큰을 제공하는 컨트롤러
 */

@RestController
public class CsrfController {

    final static String CSRF_ATTRIBUTE_NAME = "_csrf";

    @GetMapping("/csrf")
    public ResponseEntity getOrCreateCsrfToken(HttpServletRequest request) {
        final CsrfToken token = (CsrfToken) request.getAttribute(CSRF_ATTRIBUTE_NAME);
        return (token != null)? ResponseEntity.ok(token) : ResponseEntity.notFound().build();
    }
}

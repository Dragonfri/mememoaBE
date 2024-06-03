package bside.meme.kakao;

import bside.meme.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "유저 API")
public class KakaoController {

    private final KakaoService kakaoService;
    //web 버전
    @ResponseBody
    @GetMapping("/login/oauth/kakao")
    @Operation(summary = "웹 카카오 로그인", description = "웹 프론트 버전 카카오 로그인")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code, HttpServletRequest request) {
        try {
            // 현재 도메인 확인
            String currentDomain = request.getServerName();
            return ResponseEntity.ok(kakaoService.kakaoLogin(code, currentDomain));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }
}
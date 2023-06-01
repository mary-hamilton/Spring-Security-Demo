package com.example.springjpa.resources;

import com.example.springjpa.domain.Member;
import com.example.springjpa.domain.dto.AuthSuccessDto;
import com.example.springjpa.domain.dto.MemberDto;
import com.example.springjpa.service.MemberService;
import com.example.springjpa.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final TokenService tokenService;
    private final MemberService memberService;

    public AuthResource(TokenService tokenService, MemberService memberService) {
        this.tokenService = tokenService;
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthSuccessDto> login(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        Member member = memberService.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        AuthSuccessDto authSuccessDto = new AuthSuccessDto(token, member.getUsername());
        return new ResponseEntity<>(authSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthSuccessDto> signup(@Valid @RequestBody MemberDto memberDto) {
        AuthSuccessDto authSuccessDto = memberService.signup(memberDto);
        return new ResponseEntity<>(authSuccessDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/admins-only")
    public String adminOnly() {
        return "Hello admin";
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/users-only")
    public String userOnly() {
        return "Hello user";
    }
}

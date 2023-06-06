
package com.mon.Chatserver.config;
import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
public class JwtTokenValidator  extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
                String jwt=request.getHeader("Authorization");
                if(jwt!=null){
                    try{
                     jwt=jwt.substring(7);
                     SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                     Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();
                     String username=String.valueOf(claims.get("username"));
                     String authorities=String.valueOf(claims.get("authorities"));
                     List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                      Authentication authenticated=new UsernamePasswordAuthenticationToken(username,null,auths);
                     SecurityContextHolder.getContext().setAuthentication(authenticated);
                    }catch(Exception ex){
                           throw new BadCredentialsException("Invalid token received.");
                    }

                    filterChain.doFilter(request, response);
                }

    }

}
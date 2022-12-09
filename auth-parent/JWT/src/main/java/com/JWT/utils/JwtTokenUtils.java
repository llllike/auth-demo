package com.JWT.utils;

import cn.hutool.core.io.FileUtil;
import com.JWT.entity.Token;
import com.JWT.entity.UserInfo;
import com.JWT.properties.JWTProperties;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@AllArgsConstructor
@Slf4j
public class JwtTokenUtils {
    private JWTProperties jwtProperties;

    /**
     * 生成token
     */
    public Token generateUserToken(UserInfo userInfo) throws Exception {
        JWTProperties.TokenInfo user = jwtProperties.getUser();
        String priKey = user.getPriKey();
        priKey=priKey+"-"+userInfo.getId()+".key";
        String pubKey = user.getPubKey();
        pubKey=pubKey+"-"+userInfo.getId()+".key";
        if (user.getExpire() == null || user.getExpire() <= 0) {
            user.setExpire(7200);
        }
        if (user.getKey()==null){
            user.setKey(RandomStringUtils.randomAlphanumeric(10));
        }
        if (userInfo.getId()==null){
            userInfo.setId(String.valueOf(Thread.currentThread().getId()));
        }
        if (Objects.equals(user.getAlg(), "HS256")){
            HashMap header = new HashMap();
            header.put("alg", SignatureAlgorithm.HS256.getValue());
            header.put("typ","JWT");

            HashMap body = new HashMap();
            body.put("msg",userInfo.getT());

            String compact = Jwts.builder()
                    .setExpiration(DateUtils.localDateTime2Date(LocalDateTime.now()
                            .plusSeconds(user.getExpire())))
                    .setHeader(header).setClaims(body)
                    .setId(userInfo.getId())
                    .signWith(SignatureAlgorithm.HS256,user.getKey()).compact();
            return new Token(compact,user.getExpire());
        }
        if (Objects.equals(user.getAlg(), "RS256") ||user.getAlg()==null|| Objects.equals(user.getAlg(), "")){
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom(user.getKey().getBytes());
            keyPairGenerator.initialize(1024, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();

            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            String path=this.getClass().getResource("").getPath();
            FileUtil.writeBytes(publicKeyBytes,path+pubKey);
            FileUtil.writeBytes(privateKeyBytes,path+priKey);
            log.debug("path+pubKey={}",path+pubKey);
            log.debug("path+priKey={}",path+priKey);
            HashMap header = new HashMap();
            header.put("alg", SignatureAlgorithm.RS256.getValue());
            header.put("typ","JWT");

            HashMap body = new HashMap();
            body.put("msg",userInfo.getT());

            String compact = Jwts.builder()
                    .setExpiration(DateUtils.localDateTime2Date(LocalDateTime.now().plusSeconds(user.getExpire())))
                    .setHeader(header).setClaims(body).setId(userInfo.getId())
                    .signWith(SignatureAlgorithm.RS256,getPriKey(priKey)).
                    compact();
            return new Token(compact,user.getExpire());
        }
        return null;
    }

    //获取密钥
    public PrivateKey getPriKey(String key) throws Exception{
        byte[] keyBytes = getBytes(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
    public PublicKey getPubKey(String key) throws Exception{
        byte[] keyBytes = getBytes(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
    public byte[] getBytes(String key) throws Exception{
        String path=this.getClass().getResource("").getPath();
        log.debug("path + key={}",path + key);
        File file = new File(path + key);
        FileInputStream fis = new FileInputStream(file);
        byte[] keyBytes = new byte[Math.toIntExact(file.length())];
        fis.read(keyBytes);
        return keyBytes;
    }
    /**
     * 解析token
     */
    public UserInfo getUserInfo(String token,String id) throws Exception {
        JWTProperties.TokenInfo user = jwtProperties.getUser();
        if (Objects.equals(user.getAlg(), "HS256")){
            Jwt parse = Jwts.parser().setSigningKey(user.getKey()).parse(token);
            Map body = (Map) parse.getBody();
            return new UserInfo(body.get("msg"),id);
        }else {
            Jwt parse = Jwts.parser().setSigningKey(getPubKey("keys/pub-"+id+".key")).parse(token);
            Map body = (Map) parse.getBody();
            return new UserInfo(body.get("msg"),id);
        }
    }
}

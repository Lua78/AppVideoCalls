package com.app.video.services;
import io.agora.media.RtcTokenBuilder2;
import org.springframework.stereotype.Service;

@Service
public class AgoraService {
    private final String appId = "c2494836b73e4cf89036741de30f8b93";
    private final String appCertificate = "914b60bf3689494592b9885e1ad3766a";

    public String generateToken(String channelName, String userId) {
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        int tokenExpirationInSeconds = 3600; // Token válido por 1 hora
        int privilegeExpirationInSeconds = 3600;

        return tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, Integer.parseInt(userId),
                RtcTokenBuilder2.Role.ROLE_PUBLISHER, tokenExpirationInSeconds, privilegeExpirationInSeconds);
    }
}
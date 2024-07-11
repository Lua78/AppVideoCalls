package com.app.video.services;
import org.springframework.stereotype.Service;
import com.app.video.media.RtcTokenBuilder;

import java.util.Random;


@Service
public class AgoraService {
    private static final Random RANDOM = new Random();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
    private static final int LENGHT = 6;
    public String generateToken(String channelName, String userId) {
        RtcTokenBuilder tokenBuilder = new RtcTokenBuilder();
        int expireTimestamp = (int) (System.currentTimeMillis() / 1000) + 3600;
        String appCertificate = "dc0fef5bf0424784b8f74b5a8d6b91d0";
        String appId = "c2494836b73e4cf89036741de30f8b93";
        return tokenBuilder.buildTokenWithUid(appId, appCertificate, channelName, Integer.parseInt(userId),
                RtcTokenBuilder.Role.Role_Publisher, expireTimestamp);
    }

    public String generateRandomChannelName() {
        StringBuilder sb = new StringBuilder(LENGHT);
        for (int i = 0; i < LENGHT; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
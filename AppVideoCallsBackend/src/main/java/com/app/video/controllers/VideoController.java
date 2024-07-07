package com.app.video.controllers;

import com.app.video.services.AgoraService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class VideoController {
    AgoraService agoraService;
    public VideoController(AgoraService agoraService) {
        this.agoraService = agoraService;
    }
    @PostMapping("/create")
    public Map<String, String> createRoom(@RequestParam("id") String id){
        Map<String, String> map = new HashMap<String, String>();
        String channelName = UUID.randomUUID().toString();
        String token = agoraService.generateToken(channelName,id);
        map.put("token", token);
        map.put("channelName", channelName);
        return map;
    }
    @PostMapping("/join")
    public Map<String, String> JoinRoom(@RequestParam("id") String id, @RequestParam("channel") String channel){
        Map<String, String> map = new HashMap<String, String>();
        String token = agoraService.generateToken(channel, id);
        map.put("token", token);
        return map;
    }
}

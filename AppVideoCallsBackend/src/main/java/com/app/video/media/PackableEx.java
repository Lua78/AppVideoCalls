package com.app.video.media;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}

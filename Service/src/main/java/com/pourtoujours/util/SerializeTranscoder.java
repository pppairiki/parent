package com.pourtoujours.util;

public interface SerializeTranscoder {

    public byte[] serialize(Object value);
    public Object deserialize(byte[] in);

}

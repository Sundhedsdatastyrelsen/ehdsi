﻿package dk.sds.ncp.cda.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@EqualsAndHashCode(callSuper = true)
@Value
@With
@SuperBuilder
public class EPrescriptionL1 extends EPrescriptionBase {

    @NonNull String base64EncodedDocument;
    @Override
    public String GetHash()  {
        return Utils.Md5Hash(base64EncodedDocument);
    }

    @Override
    public Long GetSize() {
        var bytes = Base64.getDecoder().decode(base64EncodedDocument);
        return (long) bytes.length;
    }
}
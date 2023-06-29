package it.unimib.finalproject.server.utils;

import it.unimib.finalproject.server.DB.QueryResolution;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class MiscellaneousUtilities {

    // this method is for better legibility
    public static Response forwardResponse(QueryResolution res){
        if(!res.isError())
            return Response
                    .ok(res.message())
                    .build();
        else
            return Response
                    .status(res.error())
                    .build();
    }

    public static String generate(int length) {
        return  UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, length);
    }

}

package it.unimib.finalproject.server.utils;

import it.unimib.finalproject.server.DB.QueryResolution;
import jakarta.ws.rs.core.Response;

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
}

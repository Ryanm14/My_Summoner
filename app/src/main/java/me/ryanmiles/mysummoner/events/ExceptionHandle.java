package me.ryanmiles.mysummoner.events;

import com.robrua.orianna.type.exception.APIException;
import com.robrua.orianna.type.exception.OriannaException;

/**
 * Created by ryanm on 12/23/2015.
 */
public class ExceptionHandle {
    private OriannaException mException;
    private String mString;

    public String getErrorString() {
        return mString;
    }

    public ExceptionHandle(APIException s) {
        mException = s;
        switch(s.getStatus()){
            case NOT_FOUND:
                mString = "Summoner Not Found (Check Name)";
                break;
            case RATE_LIMIT_EXCEEDED:
                mString = "Rate Limit Exceed (Try Again Later)";
                break;
            case SERVICE_UNAVAILABLE:
                mString = "Riot Api Service Down (Try Again Later)";
                break;
            case INTERNAL_SERVER_ERROR:
                mString = "Riot Api Error (Try Again Later)";
                break;
            default: mString = s.getStatus().toString();
                break;
        }

    }

}

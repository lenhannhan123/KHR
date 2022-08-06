package fpt.aptech.khrmobile.ServicesImp;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Services.TokenServices;
import fpt.aptech.khrmobile.UtilAPI.RetrofitClient;

public class TokenUtilAPI {
    public static final String url="http://"+ ConfigData.IP +":7777/api/";
    public static TokenServices getTokenServices(){
        return RetrofitClient.getClient(url).create(TokenServices.class);
    }
}

package fpt.aptech.khrmobile.ServicesImp;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Services.DayOffServices;
import fpt.aptech.khrmobile.UtilAPI.RetrofitClient;

public class DayOffUtillAPI {
    public static final String url="http://"+ ConfigData.IP +":7777/api/";
    public static DayOffServices getAccountDayoff(){
        return RetrofitClient.getClient(url).create(DayOffServices.class);
    }
}

package fpt.aptech.khrmobile.ServicesImp;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Services.AccountNotifactionServices;
import fpt.aptech.khrmobile.UtilAPI.RetrofitClient;

public class NotificationUtilAPI {
    public static final String url="http://"+ ConfigData.IP +":7777/api/";
    public static AccountNotifactionServices getAccountNotifacationServices(){
        return RetrofitClient.getClient(url).create(AccountNotifactionServices.class);
    }
}

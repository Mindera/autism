package org.mindera.autism.web.udb;


import com.mindera.udb.client.UdbAppKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component("UdbAppKeyProvider")
public class UdbAppKeyProviderImpl implements UdbAppKeyProvider {


    @Value("${udb.client.appKey}")
    private String  appKey;

    @Override
    public String getAppKey() {
        return appKey;
    }
}

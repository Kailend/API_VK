package core;

import com.google.gson.Gson;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.events.TestCaseEvent;
import ru.yandex.qatools.allure.model.Parameter;
import ru.yandex.qatools.allure.model.ParameterKind;
import ru.yandex.qatools.allure.model.TestCaseResult;
import users.User;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kailend on 14.12.2016.
 */
public class Metods {

    public Logger logger = LoggerFactory.getLogger(this.getClass());
    public OkHttpClient client = new OkHttpClient();
    public static final MediaType TYPE = MediaType.parse("multipart/form-data");
    public Gson gson=new Gson();
    public final String TOKEN="access_token=5416bafec0ac04fe89b418877f46fd0d37cda27ff50459681ea7cf8889823af381df3edf74f3c26783b65&expires_in=0&user_id=364454674";


   public ClassLoader classLoader =getClass().getClassLoader();

    public String takeServeForPhoto(int albom,String token) throws IOException {
        String askAboutServerToFoto="https://api.vk.com/method/photos.getUploadServer?album_id="+albom+"&v=5.52&";
        Request request = new Request.Builder().url(askAboutServerToFoto+token).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        str =str.substring(str.indexOf("https"),str.indexOf("album")-3).replace("\\","");
        fireAllureParameterVariable("Request for serve to upload photo",request.toString());
        fireAllureParameterVariable("Response - "," "+ str);
        return str;
    }

    public String savePhoto(int albomID,int server,String photos_list,int aid,String hash,String token) throws IOException {
        String saveFoto="https://api.vk.com/method/photos.save?album_id="+albomID+"&server="+server+"&photos_list="+photos_list+"&aid="+aid+"&hash="+hash+"&v=5.52&";
        Request request = new Request.Builder().url(saveFoto+token).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        fireAllureParameterVariable("Request for save photo",request.toString());
        fireAllureParameterVariable("Response - ",str);
        return str;
    }


    private Parameter parameter(String name, String value) {
        Parameter param = new Parameter();
        param.setName(name);
        param.setValue(value);
        param.setKind(ParameterKind.ENVIRONMENT_VARIABLE);
        return param;
    }

    public  void fireAllureParameterVariable(final String name, final String value) {
        logger.info(name+": "+value);
        Allure.LIFECYCLE.fire(new TestCaseEvent() {
            @Override
            public void process(TestCaseResult testCaseResult) {
                testCaseResult.getParameters().add(parameter(name, value));
            }
        });
    }

}

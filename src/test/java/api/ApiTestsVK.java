package api;

import com.google.gson.Gson;
import core.Metods;
import jsonPage.JsonSavePhoto;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;
import users.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kailend on 14.12.2016.
 */
public class ApiTestsVK extends Metods{



    public ApiTestsVK() throws InterruptedException {
    }


    @DataProvider(name = "file")
    public Object[][] valid(){
        return new Object[][]{
                {"PNG.png"},
                {"JPJ.jpg"},
                {"GIF.gif"},

        };
    }
    @Features({"Positive"})
    @Stories({"Send one photo"})
    @Title("Upload 1 PHOTO Format PNJ,JPJ,GIF")
    @Test(dataProvider = "file")
    public void sendOnePhoto(String fileName) throws IOException {
        File file=new File(classLoader.getResource("images/"+fileName).getFile());
        User user=new User();
        String urlServer=takeServeForPhoto(user.albomID,TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        buildernew.addFormDataPart("file1","first.jpg", RequestBody.create(TYPE, file));
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        fireAllureParameterVariable("Post photo format"+fileName,request.toString());
        fireAllureParameterVariable("Response ",str);
        JsonSavePhoto jsonSavePhoto=new JsonSavePhoto();
        jsonSavePhoto=gson.fromJson(str,JsonSavePhoto.class);
        String result=savePhoto(user.albomID,jsonSavePhoto.server,jsonSavePhoto.photos_list,jsonSavePhoto.aid,jsonSavePhoto.hash,TOKEN);
        Assert.assertFalse(result.contains("error_code"));
    }

    @Features({"Positive"})
    @Stories({"Send 5 photo"})
    @Title("Upload 5 PHOTO format jpg")
    @Test
    public void sendFivePhoto() throws IOException {
        List<File> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(new File(classLoader.getResource("images/"+i+".jpg").getFile()));
        }
        User user = new User();
        String urlServer = takeServeForPhoto(user.albomID, TOKEN);
            MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
            for (int i = 1; i <= 5; i++) {
            buildernew.addFormDataPart("file"+i, "first.jpg", RequestBody.create(TYPE, list.get(i-1)));
        }
            MultipartBody requestBody = buildernew.build();
            Request request = new Request.Builder().url(urlServer).post(requestBody).build();
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            fireAllureParameterVariable("Post 5 photo",request.toString());
            fireAllureParameterVariable("Response ",str);
            JsonSavePhoto jsonSavePhoto = new JsonSavePhoto();
            jsonSavePhoto = gson.fromJson(str, JsonSavePhoto.class);
            String result = savePhoto(user.albomID, jsonSavePhoto.server, jsonSavePhoto.photos_list, jsonSavePhoto.aid, jsonSavePhoto.hash, TOKEN);

            Assert.assertFalse(result.contains("error_code"));
        }

    @Features({"Positive"})
    @Stories({"Send 3 photo"})
    @Title("Upload 3 PHOTO format jpj,gif,png")
    @Test
    public void sendThreeDifferentFormatPhoto() throws IOException {
        List<File> list = new ArrayList<>();
            list.add(new File(classLoader.getResource("images/40.gif").getFile()));
            list.add(new File(classLoader.getResource("images/50.png").getFile()));
            list.add(new File(classLoader.getResource("images/60.jpg").getFile()));
        User user = new User();
        String urlServer = takeServeForPhoto(user.albomID, TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        for (int i = 1; i <= 3; i++) {
            buildernew.addFormDataPart("file"+i, "first.jpg", RequestBody.create(TYPE, list.get(i-1)));
        }
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str = response.body().string();
        fireAllureParameterVariable("Post 3 photo different format",request.toString());
        fireAllureParameterVariable("Response ",str);
        JsonSavePhoto jsonSavePhoto = new JsonSavePhoto();
        jsonSavePhoto = gson.fromJson(str, JsonSavePhoto.class);
        String result = savePhoto(user.albomID, jsonSavePhoto.server, jsonSavePhoto.photos_list, jsonSavePhoto.aid, jsonSavePhoto.hash, TOKEN);
        fireAllureParameterVariable("Request",request.toString());
        fireAllureParameterVariable("Send 3 photo different format ","response "+result);
        Assert.assertFalse(result.contains("error_code"));
    }

    @Features({"Negative"})
    @Stories({"Send 6 photo"})
    @Title("Upload 6 PHOTO format")
    @Test
    public void sendSixDifferentPhoto() throws IOException {
        List<File> list = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            list.add(new File(classLoader.getResource("images/"+i+".jpg").getFile()));
        }
        User user = new User();
        String urlServer = takeServeForPhoto(user.albomID, TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        for (int i = 1; i <= 6; i++) {
            buildernew.addFormDataPart("file"+i, "first.jpg", RequestBody.create(TYPE, list.get(i-1)));
        }
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str = response.body().string();
        fireAllureParameterVariable("Post 6 photo",request.toString());
        fireAllureParameterVariable("Response",str);
        JsonSavePhoto jsonSavePhoto = new JsonSavePhoto();
        jsonSavePhoto = gson.fromJson(str, JsonSavePhoto.class);
        String result = savePhoto(user.albomID, jsonSavePhoto.server, jsonSavePhoto.photos_list, jsonSavePhoto.aid, jsonSavePhoto.hash, TOKEN);
        Assert.assertTrue(result.contains("400 Bad Request"));
    }

    @DataProvider(name = "wrongFormat")
    public Object[][] notValid(){
        return new Object[][]{
                {"BMP.bmp"},
                {"JP2.jp2"},
                {"PDF.pdf"},
                {"SVG.svg"},
                {"TIF.tif"},

        };
    }
    @Features({"Negative"})
    @Stories({"Send not allowed format"})
    @Title("Upload 1 PHOTO with not allowed format")
    @Test(dataProvider = "wrongFormat")
    public void sendOnePhotoNotAllowedFormat(String fileName) throws IOException {
        File file=new File(classLoader.getResource("images/neg/"+fileName).getFile());
        User user=new User();
        String urlServer=takeServeForPhoto(user.albomID,TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        buildernew.addFormDataPart("file1","first.jpg", RequestBody.create(TYPE, file));
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        fireAllureParameterVariable("Post not allowed"+fileName, request.toString());
        fireAllureParameterVariable("Response",str);
        JsonSavePhoto jsonSavePhoto=new JsonSavePhoto();
        jsonSavePhoto=gson.fromJson(str,JsonSavePhoto.class);
        String result=savePhoto(user.albomID,jsonSavePhoto.server,jsonSavePhoto.photos_list,jsonSavePhoto.aid,jsonSavePhoto.hash,TOKEN);
        Assert.assertEquals(result.substring(0,26),"{\"error\":{\"error_code\":114");
    }

    @Features({"Negative"})
    @Stories({"Send wrong data"})
    @Title("Send with wrong server")
    @Test
    public void sendOnePhotoWrongServer() throws IOException {
        File file=new File(classLoader.getResource("images/server.png").getFile());
        User user=new User();
        String urlServer=takeServeForPhoto(user.albomID,TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        buildernew.addFormDataPart("file1","first.jpg", RequestBody.create(TYPE, file));
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        fireAllureParameterVariable("Post photo",request.toString());
        fireAllureParameterVariable("Response",str);
        JsonSavePhoto jsonSavePhoto=new JsonSavePhoto();
        jsonSavePhoto=gson.fromJson(str,JsonSavePhoto.class);
        int wrogServer=1113644546;
        fireAllureParameterVariable("Input wrong server ",Integer.toString(wrogServer));
        String result=savePhoto(user.albomID,wrogServer,jsonSavePhoto.photos_list,jsonSavePhoto.aid,jsonSavePhoto.hash,TOKEN);
        Assert.assertEquals(result.substring(result.indexOf("error_code"),result.indexOf("error_msg")-1),"error_code\":118");
    }

    @Features({"Negative"})
    @Stories({"Send wrong data"})
    @Title("Send with wrong hash")
    @Test
    public void sendOnePhotoWrongHash() throws IOException {
        File file=new File(classLoader.getResource("images/server.png").getFile());
        User user=new User();
        String urlServer=takeServeForPhoto(user.albomID,TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        buildernew.addFormDataPart("file1","first.jpg", RequestBody.create(TYPE, file));
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        fireAllureParameterVariable("Post photo",request.toString());
        fireAllureParameterVariable("Response ",str);
        JsonSavePhoto jsonSavePhoto=new JsonSavePhoto();
        jsonSavePhoto=gson.fromJson(str,JsonSavePhoto.class);
        String wrogHash="1113644546e";
        fireAllureParameterVariable("Input wrong hash ",wrogHash);
        String result=savePhoto(user.albomID,jsonSavePhoto.server,jsonSavePhoto.photos_list,jsonSavePhoto.aid,wrogHash,TOKEN);;
        Assert.assertTrue(result.contains("error_code\":121"));
    }

    @Features({"Negative"})
    @Stories({"Send wrong data"})
    @Title("Send with wrong AlbomID")
    @Test
    public void sendOnePhotoWrongAlbomID() throws IOException {
        File file=new File(classLoader.getResource("images/server.png").getFile());
        User user=new User();
        String urlServer=takeServeForPhoto(user.albomID,TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        buildernew.addFormDataPart("file1","first.jpg", RequestBody.create(TYPE, file));
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        fireAllureParameterVariable("Post photo",request.toString());
        fireAllureParameterVariable("Response ",str);
        JsonSavePhoto jsonSavePhoto=new JsonSavePhoto();
        jsonSavePhoto=gson.fromJson(str,JsonSavePhoto.class);
        int wrogAlbomID=11199546;
        String result=savePhoto(wrogAlbomID,jsonSavePhoto.server,jsonSavePhoto.photos_list,jsonSavePhoto.aid,jsonSavePhoto.hash,TOKEN);
        fireAllureParameterVariable("Input wrong albomID",Integer.toString(wrogAlbomID));
        Assert.assertEquals(result.substring(result.indexOf("error_code"),result.indexOf("error_msg")-2),"error_code\":121");
    }

    @Features({"Negative"})
    @Stories({"Send big photo"})
    @Title("Upload 1 PHOTO size more 50 mb")
    @Test
    public void sendVeryBigPhoto() throws IOException {
        File file=new File(classLoader.getResource("images/mb50.jpg").getFile());
        User user=new User();
        String urlServer=takeServeForPhoto(user.albomID,TOKEN);
        MultipartBody.Builder buildernew = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file1", "first.jpg");
        buildernew.addFormDataPart("file1","first.jpg", RequestBody.create(TYPE, file));
        MultipartBody requestBody = buildernew.build();
        Request request = new Request.Builder().url(urlServer).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String str=response.body().string();
        fireAllureParameterVariable("Post photo more 50 mb",request.toString());
        fireAllureParameterVariable("Response ",str);
        JsonSavePhoto jsonSavePhoto=new JsonSavePhoto();
        jsonSavePhoto=gson.fromJson(str,JsonSavePhoto.class);
        String result=savePhoto(user.albomID,jsonSavePhoto.server,jsonSavePhoto.photos_list,jsonSavePhoto.aid,jsonSavePhoto.hash,TOKEN);
        Assert.assertTrue(result.contains("error_code"));
    }

}

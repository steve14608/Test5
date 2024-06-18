package com.example.test5.manager;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.test5.dataset.ResourcesData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class CustomAssetsManager {
    ResourcesData[] colorList;//String
    ResourcesData[] profilePicList;//bitmap
    ResourcesData[] bottomList;
    ResourcesData[] wallpaperList;
    public void init(AssetManager assetManager){
        if(colorList==null){
            colorList = getResource(assetManager,"color",resolveJson(assetManager,"color"),true);
            profilePicList = getResource(assetManager,"profile_pic",resolveJson(assetManager,"profile_pic"),false);
            bottomList = getResource(assetManager,"bottom",resolveJson(assetManager,"bottom"),false);
            wallpaperList = getResource(assetManager,"wallpaper",resolveJson(assetManager,"wallpaper"),false);
        }

    }
    private ResourcesData[] resolveJson(AssetManager assetManager,String type){
        ArrayList<ResourcesData> result = new ArrayList<>();
        try{

            InputStream inputStream = assetManager.open(type+"/setting.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuffer = new StringBuilder();
            String json;


            while ((json = bufferedReader.readLine())!=null){
                stringBuffer.append(json);
            }
            JSONArray jsonArray = new JSONArray(stringBuffer.toString());

            //System.out.println(stringBuffer);


            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                //String name =
                JSONArray jsonArray1 = jsonObject.getJSONArray("file");
                ArrayList<String> fileNameList = new ArrayList<>();
                for(int j=0;j<jsonArray1.length();j++){
                    fileNameList.add(jsonArray1.getString(j));
                }
                //result.put(jsonObject.getString("name"),fileNameList.toArray(new String[0]));
                result.add(new ResourcesData(jsonObject.getString("name"),fileNameList.toArray(new String[0])));
            }
        }catch(Exception e){e.printStackTrace();}
        //System.out.println(result.size());
        return result.toArray(new ResourcesData[0]);
    }
    private ResourcesData[] getResource(AssetManager assetManager,String type,ResourcesData[] resourcesData,boolean isText){
        ArrayList<ResourcesData> result = new ArrayList<>();
        try {
            //对每个子选项遍历
            for (ResourcesData da : resourcesData) {
                String[] fileNameList = (String[]) da.data;
                InputStream inputStream;
                BufferedReader bufferedReader;
                //每个子选项的文件列表
                ArrayList<Object> fileArray = new ArrayList<>();
                for (String fileName : fileNameList) {
                    inputStream = assetManager.open(type+"/"+fileName);
                    if(isText){
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        fileArray.add(bufferedReader.readLine());
                    }
                    else{
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        fileArray.add(bitmap);
                    }
                }
                result.add(new ResourcesData(da.name,fileArray.toArray()));

            }
        }catch(Exception e){e.printStackTrace();}
        return result.toArray(new ResourcesData[0]);
    }
    public ResourcesData[] getProfilePicList(){
        return profilePicList;
    }
    public ResourcesData[] getColorList(){
        return colorList;
    }
    public ResourcesData[] getBottomList(){
        return bottomList;
    }
    public ResourcesData[] getWallpaperList(){
        return wallpaperList;
    }
}

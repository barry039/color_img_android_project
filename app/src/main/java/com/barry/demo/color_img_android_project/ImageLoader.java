package com.barry.demo.color_img_android_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.barry.demo.color_img_android_project.network.APIManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageLoader {
    // Initialize MemoryCache
    private final MemoryCache memoryCache = new MemoryCache();

    private final FileCache fileCache;

    //Create Map (collection) to store image and image url in key value pair
    private final Map<ImageView, String> imageViews = Collections.synchronizedMap(
            new WeakHashMap<>());
    private final ExecutorService executorService;

    //handler to display images in UI thread
    private final Handler handler = new Handler();

    public ImageLoader(Context context){

        fileCache = new FileCache(context);

        // Creates a thread pool that reuses a fixed number of
        // threads operating off a shared unbounded queue.
        executorService= Executors.newFixedThreadPool(5);
    }

    // default image show in list (Before online image download)
    private final int stub_id=R.drawable.ic_launcher_foreground;

    public void DisplayImage(String url, ImageView imageView)
    {
        //Store image and url in Map
        imageViews.put(imageView, url);

        //Check image is stored in MemoryCache Map or not (see MemoryCache.java)
        Bitmap bitmap = memoryCache.get(url);

        if(bitmap!=null){
            // if image is stored in MemoryCache Map then
            // Show image in listview row
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            //queue Photo to download from url
            queuePhoto(url, imageView);

            //Before downloading image show default image
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView)
    {
        // Store image and url in PhotoToLoad object
        PhotoToLoad p = new PhotoToLoad(url, imageView);

        // pass PhotoToLoad object to PhotosLoader runnable class
        // and submit PhotosLoader runnable to executers to run runnable
        // Submits a PhotosLoader runnable task for execution

        executorService.submit(new PhotosLoader(p));
//        executorService.execute(new PhotosLoader(p));
    }

    //Task for the queue
    private static class PhotoToLoad
    {
        final String url;
        final ImageView imageView;
        PhotoToLoad(String u, ImageView i){
            url=u;
            imageView=i;
        }
    }

    class PhotosLoader implements Runnable {
        final PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }

        @Override
        public void run() {
            try{
                //Check if image already downloaded
                if(imageViewReused(photoToLoad))
                    return;
                // download image from web url
                Bitmap bmp = getBitmap(photoToLoad.url);

                // set image data in Memory Cache
                memoryCache.put(photoToLoad.url, bmp);

                if(imageViewReused(photoToLoad))
                    return;

                // Get bitmap to display
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);

                // Causes the Runnable bd (BitmapDisplayer) to be added to the message queue.
                // The runnable will be run on the thread to which this handler is attached.
                // BitmapDisplayer run method will call
                handler.post(bd);

            }catch(Throwable th){
                th.printStackTrace();
            }
        }
    }

    private Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;
        // Download image file from web
        Bitmap bitmap;
        FileOutputStream fos;
        try {
            URL imageUrl = new URL(url);
            Log.e("URL",imageUrl.toString());
            OkHttpClient client = APIManager.getINSTANCE().getOkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException(response + "");
            }
            fos = new FileOutputStream(f);
            fos.write(Objects.requireNonNull(response.body()).bytes());
            fos.flush();
            fos.close();

            bitmap = decodeFile(f);
            FileOutputStream outputStream = new FileOutputStream(f.getAbsolutePath());
            Objects.requireNonNull(bitmap).compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return bitmap;

        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    //Decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){

        try {

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.

            // Set width/height of recreated image
            final int REQUIRED_SIZE=85;

            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while (width_tmp / 2 >= REQUIRED_SIZE && height_tmp / 2 >= REQUIRED_SIZE) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            //decode with current scale values
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inJustDecodeBounds = false;
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;

        } catch (FileNotFoundException ignored) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean imageViewReused(PhotoToLoad photoToLoad){

        String tag=imageViews.get(photoToLoad.imageView);
        //Check url is already exist in imageViews MAP
        return tag == null || !tag.equals(photoToLoad.url);
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        final Bitmap bitmap;
        final PhotoToLoad photoToLoad;
        BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;

            // Show bitmap on UI
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

}

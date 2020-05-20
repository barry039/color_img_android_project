# Color Image Parser Project

Loading JsonPlaceholder Photo API data and display in gridlayout.  
![](https://github.com/barry039/color_img_android_project/blob/master/Screenshot_20200520-222614.png){:height="50%" width="50%"}  
![](https://github.com/barry039/color_img_android_project/blob/master/Screenshot_20200520-222621.png){:height="50%" width="50%"}  
## Description

### Start Page

Show app anme and request storage permission from user to enter maain page.  


### Main Page

Display color image in a grid list. It would load and display small chunks of user data at a time(per pagesize = 400).  

Loading the image with cache directory.It would store image cache and reduce imageview next loading time.

### Development Schedule

Start Date : 2020/05/19  
Start Time : 19 : 00

End Date : 2020/05/20 
End Time : 23 : 00

### Dependency

#### Android LifeCycle Component
androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version  

androidx.lifecycle:lifecycle-livedata:$lifecycle_version  

androidx.paging:paging-runtime:$paging_version

#### Retrofit(Network Usage)
com.squareup.retrofit2:retrofit:$retrofit_version

com.squareup.retrofit2:converter-gson:$retrofit_version

#### Okhttp(Network Usage)
com.squareup.okhttp3:okhttp:$okhttp_version

com.squareup.okhttp3:logging-interceptor:$okhttp_version

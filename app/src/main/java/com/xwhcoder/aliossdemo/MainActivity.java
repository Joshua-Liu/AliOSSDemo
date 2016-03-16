/**
 * ACCESS_ID=frycR64A1FSfkDAw
 * ACCESS_KEY=abIDWR4JLgSwuVTAB6d9ZuHXB0o8wz
 * OSS_ENDPOINT=http://oss-cn-beijing.aliyuncs.com/
 * BUCKET_NAME=tongchuangjob
 */

package com.xwhcoder.aliossdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private final String ACCESS_ID = "frycR64A1FSfkDAw";
    private final String ACCESS_KEY = "abIDWR4JLgSwuVTAB6d9ZuHXB0o8wz";
    private final String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private final String BUCKET_NAME = "tongchuangjob";

    private final String UPLOAD_FILE = Environment.getExternalStorageDirectory() + File.separator.toString() + "smart_jni.txt";

    private OSSClient ossClient;
    private OSSCredentialProvider ossCredentialProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        doAOSSUploadJobNow();

    }


    //Upload a file use AliOSS API
    private void doAOSSUploadJobNow() {
        if (ossClient == null) {
            ossCredentialProvider = new OSSPlainTextAKSKCredentialProvider(ACCESS_ID, ACCESS_KEY);
            ossClient = new OSSClient(MainActivity.this, OSS_ENDPOINT, ossCredentialProvider);
            ossClient = new OSSClient(MainActivity.this, OSS_ENDPOINT, ossCredentialProvider);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("Upload","Upload start now");
                    File file = new File(UPLOAD_FILE);
                    InputStream inputStream = new FileInputStream(file);
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(file.length());
                    objectMetadata.addUserMetadata("usage", "only a test");
                    PutObjectRequest putObjectRequest=new PutObjectRequest(BUCKET_NAME,"test/test.txt",UPLOAD_FILE,objectMetadata);
                    PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

                    Log.i("Upload status","Upload is OKOKOK!!!");
                    Log.i("ETag",putObjectResult.getETag());
                    Log.i("RequestID",putObjectResult.getRequestId());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}

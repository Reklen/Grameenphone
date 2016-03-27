package com.cc.grameenphone.generator;

import android.content.Context;

import com.cc.grameenphone.R;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.SelfSigningClientBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by aditlal on 23/07/15.
 */
public class ServiceGenerator {

    // public static final String BASE_URL = "http://202.56.229.146:8992/"; // Test
    public static final String BASE_URL = "https://202.56.5.215:8989/"; //Production

    //http://202.56.5.215:8989/GPTxn/CelliciumSimulator.html

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Context context, Class<S> serviceClass) {
       /* OkHttpClient client = new OkHttpClient();
        client = setupCertificateLogic(context, client);*/

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Logger.i("Retro", msg);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(SelfSigningClientBuilder.createClient(okHttpClient)));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

    private static OkHttpClient setupCertificateLogic(Context context, OkHttpClient okHttpClient) {
        try {
            // loading CAs from an InputStream
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream cert = context.getResources().openRawResource(R.raw.mapp_gp_crt);
            java.security.cert.Certificate ca;
            try {
                ca = cf.generateCertificate(cert);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                cert.close();
            }

            // creating a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // creating a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // creating an SSLSocketFactory that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
           /* ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                    .build();*/
            return okHttpClient;
        } catch (CertificateException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return okHttpClient;

    }
}
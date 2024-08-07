package com.example.learnquest;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class DatabaseHelper {
   // private static final String URL = "jdbc:mysql://project-sql-database-documentation4project.e.aivencloud.com:16222";
  //  private static final String URL = "jdbc:mysql://project-sql-database-documentation4project.e.aivencloud.com:16222/defaultdb?useSSL=true&requireSSL=true&verifyServerCertificate=false";
    private static final String URL = "jdbc:mysql://avnadmin:AVNS_4DQayfBvqZRs5GSSp6m@project-sql-database-documentation4project.e.aivencloud.com:16222/defaultdb?ssl-mode=REQUIRED";

    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_4DQayfBvqZRs5GSSp6m";

    public static Connection getConnection(Context context) throws SQLException {
        InputStream trustStoreStream;
        try {
            trustStoreStream = context.getAssets().open("truststore.jks");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance("JKS");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        try {
            trustStore.load(trustStoreStream, "mathew".toCharArray());
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            trustManagerFactory.init(trustStore);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        // Initialize SSLContext
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();
        properties.setProperty("user", "avnadmin");
        properties.setProperty("password", "AVNS_4DQayfBvqZRs5GSSp6m");
        properties.setProperty("useSSL", "true");
        properties.setProperty("requireSSL", "true");
        properties.setProperty("verifyServerCertificate", "true");

        // Your JDBC URL
        String url = "jdbc:mysql://project-sql-database-documentation4project.e.aivencloud.com:16222/your_database";



        return DriverManager.getConnection(url, properties);
    }
}
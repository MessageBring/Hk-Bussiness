package cn.hk.common.config;

import org.apache.commons.io.IOUtils;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * 配置类，加载证书到SSLContext，可用于创建需携带证书作请求的RestTemplate
 */
public class SSLContextCertConfig {

    private static final String SSL = "SSL";

    private static final String TLS = "TLS";

    private static final String RSA = "RSA";

    private static final String SUN_X509 = "SunX509";

    private static final String X_509 = "X.509";

    private static final String JKS = "JKS";

    public SSLContext getSocketSSLContext(String keyPath,String certPath) throws KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, IOException, CertificateException, InvalidKeySpecException, UnrecoverableKeyException {


        SSLContext context = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).setProtocol(TLS).build();
        byte[] pem = null;
        byte[] cert = null;
        pem = IOUtils.toByteArray(getFileInputStream(keyPath));
        cert = IOUtils.toByteArray(getFileInputStream(certPath));


        byte[] keyBytes = parseDERFromPEM(pem, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----");
        byte[] certBytes = parseDERFromPEM(cert, "-----BEGIN CERTIFICATE-----", "-----END CERTIFICATE-----");

        X509Certificate certX509 = generateCertificateFromDER(certBytes);

        PrivateKey key = generatePrivateKeyFromDER(keyBytes);

        KeyStore keystore = KeyStore.getInstance(JKS);
        keystore.load(null);
        keystore.setCertificateEntry("cert", certX509);
        keystore.setKeyEntry("key", key, "".toCharArray(), new Certificate[] { certX509 });

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(SUN_X509);
        kmf.init(keystore, "".toCharArray());

        KeyManager[] km = kmf.getKeyManagers();

        context.init(km, null, null);

        return context;
    }

    private byte[] parseDERFromPEM(byte[] pem, String beginDelimiter, String endDelimiter) {

        String data = new String(pem);
        String[] tokens = data.split(beginDelimiter);
        tokens = tokens[1].split(endDelimiter);

        return Base64.getMimeDecoder().decode(tokens[0].replace("\n","").trim());
    }

    private X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException {

        CertificateFactory factory = CertificateFactory.getInstance(X_509);

        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
    }

    private PrivateKey generatePrivateKeyFromDER(byte[] keyBytes)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory factory = KeyFactory.getInstance(RSA);

        return factory.generatePrivate(spec);
    }

    private FileInputStream getFileInputStream(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        FileInputStream f = new FileInputStream(resource.getFile());
        return f;
    }
}

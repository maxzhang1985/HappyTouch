package com.kuailedian.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.repository.AsyncCallBack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Maxzhang on 2015/6/25.
 */
public class PayApiHelper {

    //�̻�PID
    public static final String PARTNER = "2088912427616293";
    //�̻��տ��˺�
    public static final String SELLER = "kldtscom@sina.com";
    //�̻�˽Կ��pkcs8��ʽ
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMGdIuOHm87hdONxBymHn3qoTHZu4abrx4fz20glLVgjtIUcN30LuodU+s7puKsw2tM2iH8QTU2WlwxPAB3BXfyLuDXJdlMK/NfAMm3HU+RvyhpmNhREaEOT+pJx/41exp4UmFC0LkAd4ZDDWodN/+ADM3ZsjAXAIJzKydkBKzjxAgMBAAECgYAiQ3q/JWqPbBrRfVk1Ikk43bvaYbkRXzyKdL9jvyCNLpRt0haHzKGP/Zv9LiN+OEEhi88QHYRwrKvCg+CTY3sMPaY+Wc0qlsmgQhcx1a9KnWfBqQOmor1kArlmg6cw56ElxsE+hJ354SNVlL1gVhJHiaUhOboJhNYBL6vfPFGkAQJBAOJvkxf/3avR4wi/Q5zRLUfaNHP0MvE99Wqs1gvu2RglZJ3TSDtaX75ZUj3OXtQWUl8UPXEbAT+bcxf8d6PjBxECQQDa5INv6tFIphlRy3UbFDZph3hyx7y4NrzgNi3UWOn7oBb9CbIJvx35y0kSIUIMdkD2Qxf8j7cjx+kTlw+w39PhAkBc/Ja6N0+vvIxwTiMU5e2jYR9DMtgsq1X6DaoE0nwCZ3CvQ1GZVh6VS5bzXKkijVwcQXKN3RohJdQY2IvFGHkRAkBCqNB2JrZxP1P6mtEg2/juDjlaCwWnu9OtHBK81g77d/dIO8miOjdwuL2Z7dLlZKXH2QrW+RU2qkyKkLhQjvlBAkEAzMdREBF183w6/mn0lFHIXNz2BK46ZSqSUvUH43C/63c1NkVouNlfc6ABmTa89r3NNGPJ36hCylAdjfRJwT3GlQ==";
    //֧������Կ
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private Context context;
    private AsyncCallBack handerCallback;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(context, "֧���ɹ�",
                                Toast.LENGTH_SHORT).show();
                        handerCallback.onDataReceive(null,resultStatus);
                    } else {
                        // �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
                        // ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "֧�����ȷ����",
                                    Toast.LENGTH_SHORT).show();
                            handerCallback.onDataReceive(null,resultStatus);

                        } else {
                            // ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
                            Toast.makeText(context, "֧��ʧ��",
                                    Toast.LENGTH_SHORT).show();
                            handerCallback.onDataReceive(null,resultStatus);

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(context, "�����Ϊ��" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };



    public PayApiHelper(Context cxt)
    {
        context = cxt;
    }

    public void payAsync(OrderInfo info, AsyncCallBack callback)
    {
        handerCallback = callback;

        // ����
        String orderInfo = getOrderInfo(info.ID,info.Subject, info.Body, info.Price);

        // �Զ�����RSA ǩ��
        String sign = sign(orderInfo);
        try {
            // �����sign ��URL����
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // �����ķ���֧���������淶�Ķ�����Ϣ
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // ����PayTask ����
                PayTask alipay = new PayTask( (Activity)context );
                // ����֧���ӿڣ���ȡ֧�����
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // �����첽����
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



    /**
     * check whether the device has authentication alipay account.
     * ��ѯ�ն��豸�Ƿ����֧������֤�˻�
     **/
    public void checkAsync() {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // ����PayTask ����
                PayTask payTask = new PayTask( (Activity)context );
                // ���ò�ѯ�ӿڣ���ȡ��ѯ���
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }



    /**
     * get the sdk version. ��ȡSDK�汾��
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask( (Activity)context );
        String version = payTask.getVersion();
        Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. ����������Ϣ
     *
     */
    public String getOrderInfo(String tradeNo,String subject, String body, String price) {
        // ǩԼ���������ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // ǩԼ����֧�����˺�
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // �̻���վΨһ������
        orderInfo += "&out_trade_no=" + "\"" + tradeNo + "\"";

        // ��Ʒ����
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // ��Ʒ����
        orderInfo += "&body=" + "\"" + body + "\"";

        // ��Ʒ���
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // �������첽֪ͨҳ��·��
        orderInfo += "&notify_url=" + "\"" + "http://220.194.201.2:8081/notify_url.aspx"
                + "\"";

        // ����ӿ����ƣ� �̶�ֵ
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // ֧�����ͣ� �̶�ֵ
        orderInfo += "&payment_type=\"1\"";

        // �������룬 �̶�ֵ
        orderInfo += "&_input_charset=\"utf-8\"";

        // ����δ����׵ĳ�ʱʱ��
        // Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
        // ȡֵ��Χ��1m��15d��
        // m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
        // �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
        orderInfo += "&return_url=\"m.alipay.com\"";

        // �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * sign the order info. �Զ�����Ϣ����ǩ��
     *
     * @param content
     *            ��ǩ��������Ϣ
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. ��ȡǩ����ʽ
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }



}

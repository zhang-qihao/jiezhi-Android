package com.zqh.mysystem.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.utils
 * @className: VerityCode
 * @author: Zhangqihao
 * @description: 验证码验证、更换操作
 * @date: 2022/5/21
 */
public class VerifyCode {

    private static String code = Code.getInstance().getCode();;

    /**
     * @param code_in: 前端获取输入的验证码
     * @return boolean
     * @author Zhangqihao
     * @description 验证验证码是否与生成的验证码相同
     * @date 2022/5/21
     */
    public static boolean verify(String code_in) {
        boolean flag = false;
        if (code_in.toLowerCase(Locale.ROOT).equals(Code.getInstance().getCode().toLowerCase(Locale.ROOT)))
            flag = true;
        return flag;
    }

    /**
     * @param img_code: 验证码图片控件
     * @return String
     * @author Zhangqihao
     * @description 更换验证码图片
     * @date 2022/5/21
     */
    public static String change(ImageView img_code) {
        img_code.setImageBitmap(Code.getInstance().createBitmap());
        code = Code.getInstance().getCode();
        Log.i("Login", "[更换验证码] | code:" + code);
        return code;
    }

    /**
     * @param context: Activity
     * @return void
     * @author Zhangqihao
     * @description 验证码错误
     * @date 2022/5/21
     */
    public static String wrongCode(Context context, ImageView img_code, String code_input) {
        Code.getInstance().createBitmap();
        change(img_code);
        Log.i("Login", "[验证码错误] | code:" + code + " -- code(input):" + code_input);
        Toast.makeText(context, "验证码有误", Toast.LENGTH_SHORT).show();
        return Code.getInstance().getCode();
    }

    /**
     * @param context: Activity
     * @return void
     * @author Zhangqihao
     * @description 验证码未输入
     * @date 2022/5/21
     */
    public static void emptyCode(Context context) {
        Log.i("Login", "[验证码未输入] | code:" + code);
        Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
    }
}

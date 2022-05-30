package com.zqh.mysystem.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.utils
 * @className: Code
 * @author: Zhangqihao
 * @description: 验证码生成
 * @date: 2022/5/21
 */
public class Code {

    // 随机数内容
    private static final char[] CHARS = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static Code bmpCode;

    public static Code getInstance() {
        if (bmpCode == null)
            bmpCode = new Code();
        return bmpCode;
    }

    // 验证码默认随机数的个数
    private static final int DEFAULT_CODE_LENGTH = 4;
    // 默认字体大小
    private static final int DEFAULT_FONT_SIZE = 75;
    // 默认线条条数
    // padding 值
    private static final int DEFAULT_LINE_NUMBER = 10;
    public static final int BASE_PADDING_LEFT = 30, RANGE_PADDING_LEFT = 45, BASE_PADDING_TOP = 45, RANGE_PADDING_TOP = 60;
    //验证码的默认宽高
    private static final int DEFAULT_WIDTH = 300, DEFAULT_HEIGHT = 120;

    // 换变量名
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;
    private int codeLength = DEFAULT_CODE_LENGTH, line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();
    private StringBuilder buffer;

    /**
     * @return String
     * @author Zhangqihao
     * @description 生成验证码
     * @date 2022/5/21
     */
    private String createCode() {
        buffer = new StringBuilder();
        for (int i = 0; i < codeLength; i++)
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        return buffer.toString();
    }
    /**
     * @return String
     * @author Zhangqihao
     * @description 获取验证码
     * @date 2022/5/21
     */
    public String getCode() {
        return buffer.toString();
    }

    /**
     * @return int
     * @author Zhangqihao
     * @description 生成随机颜色
     * @date 2022/5/21
     */
    private int randomColor() {
        return randomColor(1);
    }
    /**
     * @param rate: 比例
     * @return int
     * @author Zhangqihao
     * @description 生成随机颜色
     * @date 2022/5/21
     */
    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }

    /**
     * @param paint: 画布
     * @return void
     * @author Zhangqihao
     * @description 随机生成文字样式，颜色，粗细，倾斜度
     * @date 2022/5/21
     */
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean()); // true 为粗体，false 为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;  // 随机左斜右斜
        paint.setTextSkewX(skewX); // float 类型参数，负数表示右斜，正数左斜
    }

    /**
     * @return void
     * @author Zhangqihao
     * @description 随机生成 padding 值
     * @date 2022/5/21
     */
    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }

    /**
     * @param canvas: canva 绘图API
     * @param paint: 画布
     * @return void
     * @author Zhangqihao
     * @description 画干扰线
     * @date 2022/5/21
     */
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    /**
     * @return Bitmap
     * @author Zhangqihao
     * @description 创建验证码图片
     * @date 2022/5/21
     */
    public Bitmap createBitmap() {

        padding_left = 0;
        // 实例化 Bitmap 对象
        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bp);

        // 生成验证码
        code = createCode();

        canvas.drawColor(Color.WHITE);
        // 实例化画布对象
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 设置抗锯齿
        paint.setTextSize(font_size); // 设置字体大小

        // 画验证码
        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }

        // 画线条
        for (int i = 0; i < line_number; i++)
            drawLine(canvas, paint);

        // 保存
        canvas.save();
        canvas.restore();

        return bp;
    }

}

package com.neuroandroid.pyreader.utils;

import android.graphics.Color;

/**
 * Created by NeuroAndroid on 2017/8/1.
 */

public class ColorPickerUtils {
    public static int[] DEFAULT_LINEAR_GRADIENT_COLORS =
            new int[]{Color.WHITE, Color.WHITE, Color.BLACK};

    public static final float[] LINEAR_GRADIENT_POSITIONS =
            new float[]{0.1f, 0.5f, 0.9f};

    /**
     * 获取某个百分比位置的颜色
     *
     * @param radio 取值[0,1]
     */
    public static int getColor(int[] colors, float radio) {
        if (colors == null) colors = DEFAULT_LINEAR_GRADIENT_COLORS;
        int startColor;
        int endColor;
        if (radio >= 0.9) {
            return colors[colors.length - 1];
        }
        for (int i = 0; i < LINEAR_GRADIENT_POSITIONS.length; i++) {
            if (radio <= LINEAR_GRADIENT_POSITIONS[i]) {
                if (i == 0) {
                    return colors[0];
                }
                startColor = colors[i - 1];
                endColor = colors[i];
                float areaRadio = getAreaRadio(radio, LINEAR_GRADIENT_POSITIONS[i - 1], LINEAR_GRADIENT_POSITIONS[i]);
                return getColorFrom(startColor, endColor, areaRadio);
            }
        }
        return -1;
    }

    private static float getAreaRadio(float radio, float startPosition, float endPosition) {
        return (radio - startPosition) / (endPosition - startPosition);
    }

    /**
     * 取两个颜色间的渐变区间 中的某一点的颜色
     */
    private static int getColorFrom(int startColor, int endColor, float radio) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
        return Color.argb(255, red, greed, blue);
    }
}

package com.xyp.tiange.textviewstyle;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Rasterizer;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RasterizerSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * User: xyp
 * Date: 2016/3/23
 * Time: 13:15
 */
public class MainActivity extends AppCompatActivity {
    private TextView span_tv;
    private TextView html_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        span_tv = (TextView) findViewById(R.id.span_tv);
        html_tv = (TextView) findViewById(R.id.html_tv);
        setSpanStyle();
        setHtmlStyle();
    }

    //利用Spannable来设置

    /**
     * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE --- 不包含start和end所在的端点     (a,b)
     * Spanned.SPAN_EXCLUSIVE_INCLUSIVE --- 不包含端start，但包含end所在的端点 (a,b]
     * Spanned.SPAN_INCLUSIVE_EXCLUSIVE --- 包含start，但不包含end所在的端点  [a,b)
     * Spanned.SPAN_INCLUSIVE_INCLUSIVE--- 包含start和end所在的端点      [a,b]
     */
    private void setSpanStyle() {
        SpannableString spannableString = new SpannableString("这个是用spannable来设置style的字体，真的是用他来实现的哦。点击弹出吐司。跳转到百度。/bot");

        //BackgroundColorSpan，设置背景颜色
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.BLUE);
        spannableString.setSpan(backgroundColorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        //ForegroundColorSpan,设置字体颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(foregroundColorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        //AbsoluteSizeSpan,设置字体的绝对大小，像素
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(20);
        spannableString.setSpan(absoluteSizeSpan, 4, 8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        //relativeSizeSpan,设置字体的相对大小，倍数
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(2.0f);
        spannableString.setSpan(relativeSizeSpan, 8, 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        //MaskFilterSpan：文字的装饰效果。分为两种：BlurMaskFilter(模糊效果)和 EmbossMaskFilter （浮雕效果）
        MaskFilterSpan maskFilterSpan = new MaskFilterSpan(new EmbossMaskFilter(new float[]{1, 1, 3}, 1.5f, 8, 3));
//        spannableString.setSpan(maskFilterSpan, 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        MaskFilter filter2 = new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER);
        MaskFilterSpan maskFilterSpan2 = new MaskFilterSpan(filter2);
        spannableString.setSpan(maskFilterSpan2, 14, 18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


        /**
         * StyleSpan ：主要由正常、粗体、斜体和同时加粗倾斜四种样式，常量值定义在Typeface类中。
         Typeface.DEFAULT //常规字体类型
         Typeface.DEFAULT_BOLD //黑体字体类型
         Typeface.MONOSPACE //等宽字体类型
         Typeface.SANS_SERIF //sans serif字体类型
         Typeface.SERIF //serif字体类型
         */
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan, 19, 23, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        TypefaceSpan typefaceSpan = new TypefaceSpan("serif");
        spannableString.setSpan(typefaceSpan, 24, 28, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.BLUE);
        spannableString.setSpan(foregroundColorSpan1, 37, 42, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //ClickableSpan: 点击事件相关的Span。
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, "点击了这一块", Toast.LENGTH_SHORT).show();
            }
        };
        spannableString.setSpan(clickableSpan, 37, 42, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //必须要设置这一句
        span_tv.setMovementMethod(LinkMovementMethod.getInstance());

        //URLSpan：链接，类似HTML中的a标签。URILSpan也是实现了onClick的方法，将URL跳转到浏览器中的
        URLSpan urlSpan = new URLSpan("http://www.baidu.com");
        spannableString.setSpan(urlSpan, 43, 48, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


        //设置下划线
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 29, 32, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        //设置删除线
        spannableString.setSpan(new StrikethroughSpan(), 33, 36, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


        //设置图片
        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher_round);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableString.setSpan(new ImageSpan(drawable), 50, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        span_tv.setText(spannableString);


    }


    /**
     * 通过Html标签来设置，使用简单的html
     */
    private void setHtmlStyle() {
        StringBuilder html = new StringBuilder();
        html.append("<font color='red' size='20'>" + "这是用Html设置样式的。" + "</font>");
        html.append("<font color='blue' size='24'>" + "跳转到" + "</font>");
        html.append("<a href='http://www.baidu.com'>"+"www.baidu.com"+"</a>");
        html.append("<img src="+R.drawable.icon+" />");//file:///android_asset/icon.png
        html_tv.setText(Html.fromHtml(html.toString(),imageGetter,null));
    }

    private Html.ImageGetter imageGetter=new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            //有三种，1.项目资源，在asset里的图片，2.本地图片，在sd卡中的，3.网络图片
            //这里是项目资源
            int resId=Integer.valueOf(source);
            Drawable drawable=null;
            if(resId!=0){
                drawable=getResources().getDrawable(resId);
                drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            }

            //drawable.createFromPath(source);//从本地获取图片：<img src=\"/mnt/sdcard/temp/1.jpg\" />";
            //从网络获取图片
//            url = new URL(source);
//            drawable = Drawable.createFromStream(url.openStream(), "");

            return drawable;

        }
    };
}

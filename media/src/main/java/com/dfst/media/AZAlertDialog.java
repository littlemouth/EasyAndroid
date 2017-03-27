package com.dfst.media;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * @author lilinlin@supcon.com
 * date 2016/04/22
 * 创建各式dialog
 */
public class AZAlertDialog {

    /**创建单按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框*/
    public static AZDialog show(Context context,String title,String content,String positiveButton) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,null,true,true,null,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param textType 设置文字类型对象*/
    public static AZDialog show(Context context,String title,String content,String positiveButton,TextType textType) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,null,true,true,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * */
    public static AZDialog show(Context context,String title,String content,String positiveButton,
                                boolean outsideBack,boolean clickBack) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                                        null,outsideBack,clickBack,null,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param textType 设置文字类型对象
     * */
    public static AZDialog show(Context context,String title,String content,String positiveButton,
                                boolean outsideBack,boolean clickBack,TextType textType) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                null,outsideBack,clickBack,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
        }）
        which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,String content,String positiveButton,
                                        boolean outsideBack,boolean clickBack,
                                        DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                                        null,outsideBack,clickBack,onClickListener,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,String content,String positiveButton,
                                boolean outsideBack,boolean clickBack,TextType textType,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                null,outsideBack,clickBack,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
        }）
        which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,String content,String positiveButton,
                                      DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,null,true,true,onClickListener,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,String content,String positiveButton,
                                TextType textType,DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,null,
                                true,true,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出*/
    public static AZDialog show(Context context,String title,String content,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                                        negativeButton,outsideBack,clickBack,null,null);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出*/
    public static AZDialog show(Context context,String title,String content,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack,TextType textType) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                negativeButton,outsideBack,clickBack,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框*/
    public static AZDialog show(Context context,String title,String content,
                                String positiveButton,String negativeButton) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,negativeButton,true,true,null,null);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框*/
    public static AZDialog show(Context context,String title,String content,
                                String positiveButton,String negativeButton,TextType textType) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,negativeButton,
                                    true,true,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }）
     which 返回-1 表示响应positiveButton单击事件
     which 返回-2 表示响应negativeButton单击事件
    */
    public static AZDialog show(Context context,String title,String content,
                                      String positiveButton,String negativeButton,
                                      DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,negativeButton,
                                    true,true,onClickListener,null);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应positiveButton单击事件
    which 返回-2 表示响应negativeButton单击事件
     */
    public static AZDialog show(Context context,String title,String content,
                                String positiveButton,String negativeButton,TextType textType,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,negativeButton,
                true,true,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应positiveButton单击事件
    which 返回-2 表示响应negativeButton单击事件
     */
    public static AZDialog show(Context context,String title,String content,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                                negativeButton,outsideBack,clickBack,onClickListener,null);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param content 提示内容
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应positiveButton单击事件
    which 返回-2 表示响应negativeButton单击事件
     */
    public static AZDialog show(Context context,String title,String content,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack,TextType textType,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,content,null,positiveButton,
                negativeButton,outsideBack,clickBack,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }


    /**创建单按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框*/
    public static AZDialog show(Context context,String title,View view,String positiveButton){
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,true,true,null,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框*/
    public static AZDialog show(Context context,String title,View view,
                                        String positiveButton,TextType textType){
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,
                                    true,true,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     ** @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * */
    public static AZDialog show(Context context,String title,View view,String positiveButton,
                                boolean outsideBack,boolean clickBack){
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,
                                                outsideBack,clickBack,null,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     ** @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * */
    public static AZDialog show(Context context,String title,View view,String positiveButton,
                                boolean outsideBack,boolean clickBack,TextType textType){
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,
                                    outsideBack,clickBack,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
        }）
        which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,View view,String positiveButton,
                            DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,
                                    true,true,onClickListener,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,View view,String positiveButton,
                                TextType textType,DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,
                                true,true,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,View view,String positiveButton,
                                boolean outsideBack,boolean clickBack,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,
                                    outsideBack,clickBack,onClickListener,null);
        azDialog.show();
        return azDialog;
    }

    /**创建单按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param onClickListener 按钮监听事件（e.g.
     *  new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应单击事件
     */
    public static AZDialog show(Context context,String title,View view,String positiveButton,
                                boolean outsideBack,boolean clickBack,TextType textType,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,null,
                outsideBack,clickBack,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     */
    public static AZDialog show(Context context,String title,View view,
                                          String positiveButton,String negativeButton) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                                            true,true,null,null);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     */
    public static AZDialog show(Context context,String title,View view,TextType textType,
                                String positiveButton,String negativeButton) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                                    true,true,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     */
    public static AZDialog show(Context context,String title,View view,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                                            outsideBack,clickBack,null,null);
        azDialog.show();
        return azDialog;
    }


    /**创建双按钮带view提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     */
    public static AZDialog show(Context context,String title,View view,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack,TextType textType) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                                        outsideBack,clickBack,null,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     *    new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
        }）
        which 返回-1 表示响应positiveButton单击事件
        which 返回-2 表示响应negativeButton单击事件
     */
    public static AZDialog show(Context context,String title,View view,
                                          String positiveButton,String negativeButton,
                                          DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                                        true,true,onClickListener,null);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     *    new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应positiveButton单击事件
    which 返回-2 表示响应negativeButton单击事件
     */
    public static AZDialog show(Context context,String title,View view,
                                String positiveButton,String negativeButton,TextType textType,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                true,true,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }

    /**创建双按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     *    new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应positiveButton单击事件
    which 返回-2 表示响应negativeButton单击事件
     */
    public static AZDialog show(Context context,String title,View view,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                                    outsideBack,clickBack,onClickListener,null);
        azDialog.show();
        return azDialog;
    }


    /**创建双按钮带view可监听提示框
     * @param context 上下文 显示
     * @param title 提示框标题
     * @param view view
     * @param positiveButton 确定框
     * @param negativeButton 取消框
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     *    new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
    }）
    which 返回-1 表示响应positiveButton单击事件
    which 返回-2 表示响应negativeButton单击事件
     */
    public static AZDialog show(Context context,String title,View view,
                                String positiveButton,String negativeButton,
                                boolean outsideBack,boolean clickBack,TextType textType,
                                DialogInterface.OnClickListener onClickListener) {
        AZDialog azDialog = config(context,title,null,view,positiveButton,negativeButton,
                outsideBack,clickBack,onClickListener,textType);
        azDialog.show();
        return azDialog;
    }

    /**
     * 提示框样式配置
     * @param context
     * @param title 标题
     * @param content 文字内容
     * @param view 自定义view
     * @param positiveButton 确定按钮
     * @param negativeButton 返回按钮
     * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
     * @param clickBack 点击返回是否退出，true表示退出，false为不退出
     * @param onClickListener 点击监听
     * @return
     */
    private static AZDialog config(Context context,String title,String content,View view,
                                   String positiveButton,String negativeButton,
                                   boolean outsideBack,boolean clickBack,
                                   DialogInterface.OnClickListener onClickListener,
                                   @Nullable TextType textType){
        AZDialog azDialog = new AZDialog(context);
        AZDialog.Builder builder = new AZDialog.Builder(context);
        if(null != textType) {
            if(textType.titleColor != 0){
                builder.setTitleColor(textType.titleColor);
            }
            if(textType.titleSize > 0.0f){
                builder.setTitleSize(textType.titleSize);
            }
            if(textType.contentColor != 0){
                builder.setContentColor(textType.contentColor);
            }
            if(textType.contentSize > 0.0f){
                builder.setContentSize(textType.contentSize);
            }
            if(textType.buttonTextColor != 0){
                builder.setbuttonTextColor(textType.buttonTextColor);
            }
            if(textType.buttonTextSize > 0.0f){
                builder.setbuttonTextSize(textType.buttonTextSize);
            }
        }else{
            builder.setTitleColor(Color.BLACK);
            builder.setTitleSize(16f);
            builder.setContentColor(Color.DKGRAY);
            builder.setContentSize(16f);
            builder.setbuttonTextColor(Color.parseColor("#007aff"));
            builder.setbuttonTextSize(16f);
        }
        if(null != title){
            builder.setTitle(title);
        }
        if(null != content){
            builder.setMessage(content);
        }
        if(null != view){
            builder.setContentView(view);
        }

        /**设置click监听*/
        if(null != onClickListener){
            if(null != positiveButton && null != negativeButton){
                azDialog = builder.setPositiveButton(positiveButton,onClickListener)
                        .setNegativeButton(negativeButton, onClickListener)
                        .create(outsideBack,clickBack);
            }else if(null != positiveButton){
                azDialog =  builder.setPositiveButton(positiveButton, onClickListener)
                        .create(outsideBack,clickBack);
            }else if(null != negativeButton){
                azDialog =  builder.setNegativeButton(negativeButton, onClickListener)
                        .create(outsideBack,clickBack);
            }
        }else{
            if(null != positiveButton && null != negativeButton){
                azDialog = builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create(outsideBack,clickBack);
            }else if(null != positiveButton){
                azDialog = builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create(outsideBack,clickBack);
            }else if(null != negativeButton){
                azDialog = builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create(outsideBack,clickBack);
            }
        }
        return azDialog;
    }
}

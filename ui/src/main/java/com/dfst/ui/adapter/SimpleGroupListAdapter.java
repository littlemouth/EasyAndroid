package com.dfst.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dfst.core.util.DensityUtil;
import com.dfst.ui.R;

/**
 * Created by yanfei on 2016-11-03.
 */
public class SimpleGroupListAdapter extends GroupListAdapter {

    private List<String> mNavigationItems;
    private List<Item> mData;
    private List<Item> originData;
    private Context mContext;

    private int headerColor;
    private int headerHeight, itemHeight, dividerHeight, dividerLeftMargin,
            dividerRightMargin, headIvLenght, headIvLeftMargin, nameTvLeftMargin;

    /**
     *
     * @param data
     */
    public SimpleGroupListAdapter(Context context, List<Item> data) {
        this.mData = new ArrayList<>();
        originData = data;
        mContext = context;
        generateAdapterData();
        initDimension();
    }

    @Override
    public boolean isGroup(int position) {
        return mData.get(position).type == ItemType.HEADER;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getTargetItemId(String navigation) {
        if ("↑".equals(navigation)) return 0;
        for (int index = 0; index < mData.size(); index++) {
            if (mData.get(index).type == ItemType.HEADER && mData.get(index).name.equals(navigation)) {
                return index;
            }
        }
        return -1;
    }

    public List<String> getNavigationItems() {
        return mNavigationItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = new LinearLayout(mContext);
            convertView.setBackgroundColor(Color.WHITE);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
            convertView.setLayoutParams(params);
            ((LinearLayout) convertView).setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            layoutParams.weight = 1;
            holder.layout = new LinearLayout(mContext);
            holder.layout.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(headIvLenght, headIvLenght);
            ivParams.gravity = Gravity.CENTER_VERTICAL;
            ivParams.leftMargin = headIvLeftMargin;
            holder.headIv = new ImageView(mContext);
            holder.headIv.setLayoutParams(ivParams);
            holder.headIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.layout.addView(holder.headIv);

            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvParams.weight = 1;
            tvParams.leftMargin = nameTvLeftMargin;
            tvParams.gravity = Gravity.CENTER_VERTICAL;
            holder.nameTv = new TextView(mContext);
            holder.nameTv.setLayoutParams(tvParams);
            holder.layout.addView(holder.nameTv);

            ((LinearLayout) convertView).addView(holder.layout);

            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight);
            dividerParams.leftMargin = dividerLeftMargin;
            dividerParams.rightMargin = dividerRightMargin;
            holder.divider = new View(mContext);
            holder.divider.setLayoutParams(dividerParams);
            holder.divider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.group_list_view_header_bg_ea_dfst));

            ((LinearLayout) convertView).addView(holder.divider);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setBackgroundColor(Color.WHITE);
        convertView.getLayoutParams().height = itemHeight;
        holder.headIv.setVisibility(View.VISIBLE);
        Item item = mData.get(position);
        if (item.type == ItemType.HEADER) {
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.group_list_view_header_bg_ea_dfst));
            holder.headIv.setVisibility(View.GONE);
            convertView.getLayoutParams().height = headerHeight;
        }
        holder.headIv.setImageResource(R.mipmap.default_head_icon);
        holder.nameTv.setText(mData.get(position).name);
        return convertView;
    }

    private static class ViewHolder {
        LinearLayout layout;
        ImageView headIv;
        TextView nameTv;
        View divider;
    }

    private void initDimension() {
        headerColor = ContextCompat.getColor(mContext, R.color.group_list_view_header_bg_ea_dfst);
        int unit = DensityUtil.dip2px(mContext, 1);
        headerHeight = 30 * unit;
        itemHeight = 50 * unit;
        headIvLenght = 40 * unit;
        dividerRightMargin = 20 * unit;
        dividerHeight = unit;
        dividerLeftMargin = nameTvLeftMargin = headIvLeftMargin
                = dividerLeftMargin = 15 * unit;

    }

    private List<Item> generateAdapterData() {
        mNavigationItems = new ArrayList<>();
        Collections.sort(originData);

        for (Item item : originData) {
            if (item.type == ItemType.TOP) {
                mNavigationItems.add(0, "↑");
                break;
            }
        }

        mNavigationItems.add("☆");
        mNavigationItems.add("A");
        mNavigationItems.add("B");
        mNavigationItems.add("C");
        mNavigationItems.add("D");
        mNavigationItems.add("E");
        mNavigationItems.add("F");
        mNavigationItems.add("G");
        mNavigationItems.add("H");
        mNavigationItems.add("I");
        mNavigationItems.add("J");
        mNavigationItems.add("K");
        mNavigationItems.add("L");
        mNavigationItems.add("M");
        mNavigationItems.add("N");
        mNavigationItems.add("O");
        mNavigationItems.add("P");
        mNavigationItems.add("Q");
        mNavigationItems.add("R");
        mNavigationItems.add("S");
        mNavigationItems.add("T");
        mNavigationItems.add("U");
        mNavigationItems.add("V");
        mNavigationItems.add("W");
        mNavigationItems.add("X");
        mNavigationItems.add("Y");
        mNavigationItems.add("Z");
        mNavigationItems.add("#");

        Map<String, List<Item>> map = new LinkedHashMap<>();
        for (String navigationItem : mNavigationItems) {
            map.put(navigationItem, new ArrayList<Item>());
        }

        for (Item item : originData) {
            if (item.type == ItemType.TOP) {
                map.get("↑").add(item);
                continue;
            }

            if (item.type == ItemType.STAR) {
                map.get("☆").add(item);
                continue;
            }

            String key = item.firstLetter();
            if (map.containsKey(key)) {
                map.get(key).add(item);
            } else {
                map.get(mNavigationItems.get(mNavigationItems.size() - 1)).add(item);
            }
        }
        mData.clear();
        for (String key : map.keySet()) {
            if (map.get(key).size() > 0 && !key.equals("↑")) {
                Item header = new Item(key);
                header.type = ItemType.HEADER;
                mData.add(header);
            }
            mData.addAll(map.get(key));
        }
        return mData;
    }

    public static class Item implements Comparable {
        public String name;
        public Uri uri;
        public ItemType type = ItemType.COMMON;
        String py;

        public Item(String name) {
            if (name == null || "".equals(name)) {
                try {
                    throw new Exception("Item.name can not be null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.name = name;
            py = "";
            for (char c : name.toCharArray()) {
                if (isChinese(c)) {
                    py += PinyinHelper.toHanyuPinyinStringArray(c)[0];
                    continue;
                }
                py += c;
            }
            py = py.toUpperCase();
        }

        public Item(String name, Uri uri) {
            this(name);
            this.uri = uri;
        }

        String firstLetter() {
            return String.valueOf(py.charAt(0));
        }

        @Override
        public int compareTo(Object obj) {
            Item item = (Item) obj;
            if (type == ItemType.TOP) {
                if (item.type != ItemType.TOP) return -1;
                if (item.type == ItemType.TOP) return  0;
            } else if (type == ItemType.STAR) {
                if (item.type == ItemType.TOP) return 1;
                if (item.type == ItemType.COMMON) return -1;
            }
            String left = name;
            String right = item.name;
            int length = left.length() > right.length() ? right.length() : left.length();
            for (int i = 0; i < length; i++) {
                char leftChar = left.charAt(i);
                char rightChar = right.charAt(i);
                char leftAlpha = '0', rightAlpha = '0';
                if (isChinese(leftChar)) {
                    if (isChinese(rightChar)) {
                        if (left.equals(right)) continue;
                        String leftPinyin = PinyinHelper.toHanyuPinyinStringArray(leftChar)[0];
                        String rightPinyin = PinyinHelper.toHanyuPinyinStringArray(rightChar)[0];
                        int len = leftPinyin.length() > rightPinyin.length() ? rightPinyin.length() : leftPinyin.length();
                        char lChar, rChar;
                        for (int j = 0; j < len; j++) {
                            lChar = leftPinyin.charAt(j);
                            rChar = rightPinyin.charAt(j);
                            if (lChar < rChar) return -1;
                            else if (lChar > rChar) return 1;
                        }
                        if (leftPinyin.length() < rightPinyin.length()) return -1;
                        else if (leftPinyin.length() > rightPinyin.length()) return 1;
                    } else if (isEnglish(rightChar)) {
                        leftAlpha = PinyinHelper.toHanyuPinyinStringArray(leftChar)[0].charAt(0);
                        rightAlpha = String.valueOf(rightChar).toLowerCase().charAt(0);
                        if (rightAlpha == leftAlpha) {
                            return -1;
                        }
                    } else {
                        return -1;
                    }

                } else if (isEnglish(leftChar)) {
                    leftAlpha = String.valueOf(leftChar).toLowerCase().charAt(0);
                    if (isChinese(rightChar)) {
                        rightAlpha = PinyinHelper.toHanyuPinyinStringArray(rightChar)[0].charAt(0);
                        if (rightAlpha == leftAlpha) {
                            return 1;
                        }
                    } else if (isEnglish(rightChar)) {
                        leftAlpha = leftChar;
                        rightAlpha = rightChar;
                    } else {
                        return -1;
                    }

                } else {
                    if (isEnglish(rightChar) || isChinese(rightChar)) {
                        return 1;
                    } else {
                        if (leftChar < rightChar) return -1;
                        else if (leftChar > rightChar) return 1;
                    }
                }

                if (leftAlpha < rightAlpha) return -1;
                else if (leftAlpha > rightAlpha) return 1;
            }
            if (left.length() < right.length())
                return -1;
            else if (left.length() > right.length())
                return 1;
            return 0;
        }

        private boolean isEnglish(char c){
            return String.valueOf(c).matches("^[a-zA-Z]*");
        }

        private boolean isChinese(char c){
            String regEx = "[\\u4e00-\\u9fa5]+";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(String.valueOf(c));
            if(m.find())
                return true;
            else
                return false;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        generateAdapterData();
        super.notifyDataSetChanged();
    }

    public enum ItemType {
        COMMON, TOP, HEADER, STAR
    }
}

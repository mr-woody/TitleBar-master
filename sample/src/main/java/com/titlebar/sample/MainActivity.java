package com.titlebar.sample;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.titlebar.sample.model.HomeCategory;
import com.titlebar.sample.popupwindow.HomeCategoryPopupWindow;
import com.titlebar.sample.util.ToastUtils;
import com.woodys.titlebar.TitleBar;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ImageView mCollectView;
    private boolean mIsSelected;

    private TitleBar mTitlebar;
    private HomeCategoryPopupWindow mCategoryPw;
    private List<HomeCategory> mHomeCategorys;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testView1();
        testView2();
    }

    /**
     * 测试各个标题文本的点击事件
     */
    private void testView1() {
        mTitlebar = (TitleBar) findViewById(R.id.titlebar);
        mTitlebar.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryPw();
            }
        });
        mTitlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void testView2() {
        final TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);

        titleBar.setBackgroundColor(Color.GREEN);

        titleBar.setLeftImageResource(R.mipmap.back_green);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleBar.setTitleText("正标题\n副标题");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);

        titleBar.setActionTextColor(Color.WHITE);
        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.mipmap.collect) {
            @Override
            public void performAction(View view) {
                Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                mCollectView.setImageResource(R.mipmap.fabu);
                titleBar.setTitleText(mIsSelected ? "变换主题\n变换副标题" : "不带副标题");
                mIsSelected = !mIsSelected;
            }
        });

        titleBar.addAction(new TitleBar.TextAction("分享") {
            @Override
            public void performAction(View view) {
                Toast.makeText(MainActivity.this, "点击了分享", Toast.LENGTH_SHORT).show();
            }
        });
        titleBar.addAction(new TitleBar.TextAction("查看") {
            @Override
            public void performAction(View view) {
                Toast.makeText(MainActivity.this, "点击了查看", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCategoryPw() {
        if (mCategoryPw == null) {
            mCategoryPw = new HomeCategoryPopupWindow(this, mTitlebar.getTitleText());
            mCategoryPw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ToastUtils.show("点击了返回");
                    ((CheckedTextView)mTitlebar.getTitleText()).setChecked(false);
                }
            });
            mCategoryPw.setDelegate(new HomeCategoryPopupWindow.HomeCategoryPopupWindowDelegate() {
                @Override
                public void onSelectCategory(HomeCategory category) {
                    ToastUtils.show("选择了分类：" + category.title);
                    mTitlebar.setTitleText(category.title);
                }
            });
        }

        if (mHomeCategorys == null) {
            mHomeCategorys = HomeCategory.getTestDatas();
        }
        mCategoryPw.setCategorys(mHomeCategorys);
        mCategoryPw.show();
        ((CheckedTextView)mTitlebar.getTitleText()).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}

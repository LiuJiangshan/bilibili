package ljs.bilibili.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ljs.bilibili.R;
import ljs.bilibili.entity.BLive;
import ljs.bilibili.entity.PlayUrl;
import ljs.bilibili.util.HttpUtils;
import ljs.lib.StringUtils;

public class BViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.iv_cover)
    AppCompatImageView iv_cover;
    @BindView(R.id.tv_uname)
    AppCompatTextView tv_uname;
    @BindView(R.id.tv_areaName)
    AppCompatTextView tv_areaName;
    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;
    @BindView(R.id.cv_face)
    CircleImageView cv_face;
    private BLive bLive;

    public BViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bindLive(BLive bLive) {
        this.bLive = bLive;
        Picasso.get()
                .load(bLive.getFace())
                .fit().centerCrop()
                .error(R.drawable.ic_error_24dp)
                .placeholder(R.drawable.animation_loading)
                .into(cv_face);

        String postUrl = bLive.getSystem_cover();
        if (StringUtils.isEmpty(postUrl))
            postUrl = bLive.getFace();
        if (!StringUtils.isEmpty(postUrl))
            Picasso.get().load(postUrl)
                    .fit().centerCrop()
                    .error(R.drawable.ic_error_24dp)
                    .placeholder(R.drawable.animation_loading)
                    .into(iv_cover);
        tv_areaName.setText(bLive.getAreaName());
        tv_title.setText(bLive.getTitle());
        tv_uname.setText(bLive.getUname());
    }

    @Override
    public void onClick(View v) {
        HttpUtils.bService.playUrl(bLive.getRoomid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<PlayUrl> playUrls = response.getData().getDurl();
                    if (!playUrls.isEmpty()) {
                        String[] urls = new String[playUrls.size()];
                        String[] titles = new String[playUrls.size()];
                        for (int i = 0; i < playUrls.size(); i++) {
                            PlayUrl playUrl = playUrls.get(i);
                            urls[i] = playUrl.getUrl();
                            titles[i] = "播放源" + i;
                        }
                        new AlertDialog.Builder(v.getContext())
                                .setItems(titles, (dialog, which) -> playerChooseDialog(v.getContext(), Uri.parse(urls[which])))
                                .show();
                    }
                });
    }

    private List<ComponentName> getAvailablePlayer(Context context) {
        List<ComponentName> availablePlayer = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        for (ComponentName player : videoPlayer) {
            try {
                packageManager.getActivityInfo(player, 0);
                availablePlayer.add(player);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return availablePlayer;
    }

    final ComponentName[] videoPlayer = new ComponentName[]{
            new ComponentName("org.videolan.vlc", "org.videolan.vlc.gui.video.VideoPlayerActivity"),
            new ComponentName("com.mxtech.videoplayer.ad", "com.mxtech.videoplayer.ad.ActivityScreen"),
            new ComponentName("com.mxtech.videoplayer.pro", "com.mxtech.videoplayer.pro.ActivityScreen"),
    };

    private String getAppName(Context context, ComponentName componentName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(componentName.getPackageName(), 0);
            return packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return componentName.getPackageName();
        }
    }

    private void playerChooseDialog(Context context, Uri uri) {
        List<ComponentName> availablePlayer = getAvailablePlayer(context);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setDataAndType(uri, "video/*");

        if (availablePlayer.isEmpty())
            new AlertDialog.Builder(context).setMessage("你未安装视频播放器,目前支持:vlc、mx ad、mx pro").show();
        else if (availablePlayer.size() == 1) {
            intent.setComponent(availablePlayer.get(0));
            context.startActivity(intent);
        } else {
            String[] playerNames = new String[availablePlayer.size()];
            for (int i = 0; i < availablePlayer.size(); i++)
                playerNames[i] = getAppName(context, availablePlayer.get(i));
            new AlertDialog.Builder(context)
                    .setTitle("推荐使用vlc")
                    .setItems(playerNames, (dialog, which) -> {
                        intent.putExtra("title", bLive.getTitle());
                        intent.setComponent(availablePlayer.get(which));
                        context.startActivity(intent);
                    }).show();
        }
    }
}

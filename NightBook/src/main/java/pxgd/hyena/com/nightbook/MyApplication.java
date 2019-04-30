package pxgd.hyena.com.nightbook;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    /**
     * ImageLoader是一个单例，因此它需在App启动时就进行实例和初始化
     */
    private void initImageLoader(){

        //获取上下文（缓存图片的目录）
        Context context = getApplicationContext();
        File cacheDir = StorageUtils.getCacheDirectory(context);


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(
                        480,
                        800) //缓存图片的最大长宽
                .diskCacheExtraOptions(
                        480,
                        800,
                        null
                )
                .threadPriority(Thread.NORM_PRIORITY - 2) //任务线程在系统中的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory() //内存中拒绝存储，同内容但尺寸不同的图像
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiskCache(cacheDir)) //缓存大小没有限制
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .imageDecoder(new BaseImageDecoder(false))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();//创建它

        //调用getInstance()方法，获取一个单例（初始化配置）
        ImageLoader.getInstance().init(config);
    }



}

package com.barry.demo.color_img_android_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.barry.demo.color_img_android_project.ImageLoader;
import com.barry.demo.color_img_android_project.R;
import com.barry.demo.color_img_android_project.application.AppApplication;
import com.barry.demo.color_img_android_project.network.ColorImgModel;


public class PhotoListAdapter extends PagedListAdapter<ColorImgModel, PhotoListAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater layoutInflater;

    public ImageLoader imageLoader;

    public PhotoListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(AppApplication.getINSTANCE());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_photo_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            ColorImgModel colorImgModel = getItem(position);
            holder.id.setText(colorImgModel.getId() + "");
            holder.title.setText(colorImgModel.getTitle());
            imageLoader.DisplayImage(colorImgModel.getThumbnailUrl(), holder.imageView);
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder().url(colorImgModel.getThumbnailUrl()).build();
//            client.newCall(request).enqueue(new Callback() {
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Fail" + response);
//                    }
//                    InputStream inputStream = response.body().byteStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            holder.imageView.setImageBitmap(bitmap);
//                        }
//                    });
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DiffUtil.ItemCallback<ColorImgModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ColorImgModel>() {
                @Override
                public boolean areItemsTheSame(ColorImgModel oldItem, ColorImgModel newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(ColorImgModel oldItem, ColorImgModel newItem) {
                    return oldItem == newItem;
                }
            };

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView id,title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.layout_photo_imageview);
            id = itemView.findViewById(R.id.layout_photo_id_textview);
            title = itemView.findViewById(R.id.layout_photo_title_textview);
        }
    }

}

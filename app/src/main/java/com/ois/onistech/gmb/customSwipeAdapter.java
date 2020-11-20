package com.ois.onistech.gmb;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ois.onistech.gmb.DataClass.Banner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class customSwipeAdapter extends PagerAdapter {

  // to get images from drwable folder
  private String[] image_resourses ;

  private Context ctx;


  private LayoutInflater layoutInflater;
  private List<Banner> sliderImg;

  public customSwipeAdapter(List<Banner> sliderImg, Context ctx ){
     this.sliderImg=sliderImg;
      this.ctx =ctx;

     /* for(int i=0;i<sliderImg.size();i++)
      {
          Toast.makeText(ctx, ""+sliderImg.get(i).getSliderImageurls(), Toast.LENGTH_SHORT).show();
      }*/
      //this.image_resourses=image_resourses;
  }

    @Override
    public int getCount() {

     // return image_resourses.length;
      //to return no. of images u have
        return sliderImg.size();

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //return (view ==(LinearLayout)object);
        return  view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
      //return object each of the swipped view
       //Here container is the View Pager
        Banner utils = sliderImg.get(position);
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        final ImageView imageView =(ImageView)item_view.findViewById(R.id.imageView);
       // CardView c1 =(CardView) item_view.findViewById(R.id.card_view);


        // ImageView imageView = new ImageView(ctx);
       //Picasso.get().load(image_resourses[position]).fit().centerCrop().into(imageView);
        Picasso.get().load(sliderImg.get(position).getB_image()).fit().into(imageView);
      //  TextView textView=(TextView)item_view.findViewById(R.id.image_count);
       // imageView.setImageResource(sliderImg.get(position).sliderImageurls);
//        textView.setText("Image"+position);
        if(imageView.getParent() != null) {
            ((ViewGroup)imageView.getParent()).removeView(imageView); // <- fix
        }

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                Picasso.get().load(sliderImg.get(position).getB_image()).fit().into(imageView);
                i++;
                if (i > sliderImg.size() - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
        container.addView(imageView);

        return imageView;
    }



    // To destroy the previous slide
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

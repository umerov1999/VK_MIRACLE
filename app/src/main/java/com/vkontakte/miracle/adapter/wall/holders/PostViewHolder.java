package com.vkontakte.miracle.adapter.wall.holders;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.vkontakte.miracle.engine.util.ColorUtil.getColorByAttributeId;
import static com.vkontakte.miracle.engine.util.NetworkUtil.validateBody;
import static com.vkontakte.miracle.engine.util.StringsUtil.reduceTheNumber;

import android.animation.LayoutTransition;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.transition.ChangeTransform;

import com.squareup.picasso.Picasso;
import com.vkontakte.miracle.R;
import com.vkontakte.miracle.engine.adapter.holder.ItemDataHolder;
import com.vkontakte.miracle.engine.adapter.holder.MiracleViewHolder;
import com.vkontakte.miracle.engine.adapter.holder.ViewHolderFabric;
import com.vkontakte.miracle.engine.async.AsyncExecutor;
import com.vkontakte.miracle.engine.util.TimeUtil;
import com.vkontakte.miracle.engine.view.PostTextView;
import com.vkontakte.miracle.engine.view.photoGridView.PhotoGridView;
import com.vkontakte.miracle.model.Attachments;
import com.vkontakte.miracle.model.Owner;
import com.vkontakte.miracle.model.users.ProfileItem;
import com.vkontakte.miracle.model.wall.PostItem;
import com.vkontakte.miracle.network.methods.Likes;

import org.json.JSONObject;

import retrofit2.Response;

public class PostViewHolder extends MiracleViewHolder {

    private final ImageView imageView;
    private PostTextView text;
    private final ViewStub textStub;
    private final TextView title;
    private final TextView date;
    private final ViewStub verifiedStub;
    private PhotoGridView photoGridView;
    private CardView photoGridViewHolder;
    private final ViewStub photosViewStub;
    private ImageView verified;

    private final LinearLayout likesHolder;
    private final LinearLayout commentsHolder;
    private final LinearLayout repostsHolder;
    private final LinearLayout viewsHolder;
    private final ImageView likesIcon;
    private final TextView likesText;
    private final TextView commentsText;
    private final TextView repostsText;
    private final TextView viewsText;

    private PostItem postItem;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.photo);
        title = itemView.findViewById(R.id.title);
        date = itemView.findViewById(R.id.date);
        verifiedStub = itemView.findViewById(R.id.verifiedStub);
        textStub = itemView.findViewById(R.id.textStub);
        photosViewStub = itemView.findViewById(R.id.photosViewStub);

        likesHolder = itemView.findViewById(R.id.likesHolder);
        commentsHolder = itemView.findViewById(R.id.commentsHolder);
        repostsHolder = itemView.findViewById(R.id.repostsHolder);
        viewsHolder = itemView.findViewById(R.id.viewsHolder);

        likesIcon = likesHolder.findViewById(R.id.likesIcon);
        likesText = likesHolder.findViewById(R.id.likesText);
        commentsText = commentsHolder.findViewById(R.id.commentsText);
        repostsText = repostsHolder.findViewById(R.id.repostsText);
        viewsText = viewsHolder.findViewById(R.id.viewsText);

        likesHolder.setOnClickListener(v -> {
            if(postItem!=null){
                final PostItem finalPostItem = PostViewHolder.this.postItem;
                if(postItem.getUserLikes()){
                    postItem.setUserLikes(false);
                    colorLikesButton(true);
                    postItem.setLikesCount(Math.max(0,postItem.getLikesCount()-1));
                    updateLikesCount(true);
                    new AsyncExecutor<Integer>(){
                        @Override
                        public Integer inBackground() {
                            try {
                                ProfileItem profileItem = getMiracleActivity().getUserItem();
                                Response<JSONObject> response =  Likes.delete("post", postItem.getId(),
                                        postItem.getOwner().getId(),profileItem.getAccessToken()).execute();
                                JSONObject jsonObject = validateBody(response).getJSONObject("response");
                                return jsonObject.getInt("likes");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return -1;
                        }
                        @Override
                        public void onExecute(Integer object) {
                            if(finalPostItem.getId().equals(postItem.getId())){
                                postItem.setLikesCount(object);
                                updateLikesCount(true);
                            }
                        }
                    }.start();
                } else {
                    postItem.setUserLikes(true);
                    colorLikesButton(true);
                    postItem.setLikesCount(postItem.getLikesCount()+1);
                    updateLikesCount(true);
                    new AsyncExecutor<Integer>(){
                        final PostItem finalPostItem = PostViewHolder.this.postItem;
                        @Override
                        public Integer inBackground() {
                            try {
                                ProfileItem profileItem = getMiracleActivity().getUserItem();
                                Response<JSONObject> response =  Likes.add("post", postItem.getId(),
                                        postItem.getOwner().getId(),profileItem.getAccessToken()).execute();
                                JSONObject jsonObject = validateBody(response).getJSONObject("response");
                                return jsonObject.getInt("likes");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return -1;
                        }
                        @Override
                        public void onExecute(Integer object) {
                            if(finalPostItem.getId().equals(postItem.getId())){
                                postItem.setLikesCount(object);
                                updateLikesCount(true);
                            }
                        }
                    }.start();
                }
            }
        });

    }

    @Override
    public void bind(ItemDataHolder itemDataHolder) {

        postItem = (PostItem) itemDataHolder;

        Owner owner = postItem.getOwner();

        title.setText(owner.getName());

        date.setText(TimeUtil.getPostDateString(postItem.getDate(),itemView.getContext()));

        if(!owner.getPhoto200().isEmpty()) {
            Picasso.get().load(owner.getPhoto200()).noFade().into(imageView);
        }

        if(owner.isVerified()){
            if(verified==null) {
                if(verifiedStub!=null) {
                    verified = (ImageView) verifiedStub.inflate();
                } else {
                    verified = itemView.findViewById(R.id.verified);
                }
            }
            if(verified.getVisibility()!=VISIBLE) {
                verified.setVisibility(VISIBLE);
            }
        } else {
            if(verified!=null&&verified.getVisibility()!=GONE){
                verified.setVisibility(GONE);
            }
        }

        if(!postItem.getText().isEmpty()){
            if(text==null) {
                if(textStub!=null) {
                    text = (PostTextView) textStub.inflate();
                } else {
                    text = itemView.findViewById(R.id.text);
                }
                text.setParent((ViewGroup) itemView.getParent());
            }
            if(text.getVisibility()!=VISIBLE) {
                text.setVisibility(VISIBLE);
            }
            text.setText(postItem.getText(),postItem.getZoomText());
        } else {
            if(text!=null&&text.getVisibility()!=GONE){
                text.setText("",false);
                text.setVisibility(GONE);
            }
        }

        if(postItem.getAttachments()!=null){
            Attachments attachments = postItem.getAttachments();
            if(!attachments.getMediaItems().isEmpty()){
                if(photoGridView==null) {
                    if(photosViewStub!=null) {
                        photoGridViewHolder = (CardView) photosViewStub.inflate();
                    } else {
                        photoGridViewHolder = itemView.findViewById(R.id.photosView);
                    }
                    photoGridView = (PhotoGridView) photoGridViewHolder.getChildAt(0);
                }
                if(photoGridViewHolder.getVisibility()!=VISIBLE) {
                    photoGridViewHolder.setVisibility(VISIBLE);
                }
                photoGridView.setPhotos(attachments.getMediaItems());
            } else {
                if(photoGridViewHolder!=null&&photoGridViewHolder.getVisibility()!=GONE){
                    photoGridViewHolder.setVisibility(GONE);
                }
            }
        } else {
            if(photoGridViewHolder!=null&&photoGridViewHolder.getVisibility()!=GONE){
                photoGridViewHolder.setVisibility(GONE);
            }
        }

        bindButtons(postItem);

    }

    private void bindButtons(PostItem postItem){

        updateLikesCount();

        colorLikesButton(false);

        if(postItem.getCommentsCount()>0){
            if(commentsHolder.getVisibility()!=VISIBLE){
                commentsHolder.setVisibility(VISIBLE);
            }
            if(commentsText.getVisibility()!=VISIBLE){
                commentsText.setVisibility(VISIBLE);
            }
            commentsText.setText(reduceTheNumber(postItem.getCommentsCount()));
        } else {
            if(!postItem.getCanPostComments()&&!postItem.getCanViewComments()){
                if(commentsHolder.getVisibility()!=GONE){
                    commentsHolder.setVisibility(GONE);
                }
            } else {
                if(commentsHolder.getVisibility()!=VISIBLE){
                    commentsHolder.setVisibility(VISIBLE);
                }
                if (commentsText.getVisibility() != GONE) {
                    commentsText.setVisibility(GONE);
                }
            }
        }

        if(postItem.getRepostsCount()>0){
            if(repostsText.getVisibility()!=VISIBLE){
                repostsText.setVisibility(VISIBLE);
            }
            repostsText.setText(reduceTheNumber(postItem.getRepostsCount()));
        } else {
            if(repostsText.getVisibility()!=GONE){
                repostsText.setVisibility(GONE);
            }
        }

        viewsText.setText(reduceTheNumber(postItem.getViewsCount()));
    }

    private void updateLikesCount(){
        updateLikesCount(false);
    }

    private void updateLikesCount(boolean animate){


        if(animate){
            //LayoutTransition layoutTransition = new LayoutTransition();
            //layoutTransition.setDuration(200);
            //layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
            //((ViewGroup)likesHolder.getParent()).setLayoutTransition(layoutTransition);
            Transition transition = new android.transition.Slide();
            transition.setDuration(200);
            TransitionManager.beginDelayedTransition((ViewGroup)likesHolder.getParent().getParent(), transition);
        }

        if(postItem.getLikesCount()>0){
            if(likesText.getVisibility()!=VISIBLE){
                likesText.setVisibility(VISIBLE);
            }
            likesText.setText(reduceTheNumber(postItem.getLikesCount()));
        } else {
            if(likesText.getVisibility()!=GONE){
                likesText.setVisibility(GONE);
            }
        }
    }

    private void colorLikesButton(){
        colorLikesButton(false);
    }

    private void colorLikesButton(boolean animate){
        if(postItem.getUserLikes()){
            likesIcon.setImageDrawable(ResourcesCompat.getDrawable(getMiracleApp().getResources(),
                    R.drawable.ic_like_filled_24, getMiracleApp().getTheme()));
            int color = ResourcesCompat.getColor(getMiracleApp().getResources(),
                    R.color.like_half,itemView.getContext().getTheme());
            PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
            likesHolder.getBackground().setColorFilter(colorFilter);
            color = ResourcesCompat.getColor(getMiracleApp().getResources(),
                    R.color.like,itemView.getContext().getTheme());
            likesText.setTextColor(color);
        } else {
            likesIcon.setImageDrawable(ResourcesCompat.getDrawable(getMiracleApp().getResources(),
                    R.drawable.ic_like_24, getMiracleApp().getTheme()));
            int color = getColorByAttributeId(itemView.getContext(),R.attr.colorContrast10);
            PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
            likesHolder.getBackground().setColorFilter(colorFilter);
            color = getColorByAttributeId(itemView.getContext(),R.attr.colorContrast60);
            likesText.setTextColor(color);
        }
    }

    public static class Fabric implements ViewHolderFabric {
        @Override
        public MiracleViewHolder create(LayoutInflater inflater, ViewGroup viewGroup) {
            return new PostViewHolder(inflater.inflate(R.layout.view_post_item_1, viewGroup, false));
        }
    }

    public PostItem getPostItem() {
        return postItem;
    }
}

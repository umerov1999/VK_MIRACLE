package com.vkontakte.miracle.model.photos;

import androidx.collection.ArrayMap;

import com.vkontakte.miracle.engine.adapter.holder.ItemDataHolder;
import com.vkontakte.miracle.model.photos.fields.Size;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.vkontakte.miracle.engine.adapter.holder.ViewHolderTypes.TYPE_PHOTO_ALBUM;

public class PhotoAlbumItem implements ItemDataHolder {

    private final String id;
    private final String ownerId;
    private final String title;
    private final int size;
    private ArrayMap<String, Size> sizes;

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public ArrayMap<String, Size> getSizes() {
        return sizes;
    }

    public PhotoAlbumItem(JSONObject jsonObject) throws JSONException{
        id = jsonObject.getString("id");
        ownerId = jsonObject.getString("owner_id");
        title = jsonObject.getString("title");
        size = jsonObject.getInt("size");
        if(jsonObject.has("sizes")){
            sizes = new ArrayMap<>();
            JSONArray jSizes = jsonObject.getJSONArray("sizes");
            for(int i=0;i<jSizes.length();i++){
                Size size = new Size(jSizes.getJSONObject(i));
                sizes.put(size.getType(),size);
            }
        }
    }

    @Override
    public int getViewHolderType() {
        return TYPE_PHOTO_ALBUM;
    }
}

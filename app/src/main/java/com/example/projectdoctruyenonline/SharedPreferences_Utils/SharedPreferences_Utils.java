package com.example.projectdoctruyenonline.SharedPreferences_Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.models.Chapter;
import com.example.projectdoctruyenonline.models.Contents;
import com.example.projectdoctruyenonline.models.Story;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferences_Utils {
    public static Context mcontext;
    public static SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefsSetting1111";
    public static final String Color = "color_Key";
    public static final String ChangeTextColor = "change_text_color_Key";
    public static final String SaveChapterWatched = "save_chapterWatched_key";
    public static final String SaveStoryWatched = "save_storyWatched_key";
    public static final String Size = "size_Key";
    public static final String Font = "font_Key";
    public static final int Default = 0;
    public static final String ButtonChangeBackgroundColor = "button_color_Key";
    public static final String ButtonChangeScreenTimeOut = "button_screen_time_out_Key";
    public static final String ButtonChangeTextSize = "button_text_size_Key";
    public static final String ButtonChangeFontStyle = "button_font_style_Key";
    public static final String ButtonChangeReadStyle = "button_read_style_Key";
    public static final String ButtonChangeLineHeight = "button_line_height_Key";
    public static final String ButtonChangeAutoScroll = "button_auto_Scroll_Key";
    public static final String SaveButtonStory = "save_button_story_key";
    public static final String LineHeight = "line_height_key";
    public static final String ScreenTimeOut = "screentimeout_Key";
    public static final String SeeBar = "see_bar_Key";
    public static final int Default_SeekBar = 1;

    public SharedPreferences_Utils(Context context) {
        mcontext = context;
        sharedPreferences = mcontext.getSharedPreferences(MyPREFERENCES, mcontext.MODE_PRIVATE);
    }

    public void setDataSharePreferences_From_Chapter(Chapter chapter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonStoryWatched = sharedPreferences.getString(SaveChapterWatched, null);
        ArrayList<Chapter> chapterArrayList = new ArrayList<>();
        if (jsonStoryWatched != null) {
            Type type = new TypeToken<ArrayList<Chapter>>() {
            }.getType();
            chapterArrayList = gson.fromJson(jsonStoryWatched, type);
        }

        Chapter chapterSave = new Chapter(chapter.getIdStory(), chapter.getTitle(),
                chapter.getChapter_id(), chapter.getContents());

        int indexChapter = findIndexChapter(chapterArrayList, chapter.getChapter_id());
        if (indexChapter >= 0) {
            chapterArrayList.remove(indexChapter);
        }
        Log.d("21321321", chapter.getContents() + "");

        chapterArrayList.add(0, chapterSave);
        String json = gson.toJson(chapterArrayList);
        editor.putString(SaveChapterWatched, json);
        editor.apply();
        Log.d("ádasdasdasd", String.valueOf(json));

    }

    public List<Story> get_SharedPreferences_Story_FollowFragment() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ADDFOLLOW", null);
        Type type = new TypeToken<ArrayList<Story>>() {
        }.getType();
        List<Story> storyArrayList = gson.fromJson(json, type);

        if (storyArrayList == null) {
            storyArrayList = new ArrayList<>();
        }
        return storyArrayList;

    }

    public void deleteStoryFromFollow(int nID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        ArrayList<Story> storyArrayListWatched = new ArrayList<>();
        String jsonStoryWatched = sharedPreferences.getString("ADDFOLLOW", null);
        if (jsonStoryWatched != null) {
            Type type = new TypeToken<ArrayList<Story>>() {
            }.getType();
            storyArrayListWatched = gson.fromJson(jsonStoryWatched, type);
        }
        int index = findIndexStory(storyArrayListWatched, nID);

            storyArrayListWatched.remove(index);

//        storyArrayListWatched.add(0, story);
//        if (storyArrayListWatched.size() > 7) {
//            storyArrayListWatched.remove(storyArrayListWatched.size() - 1);
//        }
        String json = gson.toJson(storyArrayListWatched);
        editor.putString("ADDFOLLOW", json);
        editor.apply();
        Log.d("98765", String.valueOf(json));

    }

    public void addStoriesToFollow(Story story) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        ArrayList<Story> storyArrayListWatched = new ArrayList<>();
        String jsonStoryWatched = sharedPreferences.getString("ADDFOLLOW", null);
        if (jsonStoryWatched != null) {
            Type type = new TypeToken<ArrayList<Story>>() {
            }.getType();
            storyArrayListWatched = gson.fromJson(jsonStoryWatched, type);
        }
        int index = findIndexStory(storyArrayListWatched, story.getId());
        if (index > -1) {
            storyArrayListWatched.remove(index);
        }
        storyArrayListWatched.add(0, story);
//        if (storyArrayListWatched.size() > 7) {
//            storyArrayListWatched.remove(storyArrayListWatched.size() - 1);
//        }
        String json = gson.toJson(storyArrayListWatched);
        editor.putString("ADDFOLLOW", json);
        editor.apply();

    }

    public void setDataSharePreferences_From_StoryWatched(Story story) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        ArrayList<Story> storyArrayListWatched = new ArrayList<>();
        String jsonStoryWatched = sharedPreferences.getString(SaveStoryWatched, null);
        if (jsonStoryWatched != null) {
            Type type = new TypeToken<ArrayList<Story>>() {
            }.getType();
            storyArrayListWatched = gson.fromJson(jsonStoryWatched, type);
        }
        int index = findIndexStory(storyArrayListWatched, story.getId());
        if (index > -1) {
            storyArrayListWatched.remove(index);
        }
        storyArrayListWatched.add(0, story);
        if (storyArrayListWatched.size() > 7) {
            storyArrayListWatched.remove(storyArrayListWatched.size() - 1);
        }
        String json = gson.toJson(storyArrayListWatched);
        editor.putString(SaveStoryWatched, json);
        editor.apply();
        Log.d("98765", String.valueOf(json));

    }

    private int findIndexStory(ArrayList<Story> storyArrayList, int idStory) {
        for (int i = 0; i < storyArrayList.size(); i++) {
            if (storyArrayList.get(i).getId() == idStory) {
                return i;
            }
        }
        return -1;
    }

    private int findIndexChapter(ArrayList<Chapter> chapterArrayList, int idChapter) {
        for (int i = 0; i < chapterArrayList.size(); i++) {
            if (chapterArrayList.get(i).getChapter_id() == idChapter) {
                return i;
            }
        }
        return -1;
    }

    private boolean hasStoryInList(ArrayList<Story> storyArrayListWatched, int idStory) {
        for (int i = 0; i < storyArrayListWatched.size(); i++) {
            if (storyArrayListWatched.get(i).getId() == idStory) {
                return true;
            }
        }
        return false;
    }

    private boolean removeChapterInList(ArrayList<Chapter> chapterArrayList, int idChapter) {
        for (int i = 0; i < chapterArrayList.size(); i++) {
            if (chapterArrayList.get(i).getChapter_id() == idChapter) {
                return true;
            }
        }
        return false;
    }

    public List<Story> get_SharedPreferences_Story_HistoryFragment() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SaveStoryWatched, null);
        Type type = new TypeToken<ArrayList<Story>>() {
        }.getType();
        List<Story> storyArrayList = gson.fromJson(json, type);
        Log.d("get_tasksssslist", String.valueOf(json));
        if (storyArrayList == null) {
            storyArrayList = new ArrayList<>();
        }
        return storyArrayList;

    }

    public ArrayList<Chapter> getDataSharePreferences_From_ChapterWatched(ArrayList<Chapter> chapterList, int nID) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SaveChapterWatched, null);
        Type type = new TypeToken<ArrayList<Chapter>>() {
        }.getType();//-> Array_list
        ArrayList<Chapter> list = gson.fromJson(json, type);
        if (list != null) {
            chapterList = chapterOfStory(list, nID);
        }
        return chapterList;
    }

    private ArrayList<Chapter> chapterOfStory(ArrayList<Chapter> chapterArrayList, int idStory) {
        ArrayList<Chapter> arrayList_Chapter = new ArrayList();
        for (int i = 0; i < chapterArrayList.size(); i++) {
            if (chapterArrayList.get(i).getIdStory() == idStory) {
                arrayList_Chapter.add(chapterArrayList.get(i));
                if (arrayList_Chapter.size() > 7) {
                    arrayList_Chapter.remove(arrayList_Chapter.size() - 1);
                }
            }
        }
        return arrayList_Chapter;
    }

    private boolean hasChapterInList(ArrayList<Chapter> chapterArrayListWatched, int idChapter) {
        for (int i = 0; i < chapterArrayListWatched.size(); i++) {
            if (chapterArrayListWatched.get(i).getChapter_id() == idChapter) {
                return true;
            }
        }
        return false;
    }

    private static void setSharedPreferences_Float(String keyName, float s) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(keyName, s);
        editor.apply();
    }

    public static void setSharedPreferences_Int(String keyName, int id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(keyName, id);
        editor.apply();
    }

    public static void setSharedPreferences_String(String keyName, String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyName, id);
        editor.apply();
    }

    public static void setSeeBar(int seeBar) {
        setSharedPreferences_Int(SeeBar, seeBar);
    }

    public static int getSeeBar() {
        int selectedButtonColor = sharedPreferences.getInt(SeeBar, Default_SeekBar);
        return selectedButtonColor;
    }

    public static void setChangeTextColor(int changeTextColor) {
        setSharedPreferences_Int(ChangeTextColor, changeTextColor);
    }

    public static int getChangeTextColor() {

        int selectedButtonColor = sharedPreferences.getInt(ChangeTextColor, android.graphics.Color.BLACK);
        return selectedButtonColor;
    }

    public static void setColorBackgroundReadStory(int color) {
        setSharedPreferences_Int(Color, color);
    }

    public static int getColorBackgroundReadStory() {
        int selectedColor = sharedPreferences.getInt(Color, mcontext.getResources().getColor(R.color.colorWhite));
        return selectedColor;
    }

    public static void setTextSizeReadStory(float size) {
        setSharedPreferences_Float(Size, size);

    }

    public static float getTextSizeReadStory() {
        float selectedSize = sharedPreferences.getFloat(Size, Commons.size_default);
        return selectedSize;
    }

    public static void setFontReadStory(int font) {
        setSharedPreferences_Int(Font, font);
    }

    public static int getFontReadStory() {
        int selectedFont = sharedPreferences.getInt(Font, R.font.arial);
        return selectedFont;
    }

    public static void setScreenTimeOutReadStory(int timeOut) {
        setSharedPreferences_Int(ScreenTimeOut, timeOut);
    }

    public static int getScreenTimeOutReadStory() {
        int selectedScreenTimeOut = sharedPreferences.getInt(ScreenTimeOut, Commons.DEFAULT_SECOUND);
        return selectedScreenTimeOut;
    }

    public static void setLineHeight(float lineHeight) {
        setSharedPreferences_Float(LineHeight, lineHeight);
    }

    public static float getLineHeight() {
        float selectedLineHeight = sharedPreferences.getFloat(LineHeight, 35);
        return selectedLineHeight;
    }

    public static void setButtonChangeLineHeight(int buttonChangeLineHeight) {
        setSharedPreferences_Int(ButtonChangeLineHeight, buttonChangeLineHeight);
    }

    public static int getButtonChangeLineHeight() {
        int selectedButtonColor = sharedPreferences.getInt(ButtonChangeLineHeight, 60);

        return selectedButtonColor;
    }

    public static void setButtonChangeAutoScroll(int buttonChangeAutoScroll) {
        setSharedPreferences_Int(ButtonChangeAutoScroll, buttonChangeAutoScroll);
    }

    public static int getButtonChangeAutoScroll() {
        int selectedButtonColor = sharedPreferences.getInt(ButtonChangeAutoScroll, Default);
        return selectedButtonColor;
    }

    public static void setButtonChangeReadStyle(int buttonReadStyle) {
        setSharedPreferences_Int(ButtonChangeReadStyle, buttonReadStyle);
    }

    public static int getButtonChangeReadStyle() {
        int selectedButtonColor = sharedPreferences.getInt(ButtonChangeReadStyle, Default);

        return selectedButtonColor;
    }

    public static void setButtonChangeFontStyle(int buttonFontStyle) {
        setSharedPreferences_Int(ButtonChangeFontStyle, buttonFontStyle);
    }

    public static int getButtonChangeFontStyle() {
        int selectedButtonColor = sharedPreferences.getInt(ButtonChangeFontStyle, Default);
        return selectedButtonColor;
    }

    public static void setButtonChangeTextSize(int buttonTextSize) {
        setSharedPreferences_Int(ButtonChangeTextSize, buttonTextSize);
    }

    public static int getButtonChangeTextSize() {
        int selectedButtonColor = sharedPreferences.getInt(ButtonChangeTextSize, Default);
        return selectedButtonColor;
    }

    public static void setButtonChangeColorBackgroundSetting(int buttonColor) {
        setSharedPreferences_Int(ButtonChangeBackgroundColor, buttonColor);
    }

    public static int getButtonChangeColorBackgroundSetting() {
        int selectedButtonColor = sharedPreferences.getInt(ButtonChangeBackgroundColor, Default);
        return selectedButtonColor;
    }

    public static void setButtonChangeScreenTimeOut(int buttonScreenTimeOut) {
        setSharedPreferences_Int(ButtonChangeScreenTimeOut, buttonScreenTimeOut);

    }

    public static int getButtonChangeScreenTimeOut() {
        int selectedButtonColor = sharedPreferences.getInt(ButtonChangeScreenTimeOut, Default);
        return selectedButtonColor;
    }

}

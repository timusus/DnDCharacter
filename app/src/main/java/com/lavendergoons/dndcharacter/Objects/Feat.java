package com.lavendergoons.dndcharacter.Objects;

/**
 * Simple Feat object
 */

public class Feat {
    public static final String FEAT = "Feat";
    public static final String LANGUAGE = "Language";
    public static final String SPECIAL_ABILITIES = "Special Ability";

    public static final int FEAT_INDEX = 0;
    public static final int SPEC_INDEX = 1;
    public static final int LANG_INDEX = 2;

    private String mType;
    private String mContent;

    public Feat(String mType, String mContent) {
        this.mType = mType;
        this.mContent = mContent;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}

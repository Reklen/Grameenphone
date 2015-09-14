package com.cc.grameenphone.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by sudendra on 24/1/15.
 */
public final class PreferenceManager {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public PreferenceManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("yoopinePrefs", Context.MODE_PRIVATE);
    }

    private SharedPreferences getPref() {
        return mSharedPreferences;
    }


    public void setProfileURL(String url) {
        Logger.d("Preference manager ", "user url   " + url);
        mSharedPreferences.edit().putString("url_profile", url).commit();
    }

    public String getProfileURL() {
        return mSharedPreferences.getString("url_profile", "");
    }

    public void signupComplete(boolean s) {
        Logger.d("Preference manager ", "user signup boolean   " + s);
        mSharedPreferences.edit().putBoolean("signup_done", s).commit();
    }

    public Boolean getSignupComplete() {
        return mSharedPreferences.getBoolean("signup_done", false);
    }

    public void setName(String n) {
        Logger.d("Preference manager ", "name    " + n);
        mSharedPreferences.edit().putString("name", n).commit();
    }

    public String getName() {
        return mSharedPreferences.getString("name", "");
    }

    public void setEmail(String e) {
        Logger.d("Preference manager ", "email    " + e);
        mSharedPreferences.edit().putString("email", e).commit();
    }

    public String getEmail() {
        return mSharedPreferences.getString("email", "");
    }

    public void setAuthToken(String e) {
        Logger.d("Preference manager ", "auth Token    " + e);
        mSharedPreferences.edit().putString("auth_token", e).commit();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString("auth_token", "");
    }


    public void clearAll() {
        setName("");
        setProfileURL("");
        setEmail("");
        setAuthToken("");


        setReligion("");
        setFirst_name("");
        setLast_name("");
        setApt_number("");
        setState("");
        setAge_group("");
        setZipCode("");
        setScreen_name("");
        setStreet_address("");
        setParty_affiliation("");
        setCity("");
        setPresident("");
        setVicePresident("");
        setSenator1("");
        setSenator2("");
        setMyHouseReps("");
        setPresidentName("");
        setVicePresidentName("");
        setSenator1Name("");
        setSenator2Name("");
        setMyHouseRepsName("");
        setSplashIntroSkip(false);
        signupComplete(false);

        setMatchMyVotesSkip(false);
        setRepresentativesDetailSkip(false);
        setCreateInitiativeSkip(false);
        setInitiativeSkip(false);
        setVoiceOpinionBarSkip(false);
        setBillsDetailTipSkip(false);
        setFedBillsTipSkip(false);
        setHomeTipSkip(false);

    }

    public String getReligion() {
        return mSharedPreferences.getString("religion", "");
    }

    public void setReligion(String religion) {
        Logger.d("Preference manager ", "Religion   " + religion);
        mSharedPreferences.edit().putString("religion", religion).commit();
    }

    public String getFirst_name() {
        return mSharedPreferences.getString("first_name", "");
    }

    public void setFirst_name(String first_name) {
        Logger.d("Preference manager ", "First Name   " + first_name);
        mSharedPreferences.edit().putString("first_name", first_name).commit();
    }

    public String getApt_number() {
        return mSharedPreferences.getString("apt_no", "");
    }

    public void setApt_number(String apt_no) {
        Logger.d("Preference manager ", "Apartment Number   " + apt_no);
        mSharedPreferences.edit().putString("apt_no", apt_no).commit();
    }

    public String getState() {
        return mSharedPreferences.getString("state", "");
    }

    public void setState(String state) {
        Logger.d("Preference manager ", "State    " + state);
        mSharedPreferences.edit().putString("state", state).commit();
    }

    public String getAge_group() {
        return mSharedPreferences.getString("age_group", "");
    }

    public void setAge_group(String age_group) {
        Logger.d("Preference manager ", "age_group    " + age_group);
        mSharedPreferences.edit().putString("age_group", age_group).commit();
    }

    public String getLast_name() {
        return mSharedPreferences.getString("last_name", "");
    }

    public void setLast_name(String last_name) {
        Logger.d("Preference manager ", "last_name    " + last_name);
        mSharedPreferences.edit().putString("last_name", last_name).commit();
    }

    public String getZipCode() {
        return mSharedPreferences.getString("zipCode", "");
    }

    public void setZipCode(String code) {
        Logger.d("Preference manager ", "ZipCode    " + code);
        mSharedPreferences.edit().putString("zipCode", code).commit();
    }

    public String getScreen_name() {
        return mSharedPreferences.getString("screenName", "");
    }

    public void setScreen_name(String screenName) {
        Logger.d("Preference manager ", "screenName    " + screenName);
        mSharedPreferences.edit().putString("screenName", screenName).commit();
    }

    public String getStreet_address() {
        return mSharedPreferences.getString("street_address", "");
    }

    public void setStreet_address(String street_address) {
        Logger.d("Preference manager ", "street_address    " + street_address);
        mSharedPreferences.edit().putString("street_address", street_address).commit();
    }

    public String getParty_affiliation() {
        return mSharedPreferences.getString("party_affiliation", "");
    }

    public void setParty_affiliation(String party_affiliation) {
        Logger.d("Preference manager ", "party_affiliation    " + party_affiliation);
        mSharedPreferences.edit().putString("party_affiliation", party_affiliation).commit();
    }

    public String getCity() {
        return mSharedPreferences.getString("city", "");
    }

    public void setCity(String city) {
        Logger.d("Preference manager ", "city    " + city);
        mSharedPreferences.edit().putString("city", city).commit();
    }

    public String getPresident() {
        return mSharedPreferences.getString("president", "");
    }

    public void setPresident(String president) {
        Logger.d("Preference manager ", "president    " + president);
        mSharedPreferences.edit().putString("president", president).commit();
    }

    public String getPresidentName() {
        return mSharedPreferences.getString("president_name", "");
    }

    public void setPresidentName(String president) {
        Logger.d("Preference manager ", "president name    " + president);
        mSharedPreferences.edit().putString("president_name", president).commit();
    }

    public String getVicePresident() {
        return mSharedPreferences.getString("vicepresident", "");
    }

    public void setVicePresident(String vicepresident) {
        Logger.d("Preference manager ", "vicepresident    " + vicepresident);
        mSharedPreferences.edit().putString("vicepresident", vicepresident).commit();
    }

    public String getVicePresidentName() {
        return mSharedPreferences.getString("vice_president_name", "");
    }

    public void setVicePresidentName(String vicePresidentName) {
        Logger.d("Preference manager ", "vicePresidentNamee    " + vicePresidentName);
        mSharedPreferences.edit().putString("vice_president_name", vicePresidentName).commit();
    }


    public String getSenator1Name() {
        return mSharedPreferences.getString("Senator1Name", "");
    }

    public void setSenator1Name(String senator1Name) {
        Logger.d("Preference manager ", "Senator1 name   " + senator1Name);
        mSharedPreferences.edit().putString("Senator1Name", senator1Name).commit();
    }

    public String getSenator1() {
        return mSharedPreferences.getString("Senator1", "");
    }

    public void setSenator1(String senator1) {
        Logger.d("Preference manager ", "Senator1    " + senator1);
        mSharedPreferences.edit().putString("Senator1", senator1).commit();
    }

    public String getSenator2Name() {
        return mSharedPreferences.getString("Senator2Name", "");
    }

    public void setSenator2Name(String senator2Name) {
        Logger.d("Preference manager ", "senator2Name    " + senator2Name);
        mSharedPreferences.edit().putString("Senator2Name", senator2Name).commit();
    }


    public String getSenator2() {
        return mSharedPreferences.getString("Senator2", "");
    }

    public void setSenator2(String senator2) {
        Logger.d("Preference manager ", "Senator2    " + senator2);
        mSharedPreferences.edit().putString("Senator2", senator2).commit();
    }

    public String getMyHouseReps() {
        return mSharedPreferences.getString("MyHouseReps", "");
    }

    public void setMyHouseReps(String MyHouseReps) {
        Logger.d("Preference manager ", "MyHouseReps    " + MyHouseReps);
        mSharedPreferences.edit().putString("MyHouseReps", MyHouseReps).commit();
    }


    public String getMyHouseRepsName() {
        return mSharedPreferences.getString("MyHouseRepsName", "");
    }

    public void setMyHouseRepsName(String myHouseRepsName) {
        Logger.d("Preference manager ", "MyHouseRepsName    " + myHouseRepsName);
        mSharedPreferences.edit().putString("MyHouseRepsName", myHouseRepsName).commit();
    }

    public void setSplashIntroSkip(boolean b) {
        Logger.d("Preference manager ", "Splash Intro    " + b);
        mSharedPreferences.edit().putBoolean("splashIntro", b).commit();
    }

    public Boolean getSplashIntroSkip() {
        return mSharedPreferences.getBoolean("splashIntro", false);
    }


    public void setProfileUpdateTipSkip(boolean b) {
        Logger.d("Preference manager ", "Profile tip skip    " + b);
        mSharedPreferences.edit().putBoolean("profile_tip", b).commit();
    }

    public Boolean getProfileUpdateTipSkip() {
        return mSharedPreferences.getBoolean("profile_tip", false);
    }

    public void setHomeTipSkip(boolean b) {
        Logger.d("Preference manager ", "Profile tip skip    " + b);
        mSharedPreferences.edit().putBoolean("home_tip", b).commit();
    }

    public Boolean getHomeTipSkip() {
        return mSharedPreferences.getBoolean("home_tip", false);
    }
    //

    public void setFedBillsTipSkip(boolean b) {
        Logger.d("Preference manager ", "Home tip skip    " + b);
        mSharedPreferences.edit().putBoolean("fed_bills_tip", b).commit();
    }

    public Boolean getFedBillsTipSkip() {
        return mSharedPreferences.getBoolean("fed_bills_tip", false);
    }

    public void setBillsDetailTipSkip(boolean b) {
        Logger.d("Preference manager ", "fed_bills_tip     " + b);
        mSharedPreferences.edit().putBoolean("bills_detail_tip", b).commit();
    }

    public Boolean getBillsDetailTipSkip() {
        return mSharedPreferences.getBoolean("bills_detail_tip", false);
    }

    public void setVoiceOpinionBarSkip(boolean b) {
        Logger.d("Preference manager ", "voice_bar_tip " + b);
        mSharedPreferences.edit().putBoolean("voice_bar_tip", b).commit();
    }

    public Boolean getVoiceOpinionBarSkip() {
        return mSharedPreferences.getBoolean("voice_bar_tip", false);
    }

    public void setInitiativeSkip(boolean b) {
        Logger.d("Preference manager ", "initiative_tip " + b);
        mSharedPreferences.edit().putBoolean("initiative_tip", b).commit();
    }

    public Boolean getInitiativeSkip() {
        return mSharedPreferences.getBoolean("initiative_tip", false);
    }

    public void setCreateInitiativeSkip(boolean b) {
        Logger.d("Preference manager ", "create_initiative_tip " + b);
        mSharedPreferences.edit().putBoolean("create_initiative_tip", b).commit();
    }

    public Boolean getCreateInitiativeSkip() {
        return mSharedPreferences.getBoolean("create_initiative_tip", false);
    }

    public void setRepresentativesDetailSkip(boolean b) {
        Logger.d("Preference manager ", "representative_detail_tip " + b);
        mSharedPreferences.edit().putBoolean("representative_detail_tip", b).commit();
    }

    public Boolean getRepresentativesDetailSkip() {
        return mSharedPreferences.getBoolean("representative_detail_tip", false);
    }

    public void setMatchMyVotesSkip(boolean b) {
        Logger.d("Preference manager ", "match_votes_tip " + b);
        mSharedPreferences.edit().putBoolean("match_votes_tip", b).commit();
    }

    public Boolean getMatchMyVotesSkip() {
        return mSharedPreferences.getBoolean("match_votes_tip", false);
    }

}
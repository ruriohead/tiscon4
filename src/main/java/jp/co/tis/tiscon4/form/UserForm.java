package jp.co.tis.tiscon4.form;

import jp.co.tis.tiscon4.common.code.JobType;
import jp.co.tis.tiscon4.common.code.TreatedType;
import nablarch.core.util.StringUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;

public class UserForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 漢字氏 */
    @Required
    @Domain("kanjiName")
    private String kanjiName;

    /** 漢字名 */
    @Required
    @Domain("kanjiMei")
    private String kanjiMei;

    /** カナ氏 */
    @Required
    @Domain("kanaName")
    private String kanaName;

    /** カナ名 */
    @Required
    @Domain("kanaMei")
    private String kanaMei;

    /** 英字氏 */
    @Required
    @Domain("alphabetName")
    private String alphabetName;

    /** 英字名 */
    @Required
    @Domain("alphabetName")
    private String alphabetMei;

    /** 性別 */
    @Required
    @Domain("gender")
    private String gender;

    /** 生年月日 */
    /*@Required
    @Domain("dateOfBirth")
    private String dateOfBirth;
*/
    /** 生年月日 */
    @Required
    @Domain("year")
    private String year;

    /** 生年月日 */
    @Required
    @Domain("month")
    private String month;

    /** 生年月日 */
    @Required
    @Domain("day")
    private String day;

    /** 郵便番号 */
    @Required
    @Domain("zipCode")
    private String zipCode;

    /** 住所 */
    @Required
    @Domain("address")
    private String address;

    /** 自宅電話番号 */
    @Domain("homePhoneNumber")
    private String homePhoneNumber;

    /** 携帯電話番号 */
    @Domain("mobilePhoneNumber")
    private String mobilePhoneNumber;

    /** メールアドレス */
    @Required
    @Domain("emailAddress")
    private String emailAddress;

    /** 配偶者有無 */
    @Required
    @Domain("married")
    private String married;

    /** 所得金額 */
    @Required
    @Domain("income")
    private String income;

    /** 職業 */
    @Required
    @Domain("job")
    private String job;

    /** その他の職業 */
    @Domain("otherJob")
    private String otherJob;

    /** 治療歴有無 */
    @Required
    @Domain("treated")
    private String treated;

    /** 既往歴 */
    @Domain("medicalHistory")
    private String medicalHistory;

    public String getKanjiName() {
        return kanjiName;
    }

    public void setKanjiName(String kanjiName) {
        this.kanjiName = kanjiName;
    }

    public String getKanjiMei() {
        return kanjiMei;
    }

    public void setKanjiMei(String kanjiMei) {
        this.kanjiMei = kanjiMei;
    }

    public String getKanaName() {
        return kanaName;
    }

    public void setKanaName(String kanaName) {
        this.kanaName = kanaName;
    }

    public String getKanaMei() { return kanaMei; }

    public void setKanaMei(String kanaMei) {
        this.kanaMei = kanaMei;
    }

    public String getAlphabetName() {
        return alphabetName;
    }

    public void setAlphabetName(String alphabetName) {
        this.alphabetName = alphabetName;
    }

    public String getAlphabetMei() {
        return alphabetMei;
    }

    public void setAlphabetMei(String alphabetMei) {
        this.alphabetMei = alphabetMei;
    }

    /*public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }*/

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getOtherJob() {
        return otherJob;
    }

    public void setOtherJob(String otherJob) {
        this.otherJob = otherJob;
    }

    public String getTreated() {
        return treated;
    }

    public void setTreated(String treated) {
        this.treated = treated;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * その他の職業欄が正しく入力されているかどうか判定する。
     * 職業で「その他（有職）」を選択し、その他の職業欄に何らかの値が入力された場合、正しく入力されたと判定する。
     *
     * @return その他の職業欄に入力がある場合にtrue
     */
    @AssertTrue(message = "{tiscon4.order.inputUser.error.hasValueOtherJob}")
    public boolean hasValueOtherJob() {
        if (StringUtil.isNullOrEmpty(job)) {
            // 職業が未入力の場合は、相関バリデーションは実施しない。(バリデーションOKとする)
            return true;
        } else if (JobType.EMPLOYED.getCode().equals(job) && StringUtil.isNullOrEmpty(otherJob)) {
            return false;
        }
        return true;
    }

    /**
     * 既往歴が正しく入力されているかどうか判定する。
     * 治療有無で「はい」を選択し、既往歴に何らかの値が入力された場合、正しく入力されたと判定する。
     *
     * @return 既往歴に入力がある場合にtrue
     */
    @AssertTrue(message = "{tiscon4.order.inputUser.error.hasValueMedicalHistory}")
    public boolean hasValueMedicalHistory() {
        if (StringUtil.isNullOrEmpty(treated)) {
            // 治療有無が未入力の場合は、相関バリデーションは実施しない。(バリデーションOKとする)
            return true;
        } else if (TreatedType.TREATED.getCode().equals(treated) && StringUtil.isNullOrEmpty(medicalHistory)) {
            return false;
        }
        return true;
    }
    @AssertTrue(message = "{tiscon4.order.inputUser.error.hasHomePhoneNumber}")
    public boolean hasHomePhoneNumber() {
        if (!StringUtil.isNullOrEmpty(homePhoneNumber)) {
            return true;
        } else if (!StringUtil.isNullOrEmpty(mobilePhoneNumber)) {
            return true;
        }
        return false;
    }
    @AssertTrue(message = "{tiscon4.order.inputUser.error.hasMobilePhoneNumber}")
    public boolean hasMobilePhoneNumber() {
        if (!StringUtil.isNullOrEmpty(mobilePhoneNumber)) {
            return true;
        } else if (!StringUtil.isNullOrEmpty(homePhoneNumber)) {
            return true;
        }
        return false;
    }
}

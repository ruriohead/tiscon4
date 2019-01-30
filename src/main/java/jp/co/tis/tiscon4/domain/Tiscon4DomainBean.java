package jp.co.tis.tiscon4.domain;

import jp.co.tis.tiscon4.common.code.GenderType;
import jp.co.tis.tiscon4.common.code.IndustryType;
import jp.co.tis.tiscon4.common.code.InsuranceType;
import jp.co.tis.tiscon4.common.code.JobType;
import jp.co.tis.tiscon4.common.code.MarriedType;
import jp.co.tis.tiscon4.common.code.TreatedType;
import jp.co.tis.tiscon4.core.validation.validator.CodeValue;
import jp.co.tis.tiscon4.core.validation.validator.JapaneseTelNumber;
import jp.co.tis.tiscon4.core.validation.validator.MailAddress;
import jp.co.tis.tiscon4.core.validation.validator.YYYYMMDD;
import jp.co.tis.tiscon4.core.validation.validator.ZipNumber;
import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.SystemChar;

/**
 * ドメイン定義。
 */
public class Tiscon4DomainBean {

    /** 保険の区分 */
    @CodeValue(enumClass = InsuranceType.class)
    private String insuranceType;

    /** 漢字氏 */
    @SystemChar(charsetDef = "全角文字", message = "{domain.illegalCharacter}")
    @Length(max = 30)
    private String kanjiName;

    /** 漢字名 */
    @SystemChar(charsetDef = "全角文字", message = "{domain.illegalCharacter}")
    @Length(max = 30)
    private String kanjiMei;

    /** カナ氏 */
    @SystemChar(charsetDef = "全角カタカナスペース", message = "{domain.illegalCharacter}")
    @Length(max = 45)
    private String kanaName;

    /** カナ名 */
    @SystemChar(charsetDef = "全角カタカナスペース", message = "{domain.illegalCharacter}")
    @Length(max = 45)
    private String kanaMei;

    /** 英字氏 */
    @SystemChar(charsetDef = "半角英字スペース", message = "{domain.illegalCharacter}")
    @Length(max = 60)
    private String alphabetName;

    /** 英字名 */
    @SystemChar(charsetDef = "半角英字スペース", message = "{domain.illegalCharacter}")
    @Length(max = 60)
    private String alphabetMei;

    /** 性別 */
    @CodeValue(enumClass = GenderType.class)
    private String gender;

    /** 生年月日 */
    @YYYYMMDD()
    private String dateOfBirth;

    /** 郵便番号 */
    @ZipNumber
    private String zipCode;

    /** 住所 */
    @SystemChar(charsetDef = "全角文字", message = "{domain.illegalCharacter}")
    @Length(max = 255)
    private String address;

    /** 自宅電話番号 */
    @JapaneseTelNumber()
    private String homePhoneNumber;

    /** 携帯電話番号 */
    @JapaneseTelNumber()
    private String mobilePhoneNumber;

    /** メールアドレス */
    @MailAddress()
    @Length(max = 255)
    private String emailAddress;

    /** 配偶者有無 */
    @CodeValue(enumClass = MarriedType.class)
    private String married;

    /** 所得金額 */
    @Digits(integer = 9, fraction = 0)
    private String income;

    /** 職業 */
    @CodeValue(enumClass = JobType.class)
    private String job;

    /** その他の職業 */
    @SystemChar(charsetDef = "全角文字", message = "{domain.illegalCharacter}")
    @Length(max = 120)
    private String otherJob;

    /** 治療歴有無 */
    @CodeValue(enumClass = TreatedType.class)
    private String treated;

    /** 既往歴 */
    @SystemChar(charsetDef = "全角文字", message = "{domain.illegalCharacter}")
    @Length(max = 255)
    private String medicalHistory;

    /** 勤務先 */
    @SystemChar(charsetDef = "全角文字", message = "{domain.illegalCharacter}")
    @Length(max = 255)
    private String employerName;

    /** 勤務先郵便番号 */
    @ZipNumber
    private String employerZipCode;

    /** 勤務先住所 */
    @SystemChar(charsetDef = "全角文字", message = "{domain.illegalCharacter}")
    @Length(max = 255)
    private String employerAddress;

    /** 勤務先電話番号 */
    @JapaneseTelNumber()
    private String employerPhoneNumber;

    /** 業種 */
    @CodeValue(enumClass = IndustryType.class)
    private String industryType;

}

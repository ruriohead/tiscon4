package jp.co.tis.tiscon4.action;

import jp.co.tis.tiscon4.common.code.GenderType;
import jp.co.tis.tiscon4.common.code.IndustryType;
import jp.co.tis.tiscon4.common.code.JobType;
import jp.co.tis.tiscon4.common.code.MarriedType;
import jp.co.tis.tiscon4.common.code.TreatedType;
import jp.co.tis.tiscon4.dto.ZipcodeDto;
import jp.co.tis.tiscon4.entity.InsuranceOrder;
import jp.co.tis.tiscon4.form.AcceptForm;
import jp.co.tis.tiscon4.form.IndexForm;
import jp.co.tis.tiscon4.form.JobForm;
import jp.co.tis.tiscon4.form.UserForm;
import nablarch.common.dao.UniversalDao;
import nablarch.common.web.interceptor.InjectForm;
import nablarch.common.web.session.SessionUtil;
import nablarch.common.web.token.OnDoubleSubmission;
import nablarch.common.web.token.UseToken;
import nablarch.core.beans.BeanUtil;
import nablarch.core.message.ApplicationException;
import nablarch.core.message.Message;
import nablarch.core.validation.ValidationUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.interceptor.OnError;

import java.util.ArrayList;
import java.util.List;

/**
 * 保険申し込みに関するActionクラス.
 *
 * @author Kudo Sae
 */
public class OrderAction {

    /**
     * 加入条件確認画面を表示する。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    @InjectForm(form = IndexForm.class)
    @OnError(type = ApplicationException.class, path = "index.html")
    public HttpResponse accept(HttpRequest req, ExecutionContext ctx) {
        IndexForm indexForm = ctx.getRequestScopedVar("form");
        InsuranceOrder insOrder = BeanUtil.createAndCopy(InsuranceOrder.class, indexForm);

        SessionUtil.delete(ctx, "insOrder");
        SessionUtil.put(ctx, "insOrder", insOrder);

        ctx.setRequestScopedVar("form", new AcceptForm());

        if(insOrder.getInsuranceType().equals("treat")){
            return new HttpResponse("acceptanceTreat.html");
        } else if(insOrder.getInsuranceType().equals("treatLady")) {
            return new HttpResponse("acceptanceTreatLady.html");
        } else if (insOrder.getInsuranceType().equals("future")) {
            return new HttpResponse("acceptanceFuture.html");
        } else{
            return null;
        }
    }

    /**
     * 本人登録画面を表示する。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */


    List<Integer> daylist=new ArrayList<>();
    @InjectForm(form = AcceptForm.class)
    @OnError(type = ApplicationException.class, path = "acceptanceTreat.html")
    public HttpResponse inputUser(HttpRequest req, ExecutionContext ctx) {
        List<Integer> yearlist=new ArrayList<>();
        for(int i=1900; i<=2020; i++) {
            yearlist.add(i);
        }
        List<Integer> monthlist=new ArrayList<>();
        for(int j=1; j<=12; j++) {
            monthlist.add(j);
        }
        List<Integer> daylist=new ArrayList<>();
        for(int k=1; k<=31; k++) {
            daylist.add(k);
        }


        ctx.setRequestScopedVar("form", new UserForm());
        ctx.setRequestScopedVar("yearlist", yearlist);
        ctx.setRequestScopedVar("monthlist", monthlist);
        ctx.setRequestScopedVar("daylist", daylist);
        ctx.setRequestScopedVar("genderTypes", GenderType.values());
        ctx.setRequestScopedVar("marriedTypes", MarriedType.values());
        ctx.setRequestScopedVar("jobTypes", JobType.values());
        ctx.setRequestScopedVar("treatedTypes", TreatedType.values());

        return new HttpResponse("user.html");
    }

    /**
     * お勤め先登録画面を表示する。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    @InjectForm(form = UserForm.class)
    @OnError(type = ApplicationException.class, path = "forward://inputUserForError")
    @UseToken
    public HttpResponse inputJob(HttpRequest req, ExecutionContext ctx) {
        UserForm form = ctx.getRequestScopedVar("form");
        InsuranceOrder insOrder = SessionUtil.get(ctx, "insOrder");

        // treatLadyは女性しか加入できないため、性別選択チェックを行う。
        if (insOrder.getInsuranceType().equals("treatLady") && form.getGender().equals("male")) {
            Message message = ValidationUtil.createMessageForProperty("gender", "tiscon4.order.inputUser.error.gender");
            throw new ApplicationException(message);
        }

        UniversalDao.findAllBySqlFile(ZipcodeDto.class, "ZIPCODE_LIST");

        BeanUtil.copy(form, insOrder);

        ctx.setRequestScopedVar("form", new JobForm());
        ctx.setRequestScopedVar("industryTypes", IndustryType.values());

        return new HttpResponse("job.html");
    }

    /**
     * 本人登録画面に入力エラーがあった時に再表示する。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    public HttpResponse inputUserForError(HttpRequest req, ExecutionContext ctx) {
        List<Integer> yearlist=new ArrayList<>();
        for(int i=1900; i<=2020; i++) {
            yearlist.add(i);
        }
        List<Integer> monthlist=new ArrayList<>();
        for(int j=1; j<=12; j++) {
            monthlist.add(j);
        }
        List<Integer> daylist=new ArrayList<>();
        for(int k=1; k<=31; k++) {
            daylist.add(k);
        }

        ctx.setRequestScopedVar("yearlist", yearlist);
        ctx.setRequestScopedVar("monthlist", monthlist);
        ctx.setRequestScopedVar("daylist", daylist);
        ctx.setRequestScopedVar("genderTypes", GenderType.values());
        ctx.setRequestScopedVar("marriedTypes", MarriedType.values());
        ctx.setRequestScopedVar("jobTypes", JobType.values());
        ctx.setRequestScopedVar("treatedTypes", TreatedType.values());

        return new HttpResponse("user.html");
    }

    /**
     * 申し込み情報をデータベースに登録する。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    @InjectForm(form = JobForm.class)
    @OnError(type = ApplicationException.class, path = "forward://inputJobForError")
    @OnDoubleSubmission(path = "doubleSubmissionError.html")
    public HttpResponse create(HttpRequest req, ExecutionContext ctx) {
        JobForm form = ctx.getRequestScopedVar("form");
        InsuranceOrder insOrder = SessionUtil.get(ctx, "insOrder");

        BeanUtil.copy(form, insOrder);

        UniversalDao.insert(insOrder);

        return new HttpResponse("redirect://completed");
    }

    /**
     * お勤め先登録画面に入力エラーがあった時に再表示する。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    @UseToken
    public HttpResponse inputJobForError(HttpRequest req, ExecutionContext ctx) {
        ctx.setRequestScopedVar("industryTypes", IndustryType.values());

        return new HttpResponse("job.html");
    }

    /**
     * 完了ページを表示する。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    public HttpResponse completed(HttpRequest req, ExecutionContext ctx) {
        return new HttpResponse("completed.html");
    }

    /**
     * 加入条件確認画面に戻る。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    public HttpResponse backToAccept(HttpRequest req, ExecutionContext ctx) {
        InsuranceOrder oldInsOrder = SessionUtil.delete(ctx, "insOrder");
        InsuranceOrder newInsOrder = new InsuranceOrder();

        // 本人情報は改めて入力するため、保険の区分のみ情報を引き継ぐ。
        newInsOrder.setInsuranceType(oldInsOrder.getInsuranceType());
        SessionUtil.put(ctx, "insOrder", newInsOrder);

        ctx.setRequestScopedVar("form", new AcceptForm());

        return new HttpResponse("acceptanceTreat.html");
    }

    /**
     * 本人登録画面に戻る。
     *
     * @param req リクエストコンテキスト
     * @param ctx HTTPリクエストの処理に関連するサーバ側の情報
     * @return HTTPレスポンス
     */
    public HttpResponse backToUser(HttpRequest req, ExecutionContext ctx) {
        InsuranceOrder insOrder = SessionUtil.get(ctx, "insOrder");
        UserForm form = new UserForm();
        BeanUtil.copy(insOrder, form);

        ctx.setRequestScopedVar("form", form);
        ctx.setRequestScopedVar("genderTypes", GenderType.values());
        ctx.setRequestScopedVar("marriedTypes", MarriedType.values());
        ctx.setRequestScopedVar("jobTypes", JobType.values());
        ctx.setRequestScopedVar("treatedTypes", TreatedType.values());

        return new HttpResponse("user.html");
    }

}

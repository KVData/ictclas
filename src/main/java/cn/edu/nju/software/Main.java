package cn.edu.nju.software;

/**
 * @author dalec
 */
public class Main {
    public static void main(String[] args) {
        String content = "中國共產黨第19次全國代表大會於10月18日北京揭幕，預估會期7天。除了中國國家主席兼中國共產黨中央委員會總書記習近平、國務院總理李克強留任外，外界關注的焦點集中在中紀委書記王岐山的去留、新一屆接班人名單、隔代指定接班慣例會否被打破、「習近平思想」是否寫入黨章。\n" +
                "\n" +
                "中共中央政治局8月31召開會議，敲定了「十九大」於2017年10月18日在北京召開，而第十八屆中央委員會第七次全體會議於2017年10月11日在北京召開。\n" +
                "\n" +
                "年屆69歲的中紀委書記王岐山，按照中共黨內「七上八下」的不成文規定，即是67歲還可以再留任，但68歲就必須退休，而王岐山是否會留任政治局常委，外界一直有不同猜測。";
        String result = NlpirMethod.NLPIR_GetKeyWords(content, 3, true);
        System.out.println(result);
        NlpirMethod.NLPIR_Exit();
    }
}

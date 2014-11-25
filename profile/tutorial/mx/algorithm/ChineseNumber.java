package mx.algorithm;

/**
 * Created by hxiong on 6/27/14.
 */
public class ChineseNumber {

    private final static String[] name = new String[]{
        "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"
    };

    private final static String[] UNIT = new String[]{
        "", "十", "百", "千"
    };

    private final static String[] SPE_UNIT = new String[]{
        "万", "亿", "兆"
    };

    static String convert(String numStr, int index){
        if(index >= numStr.length()){
            return "";
        }
        String tmp = "";
        int num = 0;
        for(int i = 0; i < 4; i++){
            if((num = Integer.parseInt(String.valueOf(numStr.charAt(index)))) == 0){
                if("".equals(tmp) || tmp.endsWith(name[0])){
                    //do nothing
                }else{
                    tmp += name[num];
                }
            }else{
                tmp += UNIT[i] + name[num];
            }

            if(++index >= numStr.length())break;
        }

        if(index < numStr.length()){
            tmp += getUnit(index);
            tmp += convert(numStr, index);
        }

        return tmp;
    }

    static String getUnit(int index){

        for(int i = SPE_UNIT.length - 1; -1 < i; i--){
            int scale = 4;
            int j = 0;
            while(j++ < i){
                scale *= 2;
            }
            if(0 == index % scale){
                return SPE_UNIT[i];
            }
        }
        //only go to the last may return "";
        return "";
    }


    public static void main(String[] args) {
        String s = convert("100345008", 0);
        System.out.println(new StringBuilder(s).reverse().toString());
        System.out.println(getUnit(8));
    }
}

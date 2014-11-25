import static org.junit.Assert.*;

import org.junit.Test;




public class Solution {

    public boolean isNumber(String s) {
        return isNumber(s, true);
    }

    public boolean isNumberNoneFirst(String s) {
        return isNumber(s, false);
    }

    public boolean isNumber(String s, boolean firstRound) {

        s = s.trim();

        //chop symbol
        if (s.length() > 0 && firstRound) {
            if ((s.charAt(0) == '+' || s.charAt(0) == '-')) {
                s = s.substring(1);
            }
        }

        //none zero string
        if (s.length() == 0)
            return false;

        //each special can occur once
        if (duplicate(s, '.', 'e'))
            return false;

        //can not have inner white space
        if (innerWhiteSpace(s))
            return false;

        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            //none digit
            if (!Character.isDigit(c)) {
                String l = s.substring(0, i);
                String r = s.substring(i + 1);

                if (c == '.') {
                    //left empty right digit
                    boolean lerd = l.trim().length() == 0 && isNumberNoneFirst(r);
                    //left digit right digit
                    boolean ldrd = isNumberNoneFirst(l) && isNumberNoneFirst(r);
                    //left digit right empty
                    boolean ldre = isNumberNoneFirst(l) && r.trim().length() == 0;
                    return lerd || ldrd || ldre;
                }

                if (c == 'e') {
                    //left digit right digit
                    boolean ldrd = isNumberNoneFirst(l) && isNumberNoneFirst(r);
                    //left empty right digit
                    boolean lerd = l.trim().length() == 0 && isNumberNoneFirst(r);
                    return ldrd || (!firstRound && lerd);
                }

                return false;
            }
        }
        return true;
    }

    private boolean duplicate(String s, char... special) {
        for (char s1 : special) {
            int cnt = 0;
            for (char c : s.toCharArray()) {
                if (c == s1)
                    cnt++;
            }
            if (cnt > 1)
                return true;
        }
        return false;
    }

    private boolean innerWhiteSpace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i)) && i > 0 && i < s.length())
                return true;
        }
        return false;
    }

    @Test
    public void test() {
        Solution s = new Solution();
        assertTrue(s.isNumber("0"));
        assertTrue(s.isNumber(" 0.1 "));
        assertTrue(s.isNumber(".1"));
        assertFalse(s.isNumber("abc"));
        assertFalse(s.isNumber("1 a"));
        assertTrue(s.isNumber("2e10"));
        assertFalse(s.isNumber("e"));
        assertTrue(s.isNumber("3."));
        assertFalse(s.isNumber("..2"));
        assertFalse(s.isNumber(". 1"));
        assertTrue(s.isNumber("-1."));
        assertFalse(s.isNumber(".-4"));
        assertFalse(s.isNumber("4e+"));
        assertFalse(s.isNumber("e9"));
        assertTrue(s.isNumber("46.e3"));
    }

    @Test
    public void testFail() {
        System.out.println(1<<2);
    }
}

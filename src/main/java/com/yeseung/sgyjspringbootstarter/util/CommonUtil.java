package com.yeseung.sgyjspringbootstarter.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 공통 유틸리티
 *
 * @author seunggu.lee
 */
public class CommonUtil {

    private CommonUtil() {
        throw new AssertionError();
    }

    /**
     * 사업자 번호가 정상적인지 판단
     *
     * @param businessId 사업자 번호
     * @return 정상 여부
     * @see <a href="https://hyejikim.tistory.com/43">사업자 번호 유효성 검사 알고리즘</a>
     */
    public static boolean isBusinessId(String businessId) {
        String[] strings = businessId.split("");
        if (strings.length != 10) {
            return false;
        }
        int[] intArray = new int[10];
        for (int i = 0; i < strings.length; i++) {
            intArray[i] = Integer.parseInt(strings[i]);
        }
        int sum = 0;
        int[] indexes = new int[] {1, 3, 7, 1, 3, 7, 1, 3};
        for (int i = 0; i < 8; i++) {
            sum += intArray[i] * indexes[i];
        }
        int num = intArray[8] * 5;
        sum += (num / 10) + (num % 10);
        sum = 10 - (sum % 10);
        if (sum == 10) {
            return 0 == intArray[9];
        } else {
            return sum == intArray[9];
        }
    }

    public static String getClientIp() {
        return getClientIp(false);
    }

    /**
     * @param flag : 클라이언트 여부
     * @apiNote flag 가 true 면 client ip, 아니면 web server 혹은 load balancer ip
     */
    public static String getClientIp(boolean flag) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (flag || ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
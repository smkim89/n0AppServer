package com.swag.common.interceptor;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.swag.common.util.DataBox;
import com.swag.system.access.service.AccessService;

/**
 * IpAccessInterceptor
 *
 * @author 
 * @update 2015-11-26 
 */
public class IpAccessInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(IpAccessInterceptor.class);


	@Inject
	private AccessService accessManagementService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		String remoteIp = request.getRemoteAddr();
		boolean isAccessIp = false;
		DataBox accessIpBox = accessManagementService.getAccessIp(remoteIp, "Y");
		if (!accessIpBox.isEmpty()) {
			logger.debug("{} : {}", accessIpBox.getString("ACCESS_NM"), accessIpBox.getString("ACCESS_IP"));
			isAccessIp = true;
		}else{
			List<DataBox> accessList = accessManagementService.getIpList(false, "Y");

			for (DataBox temp : accessList) {
				String accessIp = temp.getString("ACCESS_IP");
				String accessEndIp = temp.getString("END_ACCESS_IP");
				long longIp = ipToNumber(remoteIp);
				if (ipToNumber(accessIp) <= longIp && ipToNumber(accessEndIp) >= longIp) {
					logger.debug("{} : {} ~ {}", temp.getString("ACCESS_NM"), accessIp, temp.getString("END_ACCESS_IP"));
					isAccessIp = true;
					break;
				}
			}
		}
		
		if (!isAccessIp) {
			session.invalidate();
			response.sendRedirect(request.getContextPath().concat("/login"));
			return false;
		}
		
		return true;
	}


	private Long ipToNumber(String ip) {
		long num = 0;
		if (!StringUtils.isEmpty(ip)) {
			String[] ipArray = ip.replaceAll(":", "").split("\\.");

			for (int i = 0; i < ipArray.length; i++) {
				int mask = (ipArray.length - 1) - i;
				try {
					num += ((Integer.parseInt(ipArray[i]) % 256 * Math.pow(256, mask)));
				} catch (NumberFormatException e) {
					logger.error("[FATAL] IP 입력 오류.");
				}
			}
		}

		return num;
	}


	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) {
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
	}
}

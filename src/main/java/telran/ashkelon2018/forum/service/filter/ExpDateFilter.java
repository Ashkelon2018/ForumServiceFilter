package telran.ashkelon2018.forum.service.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.configuration.AccountConfiguration;
import telran.ashkelon2018.forum.configuration.AccountUserCredentials;
import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.UserAccount;

@Service
@Order(2)
public class ExpDateFilter implements Filter {
	@Autowired
	UserAccountRepository repository;

	@Autowired
	AccountConfiguration configuration;

	

	@Override
	public void doFilter(ServletRequest reqs, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) reqs;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String token = request.getHeader("Authorization");
		if (!path.startsWith("/account/password") && token != null) {
			AccountUserCredentials userCredentials = configuration.tokenDecode(token);
			UserAccount userAccount = repository.findById(userCredentials.getLogin()).orElse(null);
			if (userAccount.getExpdate().isBefore(LocalDateTime.now())) {
				response.sendError(403,"Password expired");
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}

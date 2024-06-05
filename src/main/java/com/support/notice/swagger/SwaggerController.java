package com.support.notice.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerController {

    /**
     * 스웨거 docs 페이지로 이동합니다.
     */
	@GetMapping({"/swagger"})
	public String swagger() {
		return "redirect:/swagger-ui.html";
	}
}

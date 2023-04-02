package com.moiza.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitalizer extends AbstractAnnotationConfigDispatcherServletInitializer {

	//이니셜라이제이션 initialization
	//기계를 시동 가능한 상태로 만들기 위한 조작. 또는 데이터 매체를 사용하기 전이나 처리하기 전에 필요한 조작.
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	//컨트롤러러 가기전에 먼저 읽어달라고 선언하는 부분
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { DemoAppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
		//getServletMappings 메서드는 브라우저가 요청한 주소 패턴을 보고 
		//Spring에서 처리할지 말지를 결정할 수 있도록 해주는 메서드 입니다. 
		//return new String[] {"/"}; 라고 해주면 모든 요청에 대해 
		//Spring에서 처리해주겠다는 의미입니다. 
		//만약 특정 패턴의 주소만 처리해주겠다 하면 그 패턴의 주소 문자열을 셋팅하면 됩니다.
	}

}

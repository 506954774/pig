/*
 *    Copyright (c) 2018-2025, iot All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: iot
 */

package com.pig4cloud.pig.admin.filters;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.common.security.exception.InvalidException;
import com.pig4cloud.pig.common.security.exception.UnauthorizedException;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * @Name:
 * @Description: 自定义的token拦截器.凡是加了Inner注解的controller层的接口不会被拦截,否则将检验token的合法性
 * @Param:
 * @return:
 * @Author: LeiChen
 * @Date:2022/6/25 23:09
 *
 * */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public class YnlkTokenFilter extends GenericFilterBean {

	@Autowired
	private ObjectMapper objectMapper;

	public static final String TOKEN="Token";

	public static final String LEGAL_TOKEN="859f5cd2-8285-4c23-bde7-17310b14772d";
	public static final String LEGAL_TOKEN2="C463E918-6AE3-BC91-23EF-34D7E8752359";

	@Data
	public static class YnlkUri implements Comparable<YnlkUri>{
		String uri;
		String method;

		@Override
		public int compareTo(YnlkUri o) {
			int c=this.getUri().compareTo(o.getUri());
			if(c==0){
				return this.getMethod().compareTo(o.getMethod());
			}
			return c;
		}
	}

	//储存加了inner注解的uri
	private ArrayList<YnlkUri> mOpenUris=new ArrayList<>();

	@PostConstruct
	public void init() {

		try {
			initInnerUris();
			log.info("YnlkTokenFilter,initInnerUris.结束"  );

		} catch (Exception e) {
			log.info("YnlkTokenFilter,initInnerUris.异常:{}", e.getMessage());

		}
	}


	@Override
	@SneakyThrows
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;


		//如果命中了inner注解的接口,则直接放行
		if(isMathchInnerUrl(request)){
			filterChain.doFilter(request, response);
			return;
		}


		String token = request.getHeader(TOKEN);
		log.info("YnlkTokenFilter,获取header中的token为:{}", token);


		if (TextUtils.isEmpty(token)) {
			log.info("YnlkTokenFilter,token为空"  );
			handleTokenError((HttpServletResponse) servletResponse);
		}
		else {
			//如果传了token但是token错误,则直接抛出异常
			 if(!LEGAL_TOKEN.equals(token)&&!LEGAL_TOKEN2.equals(token)){
				 log.info("YnlkTokenFilter,传了token,但是token不对:{}",token );

				 handleTokenError((HttpServletResponse) servletResponse);
			 }
			 else {
				 log.info("YnlkTokenFilter,token合法,继续执行:{}", token);
				 filterChain.doFilter(request, response);
			 }
		}


	}

	private void handleTokenError(HttpServletResponse servletResponse) throws IOException {
		HttpServletResponse response;
		response = servletResponse;
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		R failed = R.failed("token失效");

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(objectMapper.writeValueAsBytes(failed));
		outputStream.close();
	}


	/**
	 * @Name:
	 * @Description: 根据request匹配出当前的请求是否为加了inner注解的uri
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2022/6/25 23:01
	 *
	 * */
	private boolean isMathchInnerUrl(HttpServletRequest request){
		boolean match=false;
		if(CollectionUtils.isEmpty(mOpenUris)){
		  	return false;
		}
		else {
			String uri=request.getRequestURI();
			String method=request.getMethod();
			for (YnlkUri ynlkUri:mOpenUris){
				AntPathMatcher matcher = new AntPathMatcher();
				String pattern = ynlkUri.getUri();
				boolean uriMatch = matcher.match(pattern, uri);
				boolean methodSame=ynlkUri.getMethod().equals(method);
				if(uriMatch&&methodSame){
                  match=true;
                  return match;
				}
			}
		}
		return match;
	}


	/**
	 * @Name:
	 * @Description: 此方法,将遍历所有的controller里的接口,把加了Inner注解的方法存入mOpenUris集合里
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2022/6/25 22:54
	 *
	 * */
	public void initInnerUris()  {

		Field field;
		try{
			// 获取所有已经加载类
			field = ClassLoader.class.getDeclaredField("classes");
			field.setAccessible(true);
			Vector classes=(Vector) field.get(this.getClass().getClassLoader());
			List<Class> cl=new ArrayList<>(classes);

			// 获取RequestMapping值
			for (Class c : cl) {
				if (c.getAnnotation(RequestMapping.class)!=null) {
					System.out.println(c.getName());
					RequestMapping req = (RequestMapping) c.getAnnotation(RequestMapping.class);
					String[] bath = req.path().length > 0 ? req.path() : req.value();
					if (bath.length == 0) {
						continue;
					}
					Method[] ms = c.getDeclaredMethods();
					for (Method m : ms) {
						String[] bath2;
						if (m.getAnnotation(RequestMapping.class) != null) {
							RequestMapping rm = m.getAnnotation(RequestMapping.class);

							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}

							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];


						} else if (m.getAnnotation(GetMapping.class) != null) {
							GetMapping rm = m.getAnnotation(GetMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];

							// 组装RequestMapping,存入uri到内存中
							String uri = bath[0] + bath2[0];
							Inner inner = m.getAnnotation(Inner.class);
							if(inner!=null){
								YnlkUri ynlkUri=new YnlkUri();
								ynlkUri.setUri(uri);
								ynlkUri.setMethod(HttpMethod.GET.name());
								mOpenUris.add(ynlkUri);
								log.info("YnlkTokenFilter,遍历加了Inner注解的接口,uri:{},method:{}" ,uri,ynlkUri.getMethod() );
							}
						} else if (m.getAnnotation(PostMapping.class) != null){
							PostMapping rm = m.getAnnotation(PostMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];

							// 组装RequestMapping,存入uri到内存中
							String uri = bath[0] + bath2[0];
							Inner inner = m.getAnnotation(Inner.class);
							if(inner!=null){
								YnlkUri ynlkUri=new YnlkUri();
								ynlkUri.setUri(uri);
								ynlkUri.setMethod(HttpMethod.POST.name());
								mOpenUris.add(ynlkUri);
								log.info("YnlkTokenFilter,遍历加了Inner注解的接口,uri:{},method:{}" ,uri,ynlkUri.getMethod() );
							}
						} else if (m.getAnnotation(PutMapping.class) != null){
							PutMapping rm = m.getAnnotation(PutMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];

							// 组装RequestMapping,存入uri到内存中
							String uri = bath[0] + bath2[0];
							Inner inner = m.getAnnotation(Inner.class);
							if(inner!=null){
								YnlkUri ynlkUri=new YnlkUri();
								ynlkUri.setUri(uri);
								ynlkUri.setMethod(HttpMethod.PUT.name());
								mOpenUris.add(ynlkUri);
								log.info("YnlkTokenFilter,遍历加了Inner注解的接口,uri:{},method:{}" ,uri,ynlkUri.getMethod() );
							}
						} else if (m.getAnnotation(DeleteMapping.class) != null){
							DeleteMapping rm = m.getAnnotation(DeleteMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];

							// 组装RequestMapping,存入uri到内存中
							String uri = bath[0] + bath2[0];
							Inner inner = m.getAnnotation(Inner.class);
							if(inner!=null){
								YnlkUri ynlkUri=new YnlkUri();
								ynlkUri.setUri(uri);
								ynlkUri.setMethod(HttpMethod.DELETE.name());
								mOpenUris.add(ynlkUri);
								log.info("YnlkTokenFilter,遍历加了Inner注解的接口,uri:{},method:{}" ,uri,ynlkUri.getMethod() );
							}
						} else if (m.getAnnotation(PatchMapping.class) != null){
							PatchMapping rm = m.getAnnotation(PatchMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];

							// 组装RequestMapping,存入uri到内存中
							String uri = bath[0] + bath2[0];
							Inner inner = m.getAnnotation(Inner.class);
							if(inner!=null){
								YnlkUri ynlkUri=new YnlkUri();
								ynlkUri.setUri(uri);
								ynlkUri.setMethod(HttpMethod.PATCH.name());
								mOpenUris.add(ynlkUri);
								log.info("YnlkTokenFilter,遍历加了Inner注解的接口,uri:{},method:{}" ,uri,ynlkUri.getMethod() );
							}
						} else{
							continue;
						}
						if (bath2.length == 0) {
							continue;
						}

					}
				}
			}

		}catch(   Exception  e ){

		} finally{

		}
	}



	// JDBC 驱动名及数据库 URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver" ;
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/data_center?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false&noAccessToProcedureBodies=true&allowMultiQueries=true";

	// 数据库的用户名与密码
	private static final String USER = "jira";
	private static final String PASS = "jira";


	public void annotationScan() throws  Exception{
		Connection conn = null;
		Statement stmt = null;
		Integer i = 0, j = 0;
		ArrayList uriList = new ArrayList<>();
		Field field;


		try{
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);

			// 打开链接
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			// 创建资源
			stmt = (Statement) conn.createStatement();

			// 获取所有已经加载类
			field = ClassLoader.class.getDeclaredField("classes");
			field.setAccessible(true);
			Vector classes=(Vector) field.get(this.getClass().getClassLoader());
			List<Class> cl=new ArrayList<>(classes);

			// 获取RequestMapping值
			for (Class c : cl) {
				if (c.getAnnotation(RequestMapping.class)!=null) {
					System.out.println(c.getName());
					RequestMapping req = (RequestMapping) c.getAnnotation(RequestMapping.class);
					String[] bath = req.path().length > 0 ? req.path() : req.value();
					if (bath.length == 0) {
						continue;
					}
					Method[] ms = c.getDeclaredMethods();
					for (Method m : ms) {
						String[] bath2;
						if (m.getAnnotation(RequestMapping.class) != null) {
							RequestMapping rm = m.getAnnotation(RequestMapping.class);

							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}

							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];
						} else if (m.getAnnotation(GetMapping.class) != null) {
							GetMapping rm = m.getAnnotation(GetMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];
						} else if (m.getAnnotation(PostMapping.class) != null){
							PostMapping rm = m.getAnnotation(PostMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];
						} else if (m.getAnnotation(PutMapping.class) != null){
							PutMapping rm = m.getAnnotation(PutMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];
						} else if (m.getAnnotation(DeleteMapping.class) != null){
							DeleteMapping rm = m.getAnnotation(DeleteMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];
						} else if (m.getAnnotation(PatchMapping.class) != null){
							PatchMapping rm = m.getAnnotation(PatchMapping.class);
							if(rm.path().length > 0){
								bath2 = rm.path();
							}
							else {
								if(rm.value().length > 0){
									bath2 = rm.value();
								}
								else {
									bath2 = new String[]{""};
								}
							}
							bath2[0] = bath2[0].startsWith("/")? bath2[0] : "/"+bath2[0];
						} else{
							continue;
						}
						if (bath2.length == 0) {
							continue;
						}

						// 组装RequestMapping
						String uri = bath[0] + bath2[0];

						// uri保存到数据库
						String sql;
						sql = "SELECT * FROM zx_management_operation WHERE interface_uri='" + uri + "'";
						ResultSet rs = stmt.executeQuery(sql);
						if (!rs.next()) {
							sql = "insert into zx_management_operation (interface_uri)" +
									"    values ('" + uri + "')";
							stmt.execute(sql);
							uriList.add(uri);
							j++;
						}
						System.out.println(uri);
						i++;
					}
				}
			}
			System.out.println("共检测出"+i+"个uri");
			if (uriList.size()!=0) {
				uriList.forEach(System.out::println);
			}
			System.out.println("共新增加"+j+"个uri");
			stmt.close();
			conn.close();
		}catch(SQLException | ClassNotFoundException | NoSuchFieldException se){
			// 处理 JDBC 错误
			se.printStackTrace();
		} finally{
			// 关闭资源
			try{
				if(stmt!=null) {stmt.close();}
			}catch(SQLException ignored){
			}// 什么都不做
			try{
				if(conn!=null){ conn.close();}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}




}

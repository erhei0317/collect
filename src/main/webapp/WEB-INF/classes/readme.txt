一、搭建maven工程：
		第一步：问题:创maven工程的时候，第一次没有出现完整的maven格式的文件。
			解决：修改web.xml中是头部为：
			将 Project Facets中的Dynamic Web Module变更为3.0以上的
	 		1. 把Servlet改成3.0，打开项目的web.xml
				<?xml version="1.0" encoding="UTF-8"?>
				<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				    xmlns="http://java.sun.com/xml/ns/javaee"
				    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
				    id="schedule-console" version="3.0">
	
			2. 修改项目的设置，在Navigator下打开项目.settings目录下的org.eclipse.jdt.core.prefs
			把1.5改成1.7
					eclipse.preferences.version=1
					org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode=enabled
					org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.7
					org.eclipse.jdt.core.compiler.compliance=1.7
					org.eclipse.jdt.core.compiler.problem.assertIdentifier=error
					org.eclipse.jdt.core.compiler.problem.enumIdentifier=error
					org.eclipse.jdt.core.compiler.problem.forbiddenReference=warning
					org.eclipse.jdt.core.compiler.source=1.7
					
			
			3. 打开org.eclipse.wst.common.project.facet.core.xml 
			把java改为1.7， 把jst.web改为3.0；
					<?xml version="1.0" encoding="UTF-8"?>
					<faceted-project>
					  <fixed facet="wst.jsdt.web"/>
					  <installed facet="jst.web" version="3.0"/>
					  <installed facet="wst.jsdt.web" version="1.0"/>
					  <installed facet="java" version="1.7"/>
					</faceted-project>
	 		
	 	第二步：到maven中央仓库添加mybatis依赖包。
	 		 mybatis -core
	 		 mybatis -spring 整合包
	 		 mysql-connector-java
	 		 spring-test 4.0.9
	 		 spring-core 4.0.9
	 		 spring-context 4.0.9
	 		 spring-tx 4.0.9
	 		 spring-jdbc 4.0.9
	 		   数据库连接池
	 		 druid
	 	
	 		以上这样基本的与spring整合的包就创建好了
	 		
	 
		 第三部：用mybatis的generator生成映射文件，以及pojo。
				将生成的pojo以及mapper放到 com.candata.coremodel
		 		使用generator时，只需要下载core包，然后根据实际情况修改generator.xml中的内容
		 		java -jar mybatis-generator-core.jar -configfile generator.xml -overwrite
		 		-configfile 指定配置文件  -overwrite 覆盖指令
		 		
		 		generator配置文件的说明：只需要修改三个地方：
		 		1》》数据库驱动包的路径、用户名、url、密码等信息
		 		2》》包中 mapper、pojo类的位置
		 		3》》实体类与数据库的映射字段
	 		
	  	  第四步：1.》》 拷贝spring的配置文件，修改引入的context:porperty-placehodler(属性占位符)中引入的属性文件的参数信息。
	  	  	  2.》》查看数据源的信息、一般只需要把固定的配置文件拷贝过来稍加修改就可以运行了。
	  	 
	  	   第五步：spring与mybatis整合测试：
	  	  		
	  	  	   用两种方法测试：1. 做junit单元测试：
	  	  	   				ApplicationContext ac=
	  	  	   				new ClassPathXMLApplicationContext(new String[]{"spring.xml","spring-mybatis.xml"});
	  	  	   			   	UserService userService=(UserService)ac.getBean("userServiceImpl");
	  	  	   			   	User user=userService.getUserByid(111);
	  	  	   			   	
	  	  	   			2. 让做单元测试的类，在运行期间以SpringJunit的方式启动测试：
	  	  	   				只需要在测试类上添加两个注解，然后将测试的服务入口对象注入进去就可以了
	  	  	   				 @RunWith(SpringJUnit4ClassRunner.class)
	  	  	   				 @ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
	  	  	   				 
	  	 第六步：以上就完成了spring与 mybatis的整合。接下来将springmvc整合到项目中去：
	  	 1.将springmvc的包导入pom.xml文件中：spring-webmvc
	  	 2.添加spring-mvc.xml配置
	  	 	a>添加包扫描组件： component,让spring知道以这个包下面的 以Controller注解的为控制器类。
	  	 	b>如果将jsp放在WEB-INF下面，需要将p:prefix="/" 前缀改为WEB-INF
	  	 	<!-- 对模型视图名称的解析，即在模型视图名称添加前后缀 -->
			<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" 
					p:prefix="/" p:suffix=".jsp" />
		
		 3.修改web.xml : a 添加编码过滤器
		 				b 添加spring上下文监听器、以及配置文件的位置说明
		 				c 添加spring-webmvc的DispacherServlet的servlet,以及配置文件的位置和初始化的一切参数，注意将dispatchservlet设置为应用程序
		 					加载就启动。
		 					
		 					<servlet>
										<description>spring mvc servlet</description>
										<servlet-name>springMvc</servlet-name>
										<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
										<init-param>
											<description>spring mvc 配置文件</description>
											<param-name>contextConfigLocation</param-name>
											<param-value>classpath:spring-mvc.xml</param-value>
										</init-param>
										<load-on-startup>1</load-on-startup>
							</servlet>
	  	 
	  	  	   			   
	 		4.	编写controller层：如果在编写过程中需要 HttpServletRequest的话，需要添加 servlet-api依赖包。
	 		5.	启动Tomcat测试。
	 			maven项目中resource包中的配置文件load失败问题：
	 			1.编辑项目的build path属性
	 			2.在source中将项目的resource包加进来
	 			  http://blog.csdn.net/ljj2312/article/details/60877871
	 		6.	完成基本的测试
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
		
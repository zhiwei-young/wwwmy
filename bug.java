第一题:(忘记格式,应该相对路径)图片之类的只能是相对路径不能使绝对路径.
<body>
    <a href="G:\itheima\tomcat\day28_2\web\1.jpg" > rr</a>
    <a href="1.jpg" > rr</a>
<h1>这是一个下载测试</h1>我速度快和收款方反反复复

</body>

第二题:
servlet中,html,图片等文件,放在web下面,而不是web下面的WEB-INF(耽误了15分钟)

第三题servlet路径问题:
@WebServlet("name=/dowmload")
public class Servletdemo01 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println(username);
    }
}

@WebServlet("/dowmload")
public class Servletdemo01 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println(username);
    }
}
第四题(抄写错误,没话可说)
<a href="/dowmload?username=1.pig" >1下载</a><br/>
<a href="/dowmload?username=2.pig" >2下载</a><br/>
<a href="/dowmload?username=3.txt" >3下载</a><br/>
------------------------------------------------------
<a href="/dowmload?username=1.jpg" >1下载</a><br/>
<a href="/dowmload?username=2.jpg" >2下载</a><br/>
<a href="/dowmload?username=3.txt" >3下载</a><br/>

2类加载器的文件位置写法:相对路径,从out里看.InputStream is = Servletdemo01.class.getClassLoader().getResourceAsStream("../../"+name);
3iot的jar工具包需要放在web-itf里面.不是在最上面的建个lib

  //1.如果文件在resources里面我们使用类加载器将文件转换成流
  //2.如果文件在web里面，使用ServletContext将文件转换成流,路径怎么写"pages/2.jpg"

第五题重大概念错误
关于登录页面代码,三级架构分离时候,user对象是在业务层或者数据库层返回前面呢,,还是在接收层定义呢
既注重点:
1.方法里的变量是动态变量,无论在方法里如何修改,都修改不了,他的原来值.(牢牢记住)
2.什么时候是用返回值?需要返回给别人的时候,什么时候用参数,自己接收别人数据的时候.
3这个题的bug是什么?
    1你把html文件中的变量名和domain中的user类变量名不一致.然后在servletz就读不出来数据.
    2.联系逻辑细节思考能力.你觉得都对,而且问题锁定在读不出值,想想涉及的问题,1数据库,2user类,3sql语句.不要乱找问题,无头苍蝇.
public class Servletdenglu extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");//此处无用,但是读的时候加上,中文论码处理问题
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);

        JdbcTemplate template=new JdbcTemplate(JDBCUtil.getDataSource());

        String sql = "SELECT * FROM USER WHERE username=? AND password=?";
        //执行SQL语句，并且将结果集封装到user对象中
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }

        response.setContentType("text/html;charset=UTF-8");//解决响应的中文乱码
        PrintWriter writer = response.getWriter();
        if (user != null) {
            //登录成功
            writer.write("登录成功");
        }else {
            //登陆失败
            writer.write("登录失败");
        }
        System.out.println(user);


    }
}
一.servlet理解
1.三个生命周期方法:init();只执行一次;service()每次都是执行;detroy()服务器关闭时执行.目前servlet里的doget()
和dopodt都是和上面三个并列的方法,属于成员方法.暂时是没有用到那三个方法,如果需要,就写出来(怎么写?shift.alt.s)
2.你一直不理解的doget和dopost有参数怎么执行的,告诉你是连这个方法和里面的参数都是服务器自己调用的的,你只需要写出来方法就行,
你想想,是不是这两个个方事,法都是自动执行的,也就是说这个servlet类的对象服务器自己new了.然后调用这两个方法
3.jsp的本质是servlet.所有servlet能做的事,jsp都可以.
<form action="loginser.jsp" method="get"/>表格提交到loginser.jsp了,类比servlet就好.

 <%--向服务器提交用户的id，不让用户看到--%>
      <input type="hidden" name="id" value="${contact.id}">
      <input type="hidden" name="id" value="${xontact.id}">
二:这次把这个记住了,背会了,;清楚在什么时候用
//如果能够除尽，则直接是totalSize/pageSize就是总页数，否则就要在其基础上+1
Integer totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
三
String sql = "select * from contact limit ?,?";
List<Contact> contactList = template.query(sql, new BeanPropertyRowMapper<>(Contact.class), (currentpage-1)*pageSize, pageSize);
四.记住这个方法的写好,和这种方法
 function del(id) {
            //1.弹出一个确认框
            var flag = confirm("你真的要删除这个联系人吗?");
            //2.如果真的要删除
            if (flag) {
                //则发送请求到DeleteServlet进行删除
                //location.href = "/delete?id="+id
                location.href = "/contact?action=delete&id="+id;
            }
        }

五.反射中,读取配置文件中的键和值文件为(properties)
 //使用ResourceBundle来读取properties文件
        //经过这一步之后，配置文件中的所有数据都封装到bundle对象中
        ResourceBundle bundle = ResourceBundle.getBundle("beaninfo");
        String className = bundle.getString("className");//获取到类的全限定名
        //获取字节码
        Class<?> clazz = Class.forName(className);
        Object obj = clazz.newInstance();
        //将配置文件中的其它数据都遍历出来，并且设置到obj对象中
        Enumeration<String> keys = bundle.getKeys();
六,全限定名:src下面包名加类名.
七.两种编程思想:面向接口编程思想和整合到一个servlet处理多个请求思想

   <!--配置文件的方式怎么配置Filter-->
    <!--<filter>
        <filter-name>FilterDemo01</filter-name>
        <filter-class>com.itheima.filter.FilterDemo01</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>FilterDemo01</filter-name>
        &lt;!&ndash;filter的映射路径的作用:匹配要过滤的资源&ndash;&gt;
        <url-pattern>/demo01</url-pattern>
        <url-pattern>/demo02</url-pattern>
    </filter-mapping>-->
</web-app>

1语法规范:
response.setContentType("text/html;charset=UTF-8");
  request.setCharacterEncoding("UTF-8");
2:总结一下读取配置文件的几种方法和几种不同的配置文件格式
  public void init(FilterConfig config) throws ServletException {
        //这个方法只会在Filter对象创建的时候执行一次
        //读取文件中的数据，将每一个非法字符串都存放到strs集合中
        //1.将str.txt转换成字节输入流
        InputStream in = IllegalFilter.class.getClassLoader().getResourceAsStream("str.txt");
        //2.将字节输入流包装成字符输入流
        try {
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            //3.将字符输入流包装成BufferReader
            BufferedReader bufferedReader = new BufferedReader(reader);
            //4.循环读取，每次会读到一行---->就是一个非法字符串
            String str = null;
            while ((str = bufferedReader.readLine()) != null){
                //每读取到一个字符串，就将其添加到集合中
                strs.add(str);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
6.12号
1.jstl格式书写 <c:if test="${empty user}">
2.uri和url的使用场景和定义
//校验登录购物车时候是否已经登录,没有登录就返回去登录页面
     HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String uri = request.getRequestURI();
        StringBuffer url = request.getRequestURL();
        System.out.println("uri的值是:"+uri);
        System.out.println("url的值是:"+url);

        if (user==null){
           if (uri.contains("Car")){
               response.sendRedirect("login.jsp");
               return;
           }
       }
	//首页显示时候
	uri的值是:/
	url的值是:http://localhost:8080/
	//客户端登录时候
	uri的值是:/Car.jsp
	url的值是:http://localhost:8080/Car.jsp
-----------------------------------------------------------
3.filter的注释.	
/*@WebFilter({"/demo01","/demo02"})*/
/*@WebFilter(value ={"/demo01","/demo02"},dispatcherTypes ={ DispatcherType.FORWARD,DispatcherType.REQUEST})*/接收转发和跳转
-------------------------------------------------------
需要复习背诵的知识:
elh和jstl,从概念开始就不熟悉,api背会,特别是遍历的.

不会的bug,错了很多次了
-----------------------------------------------------
String action = request.getParameter("action");

        if ("showAll".equals(action)){
            System.out.println("看来没有弄出来");
            showAll(request,response);
        }
-------------------------------------------------------------
如何把字符串转化成intiger?方法不会了
Intiger.valueof()
注意:先假设可以,写出语句,然后系统提示会有这个方法给你补充代码
-------------------------------------------------------------
重点:错了两次了.
servlet方法中利用反射,得到方法.
错误的,你的
-------------------------------------------------------------
 @WebServlet("/contact")
public class ContactServlet extends HttpServlet {
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        Class aClazz = ContactServlet.Class();
        try {
            Method method = aClazz.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(aClazz,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
-------------------------------------------------------------------------------
三个错误:
1得到字节码文件却是是三种办法,但是那一种是类名,也可以用,但是在题中本类是父类,会被其他继承引用,所以this,代表当前对象
2字节码对象通过方法可以得到原来类的成员方法,但是有好几个方法的,要区分,你用的只是普通方法,而求的是私有方法,应为getDeclareMethod()
3用反射使用方法执行的时候应该,参数是类的对象,你写的是字节码对象.
 @WebServlet("/contact")
public class ContactServlet extends HttpServlet {
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        Class aClazz = this.getClass();
        try {
            Method method = aClazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
---------------------------------------------------------------------
   String sql="select *from contact  limit ?,?";
   你在写几遍,你就没有记住过
---------------------------------------------------
成员变量里写方法是没有提示的,可以先从方法里写好了在粘贴过去
如: privary Iservice service=Class.forName("").instence;
----------------------------------------------------------------   
问题:一直找不到cookie的名字"cookieAuto",测试找不到原因,后来发现,是写在了重定向后面了,很奇怪,
注意:写在重定向后面的其他代码可以执行,都打印出来了.但是cookie不执行
            //为了后面的跳转到购物车做准备,存储对象到session
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            response.sendRedirect("/success.jsp");
			
			if ("on".equals(rememberAuto)){
                System.out.println("自动登录标识符:"+rememberAuto);
                Cookie cookieAuto = CookieUtil.createCookie("cookieAuto", username+"#"+password, 24 * 60 * 60 * 7, request.getContextPath());
                response.addCookie(cookieAuto);
                
            }else {
                //cookie.setMaxAge(0);
            }

-----------------
正确写法:
if ("on".equals(rememberAuto)){
                System.out.println("自动登录标识符:"+rememberAuto);
                Cookie cookieAuto = CookieUtil.createCookie("cookieAuto", username+"#"+password, 24 * 60 * 60 * 7, request.getContextPath());
                response.addCookie(cookieAuto);
                
            }else {
                //cookie.setMaxAge(0);
            }

            //为了后面的跳转到购物车做准备,存储对象到session
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            response.sendRedirect("/success.jsp");
-------------------------------------------------------------------------
错误:自动登录时候你就这样做错了,根本传不了对象.
记住了:cookie 只能存储string类型,其他的不行.所以不能给客户端传对象和int等
-----------------------------------------------------------------------
crtl f 查找同名方法
-------------------------------
ctrl 左键
----------------
今天错误:
 1:学习了异常的问题.还要再回顾,晚上再看看
 2:写错字:travle
 3:没有写response"text/html;UTF-8",后来补上也不错了.
 4:你的逻辑感还没有用好,页面显示问号肯定是没有解决乱码,你还在各种测试,记住,就是粗心,逻辑也能一眼看出来
 5;刚刚action写成active,
 6:categoryServlet 明明继承了 baseSerlet,但是你还是用了重写的方法. 

 bug的原因:1没有记住,现在jQuery遍历忘了
		   2:不专心
		   3:没有系统的方法
------------------------------------------
---------------------------------
错误:前端ID中类的调用,应该是#+ID+"空格"+"."+类名
看看你下面怎么错的
$.each(poplist,function (index, element) {
    $("#popularity.row").append("<div class=\"col-md-3\">\n" +
	"                                <a href=\"javascript:;\">\n" +
	"                                    <img src=\""+element.rimage+"\" alt=\"\">\n" +
	"                                    <div class=\"has_border\">\n" +
	"                                        <h3>\"+element.rname+\"</h3>\n" +
	"                                        <div class=\"price\">网付价<em>￥</em><strong>889</strong><em>起</em></div>\n" +
	"                                    </div>\n" +
	"                                </a>\n" +
	"                            </div>")
})
------------------------------------------------

下面这句代码没有错误,但是<List<Category>>爆红了.什么原因?导包导出错了.解决办法,把上面的那个包删了,适合的会自动出来

 
 /*import jdk.internal.org.objectweb.asm.TypeReference;*/
 import com.fasterxml.jackson.core.type.TypeReference;把上面的那个删了,自动出来.
 
 List<Category> list = mapper.readValue(str,new TypeReference<List<Category>>() {});

 bug:1今天前端出不来,觉得对,但是验证没错误,那就先粘贴别人的代码,或者重写,变方法和变量名试试.
     2根部不清楚ID调用内部内需要加空格.
     3还记得两次因为方法名重复后无端报错.
     4导包错粗也几次了,还记得user嘛?
     5不要想着有什么特殊情况,你没见过的错误,迄今为止的你所有错误都是你知道的
-------------------------------------------
耽误好几个小时了::::::::对字符串拼接不会,再练习,影响很大速度
 $.post("/travle01/route","action=findByPage&cid="+cid+"&currentPage="+currentPage,function () {

        },"json").

-------------------------------
学习插入字符串
(注:字符串粘贴到字符串会自动生成,)
   $.each(poplist,function (index, element) {
	$("#popularity .row").append("<div class=\"col-md-3\">\n" +
		"                                <a href=\"javascript:;\">\n" +
		"                                    <img src=\""+element.rimage+"\" alt=\"\">\n" +
		"                                    <div class=\"has_border\">\n" +
		"                                        <h3>"+element.rname+"</h3>\n" +
		"                                        <div class=\"price\">网付价<em>￥</em><strong>889</strong><em>起</em></div>\n" +
		"                                    </div>\n" +
		"                                </a>\n" +
		"                            </div>")
	})
----------------------------------------------------
		总结:简单字符:既可以是'${value}',也可以是#${vslue}
		javaBean必须是#{JavaBean的属性名}如:#{username}
		

<!--引入getParameter.js-->
    <script src="js/getParameter.js"></script>

		从地址栏如果得到的对象为空,那么从servlet里得到的就是空字符串"",不是null.
-------------------------------------------------------------------------------------------------
<select id="findUserById" parameterType="int" resultType="User">
       select * from user where id='${value}'
    </select>
---------------------------
<!--
        传入的参数是JavaBean的时候，#{JavaBean的属性名}
    -->
    <insert id="addUser" parameterType="user">
      insert into user values (#{id},#{username},#{birthday},#{sex},#{address})
    </insert>
----------------------------
    <!--写一个delete标签，对应那个deleteUser-->
    <delete id="deleteUser" parameterType="int">
        <!--
            传入的参数是简单数据类型，那么我们就在#{随便写一个字符串做占位符}
        -->
        delete from user where id=#{abc}
    </delete>
-------------------------------------------------------------------------------------------------
封装到另一个对象的时候,如果这个对象和原对象只属性名不一样,可以两种办法解决:
1可以用别名替代
 <select id="findUserInfoById" parameterType="int" resultMap="userInfo">
        select * from user where id=#{id}
     select id userId,username,address userAddress, sex userSex,birthday userBirthday from user where id=#{id}-->
    </select>
2.可以自定义封装一个结果集
<!--
        自定义一个结果集映射resultMap
        id是这个结果集映射的唯一表示，用于让其他标签移入该映射
        type就是该结果集映射对应的POJO类的全限定名
    -->
    <resultMap id="userInfoMap" type="userInfo">
        <!--
           id子标签，用于映射主键
             column属性表示结果集的字段名，property表示POJO的属性名
        -->
        <id column="id" property="userId"></id>
        <!--
            result标签，用于映射其它非主键字段
        -->
        <result column="sex" property="userSex"></result>
        <result column="birthday" property="userBirthday"></result>
        <result column="address" property="userAddress"></result>
    </resultMap>
    <!--
        查询结果并且封装到UserInfo对象中
    -->
    <!--
        引入自定义映射规则
    -->
    <select id="findUserInfoById" parameterType="int" resultMap="userInfoMap">
        select * from user where id=#{id}
        <!--select id userId,username,address userAddress, sex userSex,birthday userBirthday from user where id=#{id}-->
    </select>
------------------------------
别名规则:
	<typeAliases>
        <!--<typeAlias type="com.itheima.domain.User" alias="user"></typeAlias>-->
        <!--
            给domain包中的所有类统一配置别名,通过包扫描
            别名是啥?别名就是你的类名，不区分大小写
        -->
        <package name="com.itheima.domain"/>
    </typeAliases>
------------------------
1:mybitis无法运行是因为你把类写到了dao层,而不是domain层,你检查来就是看不出来
2:昨天考试定的工程,死活跑不起原因是:1实现类没有没有去实现接口.
									2配置文件没有修改成现在的类路径,还用的老师的那个.
---------------------------
  <!-- 屏蔽表单的同步请求 -->
  <form action="#" method="post" onsubmit="javascript:return false;">
  <form action="#" method="post" onsubmit="javascript:return false">
----------------------------------------------------------------------------------
 这个你写错了,看看这是什么意思/自己写一遍
 String sql="SELECT * FROM tab_route r , tab_favorite f WHERE r.rid=f.rid  ORDER BY r.count desc LIMIT ?,?"
 --------------------------------------------------------------------------------------
 事务关闭之后,你执行查询语句,为什么还有数据???

 晕:因为事务的么本质是防止修改出错,你查询有没有影响到数据库,当然不影响了.一定记住应用场景
 ----------------------------------------------------------------------------------------------------------
myBatis几种小问题原因:
	1:在dao中会否一对一写成一对多,<associate> 和<colection>
	2:configuration报错,可能是标签顺序写错了,mybatis里很严格的标签顺序.

二级缓存四个条件:
		1:在配置文件加:  <setting name="cacheEnabled" value="true" />
		2:在映射文件加:  <cache />
		3:所涉及的domain类文件,需要实现serieal
		4:所有使用的sqlsession使用户必须关闭sqlsassion.close
------------------------------
mabatis 的inrerface 接口里,参数都是一个对象,不允许是多个值,若有两个多个,可以封装成map,list,对象.

错误写法: void addUser(int id, String username, Date date,String address,String sex);
正确写法;void addUser(User user);
------------------
   所有类型可用:@Select("select * from user where username like #{user.username}")*/
				这个里面不可以写百分号,在传入参数的地方写.
  简单数据类型可用:@Select("select * from user where username like '%${value}%'")
  -------------------------
  mybits里面,很方便的一点是返回值可以自己定.可以是map,可以是list<user>

   @Select("select * from user where id=#{id}")
  Map<String,Object> findUserById(int id);
  ----------------
   @Select("select * from user where id=#{id}")
  list<user> findUserById(int id);
  ----------------------------------------------------------------------------------------
resource和Autowired一样,在注解方式的时候,做注入实现类使用,只可以是核心容器中的Javabean
-------------------------
autowired若是不能自动匹配,需要借助 qualifier标签.
/*@Autowired
    @Qualifier("accountDao")*/
------------------------------------
 * 1.Component  可以使用在任意层
 * 2.Service  建议使用在业务层
 * 3.Repository 建议使用在持久层
 * 4.Controller  建议使用在Web层
 *
 * 配置Bean的范围:使用Scope注解
 *
 * Bean的生命周期，如果是单例情况:
 * 1. 核心容器加载的时候创建对象
 * 2. 核心容器销毁的时候销毁对象
 *
 * 如果是多例的情况:
 * 1. 需要实例对象的时候创建对象
 * 2. 没有引用指向它的时候，就会被GC机制回收
 *
 * 通过@PostConstruct注解来指定初始化方法
 * 通过@PreDestroy注解指定销毁方法
 *
 * 使用注解的方式进行依赖注入,而且要注入的变量可以没有set方法
 * 1.Autowired ,只能注入JavaBean对象，注入JavaBean对象的前提是:该JavaBean对象必须在Spring的核心容器中
 *       Autowired注解进行注入的时候，会进行自动装配:
 *       1.要注入的属性的实现类对象在核心容器中只有一个的话，就将这个实现类对象进行注入
 *       2.如果要注入的属性的实现类对象在核心容器中有多个的话，就会根据变量名对应JavaBean的id进行注入
 *       3.如果要注入的属性的实现类对象在核心容器中有多个的，并且变量名和任意一个JavaBean的id都不对应的情况，
 *       那么我们就得再添加Qualifier注解的value属性，指定要注入的那个JavaBean的id
 * 2. Resource注解，只能注入JavaBean对象
 *
 * 3. Value注解，注入简单数据类型(基本数据类型和String类型)
 ------------------------------
 //注解引入配置文件
 @PropertySource("classpath:druidconfig.properties")
 //宝扫描
@ComponentScan("com.itheima")
public class BeanConfiguration {
	//具体引入配置文件格式
    @Value("${user}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    @Bean("queryRunner")
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }
--------------

	为什么出现@org.junit.Test???
		因为你的test方法名字是test,不能是test,改成其他的.
	public class Test {
    @org.junit.Test
    public void test(){
        System.out.println("圣诞节很多事");
        
    }
}
-------------------------
spring中,JDBCtemplete可以继承jdbcdaosupport
	但是不能用注解方式配DataSource,需要用配置文件配置DataSource.
------------------------------
2019/7/24
	参数名要和传过来的参数一致,要不然不会转化,得不到值.
  public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds)  
	public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
	------------------
		自己检查哪里错了??(三处错误呢)
		<update id="setCheckGroup" parameterType="CheckGroup">
        update t_checkgroup

        <set>
          <if test="code!=null">
              code=#{code},
          </if>
            <if test="name!=null">
                name=#{name},
            </if>
            <if test="helpCode!=null">
                helpCode=#{helpCode},
            </if>
            <if test="sex != null">
                type = #{sex},
            </if>
            <if test="remark != null">
                remark = #{remark},
            </if>
            <if test="attention != null">
                attention = #{attention}
            </if>
				where id=#{id}
        </set>
    </update>
------------------------
正确的方法:
<update id="setCheckGroup" parameterType="CheckGroup">

        update t_checkgroup

        <set>
          <if test="code!=null">
              code=#{code},
          </if>
            <if test="name!=null">
                name=#{name},
            </if>
            <if test="helpCode!=null">
                helpCode=#{helpCode},
            </if>
            <if test="sex != null">
                sex = #{sex},
            </if>
            <if test="remark != null">
                remark = #{remark},
            </if>
            <if test="attention != null">
                attention = #{attention},
            </if>
        </set>
        where id=#{id}
    </update>
-----------------------
		第一种本方法:(将集合转化成map集合)
	 public void addCheckGroup_checkitem(CheckGroup checkGroup, Integer[] checkitemIds) {
        if (checkitemIds != null & checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                HashMap<String, Integer> hm = new HashMap();
                hm.put("checkgroup_id", checkGroup.getId());
                hm.put("checkitem_id", checkitemId);
                checkGroupDao.addCheckGroup_checkitem(hm);
            }
        }
    }
}	   
	<!--设置检查组和检查项的关联关系-->
    <insert id="addCheckGroup_checkitem" parameterType="hashmap">
        insert into t_checkgroup_checkitem(checkgroup_id,checkitem_id)
      		values
      	(#{checkgroup_id},#{checkitem_id})
    </insert>
---------------------------------------------------------------
	public void functinon(){
	for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            };
	}	
	<insert id="addCheckGroupCheckItem" parameterType="int">
      insert into t_checkgroup_checkitem values (#{checkGroupId}, #{checkitemId})
    </insert>
-------------------------
如果是请求体是@requestBody,时候,控制器必须是:resController.
-----------------------------
		insert插入的时候,通过<selectKey >标签,可以将id赋给对象,便于调用.
	public class PackageServiceImpl implements  PackageService{

    @Autowired
    private PackageDao packageDao;

    @Override
    public void add(Package pak, Integer[] checkgroupIds) {

        packageDao.addPackage(pak);

        Integer pakId = pak.getId();

        if (checkgroupIds!=null){

            for (Integer checkgroupId : checkgroupIds) {

                packageDao.addPackage_group(pakId,checkgroupId);
            }
        }

    }
	----------	
	<insert id="addPackage" parameterType="Package">

        <selectKey resultType="int" order="AFTER" keyProperty="id">
            select last_insert_id()
        </selectKey>
        insert into t_package (name,code,helpCode,sex,age,price,remark,attention,img)
        values (#{name},#{code},#{helpCode},#{sex},#{age},#{price},#{remark},#{attention},#{img})
    </insert>
------------------

bug解决思路:
	1核心思路:逻辑永远是对的.当没有知识的时候,就用逻辑.
	2:先看报错和日志.判断出错地方.
	3:用debug打断点看数据.
	4:能确定问题地方,就是找不到具体的问题.(很可能是格式,书写,顺序,字符,错别字)粘贴对的,先替换自己的.
	5:排除法:记得你的前端老师解决问题:明知道是前端的,那就排查问题吧,逐一删除找到问题点.
	6:看看注解和类是否用了同名的其他类名.
-------------------------------
	空指针异常:是调用者出现了空指针,,也就是service出现空指针,重点就是去验证为什么service空指针了,不是看结果user的.
	看看User user=service.finById(i);
-----------------------
	RBAC (Role Base Access Controll)权限模块数据模型
	1自己做的话先从权限表和菜单表入手,因为这个是访问的页面呀,也就是权限,在代码里是路径,url
	2然后在前端找到所有的访问路径,收集整理,找领导问这些权限都给哪些角色开放.
	3最后中间表自己来.
	------------------------------------------
	在学习Spring拦截器时发现

/*可以拦截任何请求?
/**也可以这两者有什么区别??

/**的意思是所有文件夹及里面的子文件夹
/*是所有文件夹，不含子文件夹--
-------------------------------------------------
对配置不清楚,写了注入数据,在配置文件里必须有注入,或者有配置注解方式.
@Autowired
     BCryptPasswordEncoder encoder;
---------------------
	   <bean id="securityUserService" class="com.itheima.security.SecurityUserServiceImpl">
        <property name="encoder" ref="encoder"/>
    </bean>
-------------------------
空指针异常最容易解决了,加上非空判断就可以啦,如果面试问道,就说最多的是illgle staut非法状态异常.这是业务层的异常.

set集合不会了,怎么回事??复习//因为角色权限都是唯一性,一个人不会有两个同种角色.需要去重,set可以,而list不行.
// 得到用户所拥有的角色和权限
        Set<Role> roles = user.getRoles();
---------------------------------
报错很麻烦,可能就是基础配置没有做好,详细的看一遍,检查一遍.
 今晚授权案例就是service没有写注解,出了那么多次错误.
---------------------
https://www.cnblogs.com/kimi9py/p/5698738.html

1. 拦截器interceptor是基于Java的反射机制的，而过滤器Filter是基于函数回调,实现的filter接口中doFilter方法就是回调函数。
2. 拦截器interceptor不依赖与servlet容器，过滤器Filter依赖与servlet容器,没有servlet容器就无法来回调doFilter方法。
3. 拦截器interceptor只能对action请求起作用，而过滤器Filter则可以对几乎所有的请求起作用,Filter的过滤范围比Interceptor大,Filter除了过滤请求外通过通配符可以保护页面，图片，文件等等。
4. 拦截器interceptor可以访问action上下文、值栈里的对象，而过滤器Filter不能访问。
5. 在action的生命周期中，拦截器interceptor可以多次被调用，而过滤器Filter只能在容器初始化时被调用一次。
6. 拦截器interceptor可以获取IOC容器中的各个bean，而过滤器Filter就不行。在拦截器里注入一个service，可以调用业务逻辑
----------------
数据库的外键设置不要为空,否则出问题
---------------
在web层,服务应该在哪里写?
2今日一劳永逸的解决日历的格式问题,什么时候用什么,date和delenda??

-------------------------------
框架ssm中,
-------------------
@Service(interfaceClass = CheckGroupService.class)//???这是说明被外部资源引用
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired//本地资源引用
    private CheckGroupDao checkGroupDao;
}
-------------------
使用Feign的时候,如果参数中带有@PathVariable形式的参数,则要用value属性去指定。
-----------------
畅购项目
1:全局异常类,如果写在connom工程里,但是没有启动类如何注入容器eureka里呢?
答:只要让这个异常类所在的包目录和其他项目的包目录一致,或者在其包目录下都可以加入容器中.
如:异常类MyException 路径:connom工程下:com.changgou.goods.exception.MyException
   品牌启动类BroundApplication 路径:bround工程下:com.changgou.goods.BroundApplication
这种情况就可以扫描到,可以注入容器里.
----------------------------------------------

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;
}
service层@@Autowired下面categoryMapper爆红,原因是idea认为找不到categoryMapper ,也就是没有注入,其实,在启动类里
已经注入了@MapperScan(basePackages = {"com.changgou.goods.dao"}),因为是字符串,idea检测不出来而已,不影响运行,可以配默认值false.
------------------------------
启动类
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.goods.dao"})
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class);
    }
}
--------------------
下满是es搜索时的pojo类,如果数据上面没有标注@Field(type = FieldType.Keyword),系统默认为type类型为text.
如下的面private String spec,当用在搜索规格展示的时候,就需要加上"spec.keyword"这个里的keyword,若pojo中有既不需要.
//设置分组条件  商品的规格
nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpecgroup").field("spec.keyword").size(100));

@Document(indexName = "skuinfo",type = "docs")
public class SkuInfo implements Serializable {

    //商品id，同时也是商品编号
    @Id
    private Long id;

    //SKU名称
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    //商品价格，单位为：元
    @Field(type = FieldType.Double)
    private Long price;

    //类目名称
    @Field(type = FieldType.Keyword)
    private String categoryName;

    //品牌名称
    @Field(type = FieldType.Keyword)
    private String brandName;

    //规格
    private String spec;
---------------------------------
controller里,涉及到requestBody时,不加require=false,就是可以为空,就是可以不传值,默认不写就是必须有参数,默认为true.
@PostMapping
    public Map search(@RequestBody(required = false) Map searchMap ){

        return  skuService.search(searchMap);
    }
------------------------
集群时候,端口号好几个,访问的时候怎么写代码?
注入,@Autowired
    private LoadBalancerClient loadBalancerClient;
	ServiceInstance choose = loadBalancerClient.choose("user-auth");
	这样可以随机得出项目的名称端口号,再与固定的后缀拼接就有了不同的访问路径

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret, String grandType) {

        //1.定义url (申请令牌的url)
        //参数 : 微服务的名称spring.appplication指定的名称
        ServiceInstance choose = loadBalancerClient.choose("user-auth");
        String url =choose.getUri().toString()+"/oauth/token";
		
	}

---------------------------
BCryp加密,来源于package org.springframework.security.crypto.bcrypt;
 @Test
    public void BCrypAddSecret(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String changgou = passwordEncoder.encode("changgou");
        System.out.println(changgou);
    }
-----------------------------------------------------------------------------------
注意了:feign注入的时候,把目录统一了,都写成com.changgou...,否则找不到feign.你下面当初没有统一在com下面,出问题
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.order.dao"})
@EnableFeignClients(basePackages = {"com.changgou.user.feign","com.changgou.goods.feign"})

public class OrderApplication {
    public static void main(String[] args) {

        SpringApplication.run(OrderApplication.class,args);
    }
}
-----------------------------------------------
Redis方法
springboot中 RedisTemplte
//读取所有的key
 Set keys = redisTemplate.boundHashOps("SeckillGoods_" + extName).keys();
//hash方式存入数据
 redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).put(seckillGood.getId(),seckillGood);
 //设置有效期
 redisTemplate.expireAt(SystemConstants.SEC_KILL_GOODS_PREFIX + extName,DateUtil.addDateHour(startTime, 2));
  //读取这个filed下的key和值
  redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).value();
            




30分钟到5秒
1代码:for
2框架:
3sql:
4应用层:pio ,用stream解析.
5:多线程
6Ljvm上解决
6:dbaOracle内存调整一下

and用in
2关联表放在后面去
3创建索引:镇对每个字段

















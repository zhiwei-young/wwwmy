��һ��:(���Ǹ�ʽ,Ӧ�����·��)ͼƬ֮���ֻ�������·������ʹ����·��.
<body>
    <a href="G:\itheima\tomcat\day28_2\web\1.jpg" > rr</a>
    <a href="1.jpg" > rr</a>
<h1>����һ�����ز���</h1>���ٶȿ���տ��������

</body>

�ڶ���:
servlet��,html,ͼƬ���ļ�,����web����,������web�����WEB-INF(������15����)

������servlet·������:
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
������(��д����,û����˵)
<a href="/dowmload?username=1.pig" >1����</a><br/>
<a href="/dowmload?username=2.pig" >2����</a><br/>
<a href="/dowmload?username=3.txt" >3����</a><br/>
------------------------------------------------------
<a href="/dowmload?username=1.jpg" >1����</a><br/>
<a href="/dowmload?username=2.jpg" >2����</a><br/>
<a href="/dowmload?username=3.txt" >3����</a><br/>

2����������ļ�λ��д��:���·��,��out�￴.InputStream is = Servletdemo01.class.getClassLoader().getResourceAsStream("../../"+name);
3iot��jar���߰���Ҫ����web-itf����.������������Ľ���lib

  //1.����ļ���resources��������ʹ������������ļ�ת������
  //2.����ļ���web���棬ʹ��ServletContext���ļ�ת������,·����ôд"pages/2.jpg"

�������ش�������
���ڵ�¼ҳ�����,�����ܹ�����ʱ��,user��������ҵ���������ݿ�㷵��ǰ����,,�����ڽ��ղ㶨����
��ע�ص�:
1.������ı����Ƕ�̬����,�����ڷ���������޸�,���޸Ĳ���,����ԭ��ֵ.(���μ�ס)
2.ʲôʱ�����÷���ֵ?��Ҫ���ظ����˵�ʱ��,ʲôʱ���ò���,�Լ����ձ������ݵ�ʱ��.
3������bug��ʲô?
    1���html�ļ��еı�������domain�е�user���������һ��.Ȼ����servletz�Ͷ�����������.
    2.��ϵ�߼�ϸ��˼������.����ö���,�������������ڶ�����ֵ,�����漰������,1���ݿ�,2user��,3sql���.��Ҫ��������,��ͷ��Ӭ.
public class Servletdenglu extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");//�˴�����,���Ƕ���ʱ�����,�������봦������
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);

        JdbcTemplate template=new JdbcTemplate(JDBCUtil.getDataSource());

        String sql = "SELECT * FROM USER WHERE username=? AND password=?";
        //ִ��SQL��䣬���ҽ��������װ��user������
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }

        response.setContentType("text/html;charset=UTF-8");//�����Ӧ����������
        PrintWriter writer = response.getWriter();
        if (user != null) {
            //��¼�ɹ�
            writer.write("��¼�ɹ�");
        }else {
            //��½ʧ��
            writer.write("��¼ʧ��");
        }
        System.out.println(user);


    }
}
һ.servlet���
1.�����������ڷ���:init();ִֻ��һ��;service()ÿ�ζ���ִ��;detroy()�������ر�ʱִ��.Ŀǰservlet���doget()
��dopodt���Ǻ������������еķ���,���ڳ�Ա����.��ʱ��û���õ�����������,�����Ҫ,��д����(��ôд?shift.alt.s)
2.��һֱ������doget��dopost�в�����ôִ�е�,�����������������������Ĳ������Ƿ������Լ����õĵ�,��ֻ��Ҫд������������,
������,�ǲ���������������,�������Զ�ִ�е�,Ҳ����˵���servlet��Ķ���������Լ�new��.Ȼ���������������
3.jsp�ı�����servlet.����servlet��������,jsp������.
<form action="loginser.jsp" method="get"/>����ύ��loginser.jsp��,���servlet�ͺ�.

 <%--��������ύ�û���id�������û�����--%>
      <input type="hidden" name="id" value="${contact.id}">
      <input type="hidden" name="id" value="${xontact.id}">
��:��ΰ������ס��,������,;�����ʲôʱ����
//����ܹ���������ֱ����totalSize/pageSize������ҳ���������Ҫ���������+1
Integer totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
��
String sql = "select * from contact limit ?,?";
List<Contact> contactList = template.query(sql, new BeanPropertyRowMapper<>(Contact.class), (currentpage-1)*pageSize, pageSize);
��.��ס���������д��,�����ַ���
 function del(id) {
            //1.����һ��ȷ�Ͽ�
            var flag = confirm("�����Ҫɾ�������ϵ����?");
            //2.������Ҫɾ��
            if (flag) {
                //��������DeleteServlet����ɾ��
                //location.href = "/delete?id="+id
                location.href = "/contact?action=delete&id="+id;
            }
        }

��.������,��ȡ�����ļ��еļ���ֵ�ļ�Ϊ(properties)
 //ʹ��ResourceBundle����ȡproperties�ļ�
        //������һ��֮�������ļ��е��������ݶ���װ��bundle������
        ResourceBundle bundle = ResourceBundle.getBundle("beaninfo");
        String className = bundle.getString("className");//��ȡ�����ȫ�޶���
        //��ȡ�ֽ���
        Class<?> clazz = Class.forName(className);
        Object obj = clazz.newInstance();
        //�������ļ��е��������ݶ������������������õ�obj������
        Enumeration<String> keys = bundle.getKeys();
��,ȫ�޶���:src�������������.
��.���ֱ��˼��:����ӿڱ��˼������ϵ�һ��servlet����������˼��

   <!--�����ļ��ķ�ʽ��ô����Filter-->
    <!--<filter>
        <filter-name>FilterDemo01</filter-name>
        <filter-class>com.itheima.filter.FilterDemo01</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>FilterDemo01</filter-name>
        &lt;!&ndash;filter��ӳ��·��������:ƥ��Ҫ���˵���Դ&ndash;&gt;
        <url-pattern>/demo01</url-pattern>
        <url-pattern>/demo02</url-pattern>
    </filter-mapping>-->
</web-app>

1�﷨�淶:
response.setContentType("text/html;charset=UTF-8");
  request.setCharacterEncoding("UTF-8");
2:�ܽ�һ�¶�ȡ�����ļ��ļ��ַ����ͼ��ֲ�ͬ�������ļ���ʽ
  public void init(FilterConfig config) throws ServletException {
        //�������ֻ����Filter���󴴽���ʱ��ִ��һ��
        //��ȡ�ļ��е����ݣ���ÿһ���Ƿ��ַ�������ŵ�strs������
        //1.��str.txtת�����ֽ�������
        InputStream in = IllegalFilter.class.getClassLoader().getResourceAsStream("str.txt");
        //2.���ֽ���������װ���ַ�������
        try {
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            //3.���ַ���������װ��BufferReader
            BufferedReader bufferedReader = new BufferedReader(reader);
            //4.ѭ����ȡ��ÿ�λ����һ��---->����һ���Ƿ��ַ���
            String str = null;
            while ((str = bufferedReader.readLine()) != null){
                //ÿ��ȡ��һ���ַ������ͽ�����ӵ�������
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
6.12��
1.jstl��ʽ��д <c:if test="${empty user}">
2.uri��url��ʹ�ó����Ͷ���
//У���¼���ﳵʱ���Ƿ��Ѿ���¼,û�е�¼�ͷ���ȥ��¼ҳ��
     HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String uri = request.getRequestURI();
        StringBuffer url = request.getRequestURL();
        System.out.println("uri��ֵ��:"+uri);
        System.out.println("url��ֵ��:"+url);

        if (user==null){
           if (uri.contains("Car")){
               response.sendRedirect("login.jsp");
               return;
           }
       }
	//��ҳ��ʾʱ��
	uri��ֵ��:/
	url��ֵ��:http://localhost:8080/
	//�ͻ��˵�¼ʱ��
	uri��ֵ��:/Car.jsp
	url��ֵ��:http://localhost:8080/Car.jsp
-----------------------------------------------------------
3.filter��ע��.	
/*@WebFilter({"/demo01","/demo02"})*/
/*@WebFilter(value ={"/demo01","/demo02"},dispatcherTypes ={ DispatcherType.FORWARD,DispatcherType.REQUEST})*/����ת������ת
-------------------------------------------------------
��Ҫ��ϰ���е�֪ʶ:
elh��jstl,�Ӹ��ʼ�Ͳ���Ϥ,api����,�ر��Ǳ�����.

�����bug,���˺ܶ����
-----------------------------------------------------
String action = request.getParameter("action");

        if ("showAll".equals(action)){
            System.out.println("����û��Ū����");
            showAll(request,response);
        }
-------------------------------------------------------------
��ΰ��ַ���ת����intiger?����������
Intiger.valueof()
ע��:�ȼ������,д�����,Ȼ��ϵͳ��ʾ��������������㲹�����
-------------------------------------------------------------
�ص�:����������.
servlet���������÷���,�õ�����.
�����,���
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
��������:
1�õ��ֽ����ļ�ȴ�������ְ취,������һ��������,Ҳ������,���������б����Ǹ���,�ᱻ�����̳�����,����this,����ǰ����
2�ֽ������ͨ���������Եõ�ԭ����ĳ�Ա����,�����кü���������,Ҫ����,���õ�ֻ����ͨ����,�������˽�з���,ӦΪgetDeclareMethod()
3�÷���ʹ�÷���ִ�е�ʱ��Ӧ��,��������Ķ���,��д�����ֽ������.
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
   ����д����,���û�м�ס��
---------------------------------------------------
��Ա������д������û����ʾ��,�����ȴӷ�����д������ճ����ȥ
��: privary Iservice service=Class.forName("").instence;
----------------------------------------------------------------   
����:һֱ�Ҳ���cookie������"cookieAuto",�����Ҳ���ԭ��,��������,��д�����ض��������,�����,
ע��:д���ض������������������ִ��,����ӡ������.����cookie��ִ��
            //Ϊ�˺������ת�����ﳵ��׼��,�洢����session
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            response.sendRedirect("/success.jsp");
			
			if ("on".equals(rememberAuto)){
                System.out.println("�Զ���¼��ʶ��:"+rememberAuto);
                Cookie cookieAuto = CookieUtil.createCookie("cookieAuto", username+"#"+password, 24 * 60 * 60 * 7, request.getContextPath());
                response.addCookie(cookieAuto);
                
            }else {
                //cookie.setMaxAge(0);
            }

-----------------
��ȷд��:
if ("on".equals(rememberAuto)){
                System.out.println("�Զ���¼��ʶ��:"+rememberAuto);
                Cookie cookieAuto = CookieUtil.createCookie("cookieAuto", username+"#"+password, 24 * 60 * 60 * 7, request.getContextPath());
                response.addCookie(cookieAuto);
                
            }else {
                //cookie.setMaxAge(0);
            }

            //Ϊ�˺������ת�����ﳵ��׼��,�洢����session
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            response.sendRedirect("/success.jsp");
-------------------------------------------------------------------------
����:�Զ���¼ʱ���������������,���������˶���.
��ס��:cookie ֻ�ܴ洢string����,�����Ĳ���.���Բ��ܸ��ͻ��˴������int��
-----------------------------------------------------------------------
crtl f ����ͬ������
-------------------------------
ctrl ���
----------------
�������:
 1:ѧϰ���쳣������.��Ҫ�ٻع�,�����ٿ���
 2:д����:travle
 3:û��дresponse"text/html;UTF-8",��������Ҳ������.
 4:����߼��л�û���ú�,ҳ����ʾ�ʺſ϶���û�н������,�㻹�ڸ��ֲ���,��ס,���Ǵ���,�߼�Ҳ��һ�ۿ�����
 5;�ո�actionд��active,
 6:categoryServlet �����̳��� baseSerlet,�����㻹��������д�ķ���. 

 bug��ԭ��:1û�м�ס,����jQuery��������
		   2:��ר��
		   3:û��ϵͳ�ķ���
------------------------------------------
---------------------------------
����:ǰ��ID����ĵ���,Ӧ����#+ID+"�ո�"+"."+����
������������ô���
$.each(poplist,function (index, element) {
    $("#popularity.row").append("<div class=\"col-md-3\">\n" +
	"                                <a href=\"javascript:;\">\n" +
	"                                    <img src=\""+element.rimage+"\" alt=\"\">\n" +
	"                                    <div class=\"has_border\">\n" +
	"                                        <h3>\"+element.rname+\"</h3>\n" +
	"                                        <div class=\"price\">������<em>��</em><strong>889</strong><em>��</em></div>\n" +
	"                                    </div>\n" +
	"                                </a>\n" +
	"                            </div>")
})
------------------------------------------------

����������û�д���,����<List<Category>>������.ʲôԭ��?������������.����취,��������Ǹ���ɾ��,�ʺϵĻ��Զ�����

 
 /*import jdk.internal.org.objectweb.asm.TypeReference;*/
 import com.fasterxml.jackson.core.type.TypeReference;��������Ǹ�ɾ��,�Զ�����.
 
 List<Category> list = mapper.readValue(str,new TypeReference<List<Category>>() {});

 bug:1����ǰ�˳�����,���ö�,������֤û����,�Ǿ���ճ�����˵Ĵ���,������д,�䷽���ͱ���������.
     2���������ID�����ڲ�����Ҫ�ӿո�.
     3���ǵ�������Ϊ�������ظ����޶˱���.
     4�������Ҳ������,���ǵ�user��?
     5��Ҫ������ʲô�������,��û�����Ĵ���,����Ϊֹ�������д�������֪����
-------------------------------------------
����ü���Сʱ��::::::::���ַ���ƴ�Ӳ���,����ϰ,Ӱ��ܴ��ٶ�
 $.post("/travle01/route","action=findByPage&cid="+cid+"&currentPage="+currentPage,function () {

        },"json").

-------------------------------
ѧϰ�����ַ���
(ע:�ַ���ճ�����ַ������Զ�����,)
   $.each(poplist,function (index, element) {
	$("#popularity .row").append("<div class=\"col-md-3\">\n" +
		"                                <a href=\"javascript:;\">\n" +
		"                                    <img src=\""+element.rimage+"\" alt=\"\">\n" +
		"                                    <div class=\"has_border\">\n" +
		"                                        <h3>"+element.rname+"</h3>\n" +
		"                                        <div class=\"price\">������<em>��</em><strong>889</strong><em>��</em></div>\n" +
		"                                    </div>\n" +
		"                                </a>\n" +
		"                            </div>")
	})
----------------------------------------------------
		�ܽ�:���ַ�:�ȿ�����'${value}',Ҳ������#${vslue}
		javaBean������#{JavaBean��������}��:#{username}
		

<!--����getParameter.js-->
    <script src="js/getParameter.js"></script>

		�ӵ�ַ������õ��Ķ���Ϊ��,��ô��servlet��õ��ľ��ǿ��ַ���"",����null.
-------------------------------------------------------------------------------------------------
<select id="findUserById" parameterType="int" resultType="User">
       select * from user where id='${value}'
    </select>
---------------------------
<!--
        ����Ĳ�����JavaBean��ʱ��#{JavaBean��������}
    -->
    <insert id="addUser" parameterType="user">
      insert into user values (#{id},#{username},#{birthday},#{sex},#{address})
    </insert>
----------------------------
    <!--дһ��delete��ǩ����Ӧ�Ǹ�deleteUser-->
    <delete id="deleteUser" parameterType="int">
        <!--
            ����Ĳ����Ǽ��������ͣ���ô���Ǿ���#{���дһ���ַ�����ռλ��}
        -->
        delete from user where id=#{abc}
    </delete>
-------------------------------------------------------------------------------------------------
��װ����һ�������ʱ��,�����������ԭ����ֻ��������һ��,�������ְ취���:
1�����ñ������
 <select id="findUserInfoById" parameterType="int" resultMap="userInfo">
        select * from user where id=#{id}
     select id userId,username,address userAddress, sex userSex,birthday userBirthday from user where id=#{id}-->
    </select>
2.�����Զ����װһ�������
<!--
        �Զ���һ�������ӳ��resultMap
        id����������ӳ���Ψһ��ʾ��������������ǩ�����ӳ��
        type���Ǹý����ӳ���Ӧ��POJO���ȫ�޶���
    -->
    <resultMap id="userInfoMap" type="userInfo">
        <!--
           id�ӱ�ǩ������ӳ������
             column���Ա�ʾ��������ֶ�����property��ʾPOJO��������
        -->
        <id column="id" property="userId"></id>
        <!--
            result��ǩ������ӳ�������������ֶ�
        -->
        <result column="sex" property="userSex"></result>
        <result column="birthday" property="userBirthday"></result>
        <result column="address" property="userAddress"></result>
    </resultMap>
    <!--
        ��ѯ������ҷ�װ��UserInfo������
    -->
    <!--
        �����Զ���ӳ�����
    -->
    <select id="findUserInfoById" parameterType="int" resultMap="userInfoMap">
        select * from user where id=#{id}
        <!--select id userId,username,address userAddress, sex userSex,birthday userBirthday from user where id=#{id}-->
    </select>
------------------------------
��������:
	<typeAliases>
        <!--<typeAlias type="com.itheima.domain.User" alias="user"></typeAlias>-->
        <!--
            ��domain���е�������ͳһ���ñ���,ͨ����ɨ��
            ������ɶ?����������������������ִ�Сд
        -->
        <package name="com.itheima.domain"/>
    </typeAliases>
------------------------
1:mybitis�޷���������Ϊ�����д����dao��,������domain��,���������ǿ�������
2:���쿼�Զ��Ĺ���,�����ܲ���ԭ����:1ʵ����û��û��ȥʵ�ֽӿ�.
									2�����ļ�û���޸ĳ����ڵ���·��,���õ���ʦ���Ǹ�.
---------------------------
  <!-- ���α���ͬ������ -->
  <form action="#" method="post" onsubmit="javascript:return false;">
  <form action="#" method="post" onsubmit="javascript:return false">
----------------------------------------------------------------------------------
 �����д����,��������ʲô��˼/�Լ�дһ��
 String sql="SELECT * FROM tab_route r , tab_favorite f WHERE r.rid=f.rid  ORDER BY r.count desc LIMIT ?,?"
 --------------------------------------------------------------------------------------
 ����ر�֮��,��ִ�в�ѯ���,Ϊʲô��������???

 ��:��Ϊ�����ô�����Ƿ�ֹ�޸ĳ���,���ѯ��û��Ӱ�쵽���ݿ�,��Ȼ��Ӱ����.һ����סӦ�ó���
 ----------------------------------------------------------------------------------------------------------
myBatis����С����ԭ��:
	1:��dao�л��һ��һд��һ�Զ�,<associate> ��<colection>
	2:configuration����,�����Ǳ�ǩ˳��д����,mybatis����ϸ�ı�ǩ˳��.

���������ĸ�����:
		1:�������ļ���:  <setting name="cacheEnabled" value="true" />
		2:��ӳ���ļ���:  <cache />
		3:���漰��domain���ļ�,��Ҫʵ��serieal
		4:����ʹ�õ�sqlsessionʹ�û�����ر�sqlsassion.close
------------------------------
mabatis ��inrerface �ӿ���,��������һ������,�������Ƕ��ֵ,�����������,���Է�װ��map,list,����.

����д��: void addUser(int id, String username, Date date,String address,String sex);
��ȷд��;void addUser(User user);
------------------
   �������Ϳ���:@Select("select * from user where username like #{user.username}")*/
				������治����д�ٷֺ�,�ڴ�������ĵط�д.
  ���������Ϳ���:@Select("select * from user where username like '%${value}%'")
  -------------------------
  mybits����,�ܷ����һ���Ƿ���ֵ�����Լ���.������map,������list<user>

   @Select("select * from user where id=#{id}")
  Map<String,Object> findUserById(int id);
  ----------------
   @Select("select * from user where id=#{id}")
  list<user> findUserById(int id);
  ----------------------------------------------------------------------------------------
resource��Autowiredһ��,��ע�ⷽʽ��ʱ��,��ע��ʵ����ʹ��,ֻ�����Ǻ��������е�Javabean
-------------------------
autowired���ǲ����Զ�ƥ��,��Ҫ���� qualifier��ǩ.
/*@Autowired
    @Qualifier("accountDao")*/
------------------------------------
 * 1.Component  ����ʹ���������
 * 2.Service  ����ʹ����ҵ���
 * 3.Repository ����ʹ���ڳ־ò�
 * 4.Controller  ����ʹ����Web��
 *
 * ����Bean�ķ�Χ:ʹ��Scopeע��
 *
 * Bean���������ڣ�����ǵ������:
 * 1. �����������ص�ʱ�򴴽�����
 * 2. �����������ٵ�ʱ�����ٶ���
 *
 * ����Ƕ��������:
 * 1. ��Ҫʵ�������ʱ�򴴽�����
 * 2. û������ָ������ʱ�򣬾ͻᱻGC���ƻ���
 *
 * ͨ��@PostConstructע����ָ����ʼ������
 * ͨ��@PreDestroyע��ָ�����ٷ���
 *
 * ʹ��ע��ķ�ʽ��������ע��,����Ҫע��ı�������û��set����
 * 1.Autowired ,ֻ��ע��JavaBean����ע��JavaBean�����ǰ����:��JavaBean���������Spring�ĺ���������
 *       Autowiredע�����ע���ʱ�򣬻�����Զ�װ��:
 *       1.Ҫע������Ե�ʵ��������ں���������ֻ��һ���Ļ����ͽ����ʵ����������ע��
 *       2.���Ҫע������Ե�ʵ��������ں����������ж���Ļ����ͻ���ݱ�������ӦJavaBean��id����ע��
 *       3.���Ҫע������Ե�ʵ��������ں����������ж���ģ����ұ�����������һ��JavaBean��id������Ӧ�������
 *       ��ô���Ǿ͵������Qualifierע���value���ԣ�ָ��Ҫע����Ǹ�JavaBean��id
 * 2. Resourceע�⣬ֻ��ע��JavaBean����
 *
 * 3. Valueע�⣬ע�����������(�����������ͺ�String����)
 ------------------------------
 //ע�����������ļ�
 @PropertySource("classpath:druidconfig.properties")
 //��ɨ��
@ComponentScan("com.itheima")
public class BeanConfiguration {
	//�������������ļ���ʽ
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

	Ϊʲô����@org.junit.Test???
		��Ϊ���test����������test,������test,�ĳ�������.
	public class Test {
    @org.junit.Test
    public void test(){
        System.out.println("ʥ���ںܶ���");
        
    }
}
-------------------------
spring��,JDBCtemplete���Լ̳�jdbcdaosupport
	���ǲ�����ע�ⷽʽ��DataSource,��Ҫ�������ļ�����DataSource.
------------------------------
2019/7/24
	������Ҫ�ʹ������Ĳ���һ��,Ҫ��Ȼ����ת��,�ò���ֵ.
  public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds)  
	public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
	------------------
		�Լ�����������??(����������)
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
��ȷ�ķ���:
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
		��һ�ֱ�����:(������ת����map����)
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
	<!--���ü����ͼ����Ĺ�����ϵ-->
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
�������������@requestBody,ʱ��,������������:resController.
-----------------------------
		insert�����ʱ��,ͨ��<selectKey >��ǩ,���Խ�id��������,���ڵ���.
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

bug���˼·:
	1����˼·:�߼���Զ�ǶԵ�.��û��֪ʶ��ʱ��,�����߼�.
	2:�ȿ��������־.�жϳ���ط�.
	3:��debug��ϵ㿴����.
	4:��ȷ������ط�,�����Ҳ������������.(�ܿ����Ǹ�ʽ,��д,˳��,�ַ�,�����)ճ���Ե�,���滻�Լ���.
	5:�ų���:�ǵ����ǰ����ʦ�������:��֪����ǰ�˵�,�Ǿ��Ų������,��һɾ���ҵ������.
	6:����ע������Ƿ�����ͬ������������.
-------------------------------
	��ָ���쳣:�ǵ����߳����˿�ָ��,,Ҳ����service���ֿ�ָ��,�ص����ȥ��֤Ϊʲôservice��ָ����,���ǿ����user��.
	����User user=service.finById(i);
-----------------------
	RBAC (Role Base Access Controll)Ȩ��ģ������ģ��
	1�Լ����Ļ��ȴ�Ȩ�ޱ�Ͳ˵�������,��Ϊ����Ƿ��ʵ�ҳ��ѽ,Ҳ����Ȩ��,�ڴ�������·��,url
	2Ȼ����ǰ���ҵ����еķ���·��,�ռ�����,���쵼����ЩȨ�޶�����Щ��ɫ����.
	3����м���Լ���.
	------------------------------------------
	��ѧϰSpring������ʱ����

/*���������κ�����?
/**Ҳ������������ʲô����??

/**����˼�������ļ��м���������ļ���
/*�������ļ��У��������ļ���--
-------------------------------------------------
�����ò����,д��ע������,�������ļ��������ע��,����������ע�ⷽʽ.
@Autowired
     BCryptPasswordEncoder encoder;
---------------------
	   <bean id="securityUserService" class="com.itheima.security.SecurityUserServiceImpl">
        <property name="encoder" ref="encoder"/>
    </bean>
-------------------------
��ָ���쳣�����׽����,���Ϸǿ��жϾͿ�����,��������ʵ�,��˵������illgle staut�Ƿ�״̬�쳣.����ҵ�����쳣.

set���ϲ�����,��ô����??��ϰ//��Ϊ��ɫȨ�޶���Ψһ��,һ���˲���������ͬ�ֽ�ɫ.��Ҫȥ��,set����,��list����.
// �õ��û���ӵ�еĽ�ɫ��Ȩ��
        Set<Role> roles = user.getRoles();
---------------------------------
������鷳,���ܾ��ǻ�������û������,��ϸ�Ŀ�һ��,���һ��.
 ������Ȩ��������serviceû��дע��,������ô��δ���.
---------------------
https://www.cnblogs.com/kimi9py/p/5698738.html

1. ������interceptor�ǻ���Java�ķ�����Ƶģ���������Filter�ǻ��ں����ص�,ʵ�ֵ�filter�ӿ���doFilter�������ǻص�������
2. ������interceptor��������servlet������������Filter������servlet����,û��servlet�������޷����ص�doFilter������
3. ������interceptorֻ�ܶ�action���������ã���������Filter����ԶԼ������е�����������,Filter�Ĺ��˷�Χ��Interceptor��,Filter���˹���������ͨ��ͨ������Ա���ҳ�棬ͼƬ���ļ��ȵȡ�
4. ������interceptor���Է���action�����ġ�ֵջ��Ķ��󣬶�������Filter���ܷ��ʡ�
5. ��action�����������У�������interceptor���Զ�α����ã���������Filterֻ����������ʼ��ʱ������һ�Ρ�
6. ������interceptor���Ի�ȡIOC�����еĸ���bean����������Filter�Ͳ��С�����������ע��һ��service�����Ե���ҵ���߼�
----------------
���ݿ��������ò�ҪΪ��,���������
---------------
��web��,����Ӧ��������д?
2����һ�����ݵĽ�������ĸ�ʽ����,ʲôʱ����ʲô,date��delenda??

-------------------------------
���ssm��,
-------------------
@Service(interfaceClass = CheckGroupService.class)//???����˵�����ⲿ��Դ����
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired//������Դ����
    private CheckGroupDao checkGroupDao;
}
-------------------
ʹ��Feign��ʱ��,��������д���@PathVariable��ʽ�Ĳ���,��Ҫ��value����ȥָ����
-----------------
������Ŀ
1:ȫ���쳣��,���д��connom������,����û�����������ע������eureka����?
��:ֻҪ������쳣�����ڵİ�Ŀ¼��������Ŀ�İ�Ŀ¼һ��,���������Ŀ¼�¶����Լ���������.
��:�쳣��MyException ·��:connom������:com.changgou.goods.exception.MyException
   Ʒ��������BroundApplication ·��:bround������:com.changgou.goods.BroundApplication
��������Ϳ���ɨ�赽,����ע��������.
----------------------------------------------

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;
}
service��@@Autowired����categoryMapper����,ԭ����idea��Ϊ�Ҳ���categoryMapper ,Ҳ����û��ע��,��ʵ,����������
�Ѿ�ע����@MapperScan(basePackages = {"com.changgou.goods.dao"}),��Ϊ���ַ���,idea��ⲻ��������,��Ӱ������,������Ĭ��ֵfalse.
------------------------------
������
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.goods.dao"})
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class);
    }
}
--------------------
������es����ʱ��pojo��,�����������û�б�ע@Field(type = FieldType.Keyword),ϵͳĬ��Ϊtype����Ϊtext.
���µ���private String spec,�������������չʾ��ʱ��,����Ҫ����"spec.keyword"������keyword,��pojo���мȲ���Ҫ.
//���÷�������  ��Ʒ�Ĺ��
nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpecgroup").field("spec.keyword").size(100));

@Document(indexName = "skuinfo",type = "docs")
public class SkuInfo implements Serializable {

    //��Ʒid��ͬʱҲ����Ʒ���
    @Id
    private Long id;

    //SKU����
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    //��Ʒ�۸񣬵�λΪ��Ԫ
    @Field(type = FieldType.Double)
    private Long price;

    //��Ŀ����
    @Field(type = FieldType.Keyword)
    private String categoryName;

    //Ʒ������
    @Field(type = FieldType.Keyword)
    private String brandName;

    //���
    private String spec;
---------------------------------
controller��,�漰��requestBodyʱ,����require=false,���ǿ���Ϊ��,���ǿ��Բ���ֵ,Ĭ�ϲ�д���Ǳ����в���,Ĭ��Ϊtrue.
@PostMapping
    public Map search(@RequestBody(required = false) Map searchMap ){

        return  skuService.search(searchMap);
    }
------------------------
��Ⱥʱ��,�˿ںźü���,���ʵ�ʱ����ôд����?
ע��,@Autowired
    private LoadBalancerClient loadBalancerClient;
	ServiceInstance choose = loadBalancerClient.choose("user-auth");
	������������ó���Ŀ�����ƶ˿ں�,����̶��ĺ�׺ƴ�Ӿ����˲�ͬ�ķ���·��

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret, String grandType) {

        //1.����url (�������Ƶ�url)
        //���� : ΢���������spring.appplicationָ��������
        ServiceInstance choose = loadBalancerClient.choose("user-auth");
        String url =choose.getUri().toString()+"/oauth/token";
		
	}

---------------------------
BCryp����,��Դ��package org.springframework.security.crypto.bcrypt;
 @Test
    public void BCrypAddSecret(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String changgou = passwordEncoder.encode("changgou");
        System.out.println(changgou);
    }
-----------------------------------------------------------------------------------
ע����:feignע���ʱ��,��Ŀ¼ͳһ��,��д��com.changgou...,�����Ҳ���feign.�����浱��û��ͳһ��com����,������
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
Redis����
springboot�� RedisTemplte
//��ȡ���е�key
 Set keys = redisTemplate.boundHashOps("SeckillGoods_" + extName).keys();
//hash��ʽ��������
 redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).put(seckillGood.getId(),seckillGood);
 //������Ч��
 redisTemplate.expireAt(SystemConstants.SEC_KILL_GOODS_PREFIX + extName,DateUtil.addDateHour(startTime, 2));
  //��ȡ���filed�µ�key��ֵ
  redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + extName).value();
            




30���ӵ�5��
1����:for
2���:
3sql:
4Ӧ�ò�:pio ,��stream����.
5:���߳�
6Ljvm�Ͻ��
6:dbaOracle�ڴ����һ��

and��in
2��������ں���ȥ
3��������:���ÿ���ֶ�
















